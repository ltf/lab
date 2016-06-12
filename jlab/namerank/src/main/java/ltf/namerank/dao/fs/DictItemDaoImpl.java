package ltf.namerank.dao.fs;

import ltf.namerank.dao.DictItemDao;
import ltf.namerank.entity.DictItem;
import ltf.namerank.utils.FileUtils;
import ltf.namerank.utils.PathUtils;

import java.io.File;
import java.util.Collection;

/**
 * @author ltf
 * @since 6/11/16, 5:17 PM
 */
public class DictItemDaoImpl implements DictItemDao {

    @Override
    public void saveDictItem(DictItem dictItem) {
        File f = new File(PathUtils.getJsonHome(), dictItem.getZi());

        if (f.exists()) {

        }

        //FileUtils.str2File(, );


    }

    @Override
    public Collection<DictItem> loadItemsByZi(String zi) {
        return null;
    }
}
