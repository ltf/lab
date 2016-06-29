package ltf.namerank.dataprepare.dict;

/**
 * @author ltf
 * @since 6/21/16, 10:22 PM
 */
class MdxtItem {

    private String key;

    private String value;

    private StringBuilder stringBuilder;

    public String getKey() {
        return key;
    }

    MdxtItem(String key) {
        this.key = key;
        stringBuilder = new StringBuilder();
    }

    @Override
    public String toString() {
        return key;
    }

    void addValue(String valueLine) {
        stringBuilder.append(valueLine).append("\n");
    }

    boolean isValid() {
        value = stringBuilder.toString();
        stringBuilder = null;
        return true;
    }
}
