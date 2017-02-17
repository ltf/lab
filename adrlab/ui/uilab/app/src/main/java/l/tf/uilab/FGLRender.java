package l.tf.uilab;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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

    private static class Triangle {
        private FloatBuffer vertexBuffer;
        // 数组中每个顶点的坐标数
        static final int COORDS_PER_VERTEX = 3;
        static float triangleCoords[] = { // 按逆时针方向顺序:
                0.0f,  0.622008459f, 0.0f,   // top
                -0.5f, -0.311004243f, 0.0f,   // bottom left
                0.5f, -0.311004243f, 0.0f    // bottom right
        };
        // 设置颜色，分别为red, green, blue 和alpha (opacity)
        float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
        public Triangle() {
            // 为存放形状的坐标，初始化顶点字节缓冲
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (坐标数 * 4)float占四字节
                    triangleCoords.length * 4);
            // 设用设备的本点字节序
            bb.order(ByteOrder.nativeOrder());

            // 从ByteBuffer创建一个浮点缓冲
            vertexBuffer = bb.asFloatBuffer();
            // 把坐标们加入FloatBuffer中
            vertexBuffer.put(triangleCoords);
            // 设置buffer，从第一个坐标开始读
            vertexBuffer.position(0);
        }
    }

    private static class Square {
        private FloatBuffer vertexBuffer;
        private ShortBuffer drawListBuffer;
        // 每个顶点的坐标数
        static final int COORDS_PER_VERTEX = 3;
        static float squareCoords[] = { -0.5f,  0.5f, 0.0f,   // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f,  0.5f, 0.0f }; // top right
        private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // 顶点的绘制顺序
        public Square() {
            // initialize vertex byte buffer for shape coordinates
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (坐标数 * 4)
                    squareCoords.length * 4);
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(squareCoords);
            vertexBuffer.position(0);

            // 为绘制列表初始化字节缓冲
            ByteBuffer dlb = ByteBuffer.allocateDirect(
                    // (对应顺序的坐标数 * 2)short是2字节
                    drawOrder.length * 2);
            dlb.order(ByteOrder.nativeOrder());
            drawListBuffer = dlb.asShortBuffer();
            drawListBuffer.put(drawOrder);
            drawListBuffer.position(0);
        }
    }
}
