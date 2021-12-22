package cn.com.shadowless.baseutils.sql;

import lombok.Builder;
import lombok.Data;

/**
 * The type Result wrapper.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
@Builder
@Data
public class ResultWrapper<T> {
    /**
     * The Object.
     */
    private T object;
}
