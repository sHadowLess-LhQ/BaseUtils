package cn.com.shadowless.baseutils.custom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 自定义提示框工具类
 *
 * @author sHadowLess
 */
public class CustomDialog {

    /**
     * 自定义视图
     */
    private View dialogView;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 自定义布局文件
     */
    private int layout;
    /**
     * 提示框宽度
     */
    private int windowWidth;
    /**
     * 提示框高度
     */
    private int windowHeight;
    /**
     * 动画值
     */
    private int anim;
    /**
     * 是否允许外部取消显示
     */
    private boolean cancel;
    /**
     * 是否设置动画
     */
    private boolean isSetAnim;
    /**
     * 是否设置全局提示框
     */
    private boolean isSystemDialog;
    /**
     * 是否去除蒙层效果
     */
    private boolean isClearLayer;
    /**
     * 是否设置标题
     */
    private boolean isTitle;
    /**
     * 是否自定义大小
     */
    private boolean isWindowSize;
    /**
     * 显示位置
     */
    private location location;
    /**
     * The Dialog.
     */
    private Dialog dialog;

    /**
     * 位置枚举
     */
    public enum location {
        /**
         * 上
         */
        TOP,
        /**
         * 右
         */
        RIGHT,
        /**
         * 左
         */
        LEFT,
        /**
         * 下
         */
        BOTTOM,
        /**
         * 居中
         */
        CENTER,
        /**
         * 左上
         */
        UPPER_LEFT,
        /**
         * 右上
         */
        UPPER_RIGHT,
        /**
         * 左下
         */
        LOWER_LEFT,
        /**
         * 右下
         */
        LOWER_RIGHT,
        /**
         * 居左
         */
        CENTER_LEFT,
        /**
         * 居右
         */
        CENTER_RIGHT
    }

    /**
     * Instantiates a new Custom dialog.
     *
     * @param dialogView     the dialog view
     * @param context        the context
     * @param layout         the layout
     * @param windowWidth    the window width
     * @param windowHeight   the window height
     * @param anim           the anim
     * @param cancel         the cancel
     * @param isSetAnim      the is set anim
     * @param isSystemDialog the is system dialog
     * @param isClearLayer   the is clear layer
     * @param isTitle        the is title
     * @param isWindowSize   the is window size
     * @param location       the location
     */
    public CustomDialog(View dialogView, Context context, int layout, int windowWidth, int windowHeight, int anim, boolean cancel, boolean isSetAnim, boolean isSystemDialog, boolean isClearLayer, boolean isTitle, boolean isWindowSize, location location) {
        this.dialogView = dialogView;
        this.context = context;
        this.layout = layout;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.anim = anim;
        this.cancel = cancel;
        this.isSetAnim = isSetAnim;
        this.isSystemDialog = isSystemDialog;
        this.isClearLayer = isClearLayer;
        this.isTitle = isTitle;
        this.isWindowSize = isWindowSize;
        this.location = location;
    }

    /**
     * Builder custom dialog builder.
     *
     * @return the custom dialog builder
     */
    public static CustomDialogBuilder builder() {
        return new CustomDialogBuilder();
    }

    /**
     * The type Custom dialog builder.
     */
    public static class CustomDialogBuilder {

        /**
         * 自定义视图
         */
        private View dialogView;
        /**
         * 上下文
         */
        private Context context;
        /**
         * 自定义布局文件
         */
        private int layout;
        /**
         * 提示框宽度
         */
        private int windowWidth;
        /**
         * 提示框高度
         */
        private int windowHeight;
        /**
         * 动画值
         */
        private int anim;
        /**
         * 是否允许外部取消显示
         */
        private boolean cancel;
        /**
         * 是否设置动画
         */
        private boolean isSetAnim;
        /**
         * 是否设置全局提示框
         */
        private boolean isSystemDialog;
        /**
         * 是否去除蒙层效果
         */
        private boolean isClearLayer;
        /**
         * 是否设置标题
         */
        private boolean isTitle;
        /**
         * 是否自定义大小
         */
        private boolean isWindowSize;
        /**
         * 显示位置
         */
        private location location;

