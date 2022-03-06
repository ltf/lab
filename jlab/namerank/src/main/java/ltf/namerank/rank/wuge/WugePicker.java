package ltf.namerank.rank.wuge;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author ltf
 * @since 2022/3/4, 1:16
 */
public class WugePicker {

    private static final boolean ALLOW_NORMAL_CASES = true;
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

    public static class StrokePicker {
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

        public static boolean isGood(int ming1, int ming2) {
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

    public static String wuxing(int strokes) {
        return WUXING[strokes % 10];
    }

}
