package ltf.namerank.rank.wuxing;

import ltf.namerank.rank.RankFilter;
import ltf.namerank.rank.dictrank.support.Bihua;

/**
 * @author ltf
 * @since 16/7/12, 下午12:43
 */
public class WugeFilter implements RankFilter {

    Bihua bihua = new Bihua();

    @Override
    public boolean banned(String givenName) {
        Integer b0 = bihua.bihuashu(givenName.charAt(0));
        Integer b1 = bihua.bihuashu(givenName.charAt(1));
        if (b0 == null || b1 == null) {
            System.out.println(String.format("%s %d  %d", givenName, b0, b1));
            return false;
        }

        if ((b0 == 18 & b1 == 6)
                || (b0 == 11 & b1 == 5)
                || (b0 == 8 & b1 == 10)
                || (b0 == 9 & b1 == 15)
                || (b0 == 9 & b1 == 16)) return false;


        return true;
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
