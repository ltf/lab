package ltf.namerank.rank;

import com.sun.istack.internal.NotNull;

/**
 * @author ltf
 * @since 16/7/1, 下午2:22
 */
public interface Ranker {
    double rank(@NotNull String target, @NotNull RankLogger logger);
}
