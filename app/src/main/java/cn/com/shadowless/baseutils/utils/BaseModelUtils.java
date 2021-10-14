package cn.com.shadowless.baseutils.utils;

import lombok.Builder;
import lombok.Data;

/**
 * 基类模型类
 *
 * @author sHadowLess
 */
public class BaseModelUtils {

    /**
     * 获取模型实例
     *
     * @param <T>  the type parameter
     * @param code the 状态码
     * @param msg  the 状态信息
     * @param data the 数据
     * @return the mod
     */
    public static <T> BaseMod<Object> getMod(int code, String msg, T data) {
        return BaseMod
                .builder()
                .statusCode(code)
                .statusMsg(msg)
                .data(data)
                .build();
    }

    /**
     * 基类模型
     *
     * @param <T> the type parameter
     */
    @Data
    @Builder
    private static class BaseMod<T> {
        /**
         * The Status code.
         */
        private int statusCode;
        /**
         * The Status msg.
         */
        private String statusMsg;
        /**
         * The Data.
         */
        private T data;
    }
}
