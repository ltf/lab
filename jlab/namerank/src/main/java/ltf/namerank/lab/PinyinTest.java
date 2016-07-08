package ltf.namerank.lab;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import ltf.namerank.utils.LinesInFile;
import ltf.namerank.utils.PathUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ltf
 * @since 16/6/15, 下午5:00
 */
public class PinyinTest {
    public void go() {

        for (String s : PinyinHelper.toHanyuPinyinStringArray('重'))
            System.out.println(s);

        //testHanPinyin();

        //testLoadMdx();
//        try {
//            testToPinyin();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void testLoadMdx() {
        //new MdxtDict(getRawHome()+"/mdx/多功能汉语辞典.txt").test();
    }


    private void testHanPinyin() {
        String text = "重载不是重任";
        List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);

        System.out.print("原文,");
        for (char c : text.toCharArray()) {
            System.out.printf("%c,", c);
        }
        System.out.println();

        System.out.print("拼音（数字音调）,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin);
        }
        System.out.println();

        System.out.print("拼音（符号音调）,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getPinyinWithToneMark());
        }
        System.out.println();

        System.out.print("拼音（无音调）,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getPinyinWithoutTone());
        }
        System.out.println();

        System.out.print("声调,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getTone());
        }
        System.out.println();

        System.out.print("声母,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getShengmu());
        }
        System.out.println();

        System.out.print("韵母,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getYunmu());
        }
        System.out.println();

        System.out.print("输入法头,");
        for (Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getHead());
        }
        System.out.println();
    }

    private String Han2Pinyin(String in) {
        List<Pinyin> pinyinList = HanLP.convertToPinyinList(in);

        StringBuilder sb = new StringBuilder();
        for (Pinyin pinyin : pinyinList) {
            sb.append(pinyin);
        }

        return sb.toString();
    }

    private void testToPinyin() throws IOException {
        initNames();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        names.forEach(name -> {
            String py = null;
            try {
                py = PinyinHelper.toHanYuPinyinString(name, format, "", true);
                String hanPy = Han2Pinyin(name);
                String hanPy2 = HanLP.convertToPinyinString(name, "", false);
                if (!py.equals(hanPy)) {
                    System.out.println(String.format("%s: %s - %s - %s", name, py, hanPy, hanPy2));
                }

//                if (!py.equals(py.replaceAll("[\\u3400-\\u9FFF]", ""))) {
//                    System.out.println(py);
//                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        });
    }


    private List<String> names;

    private void initNames() throws IOException {
        if (names == null) {
            names = new LinkedList<>();
            new LinesInFile(PathUtils.getNamesHome() + "/givenNames.txt").each(names::add);
        }
    }

}
