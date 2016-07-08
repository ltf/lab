package ltf.namerank.rank;

/**
 * @author ltf
 * @since 16/7/1, 下午5:25
 */
public class SumRankers implements Ranker {

    private Ranker[] rankers;

    public SumRankers(Ranker... rankers) {
        this.rankers = rankers;
    }

    @Override
    public double rank(String target, RankLogger logger) {
        double rk = 0;
        for (Ranker ranker : rankers)
            rk += ranker.rank(target, logger);
        return rk;
    }
}
