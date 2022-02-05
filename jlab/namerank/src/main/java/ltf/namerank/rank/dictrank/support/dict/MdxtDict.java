package ltf.namerank.rank.dictrank.support.dict;

import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.dictrank.support.PinyinMap;
import ltf.namerank.utils.LinesInFile;
import ltf.namerank.utils.Rtc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ltf.namerank.rank.RankItemHelper.acquireBuilder;
import static ltf.namerank.rank.RankItemHelper.addInfo;
import static ltf.namerank.rank.RankItemHelper.flushResult;

/**
 * @author ltf
 * @since 16/6/21, 下午4:40
 */
abstract public class MdxtDict implements Ranker {

    private final Logger logger = LoggerFactory.getLogger(MdxtDict.class);
    private static final String ITEM_END_LINE = "</>";

    abstract protected String getFileName();

    private int count = 0;

    protected Map<String, List<MdxtItem>> itemsMap;


    protected void initItems() {
        Rtc.begin();
        if (itemsMap == null) {
            itemsMap = new HashMap<>();
            try {
                loadItems();
            } catch (IOException e) {
                logger.warn("load dictionary failed: " + getFileName(), e);
            }
        }
        Rtc.end();
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
            item.finishAdd();
            if (item.isValid()) {
                List<MdxtItem> items = itemsMap.get(item.getKey());
                if (items == null) {
                    items = new ArrayList<>(5);
                    itemsMap.put(item.getKey(), items);

                    // add into pinyin manager for reverse search
                    PinyinMap.add(item.getKey());
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
    public double rank(RankItem target) {
        initItems();
        double rk = 0;
        List<MdxtItem> items = itemsMap.get(target.getKey());
        if (RankSettings.reportMode) acquireBuilder();
        if (items != null) {
            int i = 1;
            for (MdxtItem item : items) {
                double childRk = item.rank(target.newChild());
                rk += childRk;
                if (RankSettings.reportMode) addInfo(String.format("\n%d: %.1f; ", i++, childRk));
            }
        }
        if (RankSettings.reportMode) flushResult(target, rk); else target.setScore(rk);
        return rk;
    }

    public void listKeys() {
        initItems();
        for (String itemKey : itemsMap.keySet())
            System.out.println(itemKey);

        System.out.println(itemsMap.size());
    }

    public Map<String, List<MdxtItem>> getItemsMap() {
        return itemsMap;
    }

}


