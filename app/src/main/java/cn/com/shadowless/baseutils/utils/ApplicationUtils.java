package cn.com.shadowless.baseutils.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.UUID;

/**
 * 应用工具类
 *
 * @author sHadowLess
 */
public class ApplicationUtils {

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
            Toast.makeText(context, "展示终端没有安装", Toast.LENGTH_LONG).show();
        }
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
        try {
            File file = new File(apkPath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
            intent.setFlags(flags);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public static void startHomeSetting(Context context) {
        context.startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
    }

    /**
     * 跳转到系统日历
     *
     * @param context the 上下文
     */
    public static void startCalendar(Context context) {
        context.startActivity(new Intent().setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity")));
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
    public static String getUUID() {
        String mSzDevIDShort = "55" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        try {
            return new UUID(mSzDevIDShort.hashCode(), mSzDevIDShort.hashCode()).toString().replace("-", "");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }
}

