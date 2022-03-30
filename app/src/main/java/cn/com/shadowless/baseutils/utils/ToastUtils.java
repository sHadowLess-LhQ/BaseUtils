package cn.com.shadowless.baseutils.utils;

import static androidx.core.view.ViewCompat.setBackground;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import cn.com.shadowless.baseutils.R;

/**
 * 自定义Toast封装类
 *
 * @author sHadowLess
 */
public class ToastUtils {

    /**
     * 构造
     */
    public ToastUtils() {
    }

    /**
     * 字体颜色
     */
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    /**
     * 错误颜色
     */
    @ColorInt
    private static final int ERROR_COLOR = Color.parseColor("#FD4C5B");

    /**
     * 信息颜色
     */
    @ColorInt
    private static final int INFO_COLOR = Color.parseColor("#3F51B5");

    /**
     * 成功颜色
     */
    @ColorInt
    private static final int SUCCESS_COLOR = Color.parseColor("#388E3C");

    /**
     * 警告颜色
     */
    @ColorInt
    private static final int WARNING_COLOR = Color.parseColor("#FFA900");

    /**
     * 类型
     */
    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    /**
     * 土司对象
     */
    private static Toast currentToast = null;

    /**
     * The constant toastUtils.
     */
    @SuppressLint("StaticFieldLeak")
    private static ToastUtils toastUtils;

    /**
     * The constant mContext.
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ToastUtils getInstance() {
        if (toastUtils == null) {
            toastUtils = new ToastUtils();
        }

        return toastUtils;
    }

    /**
     * Init toast.
     *
     * @param mContext the m context
     */
    public void initToast(Context mContext) {
        if (null == context) {
            context = mContext;
        }
        if (null == currentToast) {
            currentToast = new Toast(context);
        }
    }

    /**
     * 正常
     *
     * @param message the 信息
     */
    public static void normal(@NonNull String message) {
        normal(message, Toast.LENGTH_SHORT, null, false).show();
    }

    /**
     * 正常带图标
     *
     * @param message the 信息
     * @param icon    the 图标
     */
    public static void normal(@NonNull String message, Drawable icon) {
        normal(message, Toast.LENGTH_SHORT, icon, true).show();
    }

    /**
     * 正常带显示时长
     *
     * @param message  the 信息
     * @param duration the 时长
     */
    public static void normal(@NonNull String message, int duration) {
        normal(message, duration, null, false).show();
    }

    /**
     * 正常带图标带显示时长
     *
     * @param message  the 信息
     * @param duration the 时长
     * @param icon     the 图标
     */
    public static void normal(@NonNull String message, int duration, Drawable icon) {
        normal(message, duration, icon, true).show();
    }

    /**
     * 正常土司
     *
     * @param message  the 信息
     * @param duration the 时长
     * @param icon     the 图标
     * @param withIcon the 是否带图标
     * @return the toast
     */
    public static Toast normal(@NonNull String message, int duration, Drawable icon, boolean withIcon) {
        return custom(message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    /**
     * 警告
     *
     * @param message the 信息
     */
    public static void warning(@NonNull String message) {
        warning(message, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 警告带时长
     *
     * @param message  the 信息
     * @param duration the 时长
     */
    public static void warning(@NonNull String message, int duration) {
        warning(message, duration, true).show();
    }

    /**
     * 警告土司
     *
     * @param message  the 信息
     * @param duration the 时长
     * @param withIcon the 是否带图标
     * @return the toast
     */
    public static Toast warning(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, getDrawable(context, R.mipmap.ic_error_outline_white_48dp), DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    /**
     * 信息
     *
     * @param message the 信息
     */
    public static void info(@NonNull String message) {
        info(message, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 信息带时长
     *
     * @param message  the 信息
     * @param duration the 时长
     */
    public static void info(@NonNull String message, int duration) {
        info(message, duration, true).show();
    }

    /**
     * 信息土司
     *
     * @param message  the 信息
     * @param duration the 时长
     * @param withIcon the 是否带图标
     * @return the toast
     */
    public static Toast info(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, getDrawable(context, R.mipmap.ic_info_outline_white_48dp), DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    /**
     * 成功
     *
     * @param message the 信息
     */
    public static void success(@NonNull String message) {
        success(message, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 成功带时长
     *
     * @param message  the 信息
     * @param duration the 时长
     */
    public static void success(@NonNull String message, int duration) {
        success(message, duration, true).show();
    }

    /**
     * 成功带时长
     *
     * @param message  the 信息
     * @param duration the 时长
     * @param withIcon the 是否带图标
     * @return the toast
     */
    public static Toast success(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, getDrawable(context, R.mipmap.ic_check_white_48dp), DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    /**
     * 错误
     *
     * @param message the 信息
     */
    public static void error(@NonNull String message) {
        error(message, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 错误带时长
     *
     * @param message  the 信息
     * @param duration the 时长
     */
    public static void error(@NonNull String message, int duration) {
        error(message, duration, true).show();
    }

    /**
     * 错误土司
     *
     * @param message  the 信息
     * @param duration the 时长
     * @param withIcon the 是否带图标
     * @return the toast
     */
    public static Toast error(@NonNull String message, int duration, boolean withIcon) {
        return custom(message, getDrawable(context, R.mipmap.ic_clear_white_48dp), DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    /**
     * 自定义土司
     *
     * @param message   the 信息
     * @param icon      the 图标
     * @param textColor the 文字颜色
     * @param duration  the 时长
     * @param withIcon  the 是否带图标
     * @return the toast
     */
    public static Toast custom(@NonNull String message, Drawable icon, @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(message, icon, textColor, -1, duration, withIcon, false);
    }

    /**
     * 自定义土司
     *
     * @param message    the 信息
     * @param iconRes    the 图标资源
     * @param textColor  the 文字颜色
     * @param tintColor  the 简单色
     * @param duration   the 时长
     * @param withIcon   the 是否带图标
     * @param shouldTint the 是否简单色
     * @return the toast
     */
    public static Toast custom(@NonNull String message, @DrawableRes int iconRes, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(message, getDrawable(context, iconRes), textColor, tintColor, duration, withIcon, shouldTint);
    }

    /**
     * 自定义土司
     *
     * @param message    the 信息
     * @param icon       the 图标
     * @param textColor  the 文字颜色
     * @param tintColor  the 简单色
     * @param duration   the 时长
     * @param withIcon   the 是否带图标
     * @param shouldTint the 是否简单色
     * @return the toast
     */
    public static Toast custom(@NonNull String message, Drawable icon, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;
        if (shouldTint) {
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = getDrawable(context, R.mipmap.toast_frame);
        }
        setBackground(toastLayout, drawableFrame);
        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            }
            setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }

    /**
     * 获取图片资源
     *
     * @param context the 上下文
     * @param id      the 资源id
     * @return the drawable
     */
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        return context.getDrawable(id);
    }

    /**
     * 获取.9位图
     *
     * @param context   the 上下文
     * @param tintColor the 9位图
     * @return the drawable
     */
    public static Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.mipmap.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }


    /**
     * 参数：消息 显示时长
     *
     * @param str    the 字符
     * @param isLong the 是否长显示
     */
    public static void showToast(String str, boolean isLong) {
        if (isLong) {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 参数：消息
     *
     * @param str the str
     */
    public static void showToastShort(String str) {
        showToast(str, false);
    }

    /**
     * 参数：消息id
     *
     * @param resId the res id
     */
    public static void showToastShort(int resId) {
        showToast(context.getString(resId), false);
    }
}
