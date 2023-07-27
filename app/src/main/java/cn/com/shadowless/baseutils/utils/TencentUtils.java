package cn.com.shadowless.baseutils.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;

/**
 * The enum Tencent tool.
 *
 * @author sHadowLess
 */
public enum TencentUtils {

    /**
     * Instance tencent tool.
     */
    INSTANCE;

    /**
     * The Tencent.
     */
    private Tencent tencent;

    /**
     * The Context.
     */
    private Context context;

    /**
     * Gets instance.
     *
     * @param context the context
     * @param appId   the app id
     */
    public void getInstance(Context context, String appId) {
        this.context = context;
        tencent = Tencent.createInstance(appId, context);
    }

    /**
     * Is install qq boolean.
     *
     * @return the boolean
     */
    public boolean canUseQq() {
        return tencent.isQQInstalled(context) && Tencent.isSupportShareToQQ(context);
    }

    /**
     * Open qq share img string.
     *
     * @param activity    the activity
     * @param title       the title
     * @param description the description
     * @param imgPath     the img path
     * @param listener    the listener
     */
    public void openQqShareImgString(Activity activity, String title, String description, String imgPath, IUiListener listener) {
        File file = new File(imgPath);
        if (file.length() >= 5 * 1024 * 1024) {
            if (listener != null) {
                listener.onError(new UiError(Constants.ERROR_IMAGE_TOO_LARGE, Constants.MSG_SHARE_IMAGE_TOO_LARGE_ERROR, null));
            }
            return;
        }
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgPath);
        tencent.shareToQQ(activity, params, listener);
    }

    /**
     * Open qq share img.
     *
     * @param activity the activity
     * @param imgPath  the img path
     * @param listener the listener
     */
    public void openQqShareImg(Activity activity, String imgPath, IUiListener listener) {
        File file = new File(imgPath);
        if (file.length() >= 5 * 1024 * 1024) {
            if (listener != null) {
                listener.onError(new UiError(Constants.ERROR_IMAGE_TOO_LARGE, Constants.MSG_SHARE_IMAGE_TOO_LARGE_ERROR, null));
            }
            return;
        }
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgPath);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        tencent.shareToQQ(activity, params, listener);
    }

    /**
     * Open qq share mini program.
     *
     * @param activity    the activity
     * @param title       the title
     * @param description the description
     * @param url         the url
     * @param imgPath     the img path
     * @param appId       the app id
     * @param path        the path
     * @param listener    the listener
     */
    public void openQqShareMiniProgram(Activity activity, String title, String description, String url, String imgPath, String appId, String path, IUiListener listener) {
        File file = new File(imgPath);
        if (file.length() >= 5 * 1024 * 1024) {
            if (listener != null) {
                listener.onError(new UiError(Constants.ERROR_IMAGE_TOO_LARGE, Constants.MSG_SHARE_IMAGE_TOO_LARGE_ERROR, null));
            }
            return;
        }
        Bundle miniProgramBundle = new Bundle();
        miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        miniProgramBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        miniProgramBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgPath);
        miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_APPID, appId);
        miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_PATH, path);
        tencent.shareToQQ(activity, miniProgramBundle, listener);
    }
}
