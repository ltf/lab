package ltf.namerank.dataprepare;

import com.alibaba.fastjson.TypeReference;
import ltf.namerank.entity.DictItem_Bm8;
import ltf.namerank.rank.wuge.WugePicker;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ltf.namerank.utils.FileUtils.*;

/**
 * @author ltf
 * @since 16/7/12, 下午1:42
 */
public class PipinameDataCollect {

    private static final String PIPI_WUGE_STROKES_SHORT = "pipiname_wuge_char2stroke";

    private static final String EXCLUDE_CHARS =
            "花蝶香瑶梅桃芳姣钅菲芹听芸蓉妡柔莹鸯刁阴";


    private void saveWugeStrokeFromPipiname() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        fromLinesData("pipiname_wuge_stroke", lines);
        Map<String, Integer> c2sMap = lines.stream().map(l -> l.split(" ")).collect(Collectors.toMap(a -> a[0], a -> Integer.parseInt(a[1])));
        toJsData(c2sMap, PIPI_WUGE_STROKES_SHORT);
    }

    private void selectWugeStrokesLib() throws IOException {
        HashMap<String, Integer> ppC2s = fromJsData(PIPI_WUGE_STROKES_SHORT, new TypeReference<HashMap<String, Integer>>() {
        });

        HashMap<String, DictItem_Bm8> dict = fromJsData("dictInOne", new TypeReference<HashMap<String, DictItem_Bm8>>() {
        });

        HashMap<Character, Integer> xmx_bihua = fromJsData("xingmingxue_pdf_bihua", new TypeReference<HashMap<Character, Integer>>() {

        });

        HashMap<Character, WugeDataCollect.WuxingBihua> xmx_wuxing = fromJsData("xingmingxue_pdf_wuxing", new TypeReference<HashMap<Character, WugeDataCollect.WuxingBihua>>() {

        });

        int count = 1;
        HashMap<Character, Integer> charRateRank = new HashMap<>();
        for (String l : fromLinesData("zipin")) {
            for (char c : l.toCharArray()) {
                count++;
                charRateRank.putIfAbsent(c, count);
            }
        }

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
//            if (dict.containsKey(k) && (!v.equals(dict.get(k).getStrokes()) || "凶".equals(dict.get(k).getLuckyornot()))) {
//                diff.add(k);
//            } else if (xmx_bihua.containsKey(c) && !v.equals(xmx_bihua.get(c))) {
//                diff.add(k);
//            } else if (xmx_wuxing.containsKey(c) && !v.equals(xmx_wuxing.get(c).bh)) {
//                diff.add(k);
//            }
            if (!charRateRank.containsKey(c) || charRateRank.get(c) > 5000) {
                if (dict.containsKey(k) && v.equals(dict.get(k).getStrokes()) && !"凶".equals(dict.get(k).getLuckyornot())) {
                    return;
                }
                if (xmx_bihua.containsKey(c) && v.equals(xmx_bihua.get(c))) return;
                if (xmx_wuxing.containsKey(c) && v.equals(xmx_wuxing.get(c).bh)) return;

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
        for (char c : EXCLUDE_CHARS.toCharArray()) {
            ppS2c.remove(String.valueOf(c));
        }

        ArrayList<String> candidates = new ArrayList<>(1000000);

        calculateBestStrokes(ppS2c, candidates);

        filterCandidatesByExistsNames(candidates);
        //toLinesData(candidates, "candidates");
        return candidates;
    }

    public void filterCandidatesByExistsNames(List<String> candidates) throws IOException {
        HashSet<String> existsNames = new HashSet<>(fromLinesData("givenNames"));
        for (int i = candidates.size() - 1; i >= 0; i--) {
            if (!existsNames.contains(candidates.get(i))) {
                candidates.remove(i);
            }
        }
    }

    private void calculateBestStrokes(Map<Integer, List<String>> stroke2charMap, List<String> candidates) {
        for (int ming1 : stroke2charMap.keySet()) {
            for (int ming2 : stroke2charMap.keySet()) {
                if (WugePicker.StrokePicker.isGood(ming1, ming2)) {
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


    public static void main(String[] args) throws IOException {
        //new PipinameDataCollect().saveWugeStrokeFromPipiname();
        new PipinameDataCollect().selectWugeStrokesLib();
        //new PipinameDataCollect().genCandidates();
        //new PipinameDataCollect().cleanSelectedCharacters();
    }
}
