package ltf.namerank.rank;

import ltf.namerank.rank.dictrank.support.Cipin;

/**
 * @author ltf
 * @since 7/13/16, 9:46 PM
 */
public class FreqRankItem {

    String word;
    int count;
    double rate;
    int freq;

    FreqRankItem(String word, int count) {
        this.word = word;
        this.count = count;
        this.freq = Cipin.get(word);
    }

    public String getWord() {
        return word;
    }
    public int getCount() {
        return count;
    }

    public double getRate() {
        return rate;
    }
}
