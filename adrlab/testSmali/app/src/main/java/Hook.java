import android.content.pm.Signature;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * @author ltf
 * @since 2017/12/15, 下午7:40
 */

public class Hook {

    public static Signature[] signatures = new Signature[1];

    private static final Random rand = new Random();

    public static String TAG = "dfy";
    public static String TAGLOG = "dfylog";
    public static String TAGAUTO = "dfyauto";

    public static double getHighScoreD() {
        return 92.0 + 8 * rand.nextDouble();
    }

    public static int getHighScoreI() {
        return 92 + rand.nextInt(8);
    }


    public static String hookReport(String orig) {
        try {
            JSONObject jo = new JSONObject(orig);
            if (jo.has("error")) return DEFAULT_REPORT;
            jo.put("overall", getHighScoreD());
            if (jo.has("words")) {
                JSONArray words = jo.getJSONArray("words");
                for (int i = 0; i < words.length(); i++) {
                    words.getJSONObject(i).put("overall", getHighScoreD());
                }
            }

            return jo.toString();
        } catch (JSONException e) {
            return DEFAULT_REPORT;
        }
    }

    public static void autoStartRecord(final View btnV) {
        Handler handler = new Handler();
        Log.e(TAGAUTO, "set auto click");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnV.performClick();
                Log.e(TAGAUTO, "auto 1st click");
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnV.performClick();
                Log.e(TAGAUTO, "auto 2nd click");
            }
        }, 1000);
    }

    private static String getStack() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i < stacks.length-5; i++) {
            sb.append(stacks[i].getClassName());
            sb.append("->");
            sb.append(stacks[i].getMethodName());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void log(int paramInt, String paramString, Object... paramVarArgs) {
        try {
            if (paramString ==null) return;
            Log.i(TAGLOG, String.format("info: %d, %s \n stack: %s", paramInt, String.format(paramString, paramVarArgs), getStack()));
            Log.i(TAGLOG, "--------------------------------------------------------------------------------------");
        } catch (Exception e) {
        }
    }

    private static final byte[] SIG = {
            48, -126, 2, 67, 48, -126, 1, -84, -96, 3, 2, 1, 2, 2, 4, 81, 110, -94, 73, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 5, 5, 0, 48, 101, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 56, 54, 49,
            17, 48, 15, 6, 3, 85, 4, 8, 19, 8, 115, 104, 97, 110, 103, 104, 97, 105, 49, 17, 48, 15, 6, 3, 85, 4, 7, 19, 8, 115, 104, 97, 110, 103, 104, 97, 105, 49, 16, 48, 14, 6, 3, 85, 4, 10, 19, 7, 85, 110,
            107, 110, 111, 119, 110, 49, 14, 48, 12, 6, 3, 85, 4, 11, 19, 5, 101, 110, 103, 122, 111, 49, 14, 48, 12, 6, 3, 85, 4, 3, 19, 5, 101, 110, 103, 122, 111, 48, 32, 23, 13, 49, 51, 48, 52, 49, 55, 49, 51, 50,
            51, 50, 49, 90, 24, 15, 50, 50, 56, 55, 48, 49, 51, 49, 49, 51, 50, 51, 50, 49, 90, 48, 101, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 56, 54, 49, 17, 48, 15, 6, 3, 85, 4, 8, 19, 8, 115, 104, 97,
            110, 103, 104, 97, 105, 49, 17, 48, 15, 6, 3, 85, 4, 7, 19, 8, 115, 104, 97, 110, 103, 104, 97, 105, 49, 16, 48, 14, 6, 3, 85, 4, 10, 19, 7, 85, 110, 107, 110, 111, 119, 110, 49, 14, 48, 12, 6, 3, 85, 4,
            11, 19, 5, 101, 110, 103, 122, 111, 49, 14, 48, 12, 6, 3, 85, 4, 3, 19, 5, 101, 110, 103, 122, 111, 48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2,
            -127, -127, 0, -82, 101, -124, -95, -122, 34, -94, -16, -94, -56, -39, 28, 19, 10, 6, 27, 6, 105, -42, -101, 40, 105, 113, 117, -121, 60, 3, 74, 9, 27, 103, 45, 100, -19, 73, -118, -58, 122, -12, -12, -117, 122, -12, -43, 53, 103, 125,
            75, 88, 73, 19, -25, -26, 35, 116, 86, -111, -35, 108, -49, -63, -43, 61, 105, 40, 38, -41, 87, 123, -39, -98, -108, -50, 62, 97, -32, -16, 121, -46, -5, -93, -7, -119, 65, -64, 15, -6, -114, -117, 59, -123, 41, 109, 123, 34, 1, -1,
            -62, -56, 127, -85, 116, 31, 80, -51, -20, -122, -35, -113, 86, 54, -104, 112, -87, 1, -102, 81, 70, 24, 119, 13, 120, -92, 103, -56, -16, -29, -125, 2, 3, 1, 0, 1, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 5, 5,
            0, 3, -127, -127, 0, 81, -27, 60, -33, -17, 90, -11, 107, -38, 45, 82, 2, 42, -95, -102, -54, 122, 102, -87, -25, -105, -108, -100, 91, 8, 81, 109, -45, -3, 49, 75, -75, 25, -49, 6, -125, 98, -98, -11, -127, 110, 79, 64, 95, 39,
            116, 101, -61, -81, 119, -98, -8, -36, 59, -66, 28, 34, 3, 63, 37, -48, -64, -55, -50, -27, -30, -99, -89, -7, 8, 89, -16, 81, 120, -100, -62, -91, -122, 78, -5, -102, 10, 28, -111, -67, -30, 112, 123, 38, -21, -64, 104, -114, -92, -51,
            -16, -17, 97, -108, -37, 33, -37, 31, 125, 49, 49, 51, -21, -108, -117, -70, 47, 28, -18, -128, -89, -57, -71, 33, -108, -109, -56, -34, -61, -72, -26, 33, -47};

    public static final String DEFAULT_REPORT = "{\n" +
            "\"service_type\":\"score\",\n" +
            "\"version\":\"5.1.0\",\n" +
            "\"locale\":\"en-us\",\n" +
            "\"am_version\":\"20170121114517\",\n" +
            "\"sm_version\":\"20170406152624\",\n" +
            "\"snr\":\"0.0\",\n" +
            "\"sil_prob\":\"0.088022\",\n" +
            "\"overall\":92.8,\n" +
            "\"pronunciation\":92.6,\n" +
            "\"avgkws\":-100.0,\n" +
            "\"tempo\":20.0,\n" +
            "\"stress\":100.0,\n" +
            "\"intonation\":0.0,\n" +
            "\"accuracy\":100.0,\n" +
            "\"integrity\":66.7,\n" +
            "\"confidence\":100.0,\n" +
            "\"fluency\":48.5,\n" +
            "\"ref_pitch\":{\n" +
            "},\n" +
            "\"pitch\":{\n" +
            "},\n" +
            "\"words\":[\n" +
            "    {\n" +
            "    \"word\":\"this\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":94.2,\n" +
            "        \"pronunciation\":94.2,\n" +
            "        \"rawscore\":0.872,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":45,\n" +
            "        \"end\":92\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":11,\n" +
            "        \"end\":53\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"is\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":97.2,\n" +
            "        \"pronunciation\":96.0,\n" +
            "        \"rawscore\":0.809,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":105,\n" +
            "        \"end\":154\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":57,\n" +
            "        \"end\":80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"dan\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":98.0,\n" +
            "        \"pronunciation\":98.0,\n" +
            "        \"rawscore\":0.825,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":199,\n" +
            "        \"end\":241\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":81,\n" +
            "        \"end\":140\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"this\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":94.2,\n" +
            "        \"pronunciation\":94.2,\n" +
            "        \"rawscore\":0.872,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":45,\n" +
            "        \"end\":92\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":11,\n" +
            "        \"end\":53\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"is\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":97.2,\n" +
            "        \"pronunciation\":96.0,\n" +
            "        \"rawscore\":0.809,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":105,\n" +
            "        \"end\":154\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":57,\n" +
            "        \"end\":80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"dan\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":98.0,\n" +
            "        \"pronunciation\":98.0,\n" +
            "        \"rawscore\":0.825,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":199,\n" +
            "        \"end\":241\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":81,\n" +
            "        \"end\":140\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"this\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":94.2,\n" +
            "        \"pronunciation\":94.2,\n" +
            "        \"rawscore\":0.872,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":45,\n" +
            "        \"end\":92\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":11,\n" +
            "        \"end\":53\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"is\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":97.2,\n" +
            "        \"pronunciation\":96.0,\n" +
            "        \"rawscore\":0.809,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":105,\n" +
            "        \"end\":154\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":57,\n" +
            "        \"end\":80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"dan\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":98.0,\n" +
            "        \"pronunciation\":98.0,\n" +
            "        \"rawscore\":0.825,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":199,\n" +
            "        \"end\":241\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":81,\n" +
            "        \"end\":140\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"this\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":94.2,\n" +
            "        \"pronunciation\":94.2,\n" +
            "        \"rawscore\":0.872,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":45,\n" +
            "        \"end\":92\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":11,\n" +
            "        \"end\":53\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"is\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":97.2,\n" +
            "        \"pronunciation\":96.0,\n" +
            "        \"rawscore\":0.809,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":105,\n" +
            "        \"end\":154\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":57,\n" +
            "        \"end\":80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"dan\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":98.0,\n" +
            "        \"pronunciation\":98.0,\n" +
            "        \"rawscore\":0.825,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":199,\n" +
            "        \"end\":241\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":81,\n" +
            "        \"end\":140\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"this\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":94.2,\n" +
            "        \"pronunciation\":94.2,\n" +
            "        \"rawscore\":0.872,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":45,\n" +
            "        \"end\":92\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":11,\n" +
            "        \"end\":53\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"is\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":97.2,\n" +
            "        \"pronunciation\":96.0,\n" +
            "        \"rawscore\":0.809,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":105,\n" +
            "        \"end\":154\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":57,\n" +
            "        \"end\":80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"dan\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":98.0,\n" +
            "        \"pronunciation\":98.0,\n" +
            "        \"rawscore\":0.825,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":199,\n" +
            "        \"end\":241\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":81,\n" +
            "        \"end\":140\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"this\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":94.2,\n" +
            "        \"pronunciation\":94.2,\n" +
            "        \"rawscore\":0.872,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":45,\n" +
            "        \"end\":92\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":11,\n" +
            "        \"end\":53\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"is\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":97.2,\n" +
            "        \"pronunciation\":96.0,\n" +
            "        \"rawscore\":0.809,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":105,\n" +
            "        \"end\":154\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":57,\n" +
            "        \"end\":80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "    \"word\":\"dan\",\n" +
            "    \"scores\":{\n" +
            "        \"overall\":98.0,\n" +
            "        \"pronunciation\":98.0,\n" +
            "        \"rawscore\":0.825,\n" +
            "        \"tempo\":100.0,\n" +
            "        \"stress\":0.0,\n" +
            "        \"intonation\":0.0,\n" +
            "        \"tone\":0.0\n" +
            "        },\n" +
            "    \"stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":199,\n" +
            "        \"end\":241\n" +
            "        },\n" +
            "    \"reference_stats\":{\n" +
            "        \"keyword\":0,\n" +
            "        \"start\":81,\n" +
            "        \"end\":140\n" +
            "        }\n" +
            "    }\n" +
            "]\n" +
            "}";


    static {
        signatures[0] = new Signature(SIG);
    }
}
