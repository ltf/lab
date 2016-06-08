package ltf.namerank.dao;

import ltf.namerank.entity.Hanzi;

import java.util.Collection;

/**
 * @author ltf
 * @since 16/6/8, 上午10:28
 */
public interface HanziDao {
    void saveHanzi(Collection<Hanzi> hanzi);
    void saveHanzi(Hanzi hanzi);
    Collection loadHanzi(String kword);
}
