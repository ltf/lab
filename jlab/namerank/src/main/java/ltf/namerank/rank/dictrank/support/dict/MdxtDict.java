package ltf.namerank.rank.dictrank.support.dict;

import ltf.namerank.rank.RankRecord;
import ltf.namerank.utils.LinesInFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 16/6/21, 下午4:40
 */
abstract public class MdxtDict {

    private final Logger logger = LoggerFactory.getLogger(MdxtDict.class);
    private static final String ITEM_END_LINE = "</>";

    abstract protected String getFileName();

    private int count = 0;
    private List<MdxtItem> items;

    private void initItems() {
        if (items == null) {
            items = new ArrayList<>();
            try {
                loadItems();
            } catch (IOException e) {
                logger.warn("load dictionary failed: " + getFileName(), e);
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

    public void rank(String word, RankRecord record) {

    }

    public void listKeys() {
        initItems();
        for (MdxtItem item : items)
            System.out.println(item.getKey());
    }
}


