package ltf.namerank.rank;

/**
 * @author ltf
 * @since 7/9/16, 4:34 PM
 */
public class RankItemHelper {

    private static StringBuilder infoBuilder = new StringBuilder();

    /**
     * construct a string with partStrs, should be finish with flushResult
     */
    public static StringBuilder addInfo(String partStr) {
        infoBuilder.append(partStr);
        return infoBuilder;
    }

    /**
     * construct a string with partStrs, should be finish with flushResult
     */
    public static StringBuilder addInfo(int partStr) {
        infoBuilder.append(partStr);
        return infoBuilder;
    }

    /**
     * update RankItem with score in param & info in static stringBuilder, then clear the stringBuilder
     * (only for single thread)
     */
    public static void flushResult(RankItem target, double score) {
        target.setScore(score);
        target.setInfo(infoBuilder.toString());
        cleanInfoBuilder();

    }

    public static void cleanInfoBuilder() {
        infoBuilder.delete(0, infoBuilder.length());
    }
}
