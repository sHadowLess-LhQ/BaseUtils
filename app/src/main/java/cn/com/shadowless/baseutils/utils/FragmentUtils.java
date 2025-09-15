package cn.com.shadowless.baseutils.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 碎片工具类
 *
 * @author sHadowLess
 */
public class FragmentUtils {

    /**
     * 当前碎片
     */
    private static Fragment currentFragment = null;

    /**
     * 私有构造函数，不允许外部实例化
     */
    private FragmentUtils() {
    }

    /**
     * 显示碎片
     *
     * @param manager  Fragment管理器
     * @param fragment 碎片对象
     * @param layout   布局ID
     */
    public static void showFragment(FragmentManager manager, Fragment fragment, int layout) {
        show(manager, fragment, layout, null);
    }

    /**
     * 显示碎片
     *
     * @param manager   Fragment管理器
     * @param fragment  碎片对象
     * @param layout    布局ID
     * @param animation 动画资源ID数组
     */
    public static void showFragment(FragmentManager manager, Fragment fragment, int layout, int... animation) {
        show(manager, fragment, layout, animation);
    }

    /**
     * 替换碎片
     *
     * @param manager  the manager
     * @param fragment the 碎片
     * @param layout   the 布局
     */
    public static void replaceFragment(FragmentManager manager, Fragment fragment, int layout) {
        replace(manager, fragment, layout, null);
    }

    /**
     * 替换碎片
     *
     * @param manager   the manager
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    public static void replaceFragment(FragmentManager manager, Fragment fragment, int layout, int... animation) {
        replace(manager, fragment, layout, animation);
    }

    /**
     * 显示
     *
     * @param manager   the manager
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    private static void show(FragmentManager manager, Fragment fragment, int layout, int... animation) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        currentFragment = fragment;
        if (!fragment.isAdded()) {
            if (animation != null && animation.length != 0) {
                transaction.add(layout, fragment).show(fragment).setCustomAnimations(
                        animation[0],
                        animation[1],
                        animation[2],
                        animation[3]
                ).commit();
            } else {
                transaction.add(layout, fragment).show(fragment).commit();
            }
        } else {
            if (animation != null && animation.length != 0) {
                transaction.show(fragment).setCustomAnimations(
                        animation[0],
                        animation[1],
                        animation[2],
                        animation[3]
                ).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    /**
     * 替换
     *
     * @param manager   the manager
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    private static void replace(FragmentManager manager, Fragment fragment, int layout, int... animation) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (animation != null && animation.length != 0) {
            transaction
                    .setCustomAnimations(
                            animation[0],
                            animation[1],
                            animation[2],
                            animation[3]
                    )
                    .replace(layout, fragment)
                    .commit();
        } else {
            transaction
                    .replace(layout, fragment)
                    .commit();
        }
    }
}
