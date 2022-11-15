package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import cn.com.shadowless.baseutils.record.RecordConfig;

/**
 * 录屏录制工具类
 *
 * @author sHadowLess
 */
public class ScreenRecordUtils {

    /**
     * 媒体项目管理
     */
    private MediaProjectionManager mediaProjectionManager;
    /**
     * 媒体录制
     */
    private MediaRecorder mediaRecorder;
    /**
     * 媒体投影
     */
    private MediaProjection mediaProjection;
    /**
     * 虚拟显示
     */
    private VirtualDisplay virtualDisplay;
    /**
     * 显示参数
     */
    private DisplayMetrics displayMetrics;
    /**
     * 保存临时文件
     */
    private File saveTempFile;
    /**
     * 是否正在录制
     */
    private boolean isRecording;
    /**
     * 录制监听
     */
    private final OnVideoRecordListener listener;
    /**
     * 录屏参数
     */
    private final RecordConfig recordConfig;
    /**
     * 请求码
     */
    private final int REQUEST_CODE = 1024;

    /**
     * 构造
     *
     * @param recordConfig the 录屏参数
     * @param listener     the 录制监听
     */
    public ScreenRecordUtils(RecordConfig recordConfig, OnVideoRecordListener listener) {
        this.listener = listener;
        this.recordConfig = recordConfig;
    }

    /**
     * 返回是否正在录制
     *
     * @return the 结果
     */
    public boolean isRecording() {
        return this.isRecording;
    }

    /**
     * 开始录制
     */
    public void startRecord() {
        if (getMediaProjectionManager() == null) {
            showToast();
            return;
        }
        MediaProjectionManager mediaProjectionManager = getMediaProjectionManager();
        if (mediaProjectionManager != null) {
            Intent intent = mediaProjectionManager.createScreenCaptureIntent();
            if (listener != null) {
                listener.onBeforeRecord();
            }
            if (recordConfig.getActivity().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                recordConfig.getActivity().startActivityForResult(intent, REQUEST_CODE);
            } else {
                showToast();
            }
        }
    }

    /**
     * 恢复
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resume() {
        if (mediaRecorder != null) {
            mediaRecorder.resume();
        }
    }

    /**
     * 停止
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pause() {
        if (mediaRecorder != null) {
            mediaRecorder.pause();
        }
    }

    /**
     * 取消录制
     */
    public void cancelRecord() {
        stopRecord();
        listener.onCancelRecord();
    }

    /**
     * 请求回调
     *
     * @param requestCode the 请求码
     * @param resultCode  the 结果码
     * @param data        the data
     */
    public void onActivityResult(int requestCode, int resultCode, @NotNull Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == -1) {
                this.mediaProjection = getMediaProjectionManager().getMediaProjection(resultCode, data);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (initRecorder()) {
                        isRecording = true;
                        if (mediaRecorder != null) {
                            mediaRecorder.start();
                        }
                        if (listener != null) {
                            listener.onStartRecord();
                        }
                    } else {
                        showToast();
                    }
                }, 150);
            } else {
                showToast();
            }
        }
    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        stop();
        File temp = new File(recordConfig.getSavePath(), recordConfig.getFileName() + ".mp4");
        saveTempFile.renameTo(temp);
        refreshVideo(temp);
        saveTempFile = null;
    }

    /**
     * 获取媒体项目管理
     *
     * @return the media projection manager
     */
    private MediaProjectionManager getMediaProjectionManager() {
        if (mediaProjectionManager == null) {
            mediaProjectionManager = (MediaProjectionManager) recordConfig.getActivity().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }
        return mediaProjectionManager;
    }

    /**
     * 获取显示参数
     *
     * @return the display metrics
     */
    private DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            recordConfig.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    /**
     * 显示土司
     */
    private void showToast() {
        Toast.makeText(recordConfig.getActivity(), "当前设备暂不支持录屏", Toast.LENGTH_SHORT).show();
    }

    /**
     * 停止
     */
    private void stop() {
        if (isRecording) {
            isRecording = false;
            try {
                mediaRecorder.setOnErrorListener(null);
                mediaRecorder.setOnInfoListener(null);
                mediaRecorder.setPreviewDisplay(null);
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mediaRecorder.reset();
                virtualDisplay.release();
                mediaProjection.stop();
                listener.onEndRecord();
            }
        }
    }

    /**
     * 初始化录制
     *
     * @return the 结果
     */
    @RequiresApi(api = Build.VERSION_CODES.S)
    private boolean initRecorder() {
        boolean result = true;
        File file = new File(recordConfig.getSavePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        saveTempFile = new File(recordConfig.getSavePath(), recordConfig.getFileName() + ".tmp");
        if (saveTempFile.exists()) {
            saveTempFile.delete();
        }
        mediaRecorder = new MediaRecorder(recordConfig.getActivity());
        //一定在setOutputFormat之前
        if (recordConfig.isRecordAudio()) {
            mediaRecorder.setAudioSource(recordConfig.getAudioSource());
        }
        mediaRecorder.setVideoSource(recordConfig.getVideoSource());
        mediaRecorder.setOutputFormat(recordConfig.getOutputFormat());
        mediaRecorder.setVideoEncoder(recordConfig.getVideoEncoder());
        //一定在setOutputFormat之后
        if (recordConfig.isRecordAudio()) {
            mediaRecorder.setAudioEncoder(recordConfig.getAudioEncoder());
        }
        mediaRecorder.setOutputFile(saveTempFile.getAbsolutePath());
        mediaRecorder.setVideoSize(recordConfig.getVideoWidth(), recordConfig.getVideoHeight());
        mediaRecorder.setVideoEncodingBitRate(recordConfig.getVideoEncodingBitRate());
        mediaRecorder.setVideoFrameRate(recordConfig.getVideoFrameRate());
        try {
            mediaRecorder.prepare();
            virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", recordConfig.getVideoWidth(), recordConfig.getVideoHeight(), getDisplayMetrics().densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 刷新保存的文件
     *
     * @param file the 文件
     */
    private void refreshVideo(File file) {
        if (file.length() > 5000) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            recordConfig.getActivity().sendBroadcast(intent);
        } else {
            file.delete();
        }
    }

    /**
     * 录制监听
     */
    public interface OnVideoRecordListener {
        /**
         * 开始录制前
         */
        void onBeforeRecord();

        /**
         * 开始录制
         */
        void onStartRecord();

        /**
         * 取消录制
         */
        void onCancelRecord();

        /**
         * 结束录制
         */
        void onEndRecord();
    }

}
