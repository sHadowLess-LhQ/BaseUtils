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
 * The type Application utils.
 *
 * @author sHadowLess
 */
public class ApplicationUtils {

    /**
     * Start activity for package.
     *
     * @param context  the context
     * @param packName the pack name
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
     * Start un install.
     *
     * @param context     the context
     * @param packageName the package name
     */
    public static void startUnInstall(Context context, String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
        uninstallIntent.putExtra("packageName", packageName);
        uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }

    /**
     * Start install apk.
     *
     * @param context the context
     * @param apkPath the apk path
     */
    public static void startInstallApk(Context context, String apkPath) {
        try {
            File file = new File(apkPath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", file);
            intent.setFlags(flags);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start application info.
     *
     * @param context the context
     */
    public static void startApplicationInfo(Context context) {
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.getPackageName())));
    }

    /**
     * Start application info.
     *
     * @param context     the context
     * @param packageName the package name
     */
    public static void startApplicationInfo(Context context, String packageName) {
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + packageName)));
    }

    /**
     * Start home setting.
     *
     * @param context the context
     */
    public static void startHomeSetting(Context context) {
        context.startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
    }

    /**
     * Start calendar.
     *
     * @param context the context
     */
    public static void startCalendar(Context context) {
        context.startActivity(new Intent().setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity")));
    }

    /**
     * Start camera.
     *
     * @param context the context
     */
    public static void startCamera(Context context) {
        context.startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    /**
     * Start browser.
     *
     * @param context the context
     */
    public static void startBrowser(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * Clear app data.
     *
     * @param context the context
     */
    public static void clearAppData(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.clearApplicationUserData();
    }

    /**
     * Gets uuid.
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

