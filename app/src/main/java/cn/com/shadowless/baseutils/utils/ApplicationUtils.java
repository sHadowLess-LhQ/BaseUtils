package cn.com.shadowless.baseutils.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 应用工具类
 *
 * @author sHadowLess
 */
public class ApplicationUtils {

    /**
     * Instantiates a new Application utils.
     */
    private ApplicationUtils() {
    }

    /**
     * 通过包名打开应用
     *
     * @param context  the 上下文
     * @param packName the 包名
     */
    public static void startActivityForPackage(Context context, String packName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packName);
        if (null != intent) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "终端没有安装", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 完全退出应用
     *
     * @param activity the 上下文
     */
    public static void completeExitApp(Activity activity) {
        activity.moveTaskToBack(true);
        int pid = Process.myPid();
        Process.killProcess(pid);
        System.exit(0);
    }

    /**
     * 通过包名卸载应用
     *
     * @param context     the 上下文
     * @param packageName the 包名
     */
    public static void startUnInstall(Context context, String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
        uninstallIntent.putExtra("packageName", packageName);
        uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }

    /**
     * 通过文件路径安装应用
     *
     * @param context the 上下文
     * @param apkPath the apk文件路径
     */
    public static void startInstallApk(Context context, String apkPath) {
        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 跳转到本应用详情信息
     *
     * @param context the 上下文
     */
    public static void startApplicationInfo(Context context) {
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.getPackageName())));
    }

    /**
     * 通过包名跳转到指定应用详情信息
     *
     * @param context     the 上下文
     * @param packageName the 包名
     */
    public static void startApplicationInfo(Context context, String packageName) {
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + packageName)));
    }

    /**
     * 跳转到设置
     *
     * @param context the 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startHomeSetting(Context context) {
        context.startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
    }

    /**
     * 跳转到系统相册
     *
     * @param context the 上下文
     */
    public static void startCamera(Context context) {
        context.startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    /**
     * 通过地址访问
     *
     * @param context the 上下文
     * @param url     the url
     */
    public static void startBrowser(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * 清除本应用数据
     *
     * @param context the 上下文
     */
    public static void clearAppData(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.clearApplicationUserData();
    }

    /**
     * 获取设备唯一码
     *
     * @return the uuid
     */
    public static String getUuid() {
        String devId = "55" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        return new UUID(devId.hashCode(), devId.hashCode()).toString().replace("-", "");
    }

    /**
     * 获取应用版本码
     *
     * @param context  the 上下文
     * @param packName the 包名
     * @return the 版本码
     */
    public static long getVersionCode(Context context, String packName) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(packName, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return packageInfo.getLongVersionCode();
        } else {
            return packageInfo.versionCode;
        }
    }

    /**
     * 获取应用版本码名称
     *
     * @param context  the 上下文
     * @param packName the 包名
     * @return the 版本码名称
     */
    public static String getVersionName(Context context, String packName) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(packName, 0);
        return packageInfo.versionName;
    }

    /**
     * 获取apk信息
     *
     * @param context  the 上下文
     * @param packName the 包名
     * @return the package info
     */
    public static PackageInfo getPackageInfo(Context context, String packName) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(packName, 0);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context     the 上下文
     * @param packageName the 应用包名
     * @return boolean boolean
     */
    @SuppressLint("QueryPermissionsNeeded")
    public static boolean isInstall(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        for (int i = 0; i < packageInfoList.size(); i++) {
            String packName = packageInfoList.get(i).packageName;
            packageNames.add(packName);
        }
        return packageNames.contains(packageName);
    }
}

