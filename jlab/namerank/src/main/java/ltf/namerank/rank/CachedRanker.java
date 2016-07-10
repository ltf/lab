package ltf.namerank.rank;

import com.alibaba.fastjson.JSON;
import ltf.namerank.service.TeardownListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName;
import static ltf.namerank.service.EvenManager.add;
import static ltf.namerank.utils.FileUtils.str2File;
import static ltf.namerank.utils.PathUtils.getCacheHome;

/**
 * @author ltf
 * @since 16/7/1, 下午5:01
 */
public class CachedRanker extends WrappedRanker implements TeardownListener {

    private Logger logger = LoggerFactory.getLogger(CachedRanker.class);

    private Map<String, SoftReference<RankItem>> rankCache = new HashMap<>();

    private CachedRanker(Ranker ranker) {
        super(ranker);
        loadCache();
    }

    @Override
    public String getName() {
        return getInnerRanker().getName();
    }

    @Override
    public double rank(RankItem target) {
        double rk;
        if (rankCache.containsKey(target.getKey())) {
            SoftReference<RankItem> ref = rankCache.get(target.getKey());
            RankItem cachedItem = ref.get();
            if (cachedItem != null) {
                target.setBy(cachedItem);
                rk = cachedItem.getScore();
                return rk;
            }
        }

        rk = super.rank(target);
        rankCache.put(target.getKey(), new SoftReference<RankItem>(target));
        return rk;
    }

    private String getCacheFilename() {
        return getCacheHome() + "/" + getInnerRanker().getClass().getCanonicalName() + ".cache";
    }

    private void saveCache() {
        try {
            str2File(JSON.toJSONString(rankCache, WriteClassName, PrettyFormat), getCacheFilename());
        } catch (Exception e) {
            logger.error("save cache failed", e);
        }
    }

    private void loadCache() {
        try {
            if (new File(getCacheFilename()).exists()) {
                //rankCache = (HashMap<String, RankItem>) JSON.parseObject(file2Str(getCacheFilename()), HashMap.class);
            }
        } catch (Exception e) {
            logger.error("load cache failed", e);
        }
    }

    protected void flush() {
        if (rankCache.size() > 0)
            saveCache();
    }

    public static CachedRanker cache(Ranker ranker) {
        CachedRanker cachedRanker = new CachedRanker(ranker);
        add(cachedRanker);
        return cachedRanker;
    }

    @Override
    public void onTeardown() {
        //flush();
    }
}
