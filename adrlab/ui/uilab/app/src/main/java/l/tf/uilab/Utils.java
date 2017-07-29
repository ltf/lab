package l.tf.uilab;

import android.util.Log;

/**
 * @author ltf
 * @since 17/7/29, 下午2:35
 */
public class Utils {
    public static void logMethodCalled() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stacks[3];
        Log.d("METHOD_CALLED", stack.getClassName() + "->" + stack.getMethodName());
    }


}
