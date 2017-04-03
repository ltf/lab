package ltf.monitor;

/**
 * @author ltf
 * @since 17/4/3, 下午4:47
 */
public interface Target {
    String url();
    boolean verify(String response);
}
