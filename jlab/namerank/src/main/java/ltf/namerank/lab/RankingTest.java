package ltf.namerank.lab;

import com.alibaba.fastjson.JSON;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.PathUtils.getJsonHome;

/**
 * @author ltf
 * @since 6/29/16, 10:05 PM
 */
public class RankingTest {

    private List<WordFeeling> wordFeelingList = new ArrayList<>();

    private List<MdxtDict> dictList = new ArrayList<>();

    private void initDicts() {
        if (dictList == null) {
            dictList = new ArrayList<>();
            dictList.add(new HanYuDaCidian());
        }
    }

    public void go() {

        try {
            //initDicts();
            initWordFeelings();

            //doRanking();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initWordFeelings() throws IOException {
        wordFeelingList = JSON.parseArray(file2Str(getJsonHome() + "/wordfeeling"), WordFeeling.class);

        System.out.println(wordFeelingList.size());

    }

    private void doRanking() throws IOException {


        //new LinesInFile(getNamesHome() + "givenNames.txt").each(this::nameRanking);

    }

    private void nameRanking(String givenName) {

    }

}
