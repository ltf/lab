package ltf.namerank.dataprepare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static ltf.namerank.utils.FileUtils.fromLinesData;
import static ltf.namerank.utils.FileUtils.toLinesData;

/**
 * @author ltf
 * @since 16/7/12, 下午1:42
 */
public class WuxingDataCollect {


    private void cleanCharWuxingdata() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        fromLinesData("wuxing_huo", list);
        list.forEach(l -> {
            for (char c : l.toCharArray()) {
                if ("\r".equals(c) ||"\t".equals(c) || " ".equals(c)) continue;
                set.add(String.valueOf(c));
            }
        });
        list.clear();
        list.addAll(set);
        toLinesData(list, "wuxing_huo2");
    }

    public static void main(String[] args) throws IOException {
        //new WuxingDataCollect().cleanCharWuxingdata();
    }
}
