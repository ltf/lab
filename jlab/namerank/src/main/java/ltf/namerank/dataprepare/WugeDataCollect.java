package ltf.namerank.dataprepare;

import com.alibaba.fastjson.JSON;
import ltf.namerank.dao.fs.DictItemDaoImpl;
import ltf.namerank.entity.DictItem_Bm8;
import ltf.namerank.rank.dictrank.support.Bihua;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static ltf.namerank.utils.FileUtils.str2File;
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
            collectXingmingxueBihua();
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
        saveData();

        System.out.println(wugeBihua.size());
    }

    private void flushXingmingxueBihua(int bihuashu) {
        String words = strBuilder.toString();
        words = words.replaceAll(" ", "").replaceAll(",", "");
        for (char c : words.toCharArray()) {
            wugeBihua.put(c+"", bihuashu);
            //verifyWithOldDict(c, bihuashu);
            //System.out.println(c + " " + bihuashu);
        }

        strBuilder.delete(0, strBuilder.length());
    }

    private void saveData() {
        try {
            str2File(JSON.toJSONString(wugeBihua, true), getJsonHome() + "/xingmingxue_pdf_bihua.json");
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


    private void collectXingmingxueWuxing() {

    }
}
