package ltf.namerank.lab.nametest3rd;

import ltf.namerank.utils.LinesInFile;
import ltf.namerank.utils.UrlHtml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static ltf.namerank.utils.CharUtils.getRandomHanzi;
import static ltf.namerank.utils.CharUtils.getRandomJianHan;
import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.getNamesHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 6/19/16, 5:25 PM
 */
public class NameTestName321 implements NameTest3rd {

    private static final String home = getRawHome() + "/buyiju";

    public void go() {

        try {
            fetchNamesRank();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testHanziRange() throws IOException {
        String allChars = file2Str(getNamesHome() + "/givenNames.txt").replaceAll("\n", "");
        int min = 0x9fa5;
        int max = 0x4e00;

        for (char c : allChars.toCharArray()) {
            if (c < min) min = c;
            if (c > max) max = c;
        }

        System.out.println(min);
        System.out.println(max);
    }


    private void fetchNamesRank() throws IOException {
        mkDirs(home);
        new LinesInFile(getNamesHome() + "/givenNames.txt").each(this::fetchNameRankWithConfusion);
    }

    private void fetchNameRankWithConfusion(String givenName) {
        if (exists(home + "/李" + givenName)) return;
        if (fetchNameRank(givenName, true))
            System.out.println(String.format("succ: %s", givenName));
        else
            System.out.println(String.format("fail: %s", givenName));

        if (Math.random() > 0.5) {
            String gvRand = "" + getRandomJianHan(2);
            fetchNameRank(gvRand, false);
            System.out.println(String.format("rand: %s", gvRand));
            if (Math.random() > 0.8) {
                gvRand = "" + getRandomJianHan(2);
                fetchNameRank(gvRand, false);
                System.out.println(String.format("rand: %s", gvRand));
            }
        }
    }

    private boolean fetchNameRank(String name, boolean save) {
        try {
            String pd = "xs=%E6%9D%8E&mz="+URLEncoder.encode(name, "utf8")+"&btnAdd=%E7%AB%8B%E5%88%BB%E6%B5%8B%E7%AE%97";
            String url = "http://xmcs.buyiju.com/dafen.php";
            UrlHtml urlHtml = new UrlHtml(url).charset("utf8").post(pd);
            if (save) urlHtml.toFile(home + "/李" + name);
            return true;
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }
        return false;
    }

}
