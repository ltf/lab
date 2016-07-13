package ltf.namerank.rank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 7/13/16, 9:45 PM
 */
public class FreqRankList {
    private List<FreqRankItem> list = new ArrayList<>();

    public void add(String word) {
        add(word, 1);
    }

    public void add(String word, int count) {
        list.add(new FreqRankItem(word, count));
    }

    public void clear() {
        list.clear();
    }

    public List<FreqRankItem> prepare() {
        int all = 0;
        for (FreqRankItem item : list) all += item.count * item.freq;
        for (FreqRankItem item : list) item.rate = item.count * item.freq / (double)all;
        list.sort((a, b) -> a.rate > b.rate ? -1 : a.rate < b.rate ? 1 : 0);
        return list;
    }
}
