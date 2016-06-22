package ltf.namerank.rank.pronounce.dict;

/**
 * Item parser based on data lines
 *
 * @author ltf
 * @since 16/6/22, 上午11:27
 */
public interface ItemValueParser {


    /**
     * init parse
     */
    void init(String key);

    /**
     * add value line
     */
    void addValueLine(String valueLine);


    /**
     * build ItemValue after all data inputted, return null if skipped
     * (call this after match ITEM_END_LINE)
     */
    <T extends ItemValue> T build();

}
