import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;

import java.util.Iterator;

import static android.content.pm.PackageManager.GET_SIGNATURES;

/**
 * @author ltf
 * @since 2017/12/16, 上午10:54
 */

public class HookTest {
    public void testGetSig() {

    }

    public static String getMySig(Context paramContext, String paramString) {
        Iterator<PackageInfo> it = paramContext.getPackageManager().getInstalledPackages(GET_SIGNATURES).iterator();
        while (it.hasNext()) {
            PackageInfo localPackageInfo = (PackageInfo) it.next();
            if (localPackageInfo.packageName.equals(paramString)) {
                return localPackageInfo.signatures[0].toCharsString();
            }
        }
        return null;
    }
    public static String getMySig2(Context paramContext, String paramString) {
        Iterator<PackageInfo> it = paramContext.getPackageManager().getInstalledPackages(GET_SIGNATURES).iterator();
        while (it.hasNext()) {
            PackageInfo localPackageInfo = (PackageInfo) it.next();
            if (localPackageInfo.packageName.equals(paramString)) {
                return Hook.signatures[0].toCharsString();
            }
        }
        return null;
    }
}
