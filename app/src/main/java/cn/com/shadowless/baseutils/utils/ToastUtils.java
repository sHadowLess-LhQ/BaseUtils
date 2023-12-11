package cn.com.shadowless.baseutils.utils;

import android.widget.Toast;

import cn.com.shadowless.baseutils.R;
import cn.com.shadowless.baseutils.toast.ToastParams;
import cn.com.shadowless.baseutils.toast.Toaster;
import cn.com.shadowless.baseutils.toast.style.CustomToastStyle;

/**
 * The type Toast utils.
 *
 * @author sHadowLess
 */
public class ToastUtils extends Toaster {

    /**
     * Instantiates a new Toast utils.
     */
    private ToastUtils() {
    }

    /**
     * 显示成功
     *
     * @param text the text
     */
    public static void showSuccess(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_success);
        show(params);
    }

    /**
     * 延迟显示成功
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowSuccess(CharSequence text, long delayMillis) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.delayMillis = delayMillis;
        params.style = new CustomToastStyle(R.layout.toast_success);
        show(params);
    }

    /**
     * 短显示成功
     *
     * @param text the text
     */
    public static void shortShowSuccess(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_SHORT;
        params.style = new CustomToastStyle(R.layout.toast_success);
        show(params);
    }

    /**
     * 长显示成功
     *
     * @param text the text
     */
    public static void longShowSuccess(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_LONG;
        params.style = new CustomToastStyle(R.layout.toast_success);
        show(params);
    }

    /**
     * 显示信息
     *
     * @param text the text
     */
    public static void showInfo(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_info);
        show(params);
    }

    /**
     * 延迟显示信息
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowInfo(CharSequence text, long delayMillis) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.delayMillis = delayMillis;
        params.style = new CustomToastStyle(R.layout.toast_info);
        show(params);
    }

    /**
     * 短显示信息
     *
     * @param text the text
     */
    public static void shortShowInfo(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_SHORT;
        params.style = new CustomToastStyle(R.layout.toast_info);
        show(params);
    }

    /**
     * 长显示信息
     *
     * @param text the text
     */
    public static void longShowInfo(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_LONG;
        params.style = new CustomToastStyle(R.layout.toast_info);
        show(params);
    }

    /**
     * 显示警告
     *
     * @param text the text
     */
    public static void showWaring(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_warn);
        show(params);
    }

    /**
     * 延迟显示警告
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowWaring(CharSequence text, long delayMillis) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.delayMillis = delayMillis;
        params.style = new CustomToastStyle(R.layout.toast_warn);
        show(params);
    }

    /**
     * 短显示警告
     *
     * @param text the text
     */
    public static void shortShowWaring(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_SHORT;
        params.style = new CustomToastStyle(R.layout.toast_warn);
        show(params);
    }

    /**
     * 长显示警告
     *
     * @param text the text
     */
    public static void longShowWaring(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_LONG;
        params.style = new CustomToastStyle(R.layout.toast_warn);
        show(params);
    }

    /**
     * 显示失败
     *
     * @param text the text
     */
    public static void showError(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.style = new CustomToastStyle(R.layout.toast_error);
        show(params);
    }

    /**
     * 延迟显示失败
     *
     * @param text        the text
     * @param delayMillis the delay millis
     */
    public static void delayShowError(CharSequence text, long delayMillis) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.delayMillis = delayMillis;
        params.style = new CustomToastStyle(R.layout.toast_error);
        show(params);
    }

    /**
     * 短显示失败
     *
     * @param text the text
     */
    public static void shortShowError(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_SHORT;
        params.style = new CustomToastStyle(R.layout.toast_error);
        show(params);
    }

    /**
     * 长显示失败
     *
     * @param text the text
     */
    public static void longShowError(CharSequence text) {
        ToastParams params = new ToastParams();
        params.text = text;
        params.duration = Toast.LENGTH_LONG;
        params.style = new CustomToastStyle(R.layout.toast_error);
        show(params);
    }
}
