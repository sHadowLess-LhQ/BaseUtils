package cn.com.shadowless.baseutils.toast.config;

import android.app.Application;

import cn.com.shadowless.baseutils.toast.ToastParams;

public interface IToastStrategy {

    /**
     * 注册策略
     */
    void registerStrategy(Application application);

    /**
     * 创建 Toast
     */
    IToast createToast(ToastParams params);

    /**
     * 显示 Toast
     */
    void showToast(ToastParams params);

    /**
     * 取消 Toast
     */
    void cancelToast();
}