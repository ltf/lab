package ltf.namerank.lab;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.*;
import ltf.namerank.rank.dictrank.pronounce.PronounceRank;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.rank.CachedRanker.cache;
import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.*;

/**
 * @author ltf
 * @since 6/29/16, 10:05 PM
 */
public class RankingTest {

    private List<MdxtDict> dictList;

    private List<RankItem> rankItems = new LinkedList<>();

    private List<String> ignoreWords = new ArrayList<>();

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
//            FileUtils.distinct(getWordsHome() + "/positive.txt");
//            FileUtils.distinct(getWordsHome() + "/negative.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //HanyuXgXfCidian dict = new HanyuXgXfCidian();
        //dict.listKeys();
        //dict.debug();


//        List<String> list = new ArrayList<>();
//        try {
//            file2Lines(getRawHome() + "/ranking4.txt", list);
//            //file2Lines(getWordsHome() + "/ignore.txt", ignoreWords);
//            String ignore = "姣娇";
//            for (int i = list.size() - 1; i >= 0; i--) {
//                //if (list.get(i).contains("淑")) list.remove(i);
//                //if (list.get(i).contains("春")) list.remove(i);
//                if (list.get(i).contains("妍")) list.remove(i);
//                if (list.get(i).contains("馨")) list.remove(i);
//                if (list.get(i).contains("雅")) list.remove(i);
//                if (list.get(i).contains("快")) list.remove(i);
//                if (list.get(i).contains("熙")) list.remove(i);
//
//                for (char c: ignore.toCharArray()){
//                    if (list.get(i).contains(c+"")) list.remove(i);
//                }
//            }
//
//            lines2File(list, getRawHome() + "/ranking4.txt");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //System.out.println(dict.getItemsMap().size());

        //WordFeelingRank.getInstance().listItems();
        try {

            Ranker hanyuCidian = cache(new HanYuDaCidian());
            ranker = cache(new AllCasesRanker(
                            new SumRankers()
                                    .addRanker(hanyuCidian, 5)
                                    .addRanker(cache(new PronounceRank(hanyuCidian)), 1)
                    ).setFamilyname('李')
            );
            doRanking();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private Set<String> picked = new HashSet<>();

    private void doRanking() throws IOException {

        RankSettings.reportMode = true;

        file2Lines(getRawHome() + "/picked.txt", picked);

        new LinesInFile(getNamesHome() + "/givenNames.txt").each(this::nameRanking);

        rankItems.sort(RankItem::descOrder);

        StringBuilder sb = new StringBuilder();
        for (RankItem item : rankItems) {
            System.out.println(String.format("%s: %f", item.getKey(), item.getScore()));
            sb.append(String.format("%s: %f", item.getKey(), item.getScore())).append("\n");
        }
        str2File(sb.toString(), getRawHome() + "/ranking.txt");
        List<RankItem> genHtmlItems = new ArrayList<>();
        for (int i = 0; i < (rankItems.size() < 1000 ? rankItems.size() : 1000); i++) {
            genHtmlItems.add(rankItems.get(i));
        }
        if (RankSettings.reportMode) HtmlGenerator.gen(genHtmlItems, getRawHome() + "/test.htm");
    }

    private void nameRanking(String givenName) {
        //if (!picked.contains(givenName)) return;
        if (!"钰琦".equals(givenName)) return;
        //if (givenName.length() == 2 && givenName.substring(0, 1).equals(givenName.substring(1))) {
        RankItem item = new RankItem(givenName);
        ranker.rank(item);
        rankItems.add(item);
        //for (MdxtDict dict : dictList) dict.rank(record);
        //}
    }
}
