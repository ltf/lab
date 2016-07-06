package ltf.namerank.rank.dictrank.support.dict;

import ltf.namerank.rank.RankConfig;
import ltf.namerank.rank.dictrank.support.WordFeelingRank;

import java.util.ArrayList;
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
    protected MdxtItem newItem(String key) {
        return new HanyuDacidianItem(key);
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


    private static class HanyuDacidianItem extends MdxtItem {

        private static final String prefix = ".&nbsp;`3`&nbsp;";
        private static final String suffix = "`7`";
        private String prefix2;


        private List<String> explains = new ArrayList<>();

        HanyuDacidianItem(String key) {
            super(key);
            System.out.println(key);
            prefix2 = "`1`" + key + "`6`<br>";
        }

        @Override
        protected void addValue(String valueLine) {
            super.addValue(valueLine);
            if (valueLine == null)
                return;
            else if (valueLine.contains(prefix)) {
                int from = valueLine.indexOf(prefix) + prefix.length();
                int to = valueLine.indexOf(suffix, from);
                explains.add(valueLine.substring(from, to));
                System.out.println("\t\t\t\t" + valueLine.substring(from, to));
            } else if (valueLine.startsWith(prefix2)){
                int from = valueLine.indexOf(prefix) + prefix.length();
                int to = valueLine.indexOf(suffix, from);
                explains.add(valueLine.substring(prefix2.length()));
                System.out.println("\t\t\t\t" + valueLine.substring(prefix2.length()));
            }
        }

        @Override
        protected void finishAdd() {
            super.finishAdd();
        }

        @Override
        protected boolean isValid() {
            return super.isValid();
        }
    }
}
