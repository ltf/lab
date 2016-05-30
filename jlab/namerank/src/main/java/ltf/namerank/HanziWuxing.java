package ltf.namerank;

import ltf.namerank.db.Hanzi;
import ltf.namerank.parser.IParser;
import ltf.namerank.parser.ParseUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ltf.namerank.PathUtils.getDefaultPath;
import static ltf.namerank.parser.ParseUtils.file2Str;

/**
 * @author ltf
 * @since 5/25/16, 10:41 PM
 */
public class HanziWuxing implements Runnable {

    private static final String patternStr = "<tr><td><table[\\s\\S]{1,999}?\"><b>(.{1,9}?)</b>[\\s\\S]{1,99}?<td>拼音：(.{1,99}?)</td>[\\s\\S]{1,99}?<td>繁体：(.{1,9}?)</td>[\\s\\S]{1,99}?<td>笔画：(.{1,9}?)</td>[\\s\\S]{1,99}?</strong>：(.{1,9}?)</td>[\\s\\S]{1,99}?<td>吉凶：(.{1,9}?)</td>[\\s\\S]{1,399}?</div><div class=\"r\">(.{1,9999}?)</div></div></td>";
    private final Pattern pattern = Pattern.compile(patternStr);
    private static final String optPatternStr = "\"#FFFFFF\">(.{1,99}?),<a href=\"http://www\\.bm8\\.com\\.cn/Tool/qiming/";
    private final Pattern optPattern = Pattern.compile(optPatternStr);
    private static final String idPatternStr = "namerank\\.jar(.{1,9}?)\\.html";
    private final Pattern idPattern = Pattern.compile(idPatternStr);

    @Override
    public void run() {
        //fetchFromWeb();
        processLocalFiles();
    }

    private Hanzi parse(final String url, final String content) {
        Hanzi result = null;
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            result = new Hanzi();
            result.setKword(matcher.group(1));
            result.setSpell(matcher.group(2));
            result.setTraditional(matcher.group(3));
            result.setStrokes(matcher.group(4));
            result.setWuxing(matcher.group(5));
            result.setLuckyornot(matcher.group(6));
            result.setInfo(matcher.group(7));

            matcher = idPattern.matcher(url);
            if (matcher.find()) result.setHtmid(matcher.group(1));
        }
        matcher = optPattern.matcher(content);
        if (result != null && matcher.find()) {
            result.setComment(matcher.group(1));
        }
        return result;
    }

    private void processLocalFiles() {
        IParser parser = new IParser() {
            @Override
            public boolean handle(String url, String content) {
                Hanzi zi = parse(url, content);
                if (zi != null)
                    System.out.println(zi.toString());
                else
                    System.out.println("error : " + url);
                return false;
            }
        };

//        try {
//            parser.handle("/f/flab/jlab/namerank/build/libs/wuxhtm/bazi.jar77.html", file2Str("/f/flab/jlab/namerank/build/libs/wuxhtm/bazi.jar77.html"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ParseUtils.processFilesInDir(PathUtils.getProjectPath() + "build/libs/wuxhtm/", parser);
    }

    private void fetchFromWeb() {
        for (int i = 7777; i > 0; i--) {
            try {
                String content = "";
                int t = 1;
                while (true) {
                    try {
                        System.out.println("wait " + t * 5 + " s");
                        Thread.sleep(t * 5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    content = getContent(i);
                    if (!content.contains("404.safedog.cn/sitedog_stat.html") &&
                            !content.contains("setTimeout(\"JumpSelf()\",700)")) break;
                    t++;
                }

                String fn = getDefaultPath() + i + ".html";
                System.out.println("success: " + i);
                saveToFile(fn, content);
            } catch (IOException e) {
                System.out.println("failed: " + i);
                e.printStackTrace();
            }
        }
    }

    private void saveToFile(String fn, String content) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fn)), "utf8"));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    private String getContent(int id) throws IOException {
        HttpClient http = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://wuxing.bm8.com.cn/wuxing/" + id + ".html");
        HttpResponse response = http.execute(get);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, "gb2312"));
        String line, result = "";
        while ((line = reader.readLine()) != null) result += line + "\n";
        return result;
    }


}
