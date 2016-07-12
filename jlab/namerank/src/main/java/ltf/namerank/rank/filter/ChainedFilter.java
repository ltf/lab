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

    @Override
    public boolean banned(String givenName) {
        for (RankFilter filter : filters) {
            if (filter.banned(givenName))
                return true;
        }
        return false;
    }

    public ChainedFilter add(RankFilter filter) {
        if (filter != null) filters.add(filter);
        return this;
    }
}
