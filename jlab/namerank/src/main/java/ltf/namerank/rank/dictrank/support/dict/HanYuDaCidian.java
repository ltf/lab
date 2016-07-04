package ltf.namerank.rank.dictrank.support.dict;

import ltf.namerank.rank.RankConfig;
import ltf.namerank.rank.dictrank.support.WordFeelingRank;

import java.util.List;

import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/6/30, 下午3:30
 */
public class HanYuDaCidian extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/汉语大词典简体精排.txt";
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
}
