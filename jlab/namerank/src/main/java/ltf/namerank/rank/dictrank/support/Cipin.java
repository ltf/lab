package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * 词频数据
 *
 * @author ltf
 * @since 16/7/11, 下午4:42
 */
public class Cipin {
    private static Map<String, CipinItem> cipinMap;

    private static Set<String> ignoreSet = new HashSet<>();

    static {
        ignoreSet.add("一");
        ignoreSet.add("县界");
        ignoreSet.add("陷身");
        ignoreSet.add("贤哲");
        ignoreSet.add("笙歌");
        ignoreSet.add("石臼");
        ignoreSet.add("声腔");
        ignoreSet.add("树影");
        ignoreSet.add("晚点");
        ignoreSet.add("宇观之");
        ignoreSet.add("开鲁县");
        ignoreSet.add("小屯村");
        ignoreSet.add("何去何从");
        ignoreSet.add("种草");
        ignoreSet.add("多极化");
        ignoreSet.add("按需分配");
        ignoreSet.add("承办");
        ignoreSet.add("师生");
        ignoreSet.add("长篇小说");
        ignoreSet.add("的");
    }

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

    private Map<Integer, Integer> gj2xc = new HashMap<>();

    private Integer v(CipinItem item) {
        //return item.gj;
        int v;
        if (item.xc != null) v = item.xc + 1;
        else v = gj2xc(item);

        int delta = 0;
        if (item.bc != null) delta += item.bc;
        if (item.bg != null) delta += item.bg;

        v += delta / 1000;

        return v;
//        if (item.bg == null) return item.bc;
//        if (item.bc != null) return item.bg + item.bc;
//        return item.bg;
    }

    private Integer gj2xc(CipinItem item) {
        for (int i = item.gj; i <= 55987; i++) {
            if (gj2xc.containsKey(i)) {
                return gj2xc.get(i);
            }
        }
        return 1;
    }


    private static Map<String, Integer> unionCipinMap = new HashMap<>();

    public void test() {
        checkOrLoad();
        List<String> list = new ArrayList<>(cipinMap.size());
        int maxGj = 0;
        int maxUnion = 0;
        for (String k : cipinMap.keySet()) {
            CipinItem item = cipinMap.get(k);
//            if (item.bg == null && item.gj == null && item.xc == null && item.xj == null)
            if (item.gj == null && item.xc == null && item.xj == null) continue;
            if (ignoreSet.contains(k)) continue;
            if (item.gj != null && item.xc != null) {
                gj2xc.put(item.gj, item.xc);
                maxGj = maxGj < item.gj ? item.gj : maxGj;
            }
            list.add(k);
        }

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

        for (String k : list) {
            CipinItem item = cipinMap.get(k);
            int v = v(item);
            if (v > 600) continue;
            maxUnion = maxUnion < v ? v : maxUnion;
            unionCipinMap.put(k, v);
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            if (!unionCipinMap.containsKey(list.get(i))) {
                list.remove(i);
                continue;
            }
            CipinItem item = cipinMap.get(list.get(i));

            list.set(i, String.format("%6d %6d %6d %6d %6d %6d %s",
                    item.gj,
                    item.xj,
                    item.xc,
                    item.bc,
                    item.bg,
                    v(item),
                    list.get(i)
            ));
        }
        System.out.println(maxGj);
        System.out.println(maxUnion);
        Collections.reverse(list);


        try {
            lines2File(list, getRawHome() + "/CipinTest.txt");
            toJsData(unionCipinMap, "unionCipin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
