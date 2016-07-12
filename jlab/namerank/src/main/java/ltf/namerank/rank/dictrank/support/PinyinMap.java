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
        return toPinyin(word, true);
    }

    public static String[] toPinyin(String word, boolean onePinyinForMutliChars) {
        try {
            String firstPinyin = PinyinHelper.toHanYuPinyinString(word, format, "", true);

            if (onePinyinForMutliChars && word.length() > 1) {
                String[] result = new String[1];
                result[0] = firstPinyin;
                return result;
            }


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
        String[] pys = toPinyin(String.valueOf(c), false);
        Pinyin[] rs = new Pinyin[pys.length];
        for (int i = 0; i < pys.length; i++) {
            rs[i] = parse(pys[i]);
        }
        return rs;
    }

    public static Pinyin parse(String onePinyin) {

//        所有元音都可以充当韵腹，能作韵头的只有（）、（）、（），能作韵尾的只有（）、（）、（）三个元音和（）、（）两个辅音。
//        参考答案
//        i；u；v；    i；u；o；n；ng

//        韵腹是韵母的（），位置在韵腹（）的是韵头，在韵腹（）的是韵尾。（）一定是韵尾。一个韵母可以没有（），但一定有韵腹。
//        参考答案
//        主要成分；前；后；韵母中的辅音；韵头或韵尾
        Pinyin pinyin = new Pinyin();
        char[] cs = onePinyin.toCharArray();
        boolean yunTouFin = false;

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
                    pinyin.shengMu += cs[i];
                    break;

                case 'r':
                case 'g':
                case 'n':
                    if (i == 0) pinyin.shengMu += cs[i];
                    else {
                        pinyin.yunMu += cs[i];
                        if (!"".equals(pinyin.yunFu)) {
                            pinyin.yunWei += cs[i];
                        }
                    }
                    break;

                case 'a':
                case 'e':
                case 'o':
                    yunTouFin = true;

                case 'u':
                case 'v':
                case 'i':
                    pinyin.yunMu += cs[i];

                    if (!yunTouFin) {
                        pinyin.yunTou += cs[i];
                        yunTouFin = true;
                    } else {
                        if (pinyin.yunFu.length() == 0) pinyin.yunFu += cs[i];
                        else {
                            pinyin.yunWei += cs[i];
                            if ('i' != cs[i] && 'u' != cs[i] && 'o' != cs[i])
                                throw new IllegalStateException("韵尾 parse error");
                        }
                    }
                    break;
            }
        }

        if (pinyin.yunFu.length() == 0) {
            pinyin.yunFu = pinyin.yunTou;
            pinyin.yunTou = "";
        }
        return pinyin;
    }

    public static class Pinyin {
        public String shengMu = "";
        public String yunMu = "";
        public String tone = "";
        public String yunTou = "";
        public String yunFu = "";
        public String yunWei = "";
    }
}
