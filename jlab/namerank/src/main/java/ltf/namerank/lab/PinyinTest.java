package ltf.namerank.lab;

import ltf.namerank.rank.pronounce.dict.MdxTxtDict;
import ltf.namerank.utils.LinesInFile;
import ltf.namerank.utils.PathUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/6/15, 下午5:00
 */
public class PinyinTest {
    public void go() {
        testLoadMdx();
//        try {
//            testToPinyin();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void testLoadMdx() {
        new MdxTxtDict(getRawHome()+"/mdx/多功能汉语辞典.txt").test();
    }

    private void testToPinyin() throws IOException {
        initNames();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        names.forEach(name -> {
            String py = null;
            try {
                py = PinyinHelper.toHanYuPinyinString(name, format, " ", true);
                if (!py.equals(py.replaceAll("[\\u3400-\\u9FFF]", ""))) {
                    System.out.println(py);
                }
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
