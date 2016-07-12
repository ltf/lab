package ltf.namerank.rank.dictrank.support;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

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
            List<String[]> pinyinsList = new ArrayList<>();
            int count = 1;
            for (char c : word.toCharArray()) {
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(word.charAt(0));
                if (pinyins == null || pinyins.length == 0) continue;
                pinyinsList.add(pinyins);
                count *= pinyins.length;
            }
            String[] result = new String[count];
            for (int i = 0; i < result.length; i++) result[i] = "";


            for (int i = 0; i < count; i++) {
                for (int pos = 0; pos < pinyinsList.size(); pos++) {
                    String[] pinyins = pinyinsList.get(pos);
                    result[i] += pinyins[i % pinyins.length];
                }
            }

            // make the most used pinyin first
            String firstPinyin = PinyinHelper.toHanYuPinyinString(word, format, "", true);
            for (int i = 0; i < count; i++) {
                if (firstPinyin.equals(result[i])) {
                    result[i] = result[0];
                    result[0] = firstPinyin;
                    break;
                }
            }

            return result;
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


    public static Pinyin[] toPinyin(char c) {
        String[] pys = toPinyin(String.valueOf(c));
        Pinyin[] rs = new Pinyin[pys.length];
        for (int i = 0; i < pys.length; i++) {
            rs[i] = parse(pys[i]);
        }
        return rs;
    }

    public static Pinyin parse(String onePinyin) {
        Pinyin pinyin = new Pinyin();
        char[] cs = onePinyin.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            switch (cs[i]) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    pinyin.tone += cs[i];
                    break;
                case 'b':
                case 'c':
                case 'd':
                case 'f':
                case 'h':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'p':
                case 'q':
                case 's':
                case 't':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    pinyin.shengmu += cs[i];
                    break;

                case 'r':
                case 'g':
                case 'n':
                    if (i == 0) pinyin.shengmu += cs[i];
                    else pinyin.yunmu += cs[i];
                    break;

                case 'a':
                case 'e':
                case 'u':
                case 'v':
                case 'i':
                case 'o':
                    pinyin.yunmu += cs[i];
            }
        }
        return pinyin;
    }

    public static class Pinyin {
        public String shengmu = "";
        public String yunmu = "";
        public String tone = "";
    }
}
