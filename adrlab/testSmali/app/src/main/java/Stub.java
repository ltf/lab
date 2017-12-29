import android.app.Activity;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * @author ltf
 * @since 2017/12/15, 下午7:40
 */

public class Stub {

    public static Signature[] signatures = new Signature[1];

    private static final Random rand = new Random();

    public static String TAG = "dfy";
    public static String TAGLOG = "dfylog";
    public static String TAGFRAG = "dfyfrag";
    public static String TAGPLAY = "dfyplay";
    public static String TAGRESULT_SOUND = "dfyresultSound";
    public static String TAGAUTO = "dfyauto";

    public static double getHighScoreD() {
        return 92.0 + 8 * rand.nextDouble();
    }

    public static int getHighScoreI() {
        return 92 + rand.nextInt(8);
    }

    public static void autoStartRecord(final View btnV) {
//        Handler handler = new Handler();
//        Log.e(TAGAUTO, "set auto click");
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                btnV.performClick();
//                Log.e(TAGAUTO, "auto 1st click");
//            }
//        }, 200);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                btnV.performClick();
//                Log.e(TAGAUTO, "auto 2nd click");
//            }
//        }, 1000);
    }

    private static String getStack() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i < stacks.length - 5; i++) {
            sb.append(stacks[i].getClassName());
            sb.append("->");
            sb.append(stacks[i].getMethodName());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void log(int paramInt, String paramString, Object... paramVarArgs) {
//        try {
//            if (paramString == null) return;
//            Log.i(TAGLOG, String.format("info: %d, %s \n stack: %s", paramInt, String.format(paramString, paramVarArgs), getStack()));
//            Log.i(TAGLOG, "--------------------------------------------------------------------------------------");
//        } catch (Exception e) {
//        }
    }

    private static WeakReference<Handler> sWeakRef;

    public static void initAutoRcord(Object obj) {
        try {
            if (obj != null && obj instanceof Fragment) {
                Log.i(TAGAUTO, "auto click init  " + obj.toString());
                Activity activity = ((Fragment) obj).getActivity();
                Handler handler = null;
                if (sWeakRef != null) {
                    handler = sWeakRef.get();
                }
                if (handler == null) {
                    handler = new Handler();
                    sWeakRef = new WeakReference<>(handler);
                }

                handler.removeCallbacksAndMessages(null);

                View player = activity.findViewById(0x7f110269);
                if (player != null && player.getVisibility() == View.VISIBLE) {
                    handler.postDelayed(new AutoClickRunnable(player), 1500);
                }

                View recorder = activity.findViewById(0x7f11026a);
                if (recorder != null && recorder.getVisibility() == View.VISIBLE) {
                    handler.postDelayed(new AutoClickRunnable(recorder), 3000);
                    handler.postDelayed(new AutoClickRunnable(recorder), 7000);
                }
            }
        } catch (Exception e) {
        }
    }

    private static class AutoClickRunnable implements Runnable {
        private final View mView;

        public AutoClickRunnable(View mView) {
            this.mView = mView;
        }

        @Override
        public void run() {
            mView.performClick();
            Log.i(TAGAUTO, "auto click : " + mView.toString());
        }
    }


    private static String getPlayStack() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < stacks.length - 3; i++) {
            sb.append(stacks[i].getClassName());
            sb.append("->");
            sb.append(stacks[i].getMethodName());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void logPlay() {
        try {
            Log.i(TAGPLAY, String.format("logPlay stack: %s", getPlayStack()));
            Log.i(TAGPLAY, "--------------------------------------------------------------------------------------");

        } catch (Exception e) {
        }
    }

    public static void logResultSound(int result) {
        try {
            Log.i(TAGRESULT_SOUND, String.format("logResultSound result %d stack: %s", result, getPlayStack()));
            Log.i(TAGRESULT_SOUND, "--------------------------------------------------------------------------------------");
        } catch (Exception e) {
        }
    }

    public static void autoClickPic(ViewGroup viewGroup, int anserIdx) {
        try {
            Log.i("dfyclickpic", String.format("autoClickPic anser id: %d, stack: %s", anserIdx, getPlayStack()));
            Log.i("dfyclickpic", "--------------------------------------------------------------------------------------");
            new Handler().postDelayed(new AutoIndexClickRunnable(viewGroup, anserIdx), 3000);
        } catch (Exception e) {
        }
    }


    private static class AutoIndexClickRunnable implements Runnable {
        private final ViewGroup mViewGroup;
        private final int mIndex;

        public AutoIndexClickRunnable(ViewGroup mViewGroup, int mIndex) {
            this.mViewGroup = mViewGroup;
            this.mIndex = mIndex;
        }

        @Override
        public void run() {
            try {
                Log.i(TAGAUTO, "auto click run");
                Log.i(TAGAUTO, mViewGroup.toString());
                Log.i(TAGAUTO, ""+mViewGroup.getChildCount());
                mViewGroup.getChildAt(mIndex).performClick();
                Log.i(TAGAUTO, "auto click by index %d" + mIndex);
//                boolean clicked = false;
//                for (int i = 0; i < mViewGroup.getChildCount(); i++) {
//                    if ((Integer) mViewGroup.getChildAt(i).getTag() == mIndex) {
//                        mViewGroup.getChildAt(i).performClick();
//                        Log.i(TAGAUTO, "auto click by tag %d" + mIndex);
//                        clicked = true;
//                    }
//                }
//
//                if (!clicked) {
//                    if (mIndex < mViewGroup.getChildCount()) {
//                        mViewGroup.getChildAt(mIndex).performClick();
//                        Log.i(TAGAUTO, "auto click by index %d" + mIndex);
//                    }
//                }
            } catch (Exception e) {
            }
        }
    }

    public static void logFrag(Object obj, ViewGroup container) {
//        try {
//            //Log.i(TAGFRAG, String.format("logFrag called, stack: %s", getStack()));
//            //Log.i(TAGFRAG, "--------------------------------------------------------------------------------------");
//            if (obj == null) return;
//            Log.i(TAGFRAG, String.format("logFrag called, obj: %s", obj.toString()));
//            if (obj instanceof Fragment) {
//                Log.i(TAGFRAG, printViewTree(((Fragment) obj).getActivity().findViewById(android.R.id.content), 0));
//            }
//            Log.i(TAGFRAG, "--------------------------------------------------------------------------------------");
//        } catch (Exception e) {
//            Log.e(TAGFRAG, e.getMessage());
//        }
    }

    private static String printViewTree(View root, int ident) {
        //Log.i(TAGFRAG, String.format("printViewTree called"));
        if (root == null) return "";
        //Log.i(TAGFRAG, String.format("printViewTree called, root: %s", root.toString()));
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < ident; i++) sb.append("  ");
        String identStr = sb.toString();
        sb = new StringBuilder("");
        sb.append(identStr).append(root.getClass().getName())
                .append(" : ").append(root.getId())
                .append(" : ").append(getOnClickListenerInfo(root));
        sb.append("\n");
        if (root instanceof ViewGroup) {
            ViewGroup vs = (ViewGroup) root;
            for (int i = 0; i < vs.getChildCount(); i++) {
                sb.append(printViewTree(vs.getChildAt(i), ident + 1));
            }
        }
        return sb.toString();
    }

    private static String getOnClickListenerInfo(final View view) {
        final View.OnClickListener listener = getOnClickListener(view);
        if (listener == null) return "";
        if (listener instanceof OnClickListenerWrapper) {
            return ((OnClickListenerWrapper) listener).mOrig.toString() + " [wrapped]";
        } else {
            view.setOnClickListener(new OnClickListenerWrapper(listener));
            return listener.toString();
        }
    }

    /**
     * Returns the current View.OnClickListener for the given View
     *
     * @param view the View whose click listener to retrieve
     * @return the View.OnClickListener attached to the view; null if it could not be retrieved
     */
    private static View.OnClickListener getOnClickListener(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getOnClickListenerV14(view);
        } else {
            return getOnClickListenerV(view);
        }
    }

    //Used for APIs lower than ICS (API 14)
    private static View.OnClickListener getOnClickListenerV(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        Field field;

        try {
            field = Class.forName(viewStr).getDeclaredField("mOnClickListener");
            retrievedListener = (View.OnClickListener) field.get(view);
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    //Used for new ListenerInfo class structure used beginning with API 14 (ICS)
    private static View.OnClickListener getOnClickListenerV14(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        String lInfoStr = "android.view.View$ListenerInfo";

        try {
            Field listenerField = Class.forName(viewStr).getDeclaredField("mListenerInfo");
            Object listenerInfo = null;

            if (listenerField != null) {
                listenerField.setAccessible(true);
                listenerInfo = listenerField.get(view);
            }

            Field clickListenerField = Class.forName(lInfoStr).getDeclaredField("mOnClickListener");

            if (clickListenerField != null && listenerInfo != null) {
                retrievedListener = (View.OnClickListener) clickListenerField.get(listenerInfo);
            }
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    private static class OnClickListenerWrapper implements View.OnClickListener {

        private View.OnClickListener mOrig;

        public OnClickListenerWrapper(View.OnClickListener mOrig) {
            this.mOrig = mOrig;
        }

        @Override
        public void onClick(View v) {
            Log.i(TAGFRAG, String.format("onClicked : %s", v.toString()));
            if (mOrig != null) mOrig.onClick(v);
        }
    }

    private static final byte[] SIG =
            {
                    48,-126,3,-57,48,-126,2,-81,-96,3,2,1,2,2,4,106,-91,-78,61,48,13,6,9,42,-122,72,-122,-9,13,1,1,11,5,0,48,-127,-110,49,11,48,9,6,3,85,4,6,19,2,67,78,
                    49,18,48,16,6,3,85,4,8,12,9,-27,-116,-105,-28,-70,-84,-27,-72,-126,49,15,48,13,6,3,85,4,7,12,6,-27,-116,-105,-28,-70,-84,49,51,48,49,6,3,85,4,10,12,42,-27,-116,
                    -105,-28,-70,-84,-27,-67,-87,-28,-70,-111,-27,-100,-88,-25,-70,-65,-26,-118,-128,-26,-100,-81,-27,-68,-128,-27,-113,-111,-26,-100,-119,-23,-103,-112,-27,-123,-84,-27,-113,-72,49,24,48,22,6,3,85,4,11,19,
                    15,77,101,101,108,105,118,101,32,65,110,100,114,111,105,100,49,15,48,13,6,3,85,4,3,19,6,109,101,101,103,111,100,48,32,23,13,49,53,48,51,49,57,48,50,52,57,51,55,90,
                    24,15,51,48,49,52,48,55,50,48,48,50,52,57,51,55,90,48,-127,-110,49,11,48,9,6,3,85,4,6,19,2,67,78,49,18,48,16,6,3,85,4,8,12,9,-27,-116,-105,-28,-70,-84,
                    -27,-72,-126,49,15,48,13,6,3,85,4,7,12,6,-27,-116,-105,-28,-70,-84,49,51,48,49,6,3,85,4,10,12,42,-27,-116,-105,-28,-70,-84,-27,-67,-87,-28,-70,-111,-27,-100,-88,-25,-70,-65,-26,
                    -118,-128,-26,-100,-81,-27,-68,-128,-27,-113,-111,-26,-100,-119,-23,-103,-112,-27,-123,-84,-27,-113,-72,49,24,48,22,6,3,85,4,11,19,15,77,101,101,108,105,118,101,32,65,110,100,114,111,105,100,49,
                    15,48,13,6,3,85,4,3,19,6,109,101,101,103,111,100,48,-126,1,34,48,13,6,9,42,-122,72,-122,-9,13,1,1,1,5,0,3,-126,1,15,0,48,-126,1,10,2,-126,1,1,0,-81,
                    -95,-97,-126,-80,-93,76,-46,-92,3,101,-35,-94,-39,-74,-64,-120,-4,94,-22,-79,-103,23,90,38,-116,127,42,-52,115,-116,55,-95,-36,-113,112,94,23,-78,88,28,-18,125,-7,48,83,73,93,-41,15,23,
                    108,88,101,-12,31,108,99,116,-25,-73,-39,-5,49,-106,-122,-108,-39,91,98,53,70,-12,-29,-80,-101,-101,24,109,-62,-126,-71,122,-57,-48,-10,52,65,-55,-111,119,-112,-81,29,38,-94,-18,-82,77,15,-46,
                    110,-62,109,96,80,-59,-101,-39,32,32,-128,-40,116,23,-6,-40,25,-35,-9,8,-126,-57,-64,65,55,-29,-46,-96,120,2,-34,-120,-126,-17,-127,-27,69,-113,126,121,115,-6,-84,-3,25,-19,-24,9,-66,-10,
                    -128,-19,81,-76,-16,26,8,-21,-92,100,65,-123,39,-114,98,-57,-68,-1,64,-25,121,88,-72,3,-86,14,-95,-46,-19,126,34,-108,115,107,7,69,81,113,-120,54,123,70,116,114,-95,-17,18,29,13,-36,
                    -87,52,-77,10,104,-50,100,-126,-71,92,66,71,1,-123,45,-1,20,-121,87,87,81,-50,-50,-51,-38,56,42,76,-41,2,-80,-95,72,98,46,-77,-13,-77,15,-7,-67,-6,-11,57,105,-38,-103,28,5,48,
                    126,-26,-71,-43,-83,2,3,1,0,1,-93,33,48,31,48,29,6,3,85,29,14,4,22,4,20,-113,-33,53,102,-67,81,75,-32,-85,-128,87,-10,12,48,-100,-8,24,-33,-108,-71,48,13,6,9,42,
                    -122,72,-122,-9,13,1,1,11,5,0,3,-126,1,1,0,-117,-59,36,100,38,77,57,-42,121,121,-86,57,-73,-96,80,-122,112,-8,81,22,-59,81,13,-80,59,-8,-91,127,15,-68,-55,-16,-8,12,-50,
                    -115,-106,-95,74,-4,52,3,-12,70,110,125,55,-83,8,-63,25,-121,61,71,-92,113,-22,-8,61,-110,-126,-120,121,-39,-42,42,26,-16,46,51,120,63,56,-47,-82,18,69,-2,-28,16,-120,92,34,63,-11,
                    54,47,65,-50,12,-45,55,53,-25,-41,11,107,27,-99,98,93,85,-115,26,-29,-37,-123,124,95,-20,51,-90,42,97,-75,76,-21,-62,96,114,111,45,-77,66,92,-17,18,23,-58,102,125,-35,19,-87,-108,
                    69,-33,-109,24,-46,-121,-24,43,-115,88,-118,104,5,98,-114,-96,-15,49,36,7,-106,-53,102,-42,55,24,102,-37,-9,-70,4,-128,30,57,-44,44,125,73,-96,11,-19,71,-99,-77,22,72,114,-48,-1,-63,
                    45,-20,118,-26,-75,65,-25,126,40,68,104,124,-5,17,85,-105,54,-126,90,54,-6,109,101,-52,117,-10,-12,-39,92,117,37,61,-71,44,-25,116,-112,19,-34,-10,50,104,107,58,87,43,-16,-85,41,-100,
                    24,-57,120,-13,-108,-114,34,99,42,93,-95,-31,80,95,-22,45,2,118,-113,-66,-97};

    static {
        signatures[0] = new Signature(SIG);
    }
}
