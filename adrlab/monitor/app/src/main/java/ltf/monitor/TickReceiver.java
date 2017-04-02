package ltf.monitor;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * @author ltf
 * @since 17/4/3, 上午1:17
 */
public class TickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context, MainActivity.class);
        context.startActivity(it);
        //vibrate(context);
        //String msg = intent.getStringExtra("msg");
        //Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }


    private void vibrate(Context c) {
        Vibrator vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
        // 等待3秒，震动3秒，从第0个索引开始，一直循环
        vibrator.vibrate(new long[]{500, 1000}, 0);
    }
}
