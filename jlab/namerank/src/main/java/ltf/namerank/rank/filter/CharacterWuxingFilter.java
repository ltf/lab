package ltf.namerank.rank.filter;

import ltf.namerank.rank.RankFilter;

import java.io.IOException;
import java.util.HashSet;

import static ltf.namerank.utils.FileUtils.fromLinesData;

/**
 * @author ltf
 * @since 2022/3/2, 20:35
 */
public class CharacterWuxingFilter implements RankFilter {
    private final HashSet<Character> wuxingChars = new HashSet<>();

    public CharacterWuxingFilter() throws IOException {
        fromLinesData("wuxing_huo").forEach(s -> wuxingChars.add(s.charAt(0)));
    }

    @Override
    public boolean banned(String givenName) {
        for (char c : givenName.toCharArray()) {
            if (wuxingChars.contains(c)) return false;
        }
        return true;
    }
}
