package ltf.namerank.dataprepare;

import com.alibaba.fastjson.JSON;
import ltf.namerank.entity.DictItem;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.utils.LinesInFile;
import ltf.namerank.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.FileUtils.str2File;

/**
 * @author ltf
 * @since 16/6/29, 下午4:55
 */
public class WordFeelings {

    private List<WordFeeling> feelings = new LinkedList<>();

    public void go() {
        try {
            new LinesInFile("/Users/f/downloads/wx/words/情感词汇本体.csv").each(this::processLine, "gbk");
            str2File(JSON.toJSONString(feelings, true), PathUtils.getJsonHome() + "/wordfeeling");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        String[] v = line.split(";");
        WordFeeling f = new WordFeeling();
        try {
            f.setWord(v[0]);
            f.setProperty(v[1]);
            f.setMeaningCount("".equals(v[2]) ? null : Integer.parseInt(v[2]));
            f.setMeaningId("".equals(v[3]) ? null : Integer.parseInt(v[3]));
            f.setCategory(v[4]);
            f.setLevel(Integer.parseInt(v[5]));
            f.setPolar(Integer.parseInt(v[6]));
            if (v.length > 7) {
                f.setSubCategory(v[7]);
                f.setSubLevel(v.length > 8 && !"".equals(v[8]) ? Integer.parseInt(v[8]) : null);
                f.setSubPolar(v.length > 9 && !"".equals(v[9]) ? Integer.parseInt(v[9]) : null);
            }
            feelings.add(f);

        } catch (Exception e) {
            System.out.println(line + "   :fail: " + e.getMessage());
        }
    }


}
