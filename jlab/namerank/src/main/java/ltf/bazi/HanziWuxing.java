package ltf.bazi;

import ltf.bazi.db.Hanzi;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.List;

import static ltf.bazi.PathUtils.getDefaultPath;

/**
 * @author ltf
 * @since 5/25/16, 10:41 PM
 */
public class HanziWuxing implements Runnable {

    HttpClient http = HttpClientBuilder.create().build();

    @Override
    public void run() {

    }

    private void processLocalFiles() {
        File dir = new File(PathUtils.getProjectPath() + "build/libs/wuxhtm/");
        if (dir.isDirectory() && dir.exists()) {
            for (File f : dir.listFiles()) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8"));
                    String s = "", line;
                    while ((line = reader.readLine()) != null) s += line + "\n";

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Hanzi parseHtml(String html){

    }

    private void fetchFromWeb() {
        for (int i = 7777; i > 0; i--) {
            try {
                String content = getContent(i);
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
        HttpGet get = new HttpGet("http://wuxing.bm8.com.cn/wuxing/" + id + ".html");
        HttpResponse response = http.execute(get);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, "gb2312"));
        String line, result = "";
        while ((line = reader.readLine()) != null) result += line + "\n";
        return result;
    }


}
