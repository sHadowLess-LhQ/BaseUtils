package cn.com.shadowless.baseutils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * The type Asset utils.
 *
 * @author sHadowLess
 */
public class AssetUtils {
    /**
     * The constant instance.
     */
    @SuppressLint("StaticFieldLeak")
    private static AssetUtils instance;
    /**
     * The constant SUCCESS.
     */
    private static final int SUCCESS = 1;
    /**
     * The constant FAILED.
     */
    private static final int FAILED = 0;
    /**
     * The Context.
     */
    private final Context context;
    /**
     * The Callback.
     */
    private FileOperateCallback callback;
    /**
     * The Is success.
     */
    private volatile boolean isSuccess;
    /**
     * The Error str.
     */
    private String errorStr;

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static AssetUtils getInstance(Context context) {
        if (instance == null) {
            instance = new AssetUtils(context);
        }
        return instance;
    }

    /**
     * Instantiates a new Asset utils.
     *
     * @param context the context
     */
    private AssetUtils(Context context) {
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
    public AssetUtils copyAssetsToSd(final String srcPath, final String sdPath) {
        new Thread(() -> {
            copyAssetsToDst(context, srcPath, sdPath);
            if (isSuccess) {
                handler.obtainMessage(SUCCESS).sendToTarget();
            } else {
                handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    /**
     * Sets file operate callback.
     *
     * @param callback the callback
     */
    public void setFileOperateCallback(FileOperateCallback callback) {
        this.callback = callback;
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
