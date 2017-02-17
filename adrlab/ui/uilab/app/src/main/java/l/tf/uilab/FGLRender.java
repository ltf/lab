package l.tf.uilab;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author ltf
 * @since 17/2/17, 下午4:51
 */
public class FGLRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //设置背景的颜色
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // 重绘背景色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
