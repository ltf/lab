package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.FileUtils.lines2File;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/7/11, 下午4:42
 */
public class Cipin {
    private static Map<String, CipinItem> cipinMap;

    private void checkOrLoad() {
        if (cipinMap == null) {
            try {
                cipinMap = (HashMap<String, CipinItem>) JSON.parseObject(file2Str(getJsonHome() + "/cipin.json"),
                        new TypeReference<HashMap<String, CipinItem>>() {
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Integer v(CipinItem item) {
        return item.bg;
    }

    public void test() {
        checkOrLoad();
        List<String> list = new ArrayList<>(cipinMap.size());
        for (String k : cipinMap.keySet()) {
//            CipinItem item = cipinMap.get(k);
//            if (item.bg == null && item.gj == null && item.xc == null && item.xj == null)
                list.add(k);
        }
        String k = "bc";
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        list.sort((a, b) -> {
            CipinItem ia = cipinMap.get(a);
            CipinItem ib = cipinMap.get(b);
            if (v(ia) == null) {
                if (v(ib) == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                if (v(ib) == null) {
                    return 1;
                } else {
                    return v(ia) - v(ib);
                }
            }
        });

        for (int i = 0; i < list.size(); i++) {
            CipinItem item = cipinMap.get(list.get(i));

            list.set(i, String.format("%6d %6d %6d %6d %6d %s",
                    item.gj,
                    item.xj,
                    item.xc,
                    item.bc,
                    item.bg,
                    list.get(i)
            ));
        }
        Collections.reverse(list);
        try {
            lines2File(list, getRawHome() + "/CipinTest.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
