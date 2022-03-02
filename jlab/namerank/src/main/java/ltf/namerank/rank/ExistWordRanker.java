package ltf.namerank.rank;

import static ltf.namerank.rank.RankItemHelper.*;

/**
 * @author ltf
 * @since 7/11/16, 4:23 AM
 */
public class ExistWordRanker implements Ranker {

    public ExistWordRanker(WordExistChecker checker, double rankDelta) {
        this.checker = checker;
        this.rankDelta = rankDelta;
    }

    private WordExistChecker checker;
    private double rankDelta;


    @Override
    public double rank(RankItem target) {
        double rk = 0;
        if (RankSettings.reportMode) acquireBuilder();
        if (checker.exists(target.getKey())) {
            rk += rankDelta;
            if (RankSettings.reportMode) addInfo(String.format("%s: normal word %.1f ", target.getKey(), rankDelta));
        }

        if (RankSettings.reportMode) flushResult(target, rk);
        else target.setScore(rk);

        return rk;
    }

    @Override
    public String getName() {
        return "常用词分析";
    }
}
