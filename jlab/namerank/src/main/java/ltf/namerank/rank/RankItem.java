package ltf.namerank.rank;

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
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public RankItem newChild(String key) {
        RankItem child = new RankItem(key);
        //addChild(child);
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
}
