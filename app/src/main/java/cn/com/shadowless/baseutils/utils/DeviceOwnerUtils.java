package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.com.shadowless.baseutils.receiver.BasicDeviceAdminReceiver;

import static android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE;

/**
 * The type Mdm utils.
 *
 * @author sHadowLess
 */
public class DeviceOwnerUtils {

    /**
     * 应用信息类
     */
    public static class AppInfo {

        /**
         * Instantiates a new App info.
         *
         * @param packageName the package name
         * @param ico         the ico
         * @param name        the name
         * @param mIsEnable   the m is enable
         */
        public AppInfo(String packageName, Drawable ico, CharSequence name, boolean mIsEnable) {
        }
    }

    /**
     * The Tag.
     */
    private final String TAG = DeviceOwnerUtils.class.getSimpleName();
    /**
     * 密码令牌
     */
    private final String token = UUID.randomUUID().toString().replace("-", "");
    /**
     * DeviceAdmin请求码
     */
    public static final int REQUEST_CODE_CHECK_ACTIVE = 1;
    /**
     * ProfileOwner请求码
     */
    public static final int REQUEST_CODE_CHECK_PROFILE = 2;
    /**
     * 确认凭证请求码
     */
    public static final int REQUEST_CODE_CONFIRM_TOKEN = 3;
    /**
     * 设备工具类实例
     */
    public static DeviceOwnerUtils mInstance = null;
    /**
     * 设备策略管理器实例
     */
    private DevicePolicyManager devicePolicyManager = null;

    /**
     * 应用列表
     */
    private List<AppInfo> mApplicationInfoList = new ArrayList<>();

    /**
     * 构造
     */
    private DeviceOwnerUtils() {

    }

    /**
     * 提示框回调接口
     */
    public interface OnDialogClick {
        /**
         * On click.
         */
        void onCheckClick();

        /**
         * On cancel click.
         */
        void onCancelClick();
    }

