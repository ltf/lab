package ltf.namerank.rank.wuxing;

import ltf.namerank.rank.WordExistChecker;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.HashSet;

/**
 * @author ltf
 * @since 2022/3/2, 16:54
 */
public class ExistsWordsChecker implements WordExistChecker {

    private final HashSet<String> words;

    public ExistsWordsChecker(String file) {
        words = new HashSet<>();
        try {
            new LinesInFile(file).each(words::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean exists(String word) {
        return words.contains(word);
    }
}
