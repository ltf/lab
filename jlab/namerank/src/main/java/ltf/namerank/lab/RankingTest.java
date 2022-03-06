package ltf.namerank.lab;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import ltf.namerank.dataprepare.PipinameDataCollect;
import ltf.namerank.dataprepare.WuxingDataCollect;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.*;
import ltf.namerank.rank.dictrank.CharRateRanker;
import ltf.namerank.rank.dictrank.PoemRanker;
import ltf.namerank.rank.dictrank.meaning.SameMeaningRanker;
import ltf.namerank.rank.dictrank.pronounce.PronounceRank;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;
import ltf.namerank.rank.filter.BlacklistCharsFilter;
import ltf.namerank.rank.filter.ChainedFilter;
import ltf.namerank.rank.filter.CharacterWuxingFilter;
import ltf.namerank.rank.filter.KeepExistsWordFilter;
import ltf.namerank.rank.wuge.WugeFilter;
import ltf.namerank.rank.wuxing.ExistsWordsChecker;

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

    private Ranker ranker;

    private ChainedFilter filter;

    private void initDicts() {
        if (dictList == null) {
            dictList = new ArrayList<>();
            dictList.add(new HanYuDaCidian());
        }
    }

    private void testNewDict() {

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

    public void go() {

        //testNewDict();
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
//            FileUtils.distinct(getNamesHome() + "/givenNames.txt");
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
//            //
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
            HanYuDaCidian hanYuDaCidian = new HanYuDaCidian();
            CachedRanker cachedHanyuCidian = cache(hanYuDaCidian);
            SameMeaningRanker sameMeaningRanker = new SameMeaningRanker();

            AllCasesRanker allCasesRanker = new AllCasesRanker(
                    new SumRankers()
                            .addRanker(cache(sameMeaningRanker), 1)
                            .addRanker(cachedHanyuCidian, 1)
                            .addRanker(cache(new PronounceRank(cachedHanyuCidian)), 1)
            );

            // 已经以名字形式存在的词，加分
//            ExistWordRanker nameListed = new ExistWordRanker(
//                    new ExistsWordsChecker(getNamesHome() + "/givenNames.txt"), 1000);

//            BihuaRanker bihuaRanker = new BihuaRanker()
//                    .addWantedChar('金', 100)
//                    .addWantedChar('水', 80);


            ranker = new SumRankers()
                    .addRanker(allCasesRanker, 1)
                    //.addRanker(nameListed, 1)
                    .addRanker(new PoemRanker(), 2)
                    .addRanker(new CharRateRanker(), 2)
            ;

            BlacklistCharsFilter blacklistCharsFilter = new BlacklistCharsFilter()
                    //.addChars(getWordsHome() + "/fyignore.txt")
                    .addChars(getWordsHome() + "/ignore.txt")
                    //.addChars(getWordsHome() + "/taboo_girl.txt")
                    //.addChars(getWordsHome() + "/gaopinzi.txt")
                    .addChars(getWordsHome() + "/badchars.txt");

            filter = new ChainedFilter()
                    //.add(new LengthFilter())
                    .add(new WugeFilter())
                    .add(blacklistCharsFilter)
                    //.add(new KeepExistsWordFilter(new ExistsWordsChecker(getNamesHome() + "/givenNames.txt")))
                    .add(new CharacterWuxingFilter())
            //.add(new WugeFilter())
            //.add(new YinYunFilter())
            //.add(new SameMeaningFilter())
            ;

            doRanking();

            filter.printBannedCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> picked = new HashSet<>();
    private static final String PICKED_LIST = getRawHome() + "/picked.txt";

    private void doRanking() throws IOException {

        RankSettings.reportMode = true;

        if (RankSettings.reportMode && exists(PICKED_LIST)) file2Lines(PICKED_LIST, picked);

        //new LinesInFile(getNamesHome() + "/givenNames.txt").each(this::nameRanking);
        //new LinesInFile(getWordsHome() + "/allWords.txt").each(this::nameRanking);
        Iterable<String> names4testing;
        if (RankSettings.reportMode) {
            names4testing = picked;
        } else {
//            List<String> candidates = new PipinameDataCollect().genCandidates();
            List<String> candidates = WuxingDataCollect.genCandidates();
            doFilter(candidates);
            names4testing = candidates;
        }

        // start test
        names4testing.forEach(this::nameRanking);
        rankItems.sort(RankItem::descOrder);

        StringBuilder sb = new StringBuilder();
        for (RankItem item : rankItems) {
            System.out.println(String.format("%s: %f", item.getKey(), item.getScore()));
            sb.append(String.format("%s: %f", item.getKey(), item.getScore())).append("\n");
        }
        str2File(sb.toString(), getRawHome() + "/ranking.txt");
        List<RankItem> genHtmlItems = new ArrayList<>();
        for (int i = 0; i < (Math.min(rankItems.size(), 2000)); i++) {
            genHtmlItems.add(rankItems.get(i));
        }
        if (RankSettings.reportMode) HtmlGenerator.gen(genHtmlItems, getRawHome() + "/test.htm");
    }

    private void doFilter(List<String> candidates) {
        for (int i = candidates.size() - 1; i >= 0; i--) {
            if (filter.banned(candidates.get(i))) {
                candidates.remove(i);
            }
        }
    }

    private void nameRanking(String givenName) {
        try {
            if (RankSettings.reportMode && !picked.contains(givenName)) return;
            //if (!"钰琦".equals(givenName)) return;
            //if (givenName.length() == 2 && givenName.substring(0, 1).equals(givenName.substring(1))) {
            RankItem item = new RankItem(givenName);
            ranker.rank(item);
            rankItems.add(item);
            //for (MdxtDict dict : dictList) dict.rank(record);
            //}
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
