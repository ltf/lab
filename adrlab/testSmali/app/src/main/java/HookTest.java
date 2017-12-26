import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Iterator;

import static android.content.pm.PackageManager.GET_SIGNATURES;

/**
 * @author ltf
 * @since 2017/12/16, 上午10:54
 */

public class HookTest {
    private int[] mInts = new int[10];

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

    private void updateIntArrays() {
        Arrays.fill(mInts, 100);
    }

    private void logIntArray() {
        Log.e(Hook.TAG, Arrays.toString(mInts));
    }

    private void logStr() {
        String x = "xxxx";
        Log.e(Hook.TAG, x);
    }

    private void testLogView() {
        Hook.logFrag(null, null);
        Hook.initAutoRcord(null);
    }

    private void testLog(int paramInt, Object paramObject, Throwable paramThrowable, String paramString, Object... paramVarArgs) {
        Hook.log(paramInt, paramString, paramVarArgs);
    }

    private double getD() {
        return 100.0;
    }

    private float getF() {
        return 100.0f;
    }

    private double getRF() {
        return Hook.getHighScoreD();
    }

    private int getRI() {
        return Hook.getHighScoreI();
    }

    private int getI() {
        return 100;
    }

    private String getS() {
        return "string...";
    }


    private String testHackJson() {
        return Hook.hookReport(Hook.DEFAULT_REPORT);
    }

    private void testCallAutoRec() {
        Hook.autoStartRecord(new ImageView(null));
    }
}
