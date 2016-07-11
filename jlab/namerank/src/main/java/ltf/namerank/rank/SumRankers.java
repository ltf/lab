package ltf.namerank.rank;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankItemHelper.*;

/**
 * @author ltf
 * @since 16/7/1, 下午5:25
 */
public class SumRankers implements Ranker {

    private List<Ranker> rankers = new ArrayList<>();

    private List<Double> rates = new ArrayList<>();

    @Override
    public double rank(@NotNull RankItem target) {
        double rk = 0;
        if (RankSettings.reportMode) acquireBuilder();
        for (int i = 0; i < rankers.size(); i++) {
            double childRk = rankers.get(i).rank(target.newChild());
            rk += childRk * rates.get(i);
            if (RankSettings.reportMode) addInfo(String.format("%s: %.1f x %.1f; ", rankers.get(i).getName(), childRk, rates.get(i)));
        }
        if (RankSettings.reportMode) flushResult(target, rk); else target.setScore(rk);
        return rk;
    }

    public SumRankers addRanker(Ranker ranker, double rate) {
        rankers.add(ranker);
        rates.add(rate);
        return this;
    }
}
