package ltf.namerank.rank;

import java.util.Map;

/**
 * @author ltf
 * @since 16/7/7, 下午4:48
 */
public class RankScore {

    private double value = 0;

    public RankScore() {
    }

    public RankScore(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    /**
     * addition
     */
    public RankScore add(String keyword, RankScore summand) {
        add(keyword, summand, 1);
        return this;
    }

    /**
     * addition
     */
    public RankScore add(String keyword, RankScore summand, double rate) {
        this.value += summand.value * rate;
        return this;
    }

}
