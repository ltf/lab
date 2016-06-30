package ltf.namerank.rank.dictrank.support;

/**
 * @author ltf
 * @since 16/6/30, 下午5:01
 */
public class WordScore {
    private String word;
    private float score;
    public WordScore(String word, float score) {
        this.word = word;
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
