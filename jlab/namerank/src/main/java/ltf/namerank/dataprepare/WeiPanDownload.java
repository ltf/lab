package ltf.namerank.dataprepare;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ltf.namerank.utils.FileUtils.file2Lines;
import static ltf.namerank.utils.FileUtils.lines2File;

/**
 * @author ltf
 * @since 16/6/16, 上午9:39
 */
public class WeiPanDownload {

    public void go() {
        try {
            //fetchAllUrls();

            downloadAllBooks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadAllBooks() throws IOException {
        List<String> urls = new ArrayList<>();
        file2Lines(urls, "/Users/f/downloads/wx/vdisk/bookurls.txt");
        for (String url : urls) {
            boolean succ = false;
            for (int i = 0; i <= 5; i++) {
                succ = downloadBook(url, "/Users/f/downloads/wx/vdisk/books");
                if (succ) break;
                System.out.println(String.format("retry: %d, %s", i, url));
            }
            if (succ)
                System.out.println(String.format("succ: %s", url));
            else
                System.out.println(String.format("fail: %s", url));
        }
    }

    private Pattern jsonPattern = Pattern.compile("fileDown\\.init\\((.{1,9999}?)\\);");

    private boolean downloadBook(String bookPageUrl, String saveDir) {
        try {
            HttpClient http = HttpClientBuilder.create().build();
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(10000).setConnectTimeout(10000)
                    .setSocketTimeout(10000).build();
            HttpGet get = new HttpGet(bookPageUrl);
            get.setConfig(config);

            HttpResponse response = null;
            response = http.execute(get);
            InputStream content = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content, "utf8"));
            String line, result = "";
            while ((line = reader.readLine()) != null) result += line + "\n";
            Matcher matcher = jsonPattern.matcher(result);

            String dataJson = "";
            if (matcher.find()) {
                dataJson = matcher.group(1);
            }

            //System.out.println(dataJson);
            DownInfo info = JSONObject.parseObject(dataJson, DownInfo.class);


            System.out.println(String.format("downloading %s ...", info.getName()));

            get = new HttpGet(info.getDownload_list()[0]);
            get.setConfig(config);
            response = http.execute(get);

            byte[] buf = new byte[1024 * 16];
            content = response.getEntity().getContent();
            OutputStream os = new FileOutputStream(new File(saveDir, info.getName()));
            int len = -1;
            while ((len = content.read(buf)) > 0) os.write(buf, 0, len);
            os.flush();
            os.close();
            return true;
        } catch (Throwable e) {
        }
        return false;
    }

    private static class DownInfo {
        private String[] download_list;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getDownload_list() {
            return download_list;
        }

        public void setDownload_list(String[] download_list) {
            this.download_list = download_list;
        }
    }

    private void fetchAllUrls() throws IOException {
        List<String> urls = new LinkedList<>();
        fetchList("1249925380", 1, 547, urls);
        System.out.println(urls.size());
        lines2File(urls, "/Users/f/downloads/wx/vdisk/bookurls.txt");
    }

    private Pattern urlPattern = Pattern.compile("sort_name_detail\"><a target=\"_blank\" href=\"(.{1,999}?)\" title=\"");

    private void fetchList(String uid, int pageFrom, int pageTo, Collection<String> list) {
        for (int pg = pageFrom; pg <= pageTo; pg++) {
            String content = "";
            try {
                content = getContent(uid, pg);
            } catch (IOException e) {
                System.out.println(String.format("get page %d failed", pg));
            }

            Matcher matcher = urlPattern.matcher(content);
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
        }
    }

    private String getContent(String uid, int pid) throws IOException {
        HttpClient http = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(String.format("http://vdisk.weibo.com/u/%s?page=%d", uid, pid));
        HttpResponse response = http.execute(get);
        InputStream content = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, "utf8"));
        String line, result = "";
        while ((line = reader.readLine()) != null) result += line + "\n";
        return result;
    }
}
