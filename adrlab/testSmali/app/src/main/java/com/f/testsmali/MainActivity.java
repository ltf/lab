package com.f.testsmali;

import android.app.Activity;
import android.os.RemoteException;

/**
 * @author ltf
 * @since 16/5/31, 下午3:48
 */
public class MainActivity extends Activity {
    private static final String ID = "111";

    protected String xxxId;
    protected String xxxId2;
    protected String xxxId3;

    public String getId() {
        return "222";
    }
    public String getConstId() {
        return ID;
    }
    public void initXXXId() {
        xxxId = ID;
        xxxId2 = "123123";
        String tmp = ID;
        xxxId3 = tmp;
    }
}
