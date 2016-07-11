package ltf.namerank.rank.dictrank;

import com.sun.istack.internal.NotNull;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.dictrank.support.Bihua;

import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankItemHelper.*;

/**
 * @author ltf
 * @since 16/7/11, 上午11:53
 */
public class BihuaRanker implements Ranker {
    private List<Character> wantedChars = new ArrayList<>();
    private List<Double> wantedCharRates = new ArrayList<>();
    private List<String> wantedBihuas = new ArrayList<>();
    private List<Boolean> exists = new ArrayList<>();

    private Bihua bihua = null;

    public BihuaRanker addWantedChar(char wantedChar, double rate) {
        wantedChars.add(wantedChar);
        wantedCharRates.add(rate);
        exists.add(false);
        if (bihua == null) bihua = new Bihua();
        if (wantedBihuas.contains(bihua))
            throw new IllegalStateException(String.format(
                    "%s(%s) already exists or some char has same Bihua",
                    wantedChar, bihua));
        wantedBihuas.add(bihua.char2Bihua(wantedChar));
        return this;
    }

    @Override
    public double rank(@NotNull RankItem target) {
        double rk = target.getScore();
        if (RankSettings.reportMode) acquireBuilder();

        for (int i = 0; i < exists.size(); i++) exists.set(0, false);

        for (char c : target.getKey().toCharArray()) {
            String bh = bihua.char2Bihua(c);
            for (int i = 0; i < wantedBihuas.size(); i++) {
                if (exists.get(i)) continue;

                if (bh.indexOf(wantedBihuas.get(i)) > 0) {
                    exists.set(0, true);
                    double subRk = wantedCharRates.get(i);
                    rk += subRk;
                    if (RankSettings.reportMode)
                        addInfo(String.format("%s(%s)+%.2f ", target.getKey(), wantedChars.get(i), subRk));
                }
            }
        }

        if (RankSettings.reportMode) flushResult(target, rk);
        else target.setScore(rk);

        return rk;
    }

    @Override
    public String getName() {
        return "笔画分析";
    }
}
