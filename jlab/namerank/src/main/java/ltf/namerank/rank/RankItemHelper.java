package ltf.namerank.rank;

/**
 * @author ltf
 * @since 7/9/16, 4:34 PM
 */
public class RankItemHelper {

    private static StringBuilder[] infoBuilders = new StringBuilder[16];

    private static int currentBuilderIndex = -1;

    static {
        for (int i = 0; i < infoBuilders.length; i++) {
            infoBuilders[i] = new StringBuilder();
        }
    }

    /**
     * construct a string with partStrs, should be finish with flushResult
     */
    public static StringBuilder addInfo(String partStr) {
        infoBuilders[currentBuilderIndex].append(partStr);
        return infoBuilders[currentBuilderIndex];
    }

    /**
     * construct a string with partStrs, should be finish with flushResult
     */
    public static StringBuilder addInfo(int partStr) {
        infoBuilders[currentBuilderIndex].append(partStr);
        return infoBuilders[currentBuilderIndex];
    }

    public static StringBuilder acquireBuilder() {
        return infoBuilders[++currentBuilderIndex];
    }

    /**
     * update RankItem with score in param & info in static stringBuilder, then clear the stringBuilder
     * (only for single thread)
     */
    public static void flushResult(RankItem target, double score) {
        target.setScore(score);
        target.setInfo(infoBuilders[currentBuilderIndex].toString());
        releaseBuilder();
    }

    public static void releaseBuilder() {
        if (currentBuilderIndex >= 0) {
            infoBuilders[currentBuilderIndex].delete(0, infoBuilders[currentBuilderIndex].length());
            currentBuilderIndex--;
        }
    }
}
