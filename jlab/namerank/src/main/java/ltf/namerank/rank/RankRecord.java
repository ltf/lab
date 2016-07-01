package ltf.namerank.rank;

/**
 * @author ltf
 * @since 16/6/30, 下午3:42
 */
public class RankRecord implements Comparable<RankRecord> {
    private String word = "";

    private double score = 0;

    public RankRecord(String word) {
        this.word = word;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void add(double delta) {
        score += delta;
    }

    @Override
    public int compareTo(RankRecord o) {
        double x = o.score - score;
        return x > 0 ? 1 : (x < 0 ? -1 : 0);
    }
}
