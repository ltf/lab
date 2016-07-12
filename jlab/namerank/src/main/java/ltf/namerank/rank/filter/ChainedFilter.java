package ltf.namerank.rank.filter;

import ltf.namerank.rank.RankFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 16/7/12, 上午10:24
 */
public class ChainedFilter implements RankFilter {

    private List<RankFilter> filters = new ArrayList<>();
    private List<Integer> counter = new ArrayList<>();

    @Override
    public boolean banned(String givenName) {
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).banned(givenName)) {
                counter.set(i, counter.get(i) + 1);
                return true;
            }
        }
        return false;
    }

    public ChainedFilter add(RankFilter filter) {
        if (filter != null) {
            filters.add(filter);
            counter.add(0);
        }
        return this;
    }

    public void printBannedCount() {
        for (int i = 0; i < filters.size(); i++) {
            System.out.println(String.format("%s : %d", filters.get(i).getClass().getSimpleName(), counter.get(i)));
        }

    }
}
