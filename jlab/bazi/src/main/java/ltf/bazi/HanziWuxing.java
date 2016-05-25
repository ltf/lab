package ltf.bazi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.List;

/**
 * @author ltf
 * @since 5/25/16, 10:41 PM
 */
public class HanziWuxing implements Runnable {

    HttpClient http = HttpClientBuilder.create().build();

    @Override
    public void run() {
        for (int i = 7777; i>0; i--) {
            try {
                String content = getContent(i);
                String fn = getDefaultPath() + i + ".html";
                System.out.println(content);
                saveToFile(fn, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToFile(String fn, String content) throws IOException{
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fn)), "utf8"));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    private String getContent(int id) throws IOException {
        HttpGet get = new HttpGet("http://wuxing.bm8.com.cn/wuxing/"+id+".html");
        HttpResponse response = http.execute(get);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, "gb2312"));
        String line, result = "";
        while ((line = reader.readLine()) != null) result += line + "\n";
        return result;
    }

    public static String getDefaultPath() {
        return Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }
}
