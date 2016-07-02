package ltf.namerank.rank;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.FileUtils.str2File;
import static ltf.namerank.utils.PathUtils.getCacheHome;

/**
 * @author ltf
 * @since 16/7/1, 下午5:01
 */
public class CachedRanker extends WrappedRanker {

    private Logger logger = LoggerFactory.getLogger(CachedRanker.class);

    private Map<String, Double> rankCache = new HashMap<>();

    public CachedRanker(Ranker ranker) {
        super(ranker);
        loadCache();
    }

    @Override
    public double rank(String target, RankConfig config) {
        if (rankCache.containsKey(target))
            return rankCache.get(target);

        double rk = super.rank(target, config);
        rankCache.put(target, rk);
        return rk;
    }


    private String getCacheFilename() {
        return getCacheHome() + "/" + getInnerRanker().getClass().getCanonicalName() + ".cache";
    }

    private void saveCache() {
        try {
            str2File(JSON.toJSONString(rankCache, true), getCacheFilename());
        } catch (Exception e) {
            logger.error("save cache failed", e);
        }
    }

    private void loadCache() {
        try {
            if (new File(getCacheFilename()).exists()) {
                rankCache = (HashMap<String, Double>) JSON.parseObject(file2Str(getCacheFilename()), HashMap.class);
            }
        } catch (Exception e) {
            logger.error("load cache failed", e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        saveCache();
    }
}
