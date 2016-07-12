package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.PathUtils.getJsonHome;

/**
 * 笔画数据
 *
 * @author ltf
 * @since 16/7/11, 上午10:19
 */
public class Bihua {

    private Map<String, String> bihuaMap = null;

    private void checkOrLoad() {
        if (bihuaMap == null) {
            try {
                bihuaMap = (HashMap<String, String>) JSON.parseObject(file2Str(getJsonHome() + "/bihua.json"), HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String char2Bihua(char zi) {
        checkOrLoad();
        return bihuaMap.get(String.valueOf(zi));
    }
}
