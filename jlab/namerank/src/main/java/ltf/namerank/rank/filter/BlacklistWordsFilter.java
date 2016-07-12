package ltf.namerank.rank.filter;

import ltf.namerank.rank.RankFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static ltf.namerank.utils.FileUtils.file2Lines;

/**
 * @author ltf
 * @since 16/7/12, 上午9:13
 */
public class BlacklistWordsFilter implements RankFilter {

    private Set<String> bannedWords = new HashSet<>();

    @Override
    public boolean banned(String givenName) {
        return !bannedWords.contains(givenName);
    }

    public BlacklistWordsFilter addChars(String fn) {
        try {
            file2Lines(fn, bannedWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
