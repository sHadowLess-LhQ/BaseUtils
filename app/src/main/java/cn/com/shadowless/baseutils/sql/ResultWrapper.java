package cn.com.shadowless.baseutils.sql;


public class ResultWrapper<T> {
    /**
     * The Object.
     */
    private T object;

    public ResultWrapper(T b) {
        this.object = b;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
