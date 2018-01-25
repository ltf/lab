package f;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author ltf
 * @since 2018/1/10, 上午10:39
 */
public class Runner {

    public static void main(String[] args) {

        JSONObject jo = new JSONObject(
                "{\"game_id\":\"1515411158002163\",\"question\":{\"countdown\":10,\"desc\":\"源于基督教教义的千福年又名千禧年，下面哪位明星出生于千禧年？\",\"id\":455,\"options\":[{\"content\":\"关晓彤\",\"id\":\"A\"},{\"content\":\"易烊千玺\",\"id\":\"B\"},{\"content\":\"吴磊\",\"id\":\"C\"}],\"serial_num\":9,\"survivor_num\":109149,\"token\":\"8e66925e-f469-11e7-b696-00163e08a312\",\"total\":12,\"type\":1,\"window_show_time\":8},\"tp\":\"trivia_game.question\"}");

        processZSCR(jo);
    }


    public static void processZSCR(JSONObject pJo) {
        try {

            if (pJo != null && "trivia_game.question".equals(pJo.get("tp"))) {
                JSONObject qJo = pJo.getJSONObject("question");
//                Log.i("fylog", String.format("p1: %s, p2: %s",
//                        p1, qJo != null ? qJo.toString() : ""));
                anserQuestion(qJo);
                Log.i("fylog", "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                Log.i("fylog", "--------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
        }
    }


    private static String mLastQuestion;

    public static void anserQuestion(JSONObject qJo) {
        try {
            String qus = qJo.getString("desc");
            if (qus == null || qus.equals(mLastQuestion)) return;
            mLastQuestion = qus;

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> optionsDisplay = new ArrayList<>();
            JSONArray ja = qJo.getJSONArray("options");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject oJo = (JSONObject) ja.get(i);
                options.add(oJo.getString("content"));
                optionsDisplay.add(oJo.getString("id") + " - " + oJo.getString("content"));
            }


            new HttpThread("https://zhidao.baidu.com/search?word=" + URLEncoder.encode(qus, "UTF-8"),
                    "gbk",
                    "ZD",
                    qus,
                    options,
                    optionsDisplay).start();

            new HttpThread("https://www.baidu.com/s?wd=" + URLEncoder.encode(qus, "UTF-8"),
                    "UTF-8",
                    "BD",
                    qus,
                    options,
                    optionsDisplay).start();
        } catch (Exception e) {
        }
    }

    private static class HttpThread extends Thread {
        private String mUrl;
        private String mCharset;
        private String mTag;
        private String mQuestion;
        private ArrayList<String> mOptions;
        private ArrayList<String> mOptionsDisplay;
        private final long mStart = System.currentTimeMillis();

        public HttpThread(String mUrl,
                          String mCharset,
                          String mTag,
                          String mQuestion, ArrayList<String> mOptions, ArrayList<String> mOptionsDisplay) {
            this.mUrl = mUrl;
            this.mTag = mTag;
            this.mCharset = mCharset;
            this.mQuestion = mQuestion;
            this.mOptions = mOptions;
            this.mOptionsDisplay = mOptionsDisplay;
            setPriority(MAX_PRIORITY);
        }

        @Override
        public void run() {
            URL url;
            try {
                url = new URL(mUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setSSLSocketFactory(getSSLContext(context).getSocketFactory());
                conn.setConnectTimeout(60000);//5
                conn.setReadTimeout(60000);
                conn.setDoOutput(true);// 设置允许输出
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");

//                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; HTC_D820u Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
//                conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
//                conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//                conn.setRequestProperty("Charset", "gbk");
                conn.connect();

                /* 服务器返回的响应码 */
                int code = conn.getResponseCode();
                if (code == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), mCharset));
                    String retData = null;
                    String responseData = "";
                    while ((retData = in.readLine()) != null) {
                        responseData += retData;
                    }
                    in.close();

                    String trimedResp = getCenterStr(responseData, "<div class=\"list-inner\">","<div class=\"list-footer\">");
                    if (trimedResp != null && trimedResp.length() > 100) responseData = trimedResp;
                    trimedResp = getCenterStr(responseData, "<div id=\"content_left\">","<div id=\"rs\">");
                    if (trimedResp != null && trimedResp.length() > 100) responseData = trimedResp;

                    processResponse(responseData);
                }
            } catch (Exception e) {
                Log.w("ee", e.toString());
            }
        }

        private void processResponse(String response) {
            String bestOpt = "";
            int bestCount = 0;
            int bestIndex = 0;
            String countLine = "";
            for (int i = 0; i < mOptions.size(); i++) {
                int c = count(response, mOptions.get(i));
                countLine += String.format("\t %s \t\t\t: %d \n", mOptionsDisplay.get(i),
                        c);
                if (c > bestCount) {
                    bestCount = c;
                    bestIndex = i;
                }
            }
            Log.i("ss", response);

            Log.w("fylog", String.format("\n[ %s %d ] :----> %s \n %s \n\n\n",
                    mTag,
                    (System.currentTimeMillis() - mStart) / 100,
                    bestCount > 0 ? mOptionsDisplay.get(bestIndex) : " no suggestion",
                    countLine
            ));

        }

        private int count(String content, String word) {
            int count = 0;
            int offset = 0;
            while ((offset = content.indexOf(word, offset)) >= 0) {
                count++;
                offset++;
            }
            return count;
        }

        private String getCenterStr(String org, String begin, String end) {
            String r = null;
            if (org != null) {
                int b = org.indexOf(begin);
                int e = org.indexOf(end, b + 1);
                if (b >= 0 && e >= 0 && e >= b) {
                    r = org.substring(b, e);
                }
            }
            return r;
        }
    }
}