    /**
     * 获取设备工具类实例
     *
     * @return the instance
     */
    public synchronized static DeviceOwnerUtils getInstance() {
        if (null == mInstance) {
            synchronized (DeviceOwnerUtils.class) {
                if (null == mInstance) {
                    mInstance = new DeviceOwnerUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取设备策略管理器实例
     *
     * @param context the 上下文
     * @return the device policy manager
     */
    public DevicePolicyManager getDevicePolicyManager(Context context) {
        if (null == devicePolicyManager) {
            devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        }
        return devicePolicyManager;
    }

    /**
     * 获取DeviceAdmin权限
     *
     * @param context the 上下文
     * @return the boolean
     */
    public boolean initDeviceAdmin(Activity context) {
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        boolean isAdminActive = getDevicePolicyManager(context).isAdminActive(componentName);
        if (!isAdminActive) {
            Intent intent = new Intent();
            //指定动作
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            //指定给那个组件授权
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "获取设备管理，以控制设备");
            context.startActivityForResult(intent, REQUEST_CODE_CHECK_ACTIVE);
        }
        return isAdminActive;
    }

    /**
     * 清除DeviceOwner权限
     *
     * @param context the 上下文
     */
    public void clearDeviceOwner(Context context) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            boolean isAdminActive = getDevicePolicyManager(context).isAdminActive(componentName);
            if (isAdminActive) {
                getDevicePolicyManager(context).clearDeviceOwnerApp(context.getPackageName());
            }
        }
    }

    /**
     * 获取应用列表
     *
     * @param context the 上下文
     * @return the app
     */
    public List<AppInfo> getApp(Context context) {
        try {
            mApplicationInfoList.clear();
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            for (PackageInfo info : packageInfos) {
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                mainIntent.setPackage(info.packageName);
                List<ResolveInfo> mainActivities = packageManager.queryIntentActivities(mainIntent, PackageManager.GET_UNINSTALLED_PACKAGES);
                if (mainActivities.size() > 0) {
                    boolean isEnable = isApplicationEnabled(context, info.packageName);
                    AppInfo appInfo = new AppInfo(
                            info.packageName,
                            packageManager.getApplicationIcon(info.applicationInfo),
                            packageManager.getApplicationLabel(info.applicationInfo),
                            isEnable
                    );
                    mApplicationInfoList.add(appInfo);
                }
            }
            return mApplicationInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return mApplicationInfoList;
        }
    }

    /**
     * 判断应用是否启用
     *
     * @param context     the 上下文
     * @param packageName the 应用包名
     * @return the boolean
     */
    private boolean isApplicationEnabled(Context context, String packageName) {
        if (isDeviceOwnerDeviceApp(context)) {
            PackageManager packageManager = context.getPackageManager();
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                if (0 == (applicationInfo.flags & ApplicationInfo.FLAG_INSTALLED)) {
                    return false;
                }
                return !getDevicePolicyManager(context).isApplicationHidden(BasicDeviceAdminReceiver.getComponentName(context), packageName);
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否拥有DeviceAdmin权限
     *
     * @param context the 上下文
     * @return the boolean
     */
    public boolean isAdminDeviceApp(Context context) {
        return getDevicePolicyManager(context).isAdminActive(BasicDeviceAdminReceiver.getComponentName(context));
    }

    /**
     * 是否拥有ProfileOwner权限
     *
     * @param context the 上下文
     * @return the boolean
     */
    public boolean isProfileOwnerDeviceApp(Context context) {
        return getDevicePolicyManager(context).isProfileOwnerApp(context.getPackageName());
    }

    /**
     * 是否拥有DeviceOwner权限
     *
     * @param context the 上下文
     * @return the boolean
     */
    public boolean isDeviceOwnerDeviceApp(Context context) {
        return getDevicePolicyManager(context).isDeviceOwnerApp(context.getPackageName());
    }

    /**
     * 禁用企业空间应用截屏
     *
     * @param context the 上下文
     * @param disable the 是否禁用
     */
    public void banCompanyScreenShoot(Context context, boolean disable) {
        if (isProfileOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).setScreenCaptureDisabled(BasicDeviceAdminReceiver.getComponentName(context), disable);
        }
    }

    /**
     * 添加程序默认项
     *
     * @param context      the 上下文
     * @param packageName  the 包名
     * @param className    the 类名
     * @param intentFilter the 过滤器
     */
    public void addPersistentPreferred(Context context, String packageName, String className, IntentFilter intentFilter) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = new ComponentName(packageName, className);
            getDevicePolicyManager(context).addPersistentPreferredActivity(BasicDeviceAdminReceiver.getComponentName(context), intentFilter, componentName);
        }
    }

