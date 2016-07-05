package ltf.namerank.rank;

import java.util.HashMap;
import java.util.Map;

/**
 * A Distinct keywords list
 *
 * @author ltf
 * @since 16/7/5, 上午10:24
 */
public class RankRecordSet extends RankRecordList {

    private Map<String, RankRecord> wordsMap = new HashMap<>();

    @Override
    public RankRecord add(String word, double score) {
        RankRecord record = wordsMap.get(word);
        if (record == null) {
            record = super.add(word, score);
            wordsMap.put(word, record);
        } else {
            record.setScore(record.getScore() + score);
        }

        return record;
    }
}
