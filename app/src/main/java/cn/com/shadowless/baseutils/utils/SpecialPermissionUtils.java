package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.VpnService;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

/**
 * 特殊权限工具类
 *
 * @author sHadowLess
 */
public class SpecialPermissionUtils {

    /**
     * Instantiates a new Permission utils.
     */
    private SpecialPermissionUtils() {
    }

    /**
     * 获取分区存储权限
     *
     * @param context the context
     */
    public static void hasExternalStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 获取分区存储权限(带返回)
     *
     * @param context the context
     * @param code    the code
     */
    public static void hasExternalStoragePermission(Activity context, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivityForResult(intent, code);
            }
        }
    }

    /**
     * 获取悬浮窗权限
     *
     * @param context the context
     */
    public static void hasFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 获取悬浮窗权限(带返回)
     *
     * @param context the context
     * @param code    the code
     */
    public static void hasFloatWindowPermission(Activity context, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivityForResult(intent, code);
            }
        }
    }

    /**
     * 获取分区存储媒体管理权限
     *
     * @param context the context
     */
    public static void hasMediaManagePermission(Context context) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            intent = new Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取分区存储媒体管理权限(带返回)
     *
     * @param context the context
     * @param code    the code
     */
    public static void hasMediaManagePermission(Activity context, int code) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            intent = new Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA);
        }
        context.startActivityForResult(intent, code);
    }

    /**
     * 获取写入设置权限
     *
     * @param context the context
     */
    public static void hasWriteSettingPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 获取写入设置权限(带返回)
     *
     * @param context the context
     * @param code    the code
     */
    public static void hasWriteSettingPermission(Activity context, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivityForResult(intent, code);
            }
        }
    }

    /**
     * 获取安装未知应用权限
     *
     * @param context the context
     */
    public static void hasInstallAppPermission(Context context) {
        PackageManager manager = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!manager.canRequestPackageInstalls()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 获取安装未知应用权限(带返回)
     *
     * @param context the context
     * @param code    the code
     */
    public static void hasInstallAppPermission(Activity context, int code) {
        PackageManager manager = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!manager.canRequestPackageInstalls()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivityForResult(intent, code);
            }
        }
    }

    /**
     * 获取是否有VPN连接
     *
     * @param context the context
     */
    public static void hasVpnCreatePermission(Context context) {
        Intent intent = VpnService.prepare(context);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 获取是否有VPN连接(带返回)
     *
     * @param context the context
     * @param code    the code
     */
    public static void hasVpnCreatePermission(Activity context, int code) {
        Intent intent = VpnService.prepare(context);
        if (intent != null) {
            context.startActivityForResult(intent, code);
        }
    }
}
