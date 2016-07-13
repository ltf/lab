package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.FileUtils.fromJsData;
import static ltf.namerank.utils.PathUtils.getJsonHome;

/**
 * 笔画数据
 *
 * @author ltf
 * @since 16/7/11, 上午10:19
 */
public class Bihua {

    private Map<String, String> bihuaMap = null;
    private Map<Character, Integer> xmxBihuashu = null;

    private void checkOrLoadBihua() {
        if (bihuaMap == null) {
            try {
                bihuaMap = (HashMap<String, String>) JSON.parseObject(file2Str(getJsonHome() + "/bihua.json"), HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String char2Bihua(char zi) {
        checkOrLoadBihua();
        return bihuaMap.get(String.valueOf(zi));
    }

    private void checkOrLoadBihuaShu() {
        if (xmxBihuashu == null) {
            try {
                xmxBihuashu = fromJsData("xingmingxue_pdf_bihua", new TypeReference<HashMap<Character, Integer>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer bihuashu(char zi) {
        checkOrLoadBihuaShu();
        return xmxBihuashu.get(zi);
    }
}
