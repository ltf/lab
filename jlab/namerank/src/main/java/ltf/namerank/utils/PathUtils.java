package ltf.namerank.utils;

import ltf.namerank.Application;

/**
 * @author ltf
 * @since 5/27/16, 10:13 PM
 */
public class PathUtils {
    public static String getProjectPath() {
        String macPrefix = "";
        if (OsUtils.isOsMac()) macPrefix = "/Users";
        return macPrefix + "/f/flab/jlab/namerank/";
    }

    public static String getDefaultPath() {
        return Application.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }


    public static String getDbPath() {
        String macPrefix = "";
        if (OsUtils.isOsMac()) macPrefix = "/Users";
        return macPrefix + "/f/xdata/namerank/db/";
    }

    public static String getJsonPath() {
        String macPrefix = "";
        if (OsUtils.isOsMac()) macPrefix = "/Users";
        return macPrefix + "/f/xdata/namerank/json/";
    }

}
