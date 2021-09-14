package cn.com.shadowless.baseutils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import lombok.Builder;

/**
 * The type My window.
 *
 * @author sHadowLess
 */
@Builder
public class MyWindow {

    private Context context;
    private WindowManager windowManager;
    private boolean isSystem;
    private boolean isAnimation;
    private int anim;
    private int windowFlag;
    private int location;
    private int width;
    private int height;
    private int layout;
    private View view;

    /**
     * The interface Init view.
     */
    interface InitWindowListener {

        /**
         * Gets dialog view.
         *
         * @param view          the view
         * @param windowManager the window manager
         */
        void getWindowView(View view, WindowManager windowManager);
    }

    /**
     * Show.
     *
     * @param initWindowListener the init window listener
     */
    public void show(InitWindowListener initWindowListener) {
        if (null == windowManager) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

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
