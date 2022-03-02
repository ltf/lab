package ltf.namerank.dataprepare;

import com.alibaba.fastjson.TypeReference;
import ltf.namerank.entity.DictItem_Bm8;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ltf.namerank.utils.FileUtils.*;

/**
 * @author ltf
 * @since 16/7/12, 下午1:42
 */
public class PipinameDataCollect {
    private static final boolean ALLOW_NORMAL_CASES = true;
    private static final String PIPI_WUGE_STROKES_SHORT = "pipiname_wuge_char2stroke";
    /**
     * ARTICLE1:   1,3,5,6,7,8,11,13,15,16,17,18,21,23,24,25,29,31,32,33,35,37,39,41,45,47,48,52,55,57,61,63,65,67,68,81
     * ARTICLE2:   1,3,5,6,7,  11,13,15,16,      21,23,24,   29,31,32,33,35,37,   41,45,47,48,52,   57,61,63,65,67,68,81
     * PP BEST:    1,3,5,6,7,8,11,13,15,16,17,18,21,23,24,25,29,31,32,33,35,37,39,41,45,47,48,52,   57,61,63,65,67,68,81
     * <p>
     * ALL BEST:   1,3,5,      11,13,15,16,      21,   24,      31,32,33,35,37,39,41,45,47,48,52,   57,61,63,65,67,68,81
     * (remove normal for httpcn)
     */
    private static final int[] ALL_BEST_NUMS = {1, 3, 5, 11, 13, 15, 16, 21, 24, 31, 32, 33, 35, 37, 39, 41, 45, 47, 48, 52, 57, 61, 63, 65, 67, 68, 81};
    //private static final int[] BEST_NUMS = {1, 3, 5, 7, 8, 11, 13, 15, 16, 18, 21, 23, 24, 25, 31, 32, 33, 35, 37, 39, 41, 45, 47, 48, 52, 57, 61, 63, 65, 67, 68, 81};

    private static final int[] GOOD_NUMS = {1, 3, 5, 6, 7, 8, 11, 13, 15, 16, 18, 21, 23, 24, 25, 29, 31, 32, 33, 35, 37, 39, 41, 45, 47, 48, 52, 57, 61, 63, 65, 67, 68, 81};

    /**
     * 6,17,26,27,29,30,38,49,51,55,58,71,73
     * 27,      38,42,55,58,71,72,73,77
     * 8,17,18,25,27,30,36,38,42,49,50,51,55,58,71,72,73,75,77,78
     */
    private static final int[] NORMAL_NUMS = {8, 17, 18, 25, 27, 30, 36, 38, 42, 49, 50, 51, 55, 58, 71, 72, 73, 75, 77, 78};


    private static final String[] WUXING = {
            "水",
            "木", "木",
            "火", "火",
            "土", "土",
            "金", "金",
            "水"};

    private static final String[] SANCAI_GOOD = {
            "木木木", "木木火", "木木土", "木火木", "木火土", "木水木", "木水金", "木水水", "火木木", "火木火",
            "火木土", "火火木", "火火土", "火土火", "火土土", "火土金", "土火木", "土火火", "土火土", "土土火",
            "土土土", "土土金", "土金土", "土金金", "土金水", "金土火", "金土土", "金土金", "金金土", "金水木",
            "金水金", "水木木", "水木火", "水木土", "水木水", "水金土", "水金水", "水水木", "水水金"};

    private static final String[] SANCAI_NORMAL = {"木火火", "木土火", "火木水", "火火火", "土木木", "土木火", "土土木", "金土木", "金金金", "金金水",
            "金水水", "水火木", "水土火", "水土土", "水土金", "水金金", "水水水"};

    private static final StrokePicker picker = new StrokePicker();


