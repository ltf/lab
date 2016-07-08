package ltf.namerank.rank;

import ltf.namerank.rank.RankLogger;

import java.util.function.Supplier;

/**
 * @author ltf
 * @since 7/9/16, 12:09 AM
 */
public class NopLogger implements RankLogger {
    @Override
    public void log(Supplier<String> supplier) {

    }
}
