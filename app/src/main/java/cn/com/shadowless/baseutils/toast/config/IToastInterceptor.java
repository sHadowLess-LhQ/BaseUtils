package cn.com.shadowless.baseutils.toast.config;

import cn.com.shadowless.baseutils.toast.ToastParams;

public interface IToastInterceptor {

    /**
     * 根据显示的文本决定是否拦截该 Toast
     */
    boolean intercept(ToastParams params);
}