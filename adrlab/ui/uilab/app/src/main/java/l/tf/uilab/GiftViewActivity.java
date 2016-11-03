package l.tf.uilab;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author ltf
 * @since 16/11/1, 下午12:00
 */
public class GiftViewActivity extends Activity {
    private GiftView msv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msv = new GiftView(GiftViewActivity.this);         //实例化MySurfaceView的对象
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置屏幕显示没有title栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        //设置只允许横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(msv);                            //设置Activity显示的内容为msv
    }
}
