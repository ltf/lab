package ltf.namerank.rank.dictrank.support.dict;

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





}
