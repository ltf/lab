package ltf.namerank.rank.pronounce.dict;

/**
 * @author ltf
 * @since 6/21/16, 10:22 PM
 */
public class ItemValue {

    private String key;

    public ItemValue(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    public ItemValueParser getParser(){
        return null;
    }
}
