package ltf.namerank.rank.dictrank.meaning;

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
    public double rank(RankItem target) {
        double rk = 0;
        double childRk;
        if (RankSettings.reportMode) acquireBuilder();

        childRk = isSame(target.getKey(), positiveSet) * 20;
        if (RankSettings.reportMode) addInfo(String.format("褒: %.1f\t", childRk));
        rk += childRk;

        childRk = isSame(target.getKey(), butySet) * 60;
        if (RankSettings.reportMode) addInfo(String.format("Buty: %.1f\t", childRk));
        rk += childRk;

        childRk = isSame(target.getKey(), happySet) * 60;
        if (RankSettings.reportMode) addInfo(String.format("Happy: %.1f\t", childRk));
        rk += childRk;

        childRk = isSame(target.getKey(), goodSet) * 60;
        if (RankSettings.reportMode) addInfo(String.format("Good: %.1f\t", childRk));
        rk += childRk;

        childRk = isSame(target.getKey(), badSet) * -20;
        if (RankSettings.reportMode) addInfo(String.format("<font color=red>Bad: %.1f</font>", childRk));
        rk += childRk;

        childRk = isSame(target.getKey(), negativeSet) * -20;
        if (RankSettings.reportMode) addInfo(String.format("<font color=red>贬: %.1f\t</font>", childRk));
        rk += childRk;

        if (RankSettings.reportMode) flushResult(target, rk);
        else target.setScore(rk);
        return rk;
    }

    @Override
    public String getName() {
        return "字义";
    }
}