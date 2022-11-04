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
     * The Media projection manager.
     */
    private MediaProjectionManager mediaProjectionManager;
    /**
     * The Media recorder.
     */
    private MediaRecorder mediaRecorder;
    /**
     * The Media projection.
     */
    private MediaProjection mediaProjection;
    /**
     * The Virtual display.
     */
    private VirtualDisplay virtualDisplay;
    /**
     * The Display metrics.
     */
    private DisplayMetrics displayMetrics;
    /**
     * The Save file.
     */
    private File saveTempFile;
    /**
     * The Is recording.
     */
    private boolean isRecording;
    /**
     * The Listener.
     */
    private final OnVideoRecordListener listener;
    /**
     * The Record config.
     */
    private final RecordConfig recordConfig;
    /**
     * The constant REQUEST_CODE.
     */
    private static final int REQUEST_CODE = 1024;

    /**
     * Instantiates a new Screen record utils.
     *
     * @param recordConfig the record config
     * @param listener     the listener
     */
    public ScreenRecordUtils(RecordConfig recordConfig, OnVideoRecordListener listener) {
        this.listener = listener;
        this.recordConfig = recordConfig;
    }

    /**
     * Is recording boolean.
     *
     * @return the boolean
     */
    public boolean isRecording() {
        return this.isRecording;
    }

    /**
     * Start record.
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
     * Resume.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resume() {
        if (mediaRecorder != null) {
            mediaRecorder.resume();
        }
    }

    /**
     * Pause.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pause() {
        if (mediaRecorder != null) {
            mediaRecorder.pause();
        }
    }

    /**
     * Cancel record.
     */
    public void cancelRecord() {
        stopRecord();
        listener.onCancelRecord();
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
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
     * Stop record.
     */
    private void stopRecord() {
        stop();
        File temp = new File(recordConfig.getSavePath(), recordConfig.getFileName() + ".mp4");
        saveTempFile.renameTo(temp);
        refreshVideo(temp);
        saveTempFile = null;
    }

    /**
     * Gets media projection manager.
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
     * Gets display metrics.
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
     * Show toast.
     */
    private void showToast() {
        Toast.makeText(recordConfig.getActivity(), "当前设备暂不支持录屏", Toast.LENGTH_SHORT).show();
    }

    /**
     * Stop.
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
     * Init recorder boolean.
     *
     * @return the boolean
     */
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
     * Refresh video.
     *
     * @param file the file
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
     * The interface On video record listener.
     */
    public interface OnVideoRecordListener {
        /**
         * On before record.
         */
        void onBeforeRecord();

        /**
         * On start record.
         */
        void onStartRecord();

        /**
         * On cancel record.
         */
        void onCancelRecord();

        /**
         * On end record.
         */
        void onEndRecord();
    }

}
