package cn.com.shadowless.baseutils.record;

import android.app.Activity;
import android.media.MediaRecorder;

/**
 * The type Record bean.
 *
 * @author sHadowLess
 */
public class RecordConfig {

    /**
     * The Activity.
     */
    private Activity activity;
    /**
     * The Save path.
     */
    private String savePath;
    /**
     * The File name.
     */
    private String fileName;
    /**
     * The Is record audio.
     */
    private boolean isRecordAudio;
    /**
     * The Audio source.
     */
    private int audioSource;
    /**
     * The Audio encoder.
     */
    private int audioEncoder;
    /**
     * The Video source.
     */
    private int videoSource;
    /**
     * The Output format.
     */
    private int outputFormat;
    /**
     * The Video encoder.
     */
    private int videoEncoder;
    /**
     * The Video width.
     */
    private int videoWidth;
    /**
     * The Video height.
     */
    private int videoHeight;
    /**
     * The Video encoding bit rate.
     */
    private int videoEncodingBitRate;
    /**
     * The Video frame rate.
     */
    private int videoFrameRate;

    /**
     * Instantiates a new Record bean.
     *
     * @param activity             the activity
     * @param savePath             the save path
     * @param fileName             the file name
     * @param isRecordAudio        the is record audio
     * @param audioSource          the audio source
     * @param audioEncoder         the audio encoder
     * @param videoSource          the video source
     * @param outputFormat         the output format
     * @param videoEncoder         the video encoder
     * @param videoWidth           the video width
     * @param videoHeight          the video height
     * @param videoEncodingBitRate the video encoding bit rate
     * @param videoFrameRate       the video frame rate
     */
    public RecordConfig(Activity activity, String savePath, String fileName, boolean isRecordAudio, int audioSource, int audioEncoder, int videoSource, int outputFormat, int videoEncoder, int videoWidth, int videoHeight, int videoEncodingBitRate, int videoFrameRate) {
        this.activity = activity;
        this.savePath = savePath;
        this.fileName = fileName;
        this.isRecordAudio = isRecordAudio;
        this.audioSource = audioSource;
        this.audioEncoder = audioEncoder;
        this.videoSource = videoSource;
        this.outputFormat = outputFormat;
        this.videoEncoder = videoEncoder;
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
        this.videoEncodingBitRate = videoEncodingBitRate;
        this.videoFrameRate = videoFrameRate;
    }

    /**
     * Gets activity.
     *
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Gets save path.
     *
     * @return the save path
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * Sets save path.
     *
     * @param savePath the save path
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * Gets file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Is record audio boolean.
     *
     * @return the boolean
     */
    public boolean isRecordAudio() {
        return isRecordAudio;
    }

    /**
     * Sets record audio.
     *
     * @param recordAudio the record audio
     */
    public void setRecordAudio(boolean recordAudio) {
        isRecordAudio = recordAudio;
    }

    /**
     * Gets audio source.
     *
     * @return the audio source
     */
    public int getAudioSource() {
        if (audioSource == 0) {
            return MediaRecorder.AudioSource.VOICE_COMMUNICATION;
        }
        return audioSource;
    }

    /**
     * Sets audio source.
     *
     * @param audioSource the audio source
     */
    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    /**
     * Gets audio encoder.
     *
     * @return the audio encoder
     */
    public int getAudioEncoder() {
        if (audioEncoder == 0) {
            return MediaRecorder.AudioEncoder.AMR_NB;
        }
        return audioEncoder;
    }

    /**
     * Sets audio encoder.
     *
     * @param audioEncoder the audio encoder
     */
    public void setAudioEncoder(int audioEncoder) {
        this.audioEncoder = audioEncoder;
    }

    /**
     * Gets video source.
     *
     * @return the video source
     */
    public int getVideoSource() {
        if (videoSource == 0) {
            return MediaRecorder.VideoSource.SURFACE;
        }
        return videoSource;
    }

    /**
     * Sets video source.
     *
     * @param videoSource the video source
     */
    public void setVideoSource(int videoSource) {
        this.videoSource = videoSource;
    }

    /**
     * Gets output format.
     *
     * @return the output format
     */
    public int getOutputFormat() {
        if (outputFormat == 0) {
            return MediaRecorder.OutputFormat.MPEG_4;
        }
        return outputFormat;
    }

    /**
     * Sets output format.
     *
     * @param outputFormat the output format
     */
    public void setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * Gets video encoder.
     *
     * @return the video encoder
     */
    public int getVideoEncoder() {
        if (videoEncoder == 0) {
            return MediaRecorder.VideoEncoder.H264;
        }
        return videoEncoder;
    }

    /**
     * Sets video encoder.
     *
     * @param videoEncoder the video encoder
     */
    public void setVideoEncoder(int videoEncoder) {
        this.videoEncoder = videoEncoder;
    }

    /**
     * Gets video width.
     *
     * @return the video width
     */
    public int getVideoWidth() {
        if (videoWidth == 0) {
            return 720;
        }
        return videoWidth;
    }

    /**
     * Sets video width.
     *
     * @param videoWidth the video width
     */
    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    /**
     * Gets video height.
     *
     * @return the video height
     */
    public int getVideoHeight() {
        if (videoHeight == 0) {
            return 1280;
        }
        return videoHeight;
    }

    /**
     * Sets video height.
     *
     * @param videoHeight the video height
     */
    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    /**
     * Gets video encoding bit rate.
     *
     * @return the video encoding bit rate
     */
    public int getVideoEncodingBitRate() {
        if (videoEncodingBitRate == 0) {
            return 8388608;
        }
        return videoEncodingBitRate;
    }

