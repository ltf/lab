package ltf.namerank.rank;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltf
 * @since 16/7/1, 下午5:01
 */
public abstract class CachedRanker implements Ranker {

    private Map<String, Double> rankCache = new HashMap<>();

    abstract protected double doRank(String target, RankConfig config);

    @Override
    public double rank(String target, RankConfig config) {
        if (rankCache.containsKey(target))
            return rankCache.get(target);

        double rk = doRank(target, config);
        rankCache.put(target, rk);
        return rk;
    }
}
