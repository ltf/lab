package ltf.namerank.rank.pronounce.dict;

import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * dict base on Mdx Text format [mdict text format data]
 *
 * @author ltf
 * @since 16/6/21, 下午4:40
 */
public class MdxtDict<VT extends ItemValue> {
    private static final String ITEM_END_LINE = "</>";
    private String fileName;
    private int count = 0;
    private Map<String, VT> items = new HashMap<>();

    private ItemValueParser itemValueParser = null;

    public MdxtDict(String fileName) {
        this.fileName = fileName;
    }

    private void loadKeys() throws IOException {
        new LinesInFile(fileName).each(this::parseLine);
    }

    private void initParser(VT vt) {
        VT v = new VT();
        itemValueParser = vt.getParser();
    }

    private String key = null;

    private void parseLine(String line) {
        if (key == null) {
            key = line;
            itemValueParser.init(key);
        } else if (ITEM_END_LINE.equals(line)) {
            VT value = itemValueParser.build();
            if (value != null) items.put(key, value);
            key = null;
        } else {
            itemValueParser.addValueLine(line);
        }
        count++;
    }

    public void test() {
        try {
            loadKeys();
            //items.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(items.size());
        System.out.println(count);
    }
}
