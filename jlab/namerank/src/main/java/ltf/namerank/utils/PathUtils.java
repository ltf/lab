package ltf.namerank.utils;

import ltf.namerank.Application;

/**
 * @author ltf
 * @since 5/27/16, 10:13 PM
 */
public class PathUtils {

    private static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getProjectHome() {
        return getUserHome() + "/flab/jlab/namerank";
    }

    private static String getDataHome() {
        return getUserHome() + "/xdata/namerank";
    }

    public static String getDefaultPath() {
        return Application.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }


    public static String getDbHome() {
        return getDataHome() + "/db";
    }

    public static String getJsonHome() {
        return getDataHome() + "/json";
    }
    public static String getNamesHome() {
        return getDataHome() + "/names";
    }
    public static String getRawHome() {
        return getDataHome() + "/raw";
    }
    public static String getCacheHome() {
        return getDataHome() + "/cache";
    }
    public static String getWordsHome() {
        return getDataHome() + "/words";
    }

}
