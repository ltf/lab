package ltf.namerank.lab;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Shengmu;
import com.hankcs.hanlp.dictionary.py.Yunmu;
import ltf.namerank.rank.dictrank.support.PinyinMap;
import ltf.namerank.utils.LinesInFile;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.util.*;

import static ltf.namerank.rank.dictrank.support.PinyinMap.toPinyin;
import static ltf.namerank.utils.FileUtils.file2Lines;
import static ltf.namerank.utils.PathUtils.getNamesHome;

/**
 * @author ltf
 * @since 16/6/15, 下午5:00
 */
public class PinyinTest {
    public void go() {
        //collectShengYun();
        try {
            testAllNames();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (String s : PinyinHelper.toHanyuPinyinStringArray('重'))
//            System.out.println(s);

        //testHanPinyin();

        //testLoadMdx();
//        try {
//            testToPinyin();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void collectShengYun() {
        Set<String> sheng = new HashSet<>();
        Set<String> yun = new HashSet<>();
        for (Yunmu v : Yunmu.values()) {
            if (v.name().equals("none")) continue;
            char[] cs = v.name().toCharArray();
            for (int i = 0; i < cs.length; i++) yun.add(cs[i] + "");
        }

        for (Shengmu v : Shengmu.values()) {
            if (v.name().equals("none")) continue;
            char[] cs = v.name().toCharArray();
            for (int i = 0; i < cs.length; i++) sheng.add(cs[i] + "");
        }

        for (String c : sheng) {
            if (yun.contains(c)) System.out.print("//");
            System.out.println("case '" + c + "':");
        }

        System.out.println("");
        for (String c : yun) {
            if (sheng.contains(c)) System.out.print("//");
            System.out.println("case '" + c + "':");
        }

    }

    private void testAllNames() throws IOException {


        List<String> list = new ArrayList<>();
        Set<Character> chars = new HashSet<>();
        file2Lines(getNamesHome() + "/givenNames.txt", list);

        for (String name : list) {
            for (char c : name.toCharArray()) {

                chars.add(c);
            }
        }

        for (char c : chars) {
            System.out.print(c + " :");
            PinyinMap.Pinyin[] pys = toPinyin(c);
            for (PinyinMap.Pinyin py : pys) {
                System.out.print(py.shengMu + "-" + py.yunTou + "." + py.yunFu + "." + py.yunWei + "-" + py.tone + " ");
            }
            System.out.println("");
        }


//        Set<String> multiPyChars = new HashSet<>();
//        for (String name : chars) {
//            String pys[] = PinyinMap.toPinyin(name);
//            if (pys == null) {
//                System.out.println(name + " has no pinyin");
//                continue;
//            }
//            if (pys.length > 1) {
//                System.out.print(name + " ");
//                for (String py : pys) System.out.print(py + " ");
//
//                try {
//                    System.out.print(PinyinHelper.toHanYuPinyinString(name, new HanyuPinyinOutputFormat(), "", true));
//                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
//                    badHanyuPinyinOutputFormatCombination.printStackTrace();
//                }
//                System.out.println("");
//                multiPyChars.add(name);
//            }
//        }

        //lines2File(multiPyChars, getWordsHome()+"/multiPinyiChars.txt");

    }

    private void testLoadMdx() {
        //new MdxtDict(getRawHome()+"/mdx/多功能汉语辞典.txt").test();
    }


    private void testHanPinyin() {
        String text = "重载不是重任";
        List<com.hankcs.hanlp.dictionary.py.Pinyin> pinyinList = HanLP.convertToPinyinList(text);

        System.out.print("原文,");
        for (char c : text.toCharArray()) {
            System.out.printf("%c,", c);
        }
        System.out.println();

        System.out.print("拼音（数字音调）,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin);
        }
        System.out.println();

        System.out.print("拼音（符号音调）,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getPinyinWithToneMark());
        }
        System.out.println();

        System.out.print("拼音（无音调）,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getPinyinWithoutTone());
        }
        System.out.println();

        System.out.print("声调,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getTone());
        }
        System.out.println();

        System.out.print("声母,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getShengmu());
        }
        System.out.println();

        System.out.print("韵母,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getYunmu());
        }
        System.out.println();

        System.out.print("输入法头,");
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
            System.out.printf("%s,", pinyin.getHead());
        }
        System.out.println();
    }

    private String Han2Pinyin(String in) {
        List<com.hankcs.hanlp.dictionary.py.Pinyin> pinyinList = HanLP.convertToPinyinList(in);

        StringBuilder sb = new StringBuilder();
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyinList) {
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
            new LinesInFile(getNamesHome() + "/givenNames.txt").each(names::add);
        }
    }

}
