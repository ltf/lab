package ltf.namerank.lab;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.RankRecord;
import ltf.namerank.rank.RankRecordList;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.*;

/**
 * @author ltf
 * @since 6/29/16, 10:05 PM
 */
public class RankingTest {

    private List<MdxtDict> dictList;

    private List<RankRecord> rankRecordList = new LinkedList<>();

    private Ranker ranker;

    private void initDicts() {
        if (dictList == null) {
            dictList = new ArrayList<>();
            dictList.add(new HanYuDaCidian());
        }
    }

    public void go() {

        //new HanYuDaCidian().listKeys();

        //testWordFeeling();


//        List<String> list = new ArrayList<>();
//        try {
//            file2Lines(getRawHome() + "/buty-keywords.txt", list);
//            testWordUnion(list);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        try {
//            FileUtils.distinct(getRawHome() + "/buty-keywords.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //HanyuXgXfCidian dict = new HanyuXgXfCidian();
        //dict.listKeys();
        //dict.debug();


        //System.out.println(dict.getItemsMap().size());

        //WordFeelingRank.getInstance().listItems();
//        try {
//            ranker = new CachedRanker(new AllCasesRanker(new CachedRanker(new HanYuDaCidian())));
//            //initDicts();
//            doRanking();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private void testWordFeeling() {
        List<WordFeeling> wordFeelingList = null;
        try {
            wordFeelingList = JSON.parseArray(file2Str(getJsonHome() + "/wordfeeling"), WordFeeling.class);
        } catch (IOException e) {
        }
        List<String> list = new ArrayList<>();
        for (WordFeeling feeling : wordFeelingList) {

            if (!( //"noun".equals(feeling.getProperty()) ||
                    // "verb".equals(feeling.getProperty()) ||
                    "adj".equals(feeling.getProperty()) ||
                            "adv".equals(feeling.getProperty())))
                continue;


            if (feeling.getPolar() == 1) {
                //System.out.println(feeling.getWord());
                list.add(feeling.getWord());
            }
        }

        testWordUnion(list);
    }


    private void testWordUnion(List<String> list) {
        //List<String> list = new ArrayList<>();
        List<String> keys = new ArrayList<>();

        try {
            RankRecordList result = new RankRecordList();
            //file2Lines(getRawHome() + "/buty-keywords.txt", list);
            file2Lines(getRawHome() + "/buty-keywords.txt", keys);
            for (String s : list) {
                double score = CoreSynonymDictionary.distance(s, "美丽") * 100000;
                score += CoreSynonymDictionary.distance(s, "漂亮") * 100000;
                score += CoreSynonymDictionary.distance(s, "可爱") * 100000;
                for (String k : keys) {
                    score += CoreSynonymDictionary.distance(s, k);
                }
                result.add(s, score);
            }
            result.sortAsc();
            result.listDetails();
            lines2File(result.getWordList(), getRawHome() + "/buty-keywords2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doRanking() throws IOException {
        new LinesInFile(getNamesHome() + "/givenNames.txt").each(this::nameRanking);

        Collections.sort(rankRecordList);

        StringBuilder sb = new StringBuilder();
        for (RankRecord record : rankRecordList) {
            System.out.println(String.format("%s: %f", record.getWord(), record.getScore()));
            sb.append(String.format("%s: %f", record.getWord(), record.getScore())).append("\n");
        }
        str2File(sb.toString(), getRawHome() + "/ranking.txt");

    }

    private void nameRanking(String givenName) {
        if (givenName.length() == 2 && givenName.substring(0, 1).equals(givenName.substring(1))) {
            RankRecord record = new RankRecord(givenName);
            record.setScore(ranker.rank(givenName, null));
            rankRecordList.add(record);
            //for (MdxtDict dict : dictList) dict.rank(record);
        }
    }
}