    /**
     * 清除程序默认项
     *
     * @param context     the 上下文
     * @param packageName the 包名
     */
    public void clearPackagePersistentPreferred(Context context, String packageName) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).clearPackagePersistentPreferredActivities(BasicDeviceAdminReceiver.getComponentName(context), packageName);
        }
    }

    /**
     * 禁用全局应用截屏
     *
     * @param context the 上下文
     * @param disable the 是否禁用
     */
    public void banDeviceScreenShoot(Context context, boolean disable) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).setScreenCaptureDisabled(BasicDeviceAdminReceiver.getComponentName(context), disable);
        }
    }

    /**
     * 禁用企业空间应用拍照
     *
     * @param context the 上下文
     * @param disable the 是否禁用
     */
    public void banCompanyCamera(Context context, boolean disable) {
        if (isProfileOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).setCameraDisabled(BasicDeviceAdminReceiver.getComponentName(context), disable);
        }
    }

    /**
     * 禁用全局应用拍照
     *
     * @param context the 上下文
     * @param disable the 是否禁用
     */
    public void banDeviceCamera(Context context, boolean disable) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).setCameraDisabled(BasicDeviceAdminReceiver.getComponentName(context), disable);
        }
    }

    /**
     * 重启设别
     *
     * @param context the 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reboot(Context context) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).reboot(componentName);
        }
    }

    /**
     * 擦除企业空间
     *
     * @param context the 上下文
     */
    public void wipeCompanyData(Context context) {
        if (isProfileOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).wipeData(0);
        }
    }

    /**
     * 锁屏
     *
     * @param context the 上下文
     */
    public void lockScreen(Context context) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).lockNow();
        }
    }

    /**
     * 模式锁屏
     *
     * @param context the 上下文
     * @param flag    the 锁屏标志
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void lockScreen(Context context, int flag) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).lockNow(flag);
        }
    }

    /**
     * 恢复出厂设置
     *
     * @param context the 上下文
     */
    public void wipeDeviceData(Context context) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 清除企业空间拥有者权限
     *
     * @param context the 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clearCompanyProfile(Context context) {
        if (isProfileOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).clearProfileOwner(componentName);
        }
    }

    /**
     * 设置系统时间
     *
     * @param context the 上下文
     * @param time    the 时间
     * @return the 是否成功
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public boolean setSystemTime(Context context, long time) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).setTime(componentName, time);
        }
        return isSuccess;
    }

    /**
     * 设置系统时区
     *
     * @param context  the 上下文
     * @param timeZone the 时区
     * @return the 是否成功
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public boolean setSystemTimeZone(Context context, String timeZone) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).setTimeZone(componentName, timeZone);
        }
        return isSuccess;
    }

    /**
     * 禁用状态栏下拉
     *
     * @param context the 上下文
     * @param isBan   the 是否禁用
     * @return the 是否已经禁用
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean banStatueBar(Context context, boolean isBan) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).setStatusBarDisabled(componentName, isBan);
        }
        return isSuccess;
    }

    /**
     * 设置锁屏设备拥有者信息
     *
     * @param context the 上下文
     * @param info    the 屏幕信息
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDeviceOwnerScreenInfo(Context context, String info) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).setDeviceOwnerLockScreenInfo(componentName, info);
        }
    }

    /**
     * 令牌是否重置成功
     *
     * @param context the 上下文
     * @return the 是否成功
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isResetPasswordToken(Context context) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).isResetPasswordTokenActive(componentName);
        }
        return isSuccess;
    }

    /**
     * 清除密码令牌
     *
     * @param context the 上下文
     * @return the 是否成功
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean clearPasswordToken(Context context) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).clearResetPasswordToken(componentName);
        }
        return isSuccess;
    }

    /**
     * 确认激活token
     *
     * @param activity       the activity
     * @param confirmTitle   the 确认标题
     * @param confirmContent the 确认内容
     */
    public void confirmToken(Activity activity, String confirmTitle, String confirmContent) {
        KeyguardManager keyguardManager = (android.app.KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        Intent intent = keyguardManager.createConfirmDeviceCredentialIntent(confirmTitle, confirmContent);
        if (null != intent) {
            activity.startActivityForResult(intent, REQUEST_CODE_CONFIRM_TOKEN);
        }
    }

    /**
     * 输入最大次数错误密码擦除设备
     *
     * @param context the 上下文
     * @param num     the 次数
     */
    public void setMaxNumInputErrorPwd(Context context, int num) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).setMaximumFailedPasswordsForWipe(componentName, num);
        }
    }

    /**
     * 设置锁屏密码
     *
     * @param context  the 上下文
     * @param password the 密码
     * @return the 是否设置
     */
    public boolean setScreenPassword(Context context, String password) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            DevicePolicyManager manager = getDevicePolicyManager(context);
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                isSuccess = manager.resetPasswordWithToken(componentName, password, token.getBytes(), 0);
            } else {
                isSuccess = manager.resetPassword(password, 0);
            }
        }
        return isSuccess;
    }

    /**
     * 关闭备份服务
     *
     * @param context the 上下文
     * @param isBan   the 是否关闭
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void closeBackUpService(Context context, boolean isBan) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).setBackupServiceEnabled(componentName, isBan);
        }
    }

    /**
     * 是否关闭备份服务
     *
     * @param context the 上下文
     * @return the 是否关闭
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isCloseBackUp(Context context) {
        boolean isSuccess = false;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).isBackupServiceEnabled(componentName);
        }
        return isSuccess;
    }

    /**
     * 关闭定位服务
     *
     * @param context the 上下文
     * @param isBan   the 是否关闭
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void closeLocation(Context context, boolean isBan) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).setLocationEnabled(componentName, isBan);
        }
    }

    /**
     * 设置用户限制
     *
     * @param context the 上下文
     * @param key     the 限制名
     */
    public void setUserRestriction(Context context, String key) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).addUserRestriction(componentName, key);
        }
    }

    /**
     * 获取电源锁
     *
     * @param activity the 主页
     * @return the 电源锁
     */
    public void acquireWakeLock(Activity activity) {
        PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock m_wakeLockObj = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, activity.getClass().getName());
        m_wakeLockObj.acquire(10000);
        m_wakeLockObj.release();
    }

    /**
     * 清除指定用户限制
     *
     * @param context the 上下文
     * @param key     the 限制名
     */
    public void clearUserRestriction(Context context, String key) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).clearUserRestriction(componentName, key);
        }
    }

    /**
     * 清除所有用户限制
     *
     * @param context the 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clearAllUserRestriction(Context context) {
        if (isDeviceOwnerDeviceApp(context)) {
            Bundle bundle = getUserRestriction(context);
            if (null != bundle) {
                for (String key : bundle.keySet()) {
                    clearUserRestriction(context, key);
                }
            }
        }
    }

    /**
     * 获取所有用户限制
     *
     * @param context the 上下文
     * @return the user restriction
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Bundle getUserRestriction(Context context) {
        Bundle isSuccess = null;
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            isSuccess = getDevicePolicyManager(context).getUserRestrictions(componentName);
        }
        return isSuccess;
    }

    /**
     * 通过包名清除指定应用数据
     *
     * @param context     the 上下文
     * @param packageName the 包名
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void clearApplicationUserData(Context context, String packageName) {
        if (isDeviceOwnerDeviceApp(context)) {
            ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
            getDevicePolicyManager(context).clearApplicationUserData(componentName, packageName, command -> {

            }, (packageName1, succeeded) -> Log.e(TAG, "onApplicationUserDataCleared: " + packageName1 + succeeded));
        }
    }

    /**
     * 显示提示框
     *
     * @param context       the 上下文
     * @param message       the 提示信息
     * @param onDialogClick the on dialog click
     */
    public void showDialog(Context context, String message, final OnDialogClick onDialogClick) {
        showDialog(context, message, (dialogInterface, i) -> {
            switch (i) {
                case AlertDialog.BUTTON_NEGATIVE:
                    onDialogClick.onCancelClick();
                    break;
                case AlertDialog.BUTTON_POSITIVE:
                    onDialogClick.onCheckClick();
                    break;
                default:
                    break;
            }
            dialogInterface.dismiss();
        });
    }

    /**
     * Show dialog.
     *
     * @param context         the 上下文
     * @param text            the 提示信息
     * @param onClickListener the on click listener
     */
    private void showDialog(Context context, String text, DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog
                .Builder(context)
                .setTitle("提示")
                .setMessage(text)
                .setPositiveButton("确定", onClickListener)
                .setNegativeButton("取消", onClickListener)
                .create();
        alertDialog.show();
    }

    /**
     * 创建企业空间
     *
     * @param context the 上下文
     */
    public void initProfile(Activity context) {
        Intent intent = new Intent(ACTION_PROVISION_MANAGED_PROFILE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            intent.putExtra(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                    context.getPackageName()
            );
        } else {
            final ComponentName component = new ComponentName(context,
                    BasicDeviceAdminReceiver.class.getName());
            intent.putExtra(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                    component
            );
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivityForResult(intent, REQUEST_CODE_CHECK_PROFILE);
        } else {
            showDialog(context, "收集的界面信息失败，是否重新获取？", new OnDialogClick() {
                @Override
                public void onCheckClick() {
                    initProfile(context);
                }

                @Override
                public void onCancelClick() {

                }
            });
        }
    }
}
