package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 设备文件工具类
 *
 * @author sHadowLess
 */
public class FileUtils {

    /**
     * 私有构造函数，不允许外部实例化
     */
    private FileUtils() {
    }

    /**
     * 内部存储data文件夹
     *
     * @return /data目录
     */
    public static File getAppData() {
        return Environment.getDataDirectory();
    }

    /**
     * 内部存储应用file文件夹
     *
     * @param context the 上下文
     * @return the /data/user/0/packName/files
     */
    public static File getAppFile(Context context) {
        return context.getFilesDir();
    }

    /**
     * 内部存储应用file文件夹下的子文件
     *
     * @param context the 上下文
     * @return the /data/user/0/packName/files/
     */
    public static String[] getAppFileList(Context context) {
        return context.fileList();
    }

    /**
     * 内部存储应用cache文件夹
     *
     * @param context the 上下文
     * @return the /data/user/0/packName/cache
     */
    public static File getAppCacheFile(Context context) {
        return context.getCacheDir();
    }

    /**
     * 内部存储应用指定文件夹(若无自动创建)
     *
     * @param context  the 上下文
     * @param fileName the 文件名
     * @param mode     the 创建模式
     * @return the /data/user/0/packName/fileName
     */
    public static File getAppDirFile(Context context, String fileName, int mode) {
        return context.getDir(fileName, mode);
    }

    /**
     * 内部存储应用file文件夹下创建子文件
     *
     * @param context  the 上下文
     * @param fileName the 文件名
     * @param mode     the 创建模式
     * @return the app file out put
     * @throws FileNotFoundException the file not found exception
     */
    public static FileOutputStream getAppFileOutPut(Context context, String fileName, int mode) throws FileNotFoundException {
        return context.openFileOutput(fileName, mode);
    }

    /**
     * 内部存储应用file文件夹读取子文件
     *
     * @param context  the 上下文
     * @param fileName the 文件名
     * @return the app file in put
     * @throws FileNotFoundException the file not found exception
     */
    public static FileInputStream getAppFileInPut(Context context, String fileName) throws FileNotFoundException {
        return context.openFileInput(fileName);
    }

    /**
     * 内部存储应用file文件夹删除指定子文件
     *
     * @param context  the 上下文
     * @param fileName the 文件名
     * @return the 是否删除
     */
    public static boolean deleteAppFile(Context context, String fileName) {
        return context.deleteFile(fileName);
    }

    /**
     * 外部储存文件夹
     *
     * @return the /storage/emulated/0/
     */
    public static File getDeviceStorageDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 外部储存应用file文件夹
     *
     * @param context the 上下文
     * @return the /storage/emulated/0/Android/data/packName/files
     */
    public static File getDeviceAppFileDir(Context context) {
        return context.getExternalFilesDir(null);
    }

