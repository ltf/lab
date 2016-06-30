package ltf.namerank.rank.dictrank.support.dict;

import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/6/30, 上午10:27
 */
public class ChengyuCidian extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/成语.txt";
    }
}
