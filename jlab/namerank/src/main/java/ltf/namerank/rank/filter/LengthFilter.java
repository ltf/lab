package ltf.namerank.rank.filter;

import ltf.namerank.rank.RankFilter;

/**
 * @author ltf
 * @since 16/7/12, 下午5:10
 */
public class LengthFilter implements RankFilter {

    @Override
    public boolean banned(String givenName) {
        return givenName.length() < 2;
    }
}
