package cn.com.shadowless.baseutils.utils;

import lombok.Builder;
import lombok.Data;

/**
 * The type Base model utils.
 *
 * @author sHadowLess
 */
public class BaseModelUtils {

    /**
     * Gets mod.
     *
     * @param <T>  the type parameter
     * @param code the code
     * @param msg  the msg
     * @param data the data
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
     * The type Base mod.
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
