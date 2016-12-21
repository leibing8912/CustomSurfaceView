package cn.jianke.customsurfaceview.module.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.ref.WeakReference;
import cn.jianke.customsurfaceview.common.ThreadManager;

/**
 * @className:
 * @classDescription:
 * @author: leibing
 * @createTime: 2016/12/19
 */
public class RectSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    // SurfaceHolder instance
    private SurfaceHolder mSurfaceHolder;

    public RectSurfaceView(Context context) {
        super(context);
    }

    public RectSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    /**
     * init SurfaceHolder
     * @author leibing
     * @createTime 2016/12/19
     * @lastModify 2016/12/19
     * @param
     * @return
     */
    private void initSurfaceHolder(){
        // init SurfaceHolder instance
        mSurfaceHolder = this.getHolder();
        // add callBack
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // start canvas
        ThreadManager.getInstance().getNewCachedThreadPool().execute(
                new CanvasThread(mSurfaceHolder));
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /**
     * @className: CanvasThread
     * @classDescription: 绘画线程
     * @author: leibing
     * @createTime: 2016/12/19
     */
    static class CanvasThread implements Runnable{
        // SurfaceHolder Instance WeakRef
        private WeakReference<SurfaceHolder> mSurfaceHolderWeakRef;

        /**
         *
         * @author leibing
         * @createTime 2016/12/19
         * @lastModify 2016/12/19
         * @param
         * @return
         */
        public CanvasThread(SurfaceHolder mSurfaceHolder){
            // init SurfaceHolder Instance WeakRef
            mSurfaceHolderWeakRef = new WeakReference<SurfaceHolder>(mSurfaceHolder);
        }

        @Override
        public void run() {
            // WeakRef Deal It
            if (mSurfaceHolderWeakRef != null
                    && mSurfaceHolderWeakRef.get() != null){
                // 获取画布
                Canvas mCanvas = mSurfaceHolderWeakRef.get().lockCanvas(null);
                // 获取画笔
                Paint mPaint = new Paint();
                // 给画笔设置颜色(蓝色)
                mPaint.setColor(Color.BLUE);
                // 开始画图（长方矩形）
                mCanvas.drawRect(new RectF(40, 60, 80, 80), mPaint);
                // 解锁画布，提交画好的图像
                mSurfaceHolderWeakRef.get().unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
