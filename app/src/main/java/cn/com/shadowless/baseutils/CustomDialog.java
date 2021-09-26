package cn.com.shadowless.baseutils;

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
 * The type My dialog.
 *
 * @author sHadowLess
 */
@Builder
public class CustomDialog {

    /**
     * The Dialog view.
     */
    private View dialogView;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Layout.
     */
    private int layout;
    /**
     * The Window width.
     */
    private int windowWidth;
    /**
     * The Window height.
     */
    private int windowHeight;
    /**
     * The Anim.
     */
    private int anim;
    /**
     * The Cancel.
     */
    private boolean cancel;
    /**
     * The Is set anim.
     */
    private boolean isSetAnim;
    /**
     * The Is system dialog.
     */
    private boolean isSystemDialog;
    /**
     * The Is clear layer.
     */
    private boolean isClearLayer;
    /**
     * The Is title.
     */
    private boolean isTitle;
    /**
     * The Is window size.
     */
    private boolean isWindowSize;
    /**
     * The Location.
     */
    private location location;

    /**
     * The enum Location.
     */
    public enum location {
        /**
         * Top location.
         */
        TOP,
        /**
         * Right location.
         */
        RIGHT,
        /**
         * Left location.
         */
        LEFT,
        /**
         * Bottom location.
         */
        BOTTOM,
        /**
         * Center location.
         */
        CENTER,
        /**
         * Upper left location.
         */
        UPPER_LEFT,
        /**
         * Upper right location.
         */
        UPPER_RIGHT,
        /**
         * Lower left location.
         */
        LOWER_LEFT,
        /**
         * Lower right location.
         */
        LOWER_RIGHT,
        /**
         * Center left location.
         */
        CENTER_LEFT,
        /**
         * Center right location.
         */
        CENTER_RIGHT
    }

    /**
     * The interface Init view.
     */
    interface InitViewListener {

        /**
         * Gets dialog view.
         *
         * @param view   the view
         * @param dialog the dialog
         */
        void getDialogView(View view, Dialog dialog);
    }

    /**
     * Init.
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
