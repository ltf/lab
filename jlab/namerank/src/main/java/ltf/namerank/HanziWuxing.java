package ltf.namerank;

import ltf.namerank.db.Hanzi;
import ltf.namerank.parser.IParser;
import ltf.namerank.parser.ParseUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;

import static ltf.namerank.PathUtils.getDefaultPath;
import static ltf.namerank.parser.ParseUtils.file2Str;

/**
 * @author ltf
 * @since 5/25/16, 10:41 PM
 */
public class HanziWuxing implements Runnable {

    @Override
    public void run() {
        //fetchFromWeb();
        processLocalFiles();
    }

    private void processLocalFiles() {
        IParser parser = new IParser() {
            @Override
            public boolean handle(String url, String content) {
                System.out.println(url);
                System.out.println(content);
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
