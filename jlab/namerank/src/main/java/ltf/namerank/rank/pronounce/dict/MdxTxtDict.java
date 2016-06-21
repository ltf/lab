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
    private List<String> keys = new ArrayList<>();
    private List<String> keyLines = new ArrayList<>();


    public MdxTxtDict(String fileName) {
        this.fileName = fileName;
    }

    private void loadKeys() throws IOException {
        new LinesInFile(fileName).each(this::parseLine);
    }

    private boolean keyLine = true;
    private static final String ITEM_END_LINE = "</>";

    private void parseLine(String line) {
        if (keyLine) {
            keys.add(line);
            keyLine = false;
        } else if (ITEM_END_LINE.equals(line)) {
            keyLine = true;
        }
        count++;
    }

    public void test() {
        try {
            loadKeys();
            keys.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(keys.size());
        System.out.println(count);
    }
}
