package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
    private static Map<String, JSONObject> cipinMap;

    private void checkOrLoad() {
        if (cipinMap == null) {
            try {
                cipinMap = (HashMap<String, JSONObject>) JSON.parseObject(file2Str(getJsonHome() + "/cipin.json"), HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void test() {
        checkOrLoad();
        List<String> list = new ArrayList<>(cipinMap.size());
        for (String k : cipinMap.keySet()) {
            list.add(k);
        }
        String k = "bc";
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        list.sort((a, b) -> {
            JSONObject ia = cipinMap.get(a);
            JSONObject ib = cipinMap.get(b);
            if (ia.getInteger(k) == null) {
                if (ib.getInteger(k) == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                if (ib.getInteger(k) == null) {
                    return 1;
                } else {
                    return ia.getInteger(k) - ib.getInteger(k);
                }
            }
        });

        for (int i = 0; i < list.size(); i++) {
            JSONObject item = cipinMap.get(list.get(i));

            list.set(i, String.format("%6d %6d %6d %6d %6d %s",
                    item.getInteger("gj"),
                    item.getInteger("xj"),
                    item.getInteger("xc"),
                    item.getInteger("bc"),
                    item.getInteger("bg"),
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
