package ltf.namerank.dataprepare.dict;

/**
 * @author ltf
 * @since 6/21/16, 10:22 PM
 */
class MdxtItem {

    private String key;

    public String getKey() {
        return key;
    }

    MdxtItem(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    void addValue(String valueLine) {

    }

    boolean isValid() {
        return true;
    }
}
