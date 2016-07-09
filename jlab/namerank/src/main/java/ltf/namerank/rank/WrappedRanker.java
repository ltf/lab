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
    public double rank(RankItem target) {
        return innerRanker.rank(target);
    }

    public Ranker getInnerRanker() {
        return innerRanker;
    }

}
