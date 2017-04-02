package ltf.monitor;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mBtnStart;
    TextView mTxtInfo;
    RequestQueue queue;
    PowerManager.WakeLock mWakeLock = null;
    Handler handler = new Handler();
    MediaPlayer mMediaPlayer;
    Runnable check = new Runnable() {
        @Override
        public void run() {
            checkLater();
            volleyReq();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        mBtnStart = (Button) findViewById(R.id.start_monitor);
        mTxtInfo = (TextView) findViewById(R.id.txtinfo);
        mBtnStart.setOnClickListener(this);
    }


    private void checkLater(){
        handler.postDelayed(check, 5000);
    }

    private void awake(){
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        /**
         * PowerManager.PARTIAL_WAKE_LOCK:保持CPU运转，屏幕和键盘灯可能是关闭的
         * PowerManager.SCREEN_DIM_WAKE_LOCK:保持CPU运转,运行屏幕显示但是屏幕有可能是灰的，允许关闭键盘灯
         * PowerManager.SCREEN_BRIGHT_WAKE_LOCK：保持CPU运转，屏幕高亮显示，允许关闭键盘灯
         * PowerManager.FULL_WAKE_LOCK：保持CPU运转，屏幕高亮显示，键盘灯高亮显示
         * PowerManager.ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间
         * PowerManager.ACQUIRE_CAUSES_WAKEUP：强制屏幕亮起
         */
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SoundRecorder");
        mWakeLock.acquire();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_monitor:
                start();
                break;
        }

    }

    private void start() {
        check.run();
        awake();
        keepAlive();
    }


    private void volleyReq() {
        String url = "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/applicants-international-skilled-workers/international-skilled-worker-occupations-in-demand";
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && response.hashCode() == 1084823173){
                            alert();
                        }
                    }
                }, null);
        // Add the request to the queue
        queue.add(stringRequest);
    }

    private void alert() {
        try{
            vibrate();
            playSound();
        } catch (Exception e){
        }
    }

    private void playSound(){
        // 使用来电铃声的铃声路径
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
// 如果为空，才构造，不为空，说明之前有构造过
        if(mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(this, uri);
            mMediaPlayer.setLooping(true); //循环播放
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
        }
    }



    private void vibrate() {
        Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // 等待3秒，震动3秒，从第0个索引开始，一直循环
        vibrator.vibrate(new long[]{500, 1000}, 0);
    }


    private void keepAlive() {
        //创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
        Intent intent = new Intent("MONITOR_CLOCK");
        intent.putExtra("msg", "你该打酱油了");
        //定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
//也就是发送了action 为"ELITOR_CLOCK"的intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

        //AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

//设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
// 5秒后通过PendingIntent pi对象发送广播
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pi);
    }
}
