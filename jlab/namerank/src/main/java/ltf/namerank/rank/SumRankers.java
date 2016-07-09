package ltf.namerank.rank;

import com.sun.istack.internal.NotNull;

import static ltf.namerank.rank.RankItemHelper.addInfo;
import static ltf.namerank.rank.RankItemHelper.flushResult;

/**
 * @author ltf
 * @since 16/7/1, 下午5:25
 */
public class SumRankers implements Ranker {

    private Ranker[] rankers;

    public SumRankers(Ranker... rankers) {
        this.rankers = rankers;
    }

    @Override
    public double rank(@NotNull RankItem target) {
        double rk = 0;
        for (Ranker ranker : rankers) {
            double childRk = ranker.rank(target.newChild());
            rk += childRk;
            addInfo(String.format("%s: %f; ", ranker.getName(), childRk));
        }
        flushResult(target, rk);
        return rk;
    }
}
