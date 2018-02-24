package f.test;

import f.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ltf
 * @since 2018/1/25, 上午10:40
 */
public class Test1 {
    public static void main(String[] args) {
        String mUrl  = "http://iw.22web.org/dblog.php?i=39.975761_116.497292";
        URL url;
        try {
            url = new URL(mUrl);
            Log.w("fylog",mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);//5
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);// 设置允许输出
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Cookie","__test=89fe48b1e30839ced99873d928f00a50");
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"));
                String retData = null;
                String responseData = "";
                while ((retData = in.readLine()) != null) {
                    responseData += retData;
                }
                Log.w("fylog", "response: "+responseData);
                in.close();
            }
        } catch (Exception e) {
            Log.e("fylog", e.toString());
        }
    }

}
