package ltf.namerank.rank.dictrank.support.dict;

import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/7/11, 上午8:23
 */
public class HanyuCipinCd extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/汉语词频.txt";
    }

    @Override
    protected MdxtItem newItem(String key) {
        return new CipinItem(key);
    }


    private static class CipinItem extends MdxtItem {

        CipinItem(String key) {
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
