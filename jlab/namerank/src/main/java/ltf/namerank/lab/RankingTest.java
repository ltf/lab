package ltf.namerank.lab;

import ltf.namerank.rank.RankRecord;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.rank.dictrank.support.dict.MdxtDict;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    private void initDicts() {
        if (dictList == null) {
            dictList = new ArrayList<>();
            dictList.add(new HanYuDaCidian());
        }
    }

    public void go() {

        try {
            initDicts();

            doRanking();
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
        str2File(sb.toString(), getRawHome()+"/ranking.txt");

    }

    private void nameRanking(String givenName) {
        RankRecord record = new RankRecord(givenName);
        rankRecordList.add(record);
        for (MdxtDict dict : dictList) dict.rank(record);
    }

}
