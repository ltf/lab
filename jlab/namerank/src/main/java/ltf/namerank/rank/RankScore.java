package ltf.namerank.rank;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltf
 * @since 16/7/7, 下午4:48
 */
public class RankScore {

    private double defaultValue = 0;

    private Map<String, Double> values = new HashMap<>();

    /**
     * addition
     */
    public RankScore add(RankScore summand) {
        this.defaultValue += summand.defaultValue;
        return this;
    }

    /**
     * addition
     */
    public RankScore add(double summand) {
        this.defaultValue += summand;
        return this;
    }

    /**
     * multiplication
     */
    public RankScore mul(double multiplicand) {
        this.defaultValue *= multiplicand;
        return this;
    }


}
