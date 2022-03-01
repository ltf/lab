package ltf.namerank.dataprepare;

import com.alibaba.fastjson.JSON;
import ltf.namerank.dao.fs.DictItemDaoImpl;
import ltf.namerank.entity.DictItem_Bm8;
import ltf.namerank.rank.dictrank.support.Bihua;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/7/12, 下午1:42
 */
public class WugeDataCollect {
    public WugeDataCollect() {
        collect();
    }

    private void collect() {
        try {
            //collectXingmingxueBihua();

            loadBihuaData();
            collectXingmingxue_Wuxing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class WuxingBihua {
        public String wx;
        public Integer bh;

        public WuxingBihua() {
        }
        
        public WuxingBihua(String wx, Integer bh) {
            this.wx = wx;
            this.bh = bh;
        }
    }

    private static final String patternStr = "(.)（(\\d{1,2})";
    private final Pattern pattern = Pattern.compile(patternStr);

    private Map<String, WuxingBihua> wuxingMap = new HashMap<>();

    private String wuxing = "";

    private void collectXingmingxue_Wuxing() throws IOException {
        wuge_bihuashu = 0;
        new LinesInFile(getRawHome() + "/xingmingxue_pdf_wuxing.txt").each(s -> {
            if (s.trim().endsWith("五行") || s.trim().endsWith("五行")) {
                flushWuxing(wuxing);
                wuxing = s.substring(0, 1);
                if ("2".equals(s.substring(1, 2))) wuxing += "(2)";
            } else {
                strBuilder.append(s);
            }
        });
        flushWuxing(wuxing);
        saveWuxingData();
        System.out.println(wuxingMap.size());
    }

    private void flushWuxing(String wuxing) {
        if ("".equals(wuxing)) return;
        String words = strBuilder.toString();
        words = words.replaceAll(" ", "").replaceAll(",", "");

        Matcher matcher = pattern.matcher(words);
        while (matcher.find()) {
            String k = matcher.group(1);
            Integer bihua = Integer.parseInt(matcher.group(2));
            wuxingMap.put(k, new WuxingBihua(wuxing, bihua));
            Integer bihua2 = wugeBihua.get(k);

            if (!bihua.equals(bihua2)) {
                System.out.println(String.format("%s  this:%d  alone:%d ",
                        k, bihua, bihua2));
            }
        }

        strBuilder.delete(0, strBuilder.length());
    }


    private void saveWuxingData() {
        try {
            str2File(JSON.toJSONString(wuxingMap, true), getJsonHome() + "/xingmingxue_pdf_wuxing.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> wugeBihua = new HashMap<>();
    private int wuge_bihuashu;
    private StringBuilder strBuilder = new StringBuilder();

    private void collectXingmingxueBihua() throws IOException {
        wuge_bihuashu = 0;
        new LinesInFile(getRawHome() + "/xingmingxue_pdf_bihua.txt").each(s -> {
            if (s.trim().endsWith("畫")) {
                flushXingmingxueBihua(wuge_bihuashu);
                wuge_bihuashu++;
            } else {
                strBuilder.append(s);
            }
        });
        flushXingmingxueBihua(wuge_bihuashu);
        //saveData();

        System.out.println(wugeBihua.size());
    }

    private void flushXingmingxueBihua(int bihuashu) {
        String words = strBuilder.toString();
        words = words.replaceAll(" ", "").replaceAll(",", "");
        for (char c : words.toCharArray()) {
            wugeBihua.put(c + "", bihuashu);
            //verifyWithOldDict(c, bihuashu);
            //System.out.println(c + " " + bihuashu);
        }

        strBuilder.delete(0, strBuilder.length());
    }

    private void saveBihuaData() {
        try {
            str2File(JSON.toJSONString(wugeBihua, true), getJsonHome() + "/xingmingxue_pdf_bihua.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBihuaData() {
        try {
            wugeBihua = (HashMap<String, Integer>) JSON.parseObject(file2Str(getJsonHome() + "/xingmingxue_pdf_bihua.json"),
                    HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DictItemDaoImpl dictItemDao = new DictItemDaoImpl();
    private Bihua bihua = new Bihua();

    private void verifyWithOldDict(char c, int bihuashu) {
        DictItem_Bm8 itm = (DictItem_Bm8) dictItemDao.loadItemsByZi(c + "");
        if (itm == null) {
            //System.out.println(c + " not exists in dict");
            return;
        }
        if (bihuashu != itm.getStrokes()) {
            System.out.println(String.format("%s  xmx:%d  web:%d  dict:%d",
                    c, bihuashu, itm.getStrokes(), bihua.char2Bihua(c).length()));
        }
    }
}
