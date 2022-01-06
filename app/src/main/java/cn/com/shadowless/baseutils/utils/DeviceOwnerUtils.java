package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mengpeng.mphelper.ToastUtils;

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
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        DevicePolicyManager manager = getDevicePolicyManager(context);
        boolean isAdminActive = manager.isAdminActive(componentName);
        if (isAdminActive) {
            manager.clearDeviceOwnerApp(context.getPackageName());
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
        PackageManager packageManager = context.getPackageManager();
        DevicePolicyManager manager = getDevicePolicyManager(context);
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            if (0 == (applicationInfo.flags & ApplicationInfo.FLAG_INSTALLED)) {
                return false;
            }
            return !manager.isApplicationHidden(BasicDeviceAdminReceiver.getComponentName(context), packageName);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
        } else {
            showToast(3, "该设备暂无企业空间权限");
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
        } else {
            showToast(3, "暂无最高设备权限");
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
        } else {
            showToast(3, "该设备暂无企业空间权限");
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
        } else {
            showToast(3, "暂无最高设备权限");
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
        } else {
            showToast(3, "该设备暂无企业空间权限");
        }
    }

    /**
     * 锁屏
     *
     * @param context the 上下文
     */
    public void lockScreen(Context context) {
        getDevicePolicyManager(context).lockNow();
    }

    /**
     * 模式锁屏
     *
     * @param context the 上下文
     * @param flag    the 锁屏标志
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void lockScreen(Context context, int flag) {
        getDevicePolicyManager(context).lockNow(flag);
    }

    /**
     * 恢复出厂设置
     *
     * @param context the 上下文
     */
    public void wipeDeviceData(Context context) {
        if (isDeviceOwnerDeviceApp(context)) {
            getDevicePolicyManager(context).wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
        } else {
            showToast(3, "暂无最高设备权限");
        }
    }

    /**
     * 设置企业空间应用密码
     *
     * @param context  the 上下文
     * @param password the 密码
     */
    public void setCompanyPassword(Context context, String password) {
        DevicePolicyManager manager = getDevicePolicyManager(context);
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        if (isProfileOwnerDeviceApp(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasToken = manager.setResetPasswordToken(componentName, token.getBytes());
                boolean isTokenActive = manager.isResetPasswordTokenActive(componentName);
                if (hasToken && isTokenActive) {
                    manager.resetPasswordWithToken(componentName, password, token.getBytes(), 0);
                } else {
                    showToast(2, "企业空间已设置密码，无法再次设置");
                }
            } else {
                manager.resetPassword(password, 0);
            }
        } else {
            showToast(3, "该设备暂无企业空间权限");
        }
    }

    /**
     * 设置设备锁屏密码
     *
     * @param context  the 上下文
     * @param password the 密码
     */
    public void setDevicePassword(Context context, String password) {
        DevicePolicyManager manager = getDevicePolicyManager(context);
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        if (isDeviceOwnerDeviceApp(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasToken = manager.setResetPasswordToken(componentName, token.getBytes());
                boolean isTokenActive = manager.isResetPasswordTokenActive(componentName);
                if (hasToken && isTokenActive) {
                    manager.resetPasswordWithToken(componentName, password, token.getBytes(), 0);
                } else {
                    showToast(2, "设备已设置密码，无法再次设置");
                }
            } else {
                manager.resetPassword(password, 0);
            }
        } else {
            showToast(3, "暂无最高设备权限");
        }
    }

    /**
     * 设置用户限制
     *
     * @param context the 上下文
     * @param key     the 限制名
     */
    public void setUserRestriction(Context context, String key) {
        DevicePolicyManager manager = getDevicePolicyManager(context);
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        manager.addUserRestriction(componentName, key);
    }

    /**
     * 清除指定用户限制
     *
     * @param context the 上下文
     * @param key     the 限制名
     */
    public void clearUserRestriction(Context context, String key) {
        DevicePolicyManager manager = getDevicePolicyManager(context);
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        manager.clearUserRestriction(componentName, key);
    }

    /**
     * 清除所有用户限制
     *
     * @param context the 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clearAllUserRestriction(Context context) {
        Bundle bundle = getUserRestriction(context);
        if (null != bundle) {
            for (String key : bundle.keySet()) {
                clearUserRestriction(context, key);
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
        DevicePolicyManager manager = getDevicePolicyManager(context);
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        return manager.getUserRestrictions(componentName);
    }

    /**
     * 通过包名清除指定应用数据
     *
     * @param context     the 上下文
     * @param packageName the 包名
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void clearApplicationUserData(Context context, String packageName) {
        DevicePolicyManager manager = getDevicePolicyManager(context);
        ComponentName componentName = BasicDeviceAdminReceiver.getComponentName(context);
        manager.clearApplicationUserData(componentName, packageName, command -> {

        }, (packageName1, succeeded) -> Log.e(TAG, "onApplicationUserDataCleared: " + packageName1 + succeeded));
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
     * 显示图示
     *
     * @param level the 提示等级
     * @param text  the 提示信息
     */
    private void showToast(int level, String text) {
        switch (level) {
            case 1:
                ToastUtils.onInfoShowToast(text);
                break;
            case 2:
                ToastUtils.onWarnShowToast(text);
                break;
            case 3:
                ToastUtils.onErrorShowToast(text);
                break;
            default:
                break;
        }
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
