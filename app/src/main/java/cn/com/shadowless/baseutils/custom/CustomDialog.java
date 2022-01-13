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

import lombok.Builder;
import lombok.NonNull;


/**
 * 自定义提示框工具类
 *
 * @author sHadowLess
 */
@Builder
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
     * 自定义视图回调
     */
    public interface InitViewListener {

        /**
         * Gets dialog view.
         *
         * @param view   the 自定义视图
         * @param dialog the 提示框
         */
        void getDialogView(View view, Dialog dialog);
    }

    /**
     * 显示初始化
     *
     * @param initViewListener the init view listener
     * @throws Exception the exception
     */
    @SuppressLint("RtlHardcoded")
    public void show(@NonNull InitViewListener initViewListener) {
        if (null == dialogView) {
            dialogView = LayoutInflater.from(context).inflate(layout, null);
        }
        Dialog dialog = new Dialog(context);
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
        if (null != initViewListener) {
            initViewListener.getDialogView(dialogView, dialog);
        }
        dialog.setCanceledOnTouchOutside(cancel);
        dialog.show();
    }
}
