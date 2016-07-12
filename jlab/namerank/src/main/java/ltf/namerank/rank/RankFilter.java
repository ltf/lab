package ltf.namerank.rank;

/**
 * @author ltf
 * @since 16/7/12, 上午9:10
 */
public interface RankFilter {
    boolean banned(String givenName);
}
