package ltf.namerank.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;

import static ltf.namerank.utils.FileUtils.str2File;

/**
 * @author ltf
 * @since 6/19/16, 10:30 PM
 */
public class UrlHtml {
    private String url;
    private String charset = "GB18030";
    private String contentHtml;

    public UrlHtml(String url) {
        this.url = url;
    }

    /**
     * set charset, else use GB18030
     */
    public UrlHtml charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * get html with GET methods
     */
    public UrlHtml get() throws IOException {
        HttpGet get = new HttpGet(url);
        contentHtml = getHtml(get);
        return this;
    }

    /**
     * get html with POST methods
     */
    public UrlHtml get(Iterable<HttpEntity> entities) throws IOException {
        HttpPost post = new HttpPost(url);
        entities.forEach(post::setEntity);
        contentHtml = getHtml(post);
        return this;
    }

    /**
     * get html from url
     */
    protected String getHtml(HttpUriRequest req) throws IOException {
        HttpClient http = HttpClientBuilder.create().build();
        HttpResponse response = http.execute(req);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, charset));
        StringBuilder sb = new StringBuilder();
        String line, html = "";
        while ((line = reader.readLine()) != null) {
            if (sb.length() > 0) sb.append("\n");
            sb.append(line);
        }
        return html;
    }

    /**
     * content html to string
     */
    public String toStr() {
        return contentHtml;
    }

    public void toFile(String fn) throws IOException {
        str2File(contentHtml, fn);
    }

    public void toFile(File file) throws IOException {
        str2File(contentHtml, file);
    }

}
