package com.f.testsmali;

import android.app.Activity;
import android.os.RemoteException;

/**
 * @author ltf
 * @since 16/5/31, 下午3:48
 */
public class MainActivity extends Activity {
    private static final String ID = "111";


    public String getId() {
        return "222";
    }
    public String getConstId() {
        return ID;
    }
}
