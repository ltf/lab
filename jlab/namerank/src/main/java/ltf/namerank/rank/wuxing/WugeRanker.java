package ltf.namerank.rank.wuxing;

import com.sun.istack.internal.NotNull;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.Ranker;

/**
 * @author ltf
 * @since 16/7/12, 下午12:43
 */
public class WugeRanker implements Ranker {


    @Override
    public double rank(@NotNull RankItem target) {
        return 0;
    }

    private static class Wuge {
        private int tianGe;
        private int diGe;
        private int renGe;
        private int waiGe;
        private int zongGe;

        public Wuge(String familyName, String givenName) {
        }

        private void calculateWuge(String familyName, String givenName) {


        }
    }
}
