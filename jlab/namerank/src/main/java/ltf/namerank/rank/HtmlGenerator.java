package ltf.namerank.rank;

import java.io.IOException;
import java.util.Collection;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.FileUtils.str2File;
import static ltf.namerank.utils.PathUtils.getHtmlHome;

/**
 * @author ltf
 * @since 7/10/16, 2:37 AM
 */
public class HtmlGenerator {


    private static final String ITEMS_TAG = "<!-- ITEMS_TAG -->";

    public static void gen(Collection<RankItem> items, String fn) {
        if (items.size()>1000) throw new IllegalStateException("Not allowed to gen html for more than 1000 items.");
        try {
            StringBuilder sb = new StringBuilder();
            for (RankItem item : items) sb.append(item.toHtml(true));
            str2File(file2Str(getHtmlHome() + "/treeTemplet.html").replace(ITEMS_TAG, sb.toString()), fn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
