package l.tf.uilab;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * @author ltf
 * @since 17/2/17, 下午4:49
 */
public class FGLSurfaceView extends GLSurfaceView {
    public FGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new FGLRender());
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
