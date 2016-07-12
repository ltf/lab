package ltf.namerank.rank.dictrank.support;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ltf
 * @since 16/7/8, 下午4:46
 */
public class PinyinMap {

    private static final Map<String, Set<String>> pinyin2Words = new HashMap<>();
    private static final Map<String, Set<String>> pinyin2WordsNoTone = new HashMap<>();
    private static final Set<String> noWords = new HashSet<>(1);

    private static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private static final HanyuPinyinOutputFormat formatNoTone = new HanyuPinyinOutputFormat();

    static {
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        formatNoTone.setVCharType(HanyuPinyinVCharType.WITH_V);
        formatNoTone.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }


    private static void addMapList(Map<String, Set<String>> map, String k, String v) {
        Set<String> list = map.get(k);
        if (list == null) {
            list = new HashSet<>();
            map.put(k, list);
        }
        list.add(v);
    }

    public static String[] toPinyin(String word) {
        try {
            if (word.length() == 1) {
                return PinyinHelper.toHanyuPinyinStringArray(word.charAt(0));
            } else {
                String[] result = new String[1];
                result[0] = PinyinHelper.toHanYuPinyinString(word, format, "", true);
                return result;
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return null;
    }

    public static String[] toPinyinNoTone(String word) {
        String[] result = toPinyin(word);
        if (result != null) {
            for (int i = 0; i < result.length; i++)
                result[i] = result[i].replaceAll("\\d", "");
        }

        return result;
    }

    public static void add(String word) {
        if (word == null) return;
        String[] pys = toPinyin(word);
        if (pys != null) {
            for (String py : pys) {
                addMapList(pinyin2Words, py, word);
                addMapList(pinyin2WordsNoTone, py.replaceAll("\\d", ""), word);
            }
        }
    }

    private static Set<String> innerGetWords(Map<String, Set<String>> map, String[] pinyins) {
        if (pinyins == null)
            return noWords;
        else if (pinyins.length == 1) {
            Set<String> result = map.get(pinyins[0]);
            if (result == null) result = noWords;
            return result;
        } else {
            Set<String> result = new HashSet<>();
            for (String py : pinyins) {
                Set<String> oneSet = map.get(py);
                if (oneSet == null) result.addAll(oneSet);
            }
            return result;
        }
    }

    public static Set<String> getWords(String word) {

        return innerGetWords(pinyin2Words, toPinyin(word));
    }

    public static Set<String> getWordsNoTone(String word) {
        return innerGetWords(pinyin2WordsNoTone, toPinyinNoTone(word));
    }
}
