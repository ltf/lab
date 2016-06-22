package ltf.namerank.rank.pronounce.dict;

import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 16/6/21, 下午4:40
 */
public class MdxTxtDict {

    private String fileName;
    private int count = 0;
    private List<MdxTxtDictItem> items = new ArrayList<>();


    public MdxTxtDict(String fileName) {
        this.fileName = fileName;
    }

    private void loadKeys() throws IOException {
        new LinesInFile(fileName).each(this::parseLine);
    }

    private MdxTxtDictItem item = null;
    private static final String ITEM_END_LINE = "</>";

    private void parseLine(String line) {
        if (item == null) {
            item = new MdxTxtDictItem(line);
            items.add(item);
        } else if (ITEM_END_LINE.equals(line)) {
            item = null;
        } else {
            item.addValue(line);
        }
        count++;
    }

    public void test() {
        try {
            loadKeys();
            items.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(items.size());
        System.out.println(count);
    }
}
