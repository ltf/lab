package ltf.namerank.dataprepare;

import ltf.namerank.dao.fs.DictItemDaoImpl;
import ltf.namerank.entity.DictItem_Bm8;
import ltf.namerank.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static ltf.namerank.utils.FileUtils.toJsData;

/**
 * @author ltf
 * @since 2022/2/28, 22:45
 */
public class CombineDictItems {
    private DictItemDaoImpl dictItemDao = new DictItemDaoImpl();

    public void combine() throws IOException {
        HashMap<String, DictItem_Bm8> items = new HashMap<>();
        File dir = new File(PathUtils.getJsonHome() + "/dict");

        for (File f : dir.listFiles()) {
            DictItem_Bm8 itm = (DictItem_Bm8) dictItemDao.loadItemsByZi(f.getName());
            items.putIfAbsent(itm.getZi(), itm);
        }

        System.out.println(dir.listFiles().length);
        System.out.println(items.size());
        toJsData(items, "dictInOne");
    }
    
}
