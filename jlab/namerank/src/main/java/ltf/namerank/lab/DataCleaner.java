package ltf.namerank.lab;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.word.Word;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankRecordList;
import ltf.namerank.rank.dictrank.pronounce.YinYunFilter;
import ltf.namerank.rank.dictrank.support.Bihua;
import ltf.namerank.rank.dictrank.support.PinyinMap;
import ltf.namerank.rank.dictrank.support.Words;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;
import ltf.namerank.rank.filter.ChainedFilter;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.rank.dictrank.support.PinyinMap.toPinyin;
import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.*;

/**
 * @author ltf
 * @since 16/7/13, 下午1:58
 */
public class DataCleaner {
    public DataCleaner() {
        try {
            allWordCases();

            //autoCleanByDistance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void allWordCases() throws IOException {
        List<String> chars = new ArrayList<>();
        Set<String> words = new HashSet<>();
        Set<String> badChars = new HashSet<>();

        chars.addAll(allChars());

        file2Lines(getWordsHome() + "/badchars.txt", badChars);

        ChainedFilter filter = new ChainedFilter()
                .add(new YinYunFilter());

        for (int i = chars.size() - 1; i >= 0; i--) {
            if (badChars.contains(chars.get(i))) {
                chars.remove(i);
                continue;
            }

            PinyinMap.Pinyin[] pinyins = toPinyin(chars.get(i).charAt(0));
            if (pinyins == null || pinyins.length == 0 || "".equals(pinyins[0].tone)) {
                chars.remove(i);
                continue;
            }

            if(Words.isSame(chars.get(i), Words.butySet)<0.5 && Words.isSame(chars.get(i), Words.happySet)<0.5 )
            {
                chars.remove(i);
                continue;
            }
        }

        for (int i = 0; i < chars.size(); i++) {
            for (int j = 0; j < chars.size(); j++) {
                String s = chars.get(i) + chars.get(j);
                if (!filter.banned(s)) words.add(s);
            }
        }

        lines2File(words, getWordsHome() + "/allWords.txt");
        filter.printBannedCount();
        System.out.println(words.size());


    }

    private Set<String> allChars() throws IOException {

        Set<String> words = new HashSet<>();
        file2Lines(getWordsHome() + "/goodwords.txt", words);
        file2Lines(getWordsHome() + "/buty.txt", words);
        file2Lines(getWordsHome() + "/happy.txt", words);
        file2Lines(getNamesHome() + "/givenNames.txt", words);
//        MdxtDict cidian = new HanYuDaCidian();
//        cidian.listKeys();
//        for (String itemKey : cidian.getItemsMap().keySet())
//            words.add(itemKey);

//        cidian = new KangxiZidian();
//        cidian.listKeys();
//        for (String itemKey : cidian.getItemsMap().keySet())
//            words.add(itemKey);


        Set<String> chars = new HashSet<>();

        Bihua bihua = new Bihua();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                String bs = bihua.char2Bihua(c);
                int bhs = bs == null ? 0 : bs.length();
                if (bhs <= 2 || bhs >= 18) continue;
                chars.add(HanLP.convertToSimplifiedChinese(c + ""));
            }
        }

        lines2File(chars, getWordsHome() + "/allChars.txt");
        System.out.println(chars.size());

        return chars;

    }

    private void distanceClean() throws IOException {
        Set<String> good = new HashSet<>();
        Set<String> bad = new HashSet<>();
        Set<String> buty = new HashSet<>();
        file2Lines(getWordsHome() + "/badwords.txt", bad);
        file2Lines(getWordsHome() + "/goodwords.txt", good);
        file2Lines(getWordsHome() + "/buty.txt", buty);

        RankRecordList recordList = new RankRecordList();
        for (String s : good) {
            double v = 0;
            for (String s2 : buty) {
                v += CoreSynonymDictionary.similarity(s, s2);
            }

            recordList.add(s, v / buty.size());
        }
        recordList.sortAsc();
        recordList.listDetails();
        lines2File(recordList.getWordList(), getWordsHome() + "/goodwords_.txt");
    }


    private void testWords() throws IOException {
        Set<String> good = new HashSet<>();
        Set<String> bad = new HashSet<>();
        file2Lines(getWordsHome() + "/badwords.txt", bad);
        file2Lines(getWordsHome() + "/goodwords.txt", good);
        for (String s : good) {
            if (bad.contains(s))
                System.out.println(s);
        }
    }

    private void cleanWords() throws IOException {
        distinct(getWordsHome() + "/badwords.txt");
        distinct(getWordsHome() + "/goodwords.txt");
    }


    private void autoCleanByDistance() throws IOException {

        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        file2Lines(getWordsHome() + "/positive.txt", list);

        HanYuDaCidian cidian = new HanYuDaCidian();
        List<RankItem> rankItems = new LinkedList<>();

        for (String s : list) {
            RankItem item = new RankItem(s);
            //item.setScore();
        }
        rankItems.sort(RankItem::descOrder);

        StringBuilder sb = new StringBuilder();
        for (RankItem item : rankItems) {
            System.out.println(String.format("%s: %f", item.getKey(), item.getScore()));
            sb.append(String.format("%s: %f", item.getKey(), item.getScore())).append("\n");
        }
        str2File(sb.toString(), getRawHome() + "/wordsClean.txt");

    }

    private void autoCleanByDict() throws IOException {

        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        file2Lines(getWordsHome() + "/positive.txt", list);

        HanYuDaCidian cidian = new HanYuDaCidian();
        List<RankItem> rankItems = new LinkedList<>();

        for (String s : list) {
            RankItem item = new RankItem(s);
            cidian.rank(item);
            rankItems.add(item);
        }
        rankItems.sort(RankItem::descOrder);

        StringBuilder sb = new StringBuilder();
        for (RankItem item : rankItems) {
            System.out.println(String.format("%s: %f", item.getKey(), item.getScore()));
            sb.append(String.format("%s: %f", item.getKey(), item.getScore())).append("\n");
        }
        str2File(sb.toString(), getRawHome() + "/wordsClean.txt");

    }

    private void clean() throws IOException {

        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        file2Lines(getWordsHome() + "/positive.txt", list);
        boolean skipNextChar = false;
        for (int i = list.size() - 1; i >= 0; i--) {
            String s = list.get(i);
            System.out.print(s + "    ");
            if (skipNextChar) {
                System.in.read();
                skipNextChar = false;
            }
            char c = (char) System.in.read();
            if (c == '0' || c == ' ') {
                System.out.println("\t\t\t\t\t\tdel");
                list.remove(i);
                skipNextChar = true;
            } else {
                System.out.println("\t\t\t\tkeep");
            }
            lines2File(list, getWordsHome() + "/positive_selected.txt");
        }
    }

}
