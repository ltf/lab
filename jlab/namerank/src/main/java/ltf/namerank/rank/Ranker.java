package ltf.namerank.rank;

/**
 * @author ltf
 * @since 16/7/1, 下午2:22
 */
public interface Ranker {
    double rank(RankItem target);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
