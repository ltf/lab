package com.f.testsmali;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.Iterator;

import static android.content.pm.PackageManager.GET_SIGNATURES;

/**
 * @author ltf
 * @since 17/11/25, 上午11:51
 */

public class TestInjectPackageManager {

    public static byte[] a(Context paramContext, String paramString) {
        Iterator<PackageInfo> it = paramContext.getPackageManager().getInstalledPackages(GET_SIGNATURES).iterator();
        while (it.hasNext()) {
            PackageInfo localPackageInfo = (PackageInfo) it.next();
            if (localPackageInfo.packageName.equals(paramString)) {
                return localPackageInfo.signatures[0].toByteArray();
            }
        }
        return null;
    }

}
