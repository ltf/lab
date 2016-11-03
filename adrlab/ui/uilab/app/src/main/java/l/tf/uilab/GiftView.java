package l.tf.uilab;

import android.content.Context;
import android.graphics.*;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author ltf
 * @since 16/11/1, 上午11:31
 */
public class GiftView extends SurfaceView implements SurfaceHolder.Callback {

    //此处实现SurfaceHolder.Callback接口，为surfaceView添加生命周期回调函数
    int dy = Display.DEFAULT_DISPLAY;
    Context ctx;
    Paint paint;                            //画笔的引用
    OnDrawThread odt;                       //OnDrawThread类引用
    PicRunThread prt;                       //图片运动的Thread类引用
    private float picX = 0;                       //图片x坐标
    private float picY = 0;                       //图片y坐标
    boolean picAlphaFlag = false;                 //图片变暗效果的标记，false为不显示，true为显示。
    int picAlphaNum = 0;                          //图片变暗效果中画笔的alpha值

    public GiftView(Context context) {
        super(context);
        this.ctx = context;
        //将ma的引用指向调用了该Surfaceview类构造器方法的对象，本例为MyActivity
        this.getHolder().addCallback(this);     //注册回调接口
        paint = new Paint();                      //实例化画笔
        odt = new OnDrawThread(this);             //实例化OnDrawThread类
        prt = new PicRunThread(this);             //实例化PicRunThread类
        prt.start();
    }

    public void setPicX(float picX) {           //图片x坐标的设置器
        this.picX = picX;
    }

    public void setPicY(float picY) {           //图片y坐标的设置器
        this.picY = picY;
    }

    public void setPicAlphaNum(int picAlphaNum) {//图片变暗效果alpha参数设置器
        this.picAlphaNum = picAlphaNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {  //onDraw方法，此方法用于绘制图像，图形等
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);        //设置画笔为白色
        canvas.drawRect(0, 0, Constant.SCREENWIDTH, Constant.SCREENHEIGHT, paint);
        //此处画了一个白色的全屏幕的矩形，目的是设置背景为白色，同时每次重绘时清除背景
        //进行平面贴图
        Bitmap bitmapDuke = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ico);
        canvas.drawBitmap(bitmapDuke, picX, picY, paint);
        //图片渐暗效果
        if (picAlphaFlag) {
            Bitmap bitmapBG = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ico);
            paint.setAlpha(picAlphaNum);
            canvas.drawBitmap(bitmapBG, 0, 0, paint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {           //此方法为当surfaceView改变时调用，如屏幕大小改变。
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//此方法为在surfaceView创建时调用
        odt.start();                //启动onDraw的绘制线程
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//此方法为在surfaceView销毁前调用
    }


    //该类的作用是时时刷新onDraw，进行画面的重绘
    public static class OnDrawThread extends Thread {
        GiftView gv;      //得到MySurfaceView的引用
        SurfaceHolder sh;       //SurfaceHolder引用

        public OnDrawThread(GiftView gv) {
            super();
            this.gv = gv;         //构造方法中，将gv引用指向调用了该类的MySurfaceView的对象
            sh = gv.getHolder();
        }

        @Override
        public void run() {
            super.run();
            Canvas canvas = null;   //Canvas的引用
            while (true) {
                try {
                    canvas = sh.lockCanvas(null);         //将canvas的引用指向surfaceView的canvas的对象
                        if (canvas != null) {
                            gv.onDraw(canvas);
                        }
                } finally {
                    try {
                        if (sh != null) {
                            sh.unlockCanvasAndPost(canvas); //绘制完后解锁
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(Constant.ONDRAWSPEED);                 //休息1秒钟
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //该类是控制duke图片运动的类
    public static class PicRunThread extends Thread {
        GiftView gv;                                  //MySurfaceView的引用
        private float picX = 0;           //图片x坐标
        private float picY = Constant.SCREENHEIGHT - Constant.PICHEIGHT;            //图片y坐标
        boolean yRunFlag = false;     //y方向上的运动标记，false时y=y+speed，true时y=y-speed
        int picAlphaNum = 0;                  //图片变暗效果中画笔的alpha值

        public PicRunThread(GiftView gv) {
            super();
            this.gv = gv;         //将该线程类的引用指向调用其的MySurfaceView的对象
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                //控制duke图片的运动
                while (this.picX < Constant.SCREENWIDTH) {           //当图片的左边完全超过屏幕的右边时，循环结束
                    gv.setPicX(picX);
                    gv.setPicY(picY);
                    picX = picX + Constant.PICXSPEED;
                    if (yRunFlag) {//应该向上运动，自减
                        picY = picY - Constant.PICYSPEED;
                    } else {//应该向下运动，自加
                        picY = picY + Constant.PICYSPEED;
                    }
                    if (picY <= 0) {                                 //到达屏幕上沿
                        yRunFlag = false;
                    } else if (picY > Constant.SCREENHEIGHT - Constant.PICHEIGHT) {     //到达屏幕下沿
                        yRunFlag = true;
                    }
                    try {
                        Thread.sleep(Constant.PICRUNSPEED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //图片变暗效果演示
                gv.picAlphaFlag = true;                          //开启图片变暗效果
                for (picAlphaNum = 0; picAlphaNum <= 255; picAlphaNum++) {
                    if (picAlphaNum == 255) {
                        gv.picAlphaFlag = false;                 //当图片变暗效果结束，标记重置
                        picX = 0;         //图片x坐标
                        picY = Constant.SCREENHEIGHT - Constant.PICHEIGHT;          //图片y坐标
                        System.out.println(gv.picAlphaFlag + "picX:" + picX + "picY:" + picY);
                    }
                    gv.setPicAlphaNum(picAlphaNum);
                    try {
                        Thread.sleep(Constant.PICALPHASPEED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //Constant.java     常量类，将常量全部写在该类中
    public static class Constant {
        public static int SCREENWIDTH = 480;  //屏幕宽（本程序为横屏）
        public static int SCREENHEIGHT = 320;     //屏幕高
        public static int PICWIDTH = 64;          //图片宽度
        public static int PICHEIGHT = 64;         //图片高度
        public static int ONDRAWSPEED = 30;       //onDraw线程类的绘制间隔时间
        public static float PICXSPEED = 1.5f;     //图片水平移动速度
        public static float PICYSPEED = 2;        //图片垂直移动速度
        public static int PICRUNSPEED = 30;       //图片的运动线程的刷新速度
        public static int PICALPHASPEED = 20; //图片渐暗效果演示刷新速度
    }
}
