package ltf.namerank.rank.dictrank.support.dict;

import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/7/11, 上午8:57
 */
public class KangxiZidian extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/康熙字典.txt";
    }


    @Override
    protected MdxtItem newItem(String key) {
        return new KangxiItem(key);
    }


    private static class KangxiItem extends MdxtItem {

        KangxiItem(String key) {
            super(key);
        }


        @Override
        protected void addValue(String valueLine) {
            super.addValue(valueLine);
        }

        @Override
        protected void finishAdd() {
            super.finishAdd();
        }
    }
}