        /**
         * Dialog view custom dialog builder.
         *
         * @param dialogView the dialog view
         * @return the custom dialog builder
         */
        public CustomDialogBuilder dialogView(View dialogView) {
            this.dialogView = dialogView;
            return this;
        }

        /**
         * Context custom dialog builder.
         *
         * @param context the context
         * @return the custom dialog builder
         */
        public CustomDialogBuilder context(Context context) {
            this.context = context;
            return this;
        }

        /**
         * Layout custom dialog builder.
         *
         * @param layout the layout
         * @return the custom dialog builder
         */
        public CustomDialogBuilder layout(int layout) {
            this.layout = layout;
            return this;
        }

        /**
         * Window width custom dialog builder.
         *
         * @param windowWidth the window width
         * @return the custom dialog builder
         */
        public CustomDialogBuilder windowWidth(int windowWidth) {
            this.windowWidth = windowWidth;
            return this;
        }

        /**
         * Window height custom dialog builder.
         *
         * @param windowHeight the window height
         * @return the custom dialog builder
         */
        public CustomDialogBuilder windowHeight(int windowHeight) {
            this.windowHeight = windowHeight;
            return this;
        }

        /**
         * Anim custom dialog builder.
         *
         * @param anim the anim
         * @return the custom dialog builder
         */
        public CustomDialogBuilder anim(int anim) {
            this.anim = anim;
            return this;
        }

        /**
         * Cancel custom dialog builder.
         *
         * @param cancel the cancel
         * @return the custom dialog builder
         */
        public CustomDialogBuilder cancel(boolean cancel) {
            this.cancel = cancel;
            return this;
        }

        /**
         * Is set anim custom dialog builder.
         *
         * @param isSetAnim the is set anim
         * @return the custom dialog builder
         */
        public CustomDialogBuilder isSetAnim(boolean isSetAnim) {
            this.isSetAnim = isSetAnim;
            return this;
        }

        /**
         * Is system dialog custom dialog builder.
         *
         * @param isSystemDialog the is system dialog
         * @return the custom dialog builder
         */
        public CustomDialogBuilder isSystemDialog(boolean isSystemDialog) {
            this.isSystemDialog = isSystemDialog;
            return this;
        }

        /**
         * Is clear layer custom dialog builder.
         *
         * @param isClearLayer the is clear layer
         * @return the custom dialog builder
         */
        public CustomDialogBuilder isClearLayer(boolean isClearLayer) {
            this.isClearLayer = isClearLayer;
            return this;
        }

        /**
         * Is title custom dialog builder.
         *
         * @param isTitle the is title
         * @return the custom dialog builder
         */
        public CustomDialogBuilder isTitle(boolean isTitle) {
            this.isTitle = isTitle;
            return this;
        }

        /**
         * Is window size custom dialog builder.
         *
         * @param isWindowSize the is window size
         * @return the custom dialog builder
         */
        public CustomDialogBuilder isWindowSize(boolean isWindowSize) {
            this.isWindowSize = isWindowSize;
            return this;
        }

        /**
         * Location custom dialog builder.
         *
         * @param location the location
         * @return the custom dialog builder
         */
        public CustomDialogBuilder location(location location) {
            this.location = location;
            return this;
        }

        /**
         * Build custom dialog.
         *
         * @return the custom dialog
         */
        public CustomDialog build() {
            return new CustomDialog(this.dialogView, this.context, this.layout, this.windowWidth, this.windowHeight, this.anim, this.cancel, this.isSetAnim, this.isSystemDialog, this.isClearLayer, this.isTitle, this.isWindowSize, this.location);
        }

        @Override
        public String toString() {
            return "CustomDialogBuilder{" +
                    "dialogView=" + dialogView +
                    ", context=" + context +
                    ", layout=" + layout +
                    ", windowWidth=" + windowWidth +
                    ", windowHeight=" + windowHeight +
                    ", anim=" + anim +
                    ", cancel=" + cancel +
                    ", isSetAnim=" + isSetAnim +
                    ", isSystemDialog=" + isSystemDialog +
                    ", isClearLayer=" + isClearLayer +
                    ", isTitle=" + isTitle +
                    ", isWindowSize=" + isWindowSize +
                    ", location=" + location +
                    '}';
        }
    }


