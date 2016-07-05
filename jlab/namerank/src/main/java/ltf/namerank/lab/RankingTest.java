package ltf.namerank.lab;

import ltf.namerank.rank.RankRecord;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.HanyuXgXfCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;
import ltf.namerank.utils.FileUtils;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.utils.FileUtils.lines2File;
import static ltf.namerank.utils.FileUtils.str2File;
import static ltf.namerank.utils.PathUtils.getNamesHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

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


        try {
            FileUtils.distinct(getRawHome() + "/usedKeywords-bak.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
