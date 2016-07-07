package ltf.namerank.rank.dictrank.support;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static ltf.namerank.utils.FileUtils.file2Lines;
import static ltf.namerank.utils.PathUtils.getWordsHome;

/**
 * @author ltf
 * @since 16/7/7, 下午4:55
 */
public class Words {
    private Set<String> negative = new HashSet<>();
    private Set<String> positive = new HashSet<>();
    private Set<String> buty = new HashSet<>();


    private Words() {
        try {
            file2Lines(getWordsHome() + "/positive.txt", positive);
            file2Lines(getWordsHome() + "/negative.txt", negative);
            file2Lines(getWordsHome() + "/buty.txt", buty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNegative(String word) {
        return Holder.getInstance().negative.contains(word);
    }

    public static boolean isPositive(String word) {
        return Holder.getInstance().positive.contains(word);
    }

    public static boolean isButy(String word) {
        return Holder.getInstance().buty.contains(word);
    }

    private static class Holder {
        private static Words words = new Words();

        public static Words getInstance() {
            return words;
        }
    }
}