    /**
     * 显示初始化
     *
     * @throws Exception the exception
     */
    @SuppressLint("RtlHardcoded")
    public void show() {
        if (null == dialogView) {
            dialogView = LayoutInflater.from(context).inflate(layout, null);
        }
        dialog = new Dialog(context);
        if (isTitle) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        if (null != window) {
            if (isWindowSize) {
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = windowWidth;
                layoutParams.height = windowHeight;
                window.setAttributes(layoutParams);
            }
            if (isSetAnim && 0 != anim) {
                window.setWindowAnimations(anim);
            }
            if (isSystemDialog) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                } else {
                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                }
            }
            if (isClearLayer) {
                window.setDimAmount(0f);
            }
            switch (location) {
                case TOP:
                    window.setGravity(Gravity.TOP);
                    break;
                case LEFT:
                    window.setGravity(Gravity.LEFT);
                    break;
                case RIGHT:
                    window.setGravity(Gravity.RIGHT);
                    break;
                case BOTTOM:
                    window.setGravity(Gravity.BOTTOM);
                    break;
                case CENTER:
                    window.setGravity(Gravity.CENTER);
                    break;
                case UPPER_LEFT:
                    window.setGravity(Gravity.TOP | Gravity.LEFT);
                    break;
                case UPPER_RIGHT:
                    window.setGravity(Gravity.TOP | Gravity.RIGHT);
                    break;
                case LOWER_LEFT:
                    window.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                    break;
                case LOWER_RIGHT:
                    window.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                    break;
                case CENTER_LEFT:
                    window.setGravity(Gravity.CENTER | Gravity.LEFT);
                    break;
                case CENTER_RIGHT:
                    window.setGravity(Gravity.CENTER | Gravity.RIGHT);
                    break;
                default:
                    break;
            }
        }
        dialog.setCanceledOnTouchOutside(cancel);
        dialog.show();
    }

    /**
     * Show.
     *
     * @param style the style
     */
    public void show(int style) {
        if (null == dialogView) {
            dialogView = LayoutInflater.from(context).inflate(layout, null);
        }
        Dialog dialog = new Dialog(context, style);
        if (isTitle) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        if (null != window) {
            if (isWindowSize) {
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = windowWidth;
                layoutParams.height = windowHeight;
                window.setAttributes(layoutParams);
            }
            if (isSetAnim && 0 != anim) {
                window.setWindowAnimations(anim);
            }
            if (isSystemDialog) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                } else {
                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                }
            }
            if (isClearLayer) {
                window.setDimAmount(0f);
            }
            switch (location) {
                case TOP:
                    window.setGravity(Gravity.TOP);
                    break;
                case LEFT:
                    window.setGravity(Gravity.LEFT);
                    break;
                case RIGHT:
                    window.setGravity(Gravity.RIGHT);
                    break;
                case BOTTOM:
                    window.setGravity(Gravity.BOTTOM);
                    break;
                case CENTER:
                    window.setGravity(Gravity.CENTER);
                    break;
                case UPPER_LEFT:
                    window.setGravity(Gravity.TOP | Gravity.LEFT);
                    break;
                case UPPER_RIGHT:
                    window.setGravity(Gravity.TOP | Gravity.RIGHT);
                    break;
                case LOWER_LEFT:
                    window.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                    break;
                case LOWER_RIGHT:
                    window.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                    break;
                case CENTER_LEFT:
                    window.setGravity(Gravity.CENTER | Gravity.LEFT);
                    break;
                case CENTER_RIGHT:
                    window.setGravity(Gravity.CENTER | Gravity.RIGHT);
                    break;
                default:
                    break;
            }
        }
        dialog.setCanceledOnTouchOutside(cancel);
        dialog.show();
    }

    /**
     * Is show boolean.
     *
     * @return the boolean
     */
    public boolean isShow() {
        if (null != dialog) {
            return dialog.isShowing();
        }
        return false;
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        dialog.dismiss();
        dialog = null;
    }
}
