package ltf.namerank.lab;

import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankRecordList;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.utils.FileUtils.*;
import static ltf.namerank.utils.PathUtils.getRawHome;
import static ltf.namerank.utils.PathUtils.getWordsHome;

/**
 * @author ltf
 * @since 16/7/13, 下午1:58
 */
public class DataCleaner {
    public DataCleaner() {
        try {
            distanceClean();
            //autoCleanByDistance();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            recordList.add(s,  v / buty.size());
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
