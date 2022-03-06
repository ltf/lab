package ltf.namerank.rank.filter;

import ltf.namerank.rank.RankFilter;
import ltf.namerank.rank.WordExistChecker;

/**
 * @author ltf
 * @since 2022/3/3, 21:54
 */
public class KeepExistsWordFilter implements RankFilter {
    private final WordExistChecker checker;

    public KeepExistsWordFilter(WordExistChecker checker) {
        this.checker = checker;
    }

    @Override
    public boolean banned(String givenName) {
        return !checker.exists(givenName);
    }
}
