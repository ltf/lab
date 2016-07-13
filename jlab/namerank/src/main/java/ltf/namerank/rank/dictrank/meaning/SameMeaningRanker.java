package ltf.namerank.rank.dictrank.meaning;

import com.sun.istack.internal.NotNull;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;

import static ltf.namerank.rank.RankItemHelper.*;
import static ltf.namerank.rank.dictrank.support.Words.*;

/**
 * @author ltf
 * @since 7/14/16, 1:01 AM
 */
public class SameMeaningRanker implements Ranker {
    @Override
    public double rank(@NotNull RankItem target) {
        double rk = 0;
        double childRk;
        if (RankSettings.reportMode) acquireBuilder();

        childRk = isSame(target.getKey(), butySet) * 6;
        if (RankSettings.reportMode) addInfo(String.format("Buty: %.1f", childRk));
        rk += childRk;

        childRk = isSame(target.getKey(), happySet) * 4;
        if (RankSettings.reportMode) addInfo(String.format(" Happy: %.1f", childRk));
        rk += childRk;


        if (RankSettings.reportMode) flushResult(target, rk);
        else target.setScore(rk);
        return rk;
    }
}
