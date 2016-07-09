package ltf.namerank.rank;

import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankItemHelper.addInfo;
import static ltf.namerank.rank.RankItemHelper.flushResult;

/**
 * @author ltf
 * @since 16/7/1, 下午5:14
 */
public class AllCasesRanker extends WrappedRanker {

    public AllCasesRanker(Ranker ranker) {
        super(ranker);
    }

    private List<String> allCases(String word) {
        ArrayList<String> result = new ArrayList<>();

        for (int len = word.length(); len > 0; len--) {
            for (int offset = 0; offset <= word.length() - len; offset++) {
                result.add(word.substring(offset, offset + len));
            }
        }

        return result;
    }

    @Override
    public double rank(RankItem target) {
        double rk = 0;
        for (String word : allCases(target.getKey())) {
            double childRk = super.rank(target.newChild(word));
            double rate = word.length() / target.getKey().length();
            rk += childRk * rate;
            addInfo(String.format("%s: %f x %f; ", word, childRk, rate));
        }
        flushResult(target, rk);
        return rk;
    }
}
