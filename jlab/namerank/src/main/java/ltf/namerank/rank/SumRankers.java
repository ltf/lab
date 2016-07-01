package ltf.namerank.rank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 16/7/1, 下午5:25
 */
public class SumRankers extends CachedRanker {

    private List<Ranker> rankerList = new ArrayList<>();

    @Override
    protected double doRank(String target, RankConfig config) {
        double rk = 0;
        for (Ranker ranker : rankerList)
            rk += ranker.rank(target, config);
        return rk;
    }

    public SumRankers addRanker(Ranker ranker) {
        rankerList.add(ranker);
        return this;
    }
}
