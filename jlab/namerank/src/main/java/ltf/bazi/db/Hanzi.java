package ltf.bazi.db;

/**
 * @author ltf
 * @since 5/27/16, 10:29 PM
 */
public class Hanzi {
    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getTraditional() {
        return traditional;
    }

    public void setTraditional(String traditional) {
        this.traditional = traditional;
    }

    public String getStrokes() {
        return strokes;
    }

    public void setStrokes(String strokes) {
        this.strokes = strokes;
    }

    public String getWuxing() {
        return wuxing;
    }

    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }

    public String getGorb() {
        return gorb;
    }

    public void setGorb(String gorb) {
        this.gorb = gorb;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String spell;
    private String traditional;
    private String strokes;
    private String wuxing;
    private String gorb;
    private String info;
}
