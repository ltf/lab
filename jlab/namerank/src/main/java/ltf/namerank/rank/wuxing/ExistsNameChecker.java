package ltf.namerank.rank.wuxing;

import ltf.namerank.rank.WordExistChecker;
import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.HashSet;

import static ltf.namerank.utils.PathUtils.getNamesHome;

/**
 * @author ltf
 * @since 2022/3/2, 16:54
 */
public class ExistsNameChecker implements WordExistChecker {

    private HashSet<String> givenNames;

    public ExistsNameChecker(String file) {
        givenNames = new HashSet<>();
        try {
            new LinesInFile(file).each(givenNames::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean exists(String word) {
        return givenNames.contains(word);
    }
}
