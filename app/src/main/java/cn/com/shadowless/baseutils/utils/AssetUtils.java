package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Executors;

/**
 * 资源文件工具类
 *
 * @author sHadowLess
 */
public enum AssetUtils {

    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 成功标识
     */
    private static final int SUCCESS = 1;
    /**
     * 失败标识
     */
    private static final int FAILED = 0;
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 文件操作回调
     */
    private FileOperateCallback callback;
    /**
     * 是否成功
     */
    private volatile boolean isSuccess;
    /**
     * 错误信息
     */
    private String errorStr;

    /**
     * Gets instance.
     *
     * @param context the context
     */
    public void getInstance(Context context) {
        this.context = context;
    }

    /**
     * The Handler.
     */
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback.onSuccess();
                }
                if (msg.what == FAILED) {
                    callback.onFailed(msg.obj.toString());
                }
            }
        }
    };

    /**
     * Copy assets to sd asset utils.
     *
     * @param srcPath the src path
     * @param sdPath  the sd path
     * @return the asset utils
     */
    public void copyAssetsToSd(String srcPath, String sdPath, FileOperateCallback callback) {
        this.callback = callback;
        Executors.newSingleThreadExecutor().execute(() -> {
            copyAssetsToDst(context, srcPath, sdPath);
            if (isSuccess) {
                handler.obtainMessage(SUCCESS).sendToTarget();
            } else {
                handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        });
    }

    /**
     * Copy assets to dst.
     *
     * @param context the context
     * @param srcPath the src path
     * @param dstPath the dst path
     */
    private void copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String[] fileNames = context.getAssets().list(srcPath);
            if (fileNames != null) {
                if (fileNames.length > 0) {
                    File file = new File(dstPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    for (String fileName : fileNames) {
                        if (!"".equals(srcPath)) {
                            copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + fileName);
                        } else {
                            copyAssetsToDst(context, fileName, dstPath + fileName);
                        }
                    }
                } else {
                    File outFile = new File(dstPath);
                    InputStream is = context.getAssets().open(srcPath);
                    FileOutputStream fos = new FileOutputStream(outFile);
                    byte[] buffer = new byte[1024];
                    int byteCount;
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                }
                isSuccess = true;
            } else {
                isSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    /**
     * The interface File operate callback.
     */
    public interface FileOperateCallback {
        /**
         * On success.
         */
        void onSuccess();

        /**
         * On failed.
         *
         * @param error the error
         */
        void onFailed(String error);
    }
}
