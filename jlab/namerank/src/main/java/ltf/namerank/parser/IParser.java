package ltf.namerank.parser;

/**
 * @author ltf
 * @since 5/29/16, 2:31 PM
 */
public interface IParser {
    /**
     * Handle text content
     * @param url file name or url
     * @param content text data
     * @return true if handled, false if skipped
     */
    boolean handle(String url, String content);
}