    /**
     * 外部储存应用cache文件夹
     *
     * @param context the 上下文
     * @return the /storage/emulated/0/Android/data/packName/cache
     */
    public static File getDeviceAppCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 外部储存相册文件夹
     *
     * @return the /storage/emulated/0/DICM
     */
    public static File getDeviceDiCmDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    /**
     * 外部储存视频文件夹
     *
     * @return the /storage/emulated/0/Movies
     */
    public static File getDeviceMovieDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    /**
     * 外部储存音乐文件夹
     *
     * @return the /storage/emulated/0/Music
     */
    public static File getDeviceMusicDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    }

    /**
     * 外部储存下载文件夹
     *
     * @return the /storage/emulated/0/Download
     */
    public static File getDeviceDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 外部储存通知文件夹
     *
     * @return the /storage/emulated/0/Notifications
     */
    public static File getDeviceNotificationDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
    }

    /**
     * 外部储存警告文件夹
     *
     * @return the /storage/emulated/0/Alarms
     */
    public static File getDeviceAlarmsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
    }

    /**
     * 外部储存播客文件夹
     *
     * @return the /storage/emulated/0/Podcasts
     */
    public static File getDevicePodCastDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
    }

    /**
     * 外部储存铃声文件夹
     *
     * @return the /storage/emulated/0/Ringtones
     */
    public static File getDeviceRingTonesDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
    }

    /**
     * 外部储存文件文件夹
     *
     * @return the /storage/emulated/0/Documents
     */
    public static File getDeviceDocumentDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    }

    /**
     * 外部储存截屏文件夹
     *
     * @return the /storage/emulated/0/Screenshots
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File getDeviceScreenShotDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_SCREENSHOTS);
    }

    /**
     * 创建文件夹
     *
     * @param filePath the 文件路径
     * @return the 是否创建成功
     */
    public static boolean createFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param filePath the file 文件路径
     * @return the 是否创建成功
     * @throws IOException the io exception
     */
    public static boolean createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.createNewFile();
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param file        the file
     * @param isDelFolder the is del folder
     * @return the boolean
     */
    public static Boolean deleteFile(File file, boolean isDelFolder) {
        if (file == null || !file.exists()) {
            return false;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteFile(f, isDelFolder);
            } else {
                f.delete();
            }
        }
        if (isDelFolder) {
            file.delete();
        }
        return true;
    }

    /**
     * 转换文件大小单位MB
     *
     * @param size the 字节
     * @return the 文件MB单位大小字符串
     */
    public static double getFileLengthToMb(long size) {
        return (size / (1024.0 * 1024.0));
    }

    /**
     * 写入文件到内部存储应用file文件夹
     *
     * @param context  the 上下文
     * @param fileName the 文件名
     * @param mode     the 写入模式
     * @param is       the is
     * @throws IOException the io exception
     */
    public static void writeFileToData(Context context, String fileName, int mode, InputStream is) throws IOException {
        FileOutputStream fos = getAppFileOutPut(context, fileName, mode);
        FileDescriptor fd = fos.getFD();
        writeFileToSdCard(0, 0, is, fos);
        fd.sync();
    }

    /**
     * Write file to data.
     *
     * @param context       the context
     * @param fileName      the file name
     * @param mode          the mode
     * @param totalSize     the total size
     * @param readCacheSize the read cache size
     * @param is            the is
     * @throws IOException the io exception
     */
    public static void writeFileToData(Context context, String fileName, int mode, long totalSize, long readCacheSize, InputStream is) throws IOException {
        FileOutputStream fos = getAppFileOutPut(context, fileName, mode);
        FileDescriptor fd = fos.getFD();
        writeFileToSdCard(totalSize, readCacheSize, is, fos);
        fd.sync();
    }

    /**
     * 写入文件到SD卡
     *
     * @param file       the file
     * @param is         the is
     * @param isContinue the 是否续写
     * @throws IOException the io exception
     */
    public static void writeFileToSdCard(File file, InputStream is, boolean isContinue) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, isContinue);
        writeFileToSdCard(0, 0, is, fos);
    }

    /**
     * Write file to sd card.
     *
     * @param file          the file
     * @param totalSize     the total size
     * @param readCacheSize the read cache size
     * @param is            the is
     * @param isContinue    the is continue
     * @throws IOException the io exception
     */
    public static void writeFileToSdCard(File file, long totalSize, long readCacheSize, InputStream is, boolean isContinue) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, isContinue);
        writeFileToSdCard(totalSize, readCacheSize, is, fos);
    }

    /**
     * Write file to sd card.
     *
     * @param totalSize     the total size
     * @param readCacheSize the read cache size
     * @param is            the is
     * @param os            the os
     * @throws IOException the io exception
     */
    public static void writeFileToSdCard(long totalSize, long readCacheSize, InputStream is, OutputStream os) throws IOException {
        boolean isComputePkg = totalSize != 0 && readCacheSize != 0;
        long writeTimes = 0;
        long remainderSize = 0;
        if (isComputePkg) {
            writeTimes = totalSize / readCacheSize;
            remainderSize = totalSize % readCacheSize;
        }
        byte[] buffer = new byte[(int) readCacheSize];
        int bytesRead;
        int currentWriteTimes = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
            if (isComputePkg) {
                currentWriteTimes++;
                if (currentWriteTimes == writeTimes) {
                    buffer = new byte[(int) remainderSize];
                }
            }
        }
        os.close();
        is.close();
    }

}
