package cn.com.shadowless.baseutils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import cn.com.shadowless.baseutils.R;


/**
 * The type Toast utils.
 *
 * @author sHadowLess
 */
public enum ToastUtils {

    /**
     * Instance toast utils.
     */
    INSTANCE;

    /**
     * The constant currentToast.
     */
    private Toast currentToast;

    /**
     * The constant mContext.
     */
    private Context mContext;

    /**
     * The constant isMoreShow.
     */
    private boolean isMoreShow;

    /**
     * Init toast.
     *
     * @param context the context
     */
    public void initToast(Context context) {
        if (null == mContext) {
            this.mContext = context;
        }
        if (null == currentToast) {
            this.currentToast = new Toast(mContext);
        }
    }

    /**
     * Init toast.
     *
     * @param context    the context
     * @param isMoreShow the is more show
     */
    public void initToast(Context context, boolean isMoreShow) {
        this.isMoreShow = isMoreShow;
        if (null == mContext) {
            this.mContext = context;
        }
        if (null == currentToast) {
            this.currentToast = new Toast(mContext);
        }
    }

    /**
     * 成功
     *
     * @param message the message
     */
    public void onSuccessShowToast(String message) {
        showToast(message, R.mipmap.toast_success,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_success_shape);
    }

    /**
     * 成功显示xml
     *
     * @param messageID the message id
     */
    public void onSuccessShowToast(int messageID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, R.mipmap.toast_success,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_success_shape);
    }

    /**
     * 成功显示自定义icon
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onSuccessShowToast(String message, int iconID) {
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_success_shape);
    }

    /**
     * 成功显示xml自定义icon
     *
     * @param messageID the message id
     * @param iconID    the icon id
     */
    public void onSuccessShowToast(int messageID, int iconID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_success_shape);
    }

    /**
     * 错误显示
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onErrorShowToast(String message, int iconID) {
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_error_shape);
    }

    /**
     * On error show toast.
     *
     * @param messageID the message id
     * @param iconID    the icon id
     */
    public void onErrorShowToast(int messageID, int iconID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_error_shape);
    }

    /**
     * 错误
     *
     * @param message the message
     */
    public void onErrorShowToast(String message) {
        showToast(message, R.mipmap.toast_error,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_error_shape);
    }

    /**
     * On error show toast.
     *
     * @param messageID the message id
     */
    public void onErrorShowToast(int messageID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, R.mipmap.toast_error,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_error_shape);
    }

    /**
     * On default show toast.
     *
     * @param messageID the message id
     */
    public void onDefaultShowToast(int messageID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, R.mipmap.toast_default,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_default_shape);
    }

    /**
     * On default show toast.
     *
     * @param message the message
     */
    public void onDefaultShowToast(String message) {
        showToast(message, R.mipmap.toast_default,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_default_shape);
    }

    /**
     * On default show toast.
     *
     * @param messageID the message id
     * @param iconID    the icon id
     */
    public void onDefaultShowToast(int messageID, int iconID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_default_shape);
    }

    /**
     * On default show toast.
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onDefaultShowToast(String message, int iconID) {
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_default_shape);
    }

    /**
     * On default without icon show toast.
     *
     * @param messageID the message id
     */
    public void onDefaultWithoutIconShowToast(int messageID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, 0,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, false, R.drawable.toast_default_shape);
    }

    /**
     * On default without icon show toast.
     *
     * @param message the message
     */
    public void onDefaultWithoutIconShowToast(String message) {
        showToast(message, 0,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, false, R.drawable.toast_default_shape);
    }

    /**
     * On warn show toast.
     *
     * @param messageID the message id
     */
    public void onWarnShowToast(int messageID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, R.mipmap.toast_warn,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_warn_shape);
    }

    /**
     * On warn show toast.
     *
     * @param message the message
     */
    public void onWarnShowToast(String message) {
        showToast(message, R.mipmap.toast_warn,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_warn_shape);
    }

    /**
     * On warn show toast.
     *
     * @param messageID the message id
     * @param iconID    the icon id
     */
    public void onWarnShowToast(int messageID, int iconID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_warn_shape);
    }

    /**
     * On warn show toast.
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onWarnShowToast(String message, int iconID) {
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_warn_shape);
    }

    /**
     * On info show toast.
     *
     * @param messageID the message id
     */
    public void onInfoShowToast(int messageID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, R.mipmap.toast_info,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_info_shape);
    }

    /**
     * On info show toast.
     *
     * @param message the message
     */
    public void onInfoShowToast(String message) {
        showToast(message, R.mipmap.toast_info,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_info_shape);
    }

    /**
     * On info show toast.
     *
     * @param messageID the message id
     * @param iconID    the icon id
     */
    public void onInfoShowToast(int messageID, int iconID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_info_shape);
    }

    /**
     * On info show toast.
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onInfoShowToast(String message, int iconID) {
        showToast(message, iconID,
                ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_info_shape);
    }


    /**
     * On show toast.
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onShowToast(String message, int iconID) {
        onShowToast(message, iconID, ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_default_shape);
    }

    /**
     * On show toast.
     *
     * @param message the message
     * @param iconID  the icon id
     */
    public void onShowToast(int message, int iconID) {
        onShowToast(message, iconID, ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, R.drawable.toast_default_shape);
    }

    /**
     * On show toast.
     *
     * @param message         the message
     * @param iconID          the icon id
     * @param toastDrawableID the toast drawable id
     */
    public void onShowToast(String message, int iconID, int toastDrawableID) {
        onShowToast(message, iconID, ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, toastDrawableID);
    }

    /**
     * On show toast.
     *
     * @param message         the message
     * @param iconID          the icon id
     * @param toastDrawableID the toast drawable id
     */
    public void onShowToast(int message, int iconID, int toastDrawableID) {
        onShowToast(message, iconID, ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, true, toastDrawableID);
    }

    /**
     * On show toast.
     *
     * @param message the message
     */
    public void onShowToast(String message) {
        onShowToast(message, 0, ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, false, R.drawable.toast_default_shape);
    }

    /**
     * On show toast.
     *
     * @param message the message
     */
    public void onShowToast(int message) {
        onShowToast(message, 0, ContextCompat.getColor(mContext, R.color.toastDefaultTextColor),
                Toast.LENGTH_SHORT, false, R.drawable.toast_default_shape);
    }


    /**
     * On show toast.
     *
     * @param message         the message
     * @param iconID          the icon id
     * @param textColor       the text color
     * @param duration        the duration
     * @param withIcon        the with icon
     * @param toastDrawableID the toast drawable id
     */
    public void onShowToast(String message, int iconID,
                            @ColorInt int textColor, int duration, boolean withIcon, int toastDrawableID) {
        showToast(message, iconID, textColor, duration, withIcon, toastDrawableID);
    }

    /**
     * On show toast.
     *
     * @param messageID       the message id
     * @param iconID          the icon id
     * @param textColor       the text color
     * @param duration        the duration
     * @param withIcon        the with icon
     * @param toastDrawableID the toast drawable id
     */
    public void onShowToast(int messageID, int iconID,
                            @ColorInt int textColor, int duration, boolean withIcon, int toastDrawableID) {
        String message = mContext.getResources().getString(messageID);
        showToast(message, iconID, textColor, duration, withIcon, toastDrawableID);
    }


    /**
     * Show toast.
     *
     * @param message         the message
     * @param iconId          the icon id
     * @param textColor       the text color
     * @param duration        the duration
     * @param withIcon        the with icon
     * @param toastDrawableID the toast drawable id
     */
    private void showToast(String message, int iconId, @ColorInt int textColor, int duration, boolean withIcon, int toastDrawableID) {
        if (isMoreShow) {
            this.currentToast = new Toast(mContext);
        } else {
            if (currentToast == null) {
                this.currentToast = new Toast(mContext);
            }
        }
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_layout, null);
        RelativeLayout toastContainer = view.findViewById(R.id.toast_container);
        ImageView toastIcon = view.findViewById(R.id.toast_icon);
        TextView toastMessage = view.findViewById(R.id.toast_message);

        //左边的icon
        if (withIcon && iconId != 0) {
            toastIcon.setVisibility(View.VISIBLE);
            toastIcon.setImageDrawable(ContextCompat.getDrawable(mContext, iconId));
            toastMessage.setPadding(dp2px(), 0, 0, 0);
        } else {
            toastIcon.setVisibility(View.GONE);
            toastMessage.setPadding(0, 0, 0, 0);
        }
        //右边的message
        toastMessage.setText(message);
        if (0 != textColor) {
            toastMessage.setTextColor(textColor);
        } else {
            toastMessage.setTextColor(ContextCompat.getColor(mContext, R.color.toastDefaultTextColor));
        }
        //背景样式
        if (0 != toastDrawableID) {
            toastContainer.setBackground(ContextCompat.getDrawable(mContext, toastDrawableID));
        }
        toastMessage.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        currentToast.setView(view);
        currentToast.setDuration(duration);
        currentToast.show();

    }

    /**
     * Dp 2 px int.
     *
     * @return the int
     */
    private int dp2px() {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) ((float) 10 * scale + 0.5F);
    }

}