    private void saveWugeStrokeFromPipiname() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        fromLinesData("pipiname_wuge_stroke", lines);
        Map<String, Integer> c2sMap = lines.stream().map(l -> l.split(" ")).collect(Collectors.toMap(a -> a[0], a -> Integer.parseInt(a[1])));
        toJsData(c2sMap, PIPI_WUGE_STROKES_SHORT);
    }

    private void checkData() throws IOException {
        HashMap<String, Integer> ppC2s = fromJsData(PIPI_WUGE_STROKES_SHORT, new TypeReference<HashMap<String, Integer>>() {
        });

        HashMap<String, DictItem_Bm8> dict = fromJsData("dictInOne", new TypeReference<HashMap<String, DictItem_Bm8>>() {
        });

        HashMap<Character, Integer> xmx_bihua = fromJsData("xingmingxue_pdf_bihua", new TypeReference<HashMap<Character, Integer>>() {

        });

        HashMap<Character, WugeDataCollect.WuxingBihua> xmx_wuxing = fromJsData("xingmingxue_pdf_wuxing", new TypeReference<HashMap<Character, WugeDataCollect.WuxingBihua>>() {

        });

//        ArrayList<String> bihuaAlone = new ArrayList<>();
//        ArrayList<String> bihuaDiff = new ArrayList<>();
//        xmx_bihua.forEach((k,v)-> {
//            if (!ppC2s.containsKey(k.toString())) {
//                bihuaAlone.add(k.toString());
//                System.out.println(k);
//            } else if (!ppC2s.get(k.toString()).equals(v)) {
//                bihuaDiff.add(k.toString());
//            }
//        });
//        toLinesData(bihuaAlone, "bihuaAlone");
//        toLinesData(bihuaDiff, "bihuaDiff");

        ArrayList<String> diff = new ArrayList<>();
        ppC2s.forEach((k, v) -> {
            Character c = k.charAt(0);
            if (dict.containsKey(k) && (!v.equals(dict.get(k).getStrokes()) || "凶".equals(dict.get(k).getLuckyornot()))) {
                diff.add(k);
            } else if (xmx_bihua.containsKey(c) && !v.equals(xmx_bihua.get(c))) {
                diff.add(k);
            } else if (xmx_wuxing.containsKey(c) && !v.equals(xmx_wuxing.get(c).bh)) {
                diff.add(k);
            }
        });
        diff.forEach(ppC2s::remove);

        toJsData(ppC2s, "selected_wuge_char2stroke");

        HashMap<Integer, List<String>> ppS2c = new HashMap<>();
        ppC2s.forEach((k, v) -> {
            ppS2c.putIfAbsent(v, new ArrayList<>());
            ppS2c.get(v).add(k);
        });
        toJsData(ppS2c, "selected_wuge_stroke2char");


        //toLinesData(diff, "diff");
    }

    private void cleanSelectedCharacters() throws IOException {
        HashMap<String, Integer> ppC2s = fromJsData("selected_wuge_char2stroke", new TypeReference<HashMap<String, Integer>>() {
        });

        ArrayList<String> cs = new ArrayList<>();
        fromLinesData("namecs", cs);
        HashSet<String> csset = new HashSet<>();
        cs.forEach(line -> {
            for (char c : line.toCharArray()) {
                csset.add(String.valueOf(c));
            }
        });
        cs.clear();
        ppC2s.forEach((k, v) -> {
            if (!csset.contains(k))
                cs.add(k);
        });

        cs.forEach(ppC2s::remove);

        toJsData(ppC2s, "selected_wuge_char2stroke");

        HashMap<Integer, List<String>> ppS2c = new HashMap<>();
        ppC2s.forEach((k, v) -> {
            ppS2c.putIfAbsent(v, new ArrayList<>());
            ppS2c.get(v).add(k);
        });
        toJsData(ppS2c, "selected_wuge_stroke2char");
    }


    public List<String> genCandidates() throws IOException {
        HashMap<Integer, List<String>> ppS2c = fromJsData("selected_wuge_stroke2char", new TypeReference<HashMap<Integer, List<String>>>() {

        });

        ArrayList<String> candidates = new ArrayList<>(1000000);

        calculateBestStrokes(ppS2c, candidates);

        //toLinesData(candidates, "candidates");
        return candidates;
    }

    private void calculateBestStrokes(Map<Integer, List<String>> stroke2charMap, List<String> candidates) {
        for (int ming1 : stroke2charMap.keySet()) {
            for (int ming2 : stroke2charMap.keySet()) {
                if (picker.isGood(ming1, ming2)) {
                    int count = fullSetNames(stroke2charMap.get(ming1), stroke2charMap.get(ming2), candidates);

                    System.out.println("calculateBestStrokes: " +
                            Integer.toString(ming1) + " - " + Integer.toString(ming2)
                            + " , " + Integer.toString(count));
                }
            }
        }
    }

    private int fullSetNames(List<String> ming1, List<String> ming2, List<String> candidates) {
        for (String m1 : ming1) {
            for (String m2 : ming2) {
                candidates.add(m1 + m2);
            }
        }
        return ming1.size() * ming2.size();
    }

    private static class StrokePicker {
        static final int xing = 7;
        // 天格
        static final int tian = xing + 1;

        static final boolean[] GOOD_NUM = new boolean[300];
        static final HashSet<String> GOOD_SANCAI = new HashSet<>();

        static {
            Arrays.stream(ALL_BEST_NUMS).forEach(i -> GOOD_NUM[i] = true);
            GOOD_SANCAI.addAll(Arrays.asList(SANCAI_GOOD));
            if (ALLOW_NORMAL_CASES) {
                Arrays.stream(GOOD_NUMS).forEach(i -> GOOD_NUM[i] = true);
                Arrays.stream(NORMAL_NUMS).forEach(i -> GOOD_NUM[i] = true);
                GOOD_SANCAI.addAll(Arrays.asList(SANCAI_NORMAL));
            }
        }

        public boolean isGood(int ming1, int ming2) {
            // 人格
            int ren = xing + ming1;
            // 地格
            int di = ming1 + ming2;
            // 总格
            int zong = xing + ming1 + ming2;
            // 外格
            int wai = zong - ren + 1;

            if (GOOD_NUM[ren] && GOOD_NUM[di] && GOOD_NUM[zong] && GOOD_NUM[wai]) {
                // 三才配置
                StringBuilder sc = new StringBuilder(wuxing(tian));
                sc.append(wuxing(ren)).append(wuxing(di));
                return GOOD_SANCAI.contains(sc.toString());
            }
            return false;
        }
    }

    private static String wuxing(int strokes) {
        return WUXING[strokes % 10];
    }

    public static void main(String[] args) throws IOException {
        //new PipinameDataCollect().saveWugeStrokeFromPipiname();
        //new PipinameDataCollect().checkData();
        new PipinameDataCollect().genCandidates();
        //new PipinameDataCollect().cleanSelectedCharacters();
    }
}
