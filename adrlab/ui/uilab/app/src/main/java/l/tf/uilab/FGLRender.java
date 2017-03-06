package l.tf.uilab;

import android.app.Application;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.widget.TextView;

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
    private Triangle mTriangle;
    private Square mSquare;


    public static int loadShader(int type, String shaderCode) {

        // 创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
        // 或fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // 将源码添加到shader并编译之
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //设置背景的颜色
        GLES20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);

        // 初始化一个三角形
        mTriangle = new Triangle();
        // 初始化一个正方形
        mSquare = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // 重绘背景色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }


    private void drawText(String content) {
        TextView tv = new TextView(null);
        Canvas canvas=new Canvas();
        //canvas.drawText();

        tv.setText(content);

        //tv.draw();



        //GLES20.gltex;
    }

    private static class Triangle {
        private final int mProgram;
        private FloatBuffer vertexBuffer;
        // 数组中每个顶点的坐标数
        static final int COORDS_PER_VERTEX = 3;
        static float triangleCoords[] = { // 按逆时针方向顺序:
                0.0f, 0.622008459f, 0.0f,   // top
                -0.5f, -0.311004243f, 0.0f,   // bottom left
                0.5f, -0.311004243f, 0.0f    // bottom right
        };
        // 设置颜色，分别为red, green, blue 和alpha (opacity)
        float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
        private int mPositionHandle;
        private int mColorHandle;

        private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        private final int vertexStride = COORDS_PER_VERTEX * 4; // bytes per vertex

        private final String vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";


        public Triangle() {

            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

            mProgram = GLES20.glCreateProgram();             // 创建一个空的OpenGL ES Program
            GLES20.glAttachShader(mProgram, vertexShader);   // 将vertex shader添加到program
            GLES20.glAttachShader(mProgram, fragmentShader); // 将fragment shader添加到program
            GLES20.glLinkProgram(mProgram);                  // 创建可执行的 OpenGL ES program

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

        public void draw() {
            // 将program加入OpenGL ES环境中
            GLES20.glUseProgram(mProgram);

            // 获取指向vertex shader的成员vPosition的 handle
            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            // 启用一个指向三角形的顶点数组的handle
            GLES20.glEnableVertexAttribArray(mPositionHandle);

            // 准备三角形的坐标数据
            GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            // 获取指向fragment shader的成员vColor的handle
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

            // 设置三角形的颜色
            GLES20.glUniform4fv(mColorHandle, 1, color, 0);

            // 画三角形
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            // 禁用指向三角形的顶点数组
            GLES20.glDisableVertexAttribArray(mPositionHandle);
        }
    }

    private static class Square {
        private FloatBuffer vertexBuffer;
        private ShortBuffer drawListBuffer;
        // 每个顶点的坐标数
        static final int COORDS_PER_VERTEX = 3;
        static float squareCoords[] = {-0.5f, 0.5f, 0.0f,   // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f, 0.5f, 0.0f}; // top right
        private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // 顶点的绘制顺序

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
