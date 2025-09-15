package cn.com.shadowless.baseutils.utils;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import cn.com.shadowless.baseutils.R;
import cn.com.shadowless.baseutils.toast.ToastParams;
import cn.com.shadowless.baseutils.toast.Toaster;
import cn.com.shadowless.baseutils.toast.config.IToastInterceptor;
import cn.com.shadowless.baseutils.toast.config.IToastStrategy;
import cn.com.shadowless.baseutils.toast.config.IToastStyle;
import cn.com.shadowless.baseutils.toast.style.BlackToastStyle;
import cn.com.shadowless.baseutils.toast.style.CustomToastStyle;
import cn.com.shadowless.baseutils.toast.style.WhiteToastStyle;

/**
 * Toast工具类
 *
 * @author sHadowLess
 */
@SuppressWarnings("unused")
public final class ToastUtils {

    /**
     * 不允许被外部实例化
     */
    private ToastUtils() {
    }

    /**
     * 初始化 Toast，需要在 Application.create 中初始化
     *
     * @param application 应用的上下文
     */
    public static void init(Application application) {
        Toaster.init(application);
    }

    /**
     * 初始化
     *
     * @param application 应用对象
     * @param strategy    Toast策略
     */
    public static void init(Application application, IToastStrategy strategy) {
        Toaster.init(application, strategy, null);
    }

    /**
     * 初始化
     *
     * @param application 应用对象
     * @param style       Toast样式
     */
    public static void init(Application application, IToastStyle<?> style) {
        Toaster.init(application, null, style);
    }

    /**
     * 判断当前框架是否已经初始化
     *
     * @return the boolean
     */
    public static boolean isInit() {
        return Toaster.isInit();
    }

