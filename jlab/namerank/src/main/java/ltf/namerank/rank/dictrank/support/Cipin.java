package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ltf.namerank.utils.FileUtils.fromJsData;

/**
 * @author ltf
 * @since 16/7/13, 下午1:07
 */
public class Cipin {
    private static Map<String, Integer> unionCipinMap;

    private static void checkOrLoad() {
        if (unionCipinMap == null) {
            try {
                unionCipinMap = fromJsData("unionCipin", new TypeReference<HashMap<String, Integer>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int get(String s) {
        checkOrLoad();
        Integer r = unionCipinMap.get(s);
        if (r != null) return r + 1;
        return 1;
    }
}
