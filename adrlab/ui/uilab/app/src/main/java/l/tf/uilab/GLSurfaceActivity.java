package l.tf.uilab;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author ltf
 * @since 17/2/17, 下午4:45
 */
public class GLSurfaceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FGLSurfaceView(this));
    }
}
