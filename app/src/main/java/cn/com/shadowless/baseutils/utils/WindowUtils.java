package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * 窗口工具类
 *
 * @author sHadowLess
 */
public class WindowUtils {

    /**
     * 构造
     */
    private WindowUtils() {
    }

    /**
     * 根布局
     */
    private final static int CONTENT = android.R.id.content;

    /**
     * 状态栏
     */
    public final static String TYPE_STATUE = "statue";

    /**
     * 导航栏
     */
    public final static String TYPE_NAVIGATION = "navigation";

    /**
     * 全部
     */
    public final static String TYPE_ALL = "all";

    /**
     * 获取根布局
     *
     * @param activity the activity
     * @return the base view
     */
    private static View getBaseView(Activity activity) {
        return activity.findViewById(CONTENT);
    }

    /**
     * 设置状态栏隐藏风格
     *
     * @param activity the activity
     */
    public static void setStatueBarHideStyle(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    /**
     * 设置隐藏状态栏
     *
     * @param activity 上下文
     */
    public static void hideStatusBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.hide(WindowInsetsCompat.Type.statusBars());
    }

    /**
     * 设置显示状态栏
     *
     * @param activity the activity
     */
    public static void showStatusBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.show(WindowInsetsCompat.Type.statusBars());
    }

    /**
     * 设置隐藏虚拟键盘
     *
     * @param activity the activity
     */
    public static void hideSoftInput(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.hide(WindowInsetsCompat.Type.ime());
    }

    /**
     * 设置显示虚拟键盘
     *
     * @param activity the activity
     */
    public static void showSoftInput(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.show(WindowInsetsCompat.Type.ime());
    }

    /**
     * 设置隐藏导航栏
     *
     * @param activity the activity
     */
    public static void hideNavigationBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.hide(WindowInsetsCompat.Type.navigationBars());
    }

    /**
     * 设置显示导航栏
     *
     * @param activity the activity
     */
    public static void showNavigationBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.show(WindowInsetsCompat.Type.navigationBars());
    }

    /**
     * 设置隐藏全屏
     *
     * @param activity the activity
     */
    public static void hideSystemBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.hide(WindowInsetsCompat.Type.systemBars());
    }

    /**
     * 设置显示全屏
     *
     * @param activity the activity
     */
    public static void showSystemBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.show(WindowInsetsCompat.Type.systemBars());
    }

    /**
     * 设置隐藏标题栏
     *
     * @param activity the activity
     */
    public static void hideCaptionBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.hide(WindowInsetsCompat.Type.captionBar());
    }

    /**
     * 设置显示标题栏
     *
     * @param activity the activity
     */
    public static void showCaptionBar(Activity activity) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.show(WindowInsetsCompat.Type.captionBar());
    }

    /**
     * 设置状态栏颜色
     *
     * @param window the window
     * @param color  the color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatueBarColor(Window window, int color) {
        window.setStatusBarColor(color);
    }

    /**
     * 设置导航栏颜色
     *
     * @param window the window
     * @param color  the color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColor(Window window, int color) {
        window.setNavigationBarColor(color);
    }

    /**
     * 设置导航栏是否有阴影
     *
     * @param window    the window
     * @param hasShadow the has shadow
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void setNavigationBarHasShadow(Window window, boolean hasShadow) {
        window.setNavigationBarContrastEnforced(hasShadow);
    }

    /**
     * 设置导航栏是否为白色/黑字：黑色/白字
     *
     * @param activity the activity
     * @param isLight  the is light
     */
    public static void setNavigationBarIsLight(Activity activity, boolean isLight) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.setAppearanceLightNavigationBars(isLight);
    }

    /**
     * 设置状态栏是否为白色/黑字：黑色/白字
     *
     * @param activity the activity
     * @param isLight  the is light
     */
    public static void setStatueBarIsLight(Activity activity, boolean isLight) {
        WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(getBaseView(activity));
        controllerCompat.setAppearanceLightStatusBars(isLight);
    }

    /**
     * 设置内容是否被导航栏和状态栏遮挡：自适应高度
     *
     * @param window  the window
     * @param isFront the is front
     */
    public static void setContentFront(Window window, boolean isFront) {
        WindowCompat.setDecorFitsSystemWindows(window, isFront);
    }

    /**
     * 设置内容与状态栏/导航栏高度自适应
     *
     * @param activity the activity
     * @param type     the type
     */
    public static void setContentBelowAndTop(Activity activity, String type) {
        ViewCompat.setOnApplyWindowInsetsListener(getBaseView(activity), (v, insets) -> {
            switch (type) {
                case TYPE_STATUE:
                    v.setPadding(0, insets.getInsets(WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout()).top, 0, 0);
                    break;
                case TYPE_NAVIGATION:
                    v.setPadding(0, 0, 0, insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom);
                    break;
                default:
                    v.setPadding(0, insets.getInsets(WindowInsetsCompat.Type.statusBars()).top, 0, insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom);
                    break;
            }
            return insets;
        });
    }

    /**
     * 获取屏幕宽度
     *
     * @return the width
     */
    public static int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return the height
     */
    public static int getHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
