package ltf.jmonitor.utils;


/**
 * @author ltf
 * @since 5/27/16, 10:13 PM
 */
public class PathUtils {

    private static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getJsonHome() {
        return getUserHome() + "/sas/local_cfg";
    }
}
