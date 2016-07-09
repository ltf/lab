package ltf.namerank.rank;

/**
 * @author ltf
 * @since 16/7/7, 下午4:48
 */
public class RankResult {

    private double score = 0;

    public RankResult() {
    }

    public RankResult(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    /**
     * addition
     */
    public RankResult add(String keyword, RankResult summand) {
        add(keyword, summand, 1);
        return this;
    }

    /**
     * addition
     */
    public RankResult add(String keyword, RankResult summand, double rate) {
        this.score += summand.score * rate;
        return this;
    }

}
