package ltf.namerank.lab;

import com.alibaba.fastjson.JSON;
import ltf.namerank.dataprepare.dict.MdxtDict;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.PathUtils.getNamesHome;

/**
 * @author ltf
 * @since 6/29/16, 10:05 PM
 */
public class RankingTest {

    private List<WordFeeling> wordFeelingList = new ArrayList<>();

    private List<MdxtDict> dictList = new ArrayList<>();

    public void go() {

        try {
            doRanking();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void doRanking() throws IOException {
        wordFeelingList = JSON.parseArray(file2Str(file2Str(getJsonHome() + "/wordfeeling")), WordFeeling.class);

        new LinesInFile(getNamesHome() + "givenNames.txt").each(this::nameRanking);

    }

    private void nameRanking(String givenName) {



    }

}
