package ltf.namerank.rank.dictrank.support.dict;

import ltf.namerank.rank.RankConfig;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.dictrank.support.WordFeelingRank;
import ltf.namerank.utils.LinesInFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltf
 * @since 16/6/21, 下午4:40
 */
abstract public class MdxtDict implements Ranker {

    private final Logger logger = LoggerFactory.getLogger(MdxtDict.class);
    private static final String ITEM_END_LINE = "</>";

    abstract protected String getFileName();

    private int count = 0;
    private Map<String, List<MdxtItem>> itemsMap;

    private void initItems() {
        if (itemsMap == null) {
            itemsMap = new HashMap<>();
            try {
                loadItems();
            } catch (IOException e) {
                logger.warn("load dictionary failed: " + getFileName(), e);
            }
        }
    }

    private void loadItems() throws IOException {
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
            if (item.isValid()) {
                List<MdxtItem> items = itemsMap.get(item.getKey());
                if (items == null) {
                    items = new ArrayList<>(5);
                    itemsMap.put(item.getKey(), items);
                }
                items.add(item);
            }
            item = null;
        } else {
            item.addValue(line);
        }
        count++;
    }

    @Override
    public double rank(String target, RankConfig config) {
        initItems();
        double rk = 0;
        List<MdxtItem> items = itemsMap.get(target);
        if (items != null) {
            for (MdxtItem item : items) {
                rk += WordFeelingRank.getInstance().rank(item.getValue(), config);
            }
        }
        return rk;
    }

    public void listKeys() {
        initItems();
        for (String itemKey : itemsMap.keySet())
            System.out.println(itemKey);
    }
}


