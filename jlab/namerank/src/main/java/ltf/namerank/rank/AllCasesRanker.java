package ltf.namerank.rank;

import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankItemHelper.*;
import static ltf.namerank.rank.RankSettings.getFamilyName;

/**
 * @author ltf
 * @since 16/7/1, 下午5:14
 */
public class AllCasesRanker extends WrappedRanker {

    public AllCasesRanker(Ranker ranker) {
        super(ranker);
    }


    private List<Case> allCases(String word) {
        int fullLen = word.length();
        if (fullLen > 2) throw new IllegalStateException("Not support >2 charactors's name");
        ArrayList<Case> result = new ArrayList<>();
        char[] chars = word.toCharArray();

        if (fullLen == 1) {
            if (getFamilyName() != null) {
                result.add(new Case(getFamilyName() + "" + chars[0], 1));
            }
            result.add(new Case("" + chars[0], 1));
        } else if (fullLen == 2) {
            if (getFamilyName() != null) {
                result.add(new Case(getFamilyName() + "" + chars[0] + chars[1], 1));
                result.add(new Case("" + chars[0] + chars[1], 1));
                result.add(new Case("" + chars[0], 0.6));
                result.add(new Case("" + chars[1], 0.6));
                result.add(new Case(getFamilyName() + "" + chars[0], 0.2));
            } else {
                result.add(new Case("" + chars[0] + chars[1], 1));
                result.add(new Case("" + chars[0], 0.6));
                result.add(new Case("" + chars[1], 0.6));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "组合分析";
    }

    @Override
    public double rank(RankItem target) {
        double rk = 0;
        if (RankSettings.reportMode) acquireBuilder();
        for (Case cs : allCases(target.getKey())) {
            if (cs.rate == 0) continue;
            double childRk = super.rank(target.newChild(cs.word));
            rk += childRk * cs.rate;
            if (RankSettings.reportMode) addInfo(String.format("%s: %.1f x %.1f; ", cs.word, childRk, cs.rate));
        }
        if (RankSettings.reportMode) flushResult(target, rk);
        else target.setScore(rk);
        return rk;
    }

    private static class Case {
        private String word;
        private double rate;

        public Case(String word, double rate) {
            this.word = word;
            this.rate = rate;
        }
    }
}
