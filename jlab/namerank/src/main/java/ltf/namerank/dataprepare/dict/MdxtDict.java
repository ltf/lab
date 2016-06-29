package ltf.namerank.dataprepare.dict;

import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 16/6/21, 下午4:40
 */
public class MdxtDict {
    private static final String ITEM_END_LINE = "</>";

    protected String getFileName() {
        return "";
    }

    private int count = 0;
    private List<MdxtItem> items;

    private void init(){
        if (items == null){
            items = new ArrayList<>();
            try {
                loadItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadItems() throws IOException {
        items = new ArrayList<>();
        new LinesInFile(getFileName()).each(this::parseLine);
    }

    protected MdxtItem newItem(String key) {
        return new MdxtItem(key);
    }

    private MdxtItem item = null;

    private void parseLine(String line) {
        if (item == null) {
            item = newItem(line);
        } else if (ITEM_END_LINE.equals(line)) {
            if (item.isValid()) items.add(item);
            item = null;
        } else {
            item.addValue(line);
        }
        count++;
    }
}


