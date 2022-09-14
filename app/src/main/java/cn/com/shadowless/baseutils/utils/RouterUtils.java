package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由工具类
 *
 * @author sHadowLess
 */
public class RouterUtils {

    /**
     * 私有构造
     */
    private RouterUtils() {

    }

    /**
     * 默认跳转超时时间
     */
    public static final int DEFAULT_TIMEOUT = 2;

    /**
     * 获取跳转配置
     *
     * @param path the path
     * @return the postcard
     */
    public static Postcard getDefaultPostcard(String path) {
        return ARouter.getInstance().build(path).setTimeout(DEFAULT_TIMEOUT);
    }

    /**
     * 获取跳转配置
     *
     * @param path    the 路径
     * @param timeOut the 超时
     * @return the default postcard
     */
    public static Postcard getDefaultPostcard(String path, int timeOut) {
        return ARouter.getInstance().build(path).setTimeout(timeOut);
    }

    /**
     * 跳转
     *
     * @param path the 路径
     */
    public static void jump(String path) {
        ARouter.getInstance().build(path).setTimeout(DEFAULT_TIMEOUT).navigation();
    }

    /**
     * 跳转
     *
     * @param path    the 路径
     * @param timeOut the 超时
     */
    public static void jump(String path, int timeOut) {
        ARouter.getInstance().build(path).setTimeout(timeOut).navigation();
    }

    /**
     * 跳转
     *
     * @param path    the 路径
     * @param context the 上下文
     */
    public static void jump(String path, Context context) {
        ARouter.getInstance().build(path).setTimeout(DEFAULT_TIMEOUT).navigation(context);
    }

    /**
     * 跳转
     *
     * @param path    the 路径
     * @param context the 上下文
     * @param timeOut the 超时
     */
    public static void jump(String path, Context context, int timeOut) {
        ARouter.getInstance().build(path).setTimeout(timeOut).navigation(context);
    }

    /**
     * 跳转
     *
     * @param path     the 路径
     * @param context  the 上下文
     * @param callback the 回调
     */
    public static void jump(String path, Context context, NavigationCallback callback) {
        ARouter.getInstance().build(path).setTimeout(DEFAULT_TIMEOUT).navigation(context, callback);
    }

    /**
     * 跳转
     *
     * @param path     the 路径
     * @param context  the 上下文
     * @param timeOut  the 超时
     * @param callback the 回调
     */
    public static void jump(String path, Context context, int timeOut, NavigationCallback callback) {
        ARouter.getInstance().build(path).setTimeout(timeOut).navigation(context, callback);
    }

    /**
     * 跳转
     *
     * @param path        the 路径
     * @param context     the 上下文
     * @param requestCode the 请求数据参数
     */
    public static void jump(String path, Activity context, int requestCode) {
        ARouter.getInstance().build(path).setTimeout(DEFAULT_TIMEOUT).navigation(context, requestCode);
    }

    /**
     * 跳转
     *
     * @param path        the 路径
     * @param context     the 上下文
     * @param timeOut     the 超时
     * @param requestCode the 请求数据参数
     */
    public static void jump(String path, Activity context, int timeOut, int requestCode) {
        ARouter.getInstance().build(path).setTimeout(timeOut).navigation(context, requestCode);
    }

    /**
     * 跳转
     *
     * @param path        the 路径
     * @param context     the 上下文
     * @param requestCode the 请求数据参数
     * @param callback    the 回调
     */
    public static void jump(String path, Activity context, int requestCode, NavigationCallback callback) {
        ARouter.getInstance().build(path).setTimeout(DEFAULT_TIMEOUT).navigation(context, requestCode, callback);
    }

    /**
     * 跳转
     *
     * @param path        the 路径
     * @param context     the 上下文
     * @param timeOut     the 超时
     * @param requestCode the 请求数据参数
     * @param callback    the 回调
     */
    public static void jump(String path, Activity context, int timeOut, int requestCode, NavigationCallback callback) {
        ARouter.getInstance().build(path).setTimeout(timeOut).navigation(context, requestCode, callback);
    }
}
