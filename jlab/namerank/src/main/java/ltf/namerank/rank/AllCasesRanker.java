package ltf.namerank.rank;

import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankItemHelper.*;

/**
 * @author ltf
 * @since 16/7/1, 下午5:14
 */
public class AllCasesRanker extends WrappedRanker {

    private Character familyname = null;

    public AllCasesRanker(Ranker ranker) {
        super(ranker);
    }


    private List<Case> allCases(String word) {
        int fullLen = word.length();
        if (fullLen > 2) throw new IllegalStateException("Not support >2 charactors's name");
        ArrayList<Case> result = new ArrayList<>();
        char[] chars = word.toCharArray();

        if (fullLen == 1) {
            if (familyname != null) {
                result.add(new Case(familyname + "" + chars[0], 1));
            }
            result.add(new Case("" + chars[0], 1));
        } else if (fullLen == 2) {
            if (familyname != null) {
                result.add(new Case(familyname + "" + chars[0] + chars[1], 1));
                result.add(new Case("" + chars[0] + chars[1], 1));
                result.add(new Case("" + chars[0], 0.6));
                result.add(new Case("" + chars[1], 0.6));
                result.add(new Case(familyname + "" + chars[0], 0.2));
            } else {
                result.add(new Case("" + chars[0] + chars[1], 1));
                result.add(new Case("" + chars[0], 0.6));
                result.add(new Case("" + chars[1], 0.6));
            }
        }
        return result;
    }

    @Override
    public double rank(RankItem target) {
        double rk = 0;
        acquireBuilder();
        for (Case cs : allCases(target.getKey())) {
            if (cs.rate == 0) continue;
            double childRk = super.rank(target.newChild(cs.word));
            rk += childRk * cs.rate;
            addInfo(String.format("%s: %.1f x %.1f; ", cs.word, childRk, cs.rate));
        }
        flushResult(target, rk);
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

    public AllCasesRanker setFamilyname(Character familyname) {
        this.familyname = familyname;
        return this;
    }
}
