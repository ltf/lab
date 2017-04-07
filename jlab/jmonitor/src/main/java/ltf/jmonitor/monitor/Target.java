package ltf.jmonitor.monitor;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ltf
 * @since 4/7/17, 11:49 PM
 */
public class Target {

    static private Logger log = LoggerFactory.getLogger(Target.class);

    private final String name;
    private final String url;
    private String lastContent = null;

    public Target(String name, String url) {
        this.name = name;
        this.url = url;
    }

    boolean isChanged() {
        boolean result = false;
        try {
            String content = getContent(url);
            if (lastContent != null && !lastContent.equals(content)) {
                result = true;
            } else {
                if (lastContent == null)
                    log.info(name + " inited");
                else
                    log.info(name + " not changed");
            }
            lastContent = content;
        } catch (Exception e) {
            log.error("failed when check " + name, e);
        }
        return result;
    }

    private String getContent(String url) throws IOException {
        HttpClient http = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        HttpResponse response = http.execute(get);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, "utf8"));
        String line, result = "";
        while ((line = reader.readLine()) != null) result += line + "\n";
        return result;
    }
}
