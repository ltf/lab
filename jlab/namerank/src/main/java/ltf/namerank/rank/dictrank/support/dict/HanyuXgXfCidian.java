package ltf.namerank.rank.dictrank.support.dict;

import ltf.namerank.utils.LinesInFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static ltf.namerank.utils.FileUtils.lines2File;
import static ltf.namerank.utils.PathUtils.getRawHome;
import static ltf.namerank.utils.StrUtils.existsCount;

/**
 * 汉语相关相反词典
 *
 * @author ltf
 * @since 16/7/4, 上午9:49
 */
public class HanyuXgXfCidian extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/汉语相关相反词典.txt";
    }

    @Override
    protected MdxtItem newItem(String key) {
        return new XgXfItem(key);
    }

    public void debug() {
        selectWords();
    }

    private void selectWords(){
        Set<String> words = new HashSet<>();
        try {
            new LinesInFile(getRawHome()+"/buty.txt").each(words::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initItems();

        List<String> selected = new LinkedList<>();
        for (List<MdxtItem> list : getItemsMap().values()) {
            for (MdxtItem item: list){
                for (String key: words){
                    if (((XgXfItem)item).tongyi.contains(key)){
                        selected.add(item.getKey());
                        System.out.println(item.getKey() + " - " + key + " - " +((XgXfItem)item).tongyi);
                        break;
                    }
                }
            }
        }

        try {
            lines2File(selected, getRawHome()+"/selected.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class XgXfItem extends MdxtItem {

        private String tongyi = "";
        private String fanyi = "";


        XgXfItem(String key) {
            super(key);
        }

        @Override
        protected void addValue(String valueLine) {
            super.addValue(valueLine);
            if (valueLine.startsWith("`6`<相关>┐`4`"))
                tongyi += valueLine.replace("`6`<相关>┐`4`", "") + "\n";
            if (valueLine.startsWith("`1`<相反>┐`4`"))
                fanyi += valueLine.replace("`1`<相反>┐`4`", "") + "\n";
        }

        @Override
        protected boolean isValid() {
            return super.isValid();
        }

        @Override
        public String toString() {
            return String.format("%s\t%d\t%d", getKey(),
                    existsCount(tongyi, "、") + 1,
                    existsCount(fanyi, "、") + 1
            );
        }
    }
}
