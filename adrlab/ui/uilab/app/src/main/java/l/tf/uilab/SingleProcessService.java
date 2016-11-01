package l.tf.uilab;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author ltf
 * @since 16/10/27, 下午1:48
 */
public class SingleProcessService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
