package cn.com.shadowless.baseutils.custom;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import lombok.Builder;

/**
 * 自定义窗口工具类
 *
 * @author sHadowLess
 */
@Builder
public class CustomWindow {

    /**
     * 上下文
     */
    private Context context;
    /**
     * 是否全局显示
     */
    private boolean isSystem;
    /**
     * 是否设置动画
     */
    private boolean isAnimation;
    /**
     * 动画值
     */
    private int anim;
    /**
     * 窗口标志
     */
    private int windowFlag;
    /**
     * 显示位置
     */
    private int location;
    /**
     * 窗口宽度
     */
    private int width;
    /**
     * 窗口高度
     */
    private int height;
    /**
     * 自定义布局文件
     */
    private int layout;
    /**
     * 自定义视图
     */
    private View view;

    /**
     * 自定义视图回调
     */
    public interface InitWindowListener {

        /**
         * Gets dialog view.
         *
         * @param view          the 视图
         * @param windowManager the 视窗管理器
         */
        void getWindowView(View view, WindowManager windowManager);
    }

    /**
     * 显示
     *
     * @param initWindowListener the init window listener
     */
    public void show(InitWindowListener initWindowListener) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        if (isAnimation && 0 != anim) {
            layoutParams.windowAnimations = anim;
        }

        if (isSystem) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = location;
        layoutParams.flags = windowFlag;
        layoutParams.width = width;
        layoutParams.height = height;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(layout, null, false);
        }

        windowManager.addView(view, layoutParams);

        if (null != initWindowListener) {
            initWindowListener.getWindowView(view, windowManager);
        }
    }
}
