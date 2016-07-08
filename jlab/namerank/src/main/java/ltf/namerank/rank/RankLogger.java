package ltf.namerank.rank;

import java.util.function.Supplier;

/**
 * @author ltf
 * @since 16/7/1, 下午2:24
 */
public interface RankLogger {
    void log(Supplier<String> supplier);
}
