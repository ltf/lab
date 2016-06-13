package ltf.namerank.entity;

/**
 * @author ltf
 * @since 6/11/16, 4:46 PM
 */
public class DictItem {

    public String getItemType() {
        return this.getClass().getSimpleName();
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    private String zi;
}
