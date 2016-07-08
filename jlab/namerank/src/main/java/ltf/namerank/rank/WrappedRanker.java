package ltf.namerank.rank;

/**
 * @author ltf
 * @since 7/1/16, 10:21 PM
 */
public abstract class WrappedRanker implements Ranker {

    private Ranker innerRanker;

    public WrappedRanker(Ranker ranker) {
        this.innerRanker = ranker;
    }

    @Override
    public double rank(String target, RankLogger logger) {
        return innerRanker.rank(target, logger);
    }

    public Ranker getInnerRanker() {
        return innerRanker;
    }

}
