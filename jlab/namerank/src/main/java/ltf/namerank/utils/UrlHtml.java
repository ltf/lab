package ltf.namerank.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.str2File;

/**
 * @author ltf
 * @since 6/19/16, 10:30 PM
 */
public class UrlHtml {
    private String url;
    private String charset = "GB18030";
    private String refer = null;
    private String contentType = null;
    private String contentHtml;

    public UrlHtml(String url) {
        this.url = url;
    }

    /**
     * set charset to decode response, default use GB18030; call this before GET/POST
     */
    public UrlHtml charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * set refer; call this before GET/POST
     */
    public UrlHtml refer(String refer) {
        this.refer = refer;
        return this;
    }

    /**
     * set contentType; default is "application/x-www-form-urlencoded" for POST;
     * call this before GET/POST
     */
    public UrlHtml contentType(String contentType) {
        this.contentType = contentType;
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
    public UrlHtml post(Iterable<HttpEntity> entities) throws IOException {
        contentType = "application/x-www-form-urlencoded";
        HttpPost post = new HttpPost(url);
        entities.forEach(post::setEntity);
        contentHtml = getHtml(post);
        return this;
    }

    /**
     * get html with POST methods
     */
    public UrlHtml post(String postData) throws IOException {
        List<HttpEntity> entities = new ArrayList<>(2);
        entities.add(new StringEntity(postData));
        return post(entities);
    }

    /**
     * get html from url
     */
    protected String getHtml(HttpUriRequest req) throws IOException {
        HttpClient http = HttpClientBuilder.create().build();
        if (refer != null) req.addHeader(HttpHeaders.REFERER, refer);
        if (contentType != null) req.addHeader(HttpHeaders.CONTENT_TYPE, contentType);
        HttpResponse response = http.execute(req);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, charset));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (sb.length() > 0) sb.append("\n");
            sb.append(line);
        }
        return sb.toString();
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
