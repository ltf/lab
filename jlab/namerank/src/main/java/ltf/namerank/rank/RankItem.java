package ltf.namerank.rank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 7/9/16, 3:41 PM
 */
public class RankItem {

    private String key;

    private double score;

    private String info;

    private List<RankItem> children;

    public RankItem(String key) {
        this.key = key;
    }

    public RankItem() {
    }

    public void setBy(RankItem fromItem) {
        //key = fromItem.getKey();
        score = fromItem.getScore();
        info = fromItem.getInfo();
        children = fromItem.getChildren();
    }

    public void addChild(RankItem child) {
        // only construct hierarchy structure in report mode
        if (!RankSettings.reportMode) return;
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    /**
     * new child with same key
     */
    public RankItem newChild() {
        RankItem child = new RankItem(key);
        addChild(child);
        return child;
    }

    /**
     * new child with different key
     */
    public RankItem newChild(String newKey) {
        RankItem child = new RankItem(newKey);
        addChild(child);
        return child;
    }

    public List<RankItem> getChildren() {
        return children;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int ascOrder(RankItem o) {
        double x = score - o.score;
        return x > 0 ? 1 : (x < 0 ? -1 : 0);
    }

    public int descOrder(RankItem o) {
        double x = score - o.score;
        return x > 0 ? -1 : (x < 0 ? 1 : 0);
    }

    @Override
    public String toString() {
        String title = String.format("[ %s ] (%.1f) : %s", key, score, info);

        StringBuilder content = new StringBuilder();
        if (children != null) {
            for (RankItem item : children) {
                BufferedReader br = new BufferedReader(new StringReader(item.toString()));

                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        content.append("\t\t").append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return title + "\n" + content.toString();
    }

    public String toHtml(boolean display) {
        StringBuilder content = new StringBuilder();
        if (children != null) {
            for (RankItem item : children) {
                content.append(item.toHtml(false));
            }
        }
        String disp = "";
        if (!display) disp = " style=\"display:none;\"";
        return String.format("<li%s><span>%s (%.1f)</span>%s<ul>%s</ul></li>",
                disp, key, score, info == null ? "" : info.replaceAll("\n", "<br>"), content.toString());

    }
}