    /**
     * Sets video encoding bit rate.
     *
     * @param videoEncodingBitRate the video encoding bit rate
     */
    public void setVideoEncodingBitRate(int videoEncodingBitRate) {
        this.videoEncodingBitRate = videoEncodingBitRate;
    }

    /**
     * Gets video frame rate.
     *
     * @return the video frame rate
     */
    public int getVideoFrameRate() {
        if (videoFrameRate == 0) {
            return 30;
        }
        return videoFrameRate;
    }

    /**
     * Sets video frame rate.
     *
     * @param videoFrameRate the video frame rate
     */
    public void setVideoFrameRate(int videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    /**
     * Builder record bean builder.
     *
     * @return the record bean builder
     */
    public static RecordBeanBuilder builder() {
        return new RecordBeanBuilder();
    }

    /**
     * The type Record bean builder.
     */
    public static class RecordBeanBuilder {
        /**
         * The Activity.
         */
        private Activity activity;
        /**
         * The Save path.
         */
        private String savePath;
        /**
         * The File name.
         */
        private String fileName;
        /**
         * The Is record audio.
         */
        private boolean isRecordAudio;
        /**
         * The Audio source.
         */
        private int audioSource;
        /**
         * The Audio encoder.
         */
        private int audioEncoder;
        /**
         * The Video source.
         */
        private int videoSource;
        /**
         * The Output format.
         */
        private int outputFormat;
        /**
         * The Video encoder.
         */
        private int videoEncoder;
        /**
         * The Video width.
         */
        private int videoWidth;
        /**
         * The Video height.
         */
        private int videoHeight;
        /**
         * The Video encoding bit rate.
         */
        private int videoEncodingBitRate;
        /**
         * The Video frame rate.
         */
        private int videoFrameRate;

        /**
         * Activity record bean builder.
         *
         * @param activity the activity
         * @return the record bean builder
         */
        public RecordBeanBuilder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

        /**
         * Save path record bean builder.
         *
         * @param savePath the save path
         * @return the record bean builder
         */
        public RecordBeanBuilder savePath(String savePath) {
            this.savePath = savePath;
            return this;
        }

        /**
         * File name record bean builder.
         *
         * @param fileName the file name
         * @return the record bean builder
         */
        public RecordBeanBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Is record audio record bean builder.
         *
         * @param isRecordAudio the is record audio
         * @return the record bean builder
         */
        public RecordBeanBuilder isRecordAudio(boolean isRecordAudio) {
            this.isRecordAudio = isRecordAudio;
            return this;
        }

        /**
         * Audio source record bean builder.
         *
         * @param audioSource the audio source
         * @return the record bean builder
         */
        public RecordBeanBuilder audioSource(int audioSource) {
            this.audioSource = audioSource;
            return this;
        }

        /**
         * Audio encoder record bean builder.
         *
         * @param audioEncoder the audio encoder
         * @return the record bean builder
         */
        public RecordBeanBuilder audioEncoder(int audioEncoder) {
            this.audioEncoder = audioEncoder;
            return this;
        }

        /**
         * Video source record bean builder.
         *
         * @param videoSource the video source
         * @return the record bean builder
         */
        public RecordBeanBuilder videoSource(int videoSource) {
            this.videoSource = videoSource;
            return this;
        }

        /**
         * Output format record bean builder.
         *
         * @param outputFormat the output format
         * @return the record bean builder
         */
        public RecordBeanBuilder outputFormat(int outputFormat) {
            this.outputFormat = outputFormat;
            return this;
        }

        /**
         * Video encoder record bean builder.
         *
         * @param videoEncoder the video encoder
         * @return the record bean builder
         */
        public RecordBeanBuilder videoEncoder(int videoEncoder) {
            this.videoEncoder = videoEncoder;
            return this;
        }

        /**
         * Video width record bean builder.
         *
         * @param videoWidth the video width
         * @return the record bean builder
         */
        public RecordBeanBuilder videoWidth(int videoWidth) {
            this.videoWidth = videoWidth;
            return this;
        }

        /**
         * Video height record bean builder.
         *
         * @param videoHeight the video height
         * @return the record bean builder
         */
        public RecordBeanBuilder videoHeight(int videoHeight) {
            this.videoHeight = videoHeight;
            return this;
        }

        /**
         * Video encoding bit rate record bean builder.
         *
         * @param videoEncodingBitRate the video encoding bit rate
         * @return the record bean builder
         */
        public RecordBeanBuilder videoEncodingBitRate(int videoEncodingBitRate) {
            this.videoEncodingBitRate = videoEncodingBitRate;
            return this;
        }

        /**
         * Video frame rate record bean builder.
         *
         * @param videoFrameRate the video frame rate
         * @return the record bean builder
         */
        public RecordBeanBuilder videoFrameRate(int videoFrameRate) {
            this.videoFrameRate = videoFrameRate;
            return this;
        }

        /**
         * Build record bean.
         *
         * @return the record bean
         */
        public RecordConfig build() {
            return new RecordConfig(this.activity, this.savePath, this.fileName, this.isRecordAudio, this.audioSource, this.audioEncoder, this.videoSource, this.outputFormat, this.videoEncoder, this.videoWidth, this.videoHeight, this.videoEncodingBitRate, this.videoFrameRate);
        }
    }
}
