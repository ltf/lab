package ltf.namerank.dataprepare.nametest3rd;

import ltf.namerank.utils.LinesInFile;
import ltf.namerank.utils.UrlHtml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static ltf.namerank.utils.StrUtils.getRandomJianHan;
import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.getNamesHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 6/19/16, 5:25 PM
 */
public class NameTestName321 implements NameTest3rd {

    private static final String home = getRawHome() + "/name321";

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
        if (fetchNameRank("李" + givenName, true))
            System.out.println(String.format("succ: %s", givenName));
        else
            System.out.println(String.format("fail: %s", givenName));

        if (Math.random() > 0.5) {
            String gvRand = "" + getRandomJianHan(2);
            fetchNameRank("李" + gvRand, false);
            System.out.println(String.format("rand: %s", gvRand));
            if (Math.random() > 0.8) {
                gvRand = "" + getRandomJianHan(2);
                fetchNameRank("李" + gvRand, false);
                System.out.println(String.format("rand: %s", gvRand));
            }
        }
    }

    private boolean fetchNameRank(String name, boolean save) {
        try {
            String pd = String.format("xm=%s&dxfx=1", URLEncoder.encode(name, "gbk")) + "&input=%BF%AA%CA%BC%B2%E2%CB%E3";
            String url = "http://www.name321.net/xmdf.php";
            UrlHtml urlHtml = new UrlHtml(url).post(pd);
            if (save) urlHtml.toFile(home + "/" + name);
            return true;
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }
        return false;
    }

}
