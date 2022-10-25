package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.therouter.TheRouter;
import com.therouter.router.Navigator;
import com.therouter.router.interceptor.NavigationCallback;

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
     * 获取跳转配置
     *
     * @param path the path
     * @return the postcard
     */
    public static Navigator getNavigator(String path) {
        return TheRouter.build(path);
    }

    /**
     * 获取碎片
     *
     * @param <T>  the type parameter
     * @param path the 路径
     * @return the fragment
     */
    public static <T extends Fragment> T getFragment(String path) {
        return TheRouter.build(path).createFragment();
    }

    /**
     * 显示碎片
     *
     * @param fragmentManager the fragment manager
     * @param path            the 路径
     * @param layout          the 布局
     */
    public static void showRouterFragment(FragmentManager fragmentManager, String path, int layout) {
        FragmentUtils.showFragment(fragmentManager, getFragment(path), layout);
    }

    /**
     * 显示碎片
     *
     * @param fragmentManager the fragment manager
     * @param path            the 路径
     * @param layout          the 布局
     * @param anim            the 动画
     */
    public static void showRouterFragment(FragmentManager fragmentManager, String path, int layout, int... anim) {
        FragmentUtils.showFragment(fragmentManager, getFragment(path), layout, anim);
    }

    /**
     * 替换碎片
     *
     * @param fragmentManager the fragment manager
     * @param path            the 路径
     * @param layout          the 布局
     */
    public static void replaceRouterFragment(FragmentManager fragmentManager, String path, int layout) {
        FragmentUtils.replaceFragment(fragmentManager, getFragment(path), layout);
    }

    /**
     * 替换碎片
     *
     * @param fragmentManager the fragment manager
     * @param path            the 路径
     * @param layout          the 布局
     * @param anim            the 动画
     */
    public static void replaceRouterFragment(FragmentManager fragmentManager, String path, int layout, int... anim) {
        FragmentUtils.replaceFragment(fragmentManager, getFragment(path), layout, anim);
    }

    /**
     * 跳转
     *
     * @param path the 路径
     */
    public static void jump(String path) {
        TheRouter.build(path).navigation();
    }

    /**
     * 跳转
     *
     * @param path    the 路径
     * @param context the 上下文
     */
    public static void jump(String path, Context context) {
        TheRouter.build(path).navigation(context);
    }

    /**
     * 跳转
     *
     * @param path     the 路径
     * @param context  the 上下文
     * @param callback the 回调
     */
    public static void jump(String path, Context context, NavigationCallback callback) {
        TheRouter.build(path).navigation(context, callback);
    }

    /**
     * 跳转
     *
     * @param path        the 路径
     * @param context     the 上下文
     * @param requestCode the 请求数据参数
     */
    public static void jump(String path, Activity context, int requestCode) {
        TheRouter.build(path).navigation(context, requestCode);
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
        TheRouter.build(path).navigation(context, requestCode, callback);
    }
}
