package ltf.namerank.rank;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltf
 * @since 16/7/1, 下午5:01
 */
public class CachedRanker extends WrappedRanker {

    private Map<String, Double> rankCache = new HashMap<>();

    public CachedRanker(Ranker ranker) {
        super(ranker);
    }

    @Override
    public double rank(String target, RankConfig config) {
        if (rankCache.containsKey(target))
            return rankCache.get(target);

        double rk = super.rank(target, config);
        rankCache.put(target, rk);
        return rk;
    }
}