    /**
     * 显示成功
     *
     * @param text the text
     */
    public static void showSuccess(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_success, Gravity.BOTTOM);
        show(params);
    }

    /**
     * 延迟显示成功
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowSuccess(CharSequence text, long delayMillis) {
        delayedShow(text, delayMillis, new CustomToastStyle(R.layout.toast_success, Gravity.BOTTOM));
    }

    /**
     * 短显示成功
     *
     * @param text the text
     */
    public static void shortShowSuccess(CharSequence text) {
        showShort(text, new CustomToastStyle(R.layout.toast_success, Gravity.BOTTOM));
    }

    /**
     * 长显示成功
     *
     * @param text the text
     */
    public static void longShowSuccess(CharSequence text) {
        showLong(text, new CustomToastStyle(R.layout.toast_success, Gravity.BOTTOM));
    }

    /**
     * 显示信息
     *
     * @param text the text
     */
    public static void showInfo(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_info, Gravity.BOTTOM);
        show(params);
    }

    /**
     * 延迟显示信息
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowInfo(CharSequence text, long delayMillis) {
        delayedShow(text, delayMillis, new CustomToastStyle(R.layout.toast_info, Gravity.BOTTOM));
    }

    /**
     * 短显示信息
     *
     * @param text the text
     */
    public static void shortShowInfo(CharSequence text) {
        showShort(text, new CustomToastStyle(R.layout.toast_info, Gravity.BOTTOM));
    }

    /**
     * 长显示信息
     *
     * @param text the text
     */
    public static void longShowInfo(CharSequence text) {
        showLong(text, new CustomToastStyle(R.layout.toast_info, Gravity.BOTTOM));
    }

    /**
     * 显示警告
     *
     * @param text the text
     */
    public static void showWaring(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_warn, Gravity.BOTTOM);
        show(params);
    }

    /**
     * 延迟显示警告
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowWaring(CharSequence text, long delayMillis) {
        delayedShow(text, delayMillis, new CustomToastStyle(R.layout.toast_warn, Gravity.BOTTOM));
    }

    /**
     * 短显示警告
     *
     * @param text the text
     */
    public static void shortShowWaring(CharSequence text) {
        showShort(text, new CustomToastStyle(R.layout.toast_warn, Gravity.BOTTOM));
    }

    /**
     * 长显示警告
     *
     * @param text the text
     */
    public static void longShowWaring(CharSequence text) {
        showLong(text, new CustomToastStyle(R.layout.toast_warn, Gravity.BOTTOM));
    }

    /**
     * 显示失败
     *
     * @param text the text
     */
    public static void showError(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_error, Gravity.BOTTOM);
        show(params);
    }

    /**
     * 延迟显示失败
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowError(CharSequence text, long delayMillis) {
        delayedShow(text, delayMillis, new CustomToastStyle(R.layout.toast_error, Gravity.BOTTOM));
    }

    /**
     * 短显示失败
     *
     * @param text the text
     */
    public static void shortShowError(CharSequence text) {
        showShort(text, new CustomToastStyle(R.layout.toast_error, Gravity.BOTTOM));
    }

    /**
     * 长显示失败
     *
     * @param text the text
     */
    public static void longShowError(CharSequence text) {
        showLong(text, new CustomToastStyle(R.layout.toast_error, Gravity.BOTTOM));
    }

    /**
     * 延迟显示 Toast
     *
     * @param id          the id
     * @param delayMillis the delay millis
     */
    public static void delayedShow(int id, long delayMillis) {
        delayedShow(stringIdToCharSequence(id), delayMillis);
    }

    /**
     * Delayed show.
     *
     * @param object      the object
     * @param delayMillis the delay millis
     */
    public static void delayedShow(Object object, long delayMillis) {
        delayedShow(objectToCharSequence(object), delayMillis);
    }

    /**
     * Delayed show.
     *
     * @param text        the text
     * @param delayMillis the delay millis
     * @param style       the style
     */
    public static void delayedShow(CharSequence text, long delayMillis, IToastStyle<?> style) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.delayMillis = delayMillis;
        params.style = style;
        show(params);
    }

    /**
     * debug 模式下显示 Toast
     *
     * @param id the id
     */
    public static void debugShow(int id) {
        debugShow(stringIdToCharSequence(id));
    }

    /**
     * Debug show.
     *
     * @param object the object
     */
    public static void debugShow(Object object) {
        debugShow(objectToCharSequence(object));
    }

    /**
     * Debug show.
     *
     * @param text the text
     */
    public static void debugShow(CharSequence text) {
        if (!isDebugMode()) {
            return;
        }
        ToastParams params = new ToastParams();
        params.text = text;
        show(params);
    }

    /**
     * 显示一个短 Toast
     *
     * @param id the id
     */
    public static void showShort(int id) {
        showShort(stringIdToCharSequence(id));
    }

    /**
     * Show short.
     *
     * @param object the object
     */
    public static void showShort(Object object) {
        showShort(objectToCharSequence(object));
    }

    /**
     * Show short.
     *
     * @param text  the text
     * @param style the style
     */
    public static void showShort(CharSequence text, IToastStyle<?> style) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_SHORT;
        params.style = style;
        show(params);
    }

    /**
     * 显示一个长 Toast
     *
     * @param id the id
     */
    public static void showLong(int id) {
        showLong(stringIdToCharSequence(id));
    }

    /**
     * Show long.
     *
     * @param object the object
     */
    public static void showLong(Object object) {
        showLong(objectToCharSequence(object));
    }

    /**
     * Show long.
     *
     * @param text  the text
     * @param style the style
     */
    public static void showLong(CharSequence text, IToastStyle<?> style) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_LONG;
        params.style = style;
        show(params);
    }

    /**
     * 显示 Toast
     *
     * @param id the id
     */
    public static void show(int id) {
        show(stringIdToCharSequence(id));
    }

    /**
     * Show.
     *
     * @param object the object
     */
    public static void show(Object object) {
        show(objectToCharSequence(object));
    }

    /**
     * Show.
     *
     * @param text the text
     */
    public static void show(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        show(params);
    }

    /**
     * Show.
     *
     * @param params the params
     */
    public static void show(ToastParams params) {
        Toaster.show(params);
    }

    /**
     * 取消吐司的显示
     */
    public static void cancel() {
        Toaster.cancel();
    }

    /**
     * 设置吐司的位置
     *
     * @param gravity 重心
     */
    public static void setGravity(int gravity) {
        setGravity(gravity, 0, 0);
    }

    /**
     * Sets gravity.
     *
     * @param gravity the gravity
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        setGravity(gravity, xOffset, yOffset, 0, 0);
    }

    /**
     * Sets gravity.
     *
     * @param gravity          the gravity
     * @param xOffset          the x offset
     * @param yOffset          the y offset
     * @param horizontalMargin the horizontal margin
     * @param verticalMargin   the vertical margin
     */
    public static void setGravity(int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        Toaster.setGravity(gravity, xOffset, yOffset, horizontalMargin, verticalMargin);
    }

    /**
     * 给当前 Toast 设置新的布局
     *
     * @param id the id
     */
    public static void setView(int id) {
        Toaster.setView(id);
    }

    /**
     * 初始化全局的 Toast 样式
     *
     * @param style 样式实现类，框架已经实现两种不同的样式              黑色样式：{@link BlackToastStyle}              白色样式：{@link WhiteToastStyle}
     */
    public static void setStyle(IToastStyle<?> style) {
        if (style == null) {
            return;
        }
        Toaster.setStyle(style);
    }

    /**
     * Gets style.
     *
     * @return the style
     */
    public static IToastStyle<?> getStyle() {
        return Toaster.getStyle();
    }

    /**
     * 设置 Toast 显示策略
     *
     * @param strategy the strategy
     */
    public static void setStrategy(IToastStrategy strategy) {
        if (strategy == null) {
            return;
        }
        Toaster.setStrategy(strategy);
    }

    /**
     * Gets strategy.
     *
     * @return the strategy
     */
    public static IToastStrategy getStrategy() {
        return Toaster.getStrategy();
    }

    /**
     * 设置 Toast 拦截器（可以根据显示的内容决定是否拦截这个Toast）
     * 场景：打印 Toast 内容日志、根据 Toast 内容是否包含敏感字来动态切换其他方式显示（这里可以使用我的另外一套框架 EasyWindow）
     *
     * @param interceptor the interceptor
     */
    public static void setInterceptor(IToastInterceptor interceptor) {
        Toaster.setInterceptor(interceptor);
    }

    /**
     * Gets interceptor.
     *
     * @return the interceptor
     */
    public static IToastInterceptor getInterceptor() {
        return Toaster.getInterceptor();
    }

    /**
     * 是否为调试模式
     *
     * @param debug the debug
     */
    public static void setDebugMode(boolean debug) {
        Toaster.setDebugMode(debug);
    }

    /**
     * Is debug mode boolean.
     *
     * @return the boolean
     */
    static boolean isDebugMode() {
        return Toaster.isDebugMode();
    }

    /**
     * String id to char sequence char sequence.
     *
     * @param id the id
     * @return the char sequence
     */
    private static CharSequence stringIdToCharSequence(int id) {
        return Toaster.stringIdToCharSequence(id);
    }

    /**
     * Object to char sequence char sequence.
     *
     * @param object the object
     * @return the char sequence
     */
    private static CharSequence objectToCharSequence(Object object) {
        return object != null ? object.toString() : "null";
    }
}