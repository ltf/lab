package ltf.monitor;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MonitorService extends Service {
    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getNotification();


        return super.onStartCommand(intent, flags, startId);
    }


    public void getNotification() {
        //得到NotificationManager的对象，用来实现发送Notification
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        //得到Notification对象
        Notification notification = new Notification();
        //设置消息来时显示的消息
        notification.tickerText = "来消息了";
        //设置消息来时显示图标
        notification.icon = R.mipmap.ic_launcher;
        //设置是否会消失
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        //设置消息来时震动
        notification.defaults = Notification.DEFAULT_VIBRATE;
        notification.vibrate = new long[]{0,300,500,700};
        //设置当前时间
        long when = System.currentTimeMillis();
        notification.when = when;
        //通知的跳转事件
        Intent intent = new Intent(this, MainActivity.class);
        /**Intent一般是用作Activity、Sercvice、BroadcastReceiver之间传递数据，
         而Pendingintent，一般用在 Notification上，
         可以理解为延迟执行的intent，PendingIntent是对Intent一个包装。*/
        //参数：1、上下文 2、请求码 3、用于启动的intent 4、新开启的Activity的启动模式
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        //参数1、上下文 2、下拉通知栏 显示的标题 3、内容  4、PendingIntent对象
        notification.contentIntent = pendingIntent;

        //启动Notification
        manager.notify(0, notification);
        //取消通知
        //manager.cancelAll();
    }
}
