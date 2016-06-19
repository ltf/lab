package ltf.namerank.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;

import static ltf.namerank.utils.FileUtils.str2File;

/**
 * @author ltf
 * @since 6/19/16, 9:53 PM
 */
public class HttpUtils {


    /**
     * get html from url, with char set GB18030(super set of gbk)
     */
    public static String getHtml(String url) throws IOException {
        return getHtml(url, "GB18030");
    }

    /**
     * get html from url
     */
    public static String getHtml(String url, String charset) throws IOException {
        HttpClient http = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        HttpResponse response = http.execute(get);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, charset));
        StringBuilder sb = new StringBuilder();
        String line, result = "";
        while ((line = reader.readLine()) != null) {
            if (sb.length() > 0) sb.append("\n");
            sb.append(line);
        }
        return result;
    }

    /**
     * get html from url with GB18030, then save to file with utf8
     */
    public static void html2File(String url, String fn) throws IOException {
        str2File(getHtml(url), fn);
    }

    /**
     * get html from url with GB18030, then save to file with utf8
     */
    public static void html2File(String url, File file) throws IOException {
        str2File(getHtml(url), file);
    }

    /**
     * get html from url with charset, then save to file with utf8
     */
    public static void html2File(String url, String charset, File file) throws IOException {
        str2File(getHtml(url, charset), file);
    }

    /**
     * get html from url with charset, then save to file with utf8
     */
    public static void html2File(String url, String charset, String fn) throws IOException {
        str2File(getHtml(url, charset), fn);
    }

}
