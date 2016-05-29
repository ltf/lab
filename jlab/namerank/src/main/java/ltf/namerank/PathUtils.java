package ltf.namerank;

/**
 * @author ltf
 * @since 5/27/16, 10:13 PM
 */
public class PathUtils {
    public static String getProjectPath() {
        String macPrefix = "";
        if (OsUtils.isOsMac()) macPrefix = "/Users";
        return macPrefix + "/f/plab/jlab/namerank/";
    }

    public static String getDefaultPath() {
        return Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

}
