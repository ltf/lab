package ltf.namerank.rank;

import java.util.*;

/**
 * @author ltf
 * @since 16/7/5, 上午10:24
 */
public class RankRecordList implements Iterable<RankRecord> {

    protected List<RankRecord> list = new LinkedList<>();

    public RankRecord add(String word, double score) {
        RankRecord record = new RankRecord(word);
        record.setScore(score);
        list.add(record);
        return record;
    }

    public List<RankRecord> sortAsc() {
        Collections.sort(list);
        return list;
    }

    public List<RankRecord> sortDesc() {
        Collections.sort(list);
        Collections.reverse(list);
        return list;
    }

    public List<RankRecord> getList() {
        return list;
    }

    public List<String> getWordList() {
        List<String> wordList = new ArrayList<>(list.size() + 1);
        list.forEach(r -> wordList.add(r.getWord()));
        return wordList;
    }

    public void listDetails() {
        list.forEach(r -> {
            System.out.println(String.format("%f\t%-10s%s", r.getScore(), r.getWord(), r.getLog()));
        });
    }

    @Override
    public Iterator<RankRecord> iterator() {
        return list.iterator();
    }
}
