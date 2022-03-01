package ltf.namerank.dataprepare;

import com.alibaba.fastjson.TypeReference;
import ltf.namerank.entity.DictItem_Bm8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ltf.namerank.utils.FileUtils.*;

/**
 * @author ltf
 * @since 16/7/12, 下午1:42
 */
public class PipinameDataCollect {
    private static final String PIPI_WUGE_STROKES_SHORT = "pipiname_wuge_char2stroke";

    private void saveWugeStrokeFromPipiname() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        fromLinesData(PIPI_WUGE_STROKES_SHORT, lines);
        Map<String, Integer> c2sMap = lines.stream().map(l -> l.split(" ")).collect(Collectors.toMap(a -> a[0], a -> Integer.parseInt(a[1])));
        toJsData(c2sMap, "pipiname_wuge_char2stroke");
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
            if (dict.containsKey(k) && !v.equals(dict.get(k).getStrokes())) {
                diff.add(k);
            } else if (xmx_bihua.containsKey(k.charAt(0)) && !v.equals(xmx_bihua.get(k.charAt(0)))) {
                diff.add(k);
            } else if (xmx_wuxing.containsKey(k.charAt(0)) && !v.equals(xmx_wuxing.get(k.charAt(0)))) {
                diff.add(k);
            }
        });
        toLinesData(diff, "diff");
    }

    public static void main(String[] args) throws IOException {
        //new PipinameDataCollect().saveWugeStrokeFromPipiname();
        new PipinameDataCollect().checkData();

    }
}
