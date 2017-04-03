package ltf.monitor;

import android.annotation.TargetApi;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MonitorService extends Service {
    private boolean started;
    RequestQueue mQueue;
    PowerManager.WakeLock mWakeLock = null;
    Handler handler = new Handler();
    MediaPlayer mMediaPlayer;
    NotificationManager mNm;
    Runnable check = new Runnable() {
        @Override
        public void run() {
            checkLater();
            volleyReq();
        }
    };

    Runnable closeLed = new Runnable() {
        @Override
        public void run() {
            if (mNm != null)
                mNm.cancel(2);

        }
    };

    ArrayList<Target> mTargets = new ArrayList<>();
    Random rand = new Random();

    public MonitorService() {
        mTargets.add(new SasOid());
        mTargets.add(new SasMn());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!started) {
            started = true;

            start();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void checkLater() {
        handler.postDelayed(check, 10000);
    }

    private void awake() {
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

    private void start() {
        check.run();
        awake();
        keepAlive();
        holdNotify();
    }

    private int mId = 0;
    private Target pickTarget(){
        if (mId <0) mId =0;
        if (mId >=mTargets.size()) mId =0;
        return mTargets.get(mId++);
        //return mTargets.get(rand.nextInt(mTargets.size()));
    }

    private void volleyReq() {
        final Target target = pickTarget();
       StringRequest stringRequest = new StringRequest(Request.Method.GET, target.url(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (target.verify(response)) {
                            alert();
                        }
                        ledShow();
                    }
                }, null);
        // Add the request to the mQueue
        if (mQueue == null) mQueue = Volley.newRequestQueue(this);
        mQueue.add(stringRequest);
    }

    private void alert() {
        try {
            vibrate();
            playSound();
        } catch (Exception e) {
        }
    }

    private void playSound() {
        // 使用来电铃声的铃声路径
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
// 如果为空，才构造，不为空，说明之前有构造过
        if (mMediaPlayer == null)
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void holdNotify(){

        try {

            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setOngoing(true);

            startForeground(1, builder.build());
        } catch (Exception e){
        }
    }

    private void ledShow() {


        try {

        if(mNm == null) mNm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.ledARGB = 0xFF00FF;  //这里是颜色，我们可以尝试改变，理论上0xFF0000是红色，0x00FF00是绿色
        notification.ledOnMS = 100;
        notification.ledOffMS = 100;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        mNm.notify(2, notification);

            handler.postDelayed(closeLed, 500);

//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setOngoing(true);
//            builder.setLights(0xff00ff00, 300, 100);
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            manager.notify(1, builder.build());

        } catch (Exception e){

        }
    }


}
