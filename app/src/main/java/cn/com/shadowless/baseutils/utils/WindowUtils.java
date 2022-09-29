package cn.com.shadowless.baseutils.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * The type Window util.
 *
 * @author sHadowLess
 */
public class WindowUtils {

    /**
     * Instantiates a new Window utils.
     */
    private WindowUtils() {
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity 上下文
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void hideStatusBar(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attributes.flags |= flagTranslucentStatus;
            window.setAttributes(attributes);
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param activity the activity
     */
    public static void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 设置状态栏半透明
     *
     * @param activity the activity
     */
    public static void setHalfTransparent(Activity activity) {
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context the 上下文
     * @return the width
     */
    public static int getWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度的
     *
     * @param context the 上下文
     * @return the height
     */
    public static int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 隐藏虚拟键盘
     *
     * @param context the 上下文
     * @param view    the view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
