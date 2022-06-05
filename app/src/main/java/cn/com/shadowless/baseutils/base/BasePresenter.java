package cn.com.shadowless.baseutils.base;

import cn.com.shadowless.baseutils.utils.NetUtils;

/**
 * The type Base presenter.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BasePresenter<T> {

    /**
     * Gets singleton.
     *
     * @return the singleton
     */
    protected T getApi() {
        return NetUtils.getApi(apiName());
    }

    /**
     * Api name string.
     *
     * @return the string
     */
    public abstract String apiName();
}
