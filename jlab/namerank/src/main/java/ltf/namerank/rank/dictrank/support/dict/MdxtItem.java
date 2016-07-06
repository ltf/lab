package ltf.namerank.rank.dictrank.support.dict;

/**
 * @author ltf
 * @since 6/21/16, 10:22 PM
 */
public class MdxtItem {

    private String key;

    private String value;

    private StringBuilder stringBuilder;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    MdxtItem(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    protected void addValue(String valueLine) {
        if (stringBuilder == null)
            stringBuilder = new StringBuilder();
        stringBuilder.append(valueLine).append("\n");
    }

    protected void finishAdd() {
        value = stringBuilder.toString();
        stringBuilder = null;
    }

    protected boolean isValid() {
        return true;
    }
}
