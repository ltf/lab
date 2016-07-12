package ltf.namerank.dao;

import ltf.namerank.entity.DictItem;

import java.util.Collection;

/**
 * @author ltf
 * @since 16/6/8, 上午10:28
 */
public interface DictItemDao {
    void saveDictItem(DictItem dictItem);

    DictItem loadItemsByZi(String zi);
}
