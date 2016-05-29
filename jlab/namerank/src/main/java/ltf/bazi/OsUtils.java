package ltf.bazi;

/**
 * @author ltf
 * @since 15/2/4, 下午12:13
 */
public class OsUtils {

    public static String getOsName(){
        return System.getProperty("os.name");
    }


    public static boolean isOsMac(){
        return "Mac OS X".equals(getOsName());
    }
}
