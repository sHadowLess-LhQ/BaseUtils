package cn.com.shadowless.baseutils.sql;

/**
 * The interface Sql server.
 *
 * @author sHadowLess
 */
public interface ISqlServer {
    /**
     * 保存数据，会覆盖前面的数据
     *
     * @param key  the key
     * @param data the data
     * @return boolean boolean
     */
    boolean write(String key, String data);

    /**
     * 保存数据，会覆盖前面的数据
     *
     * @param key  the key
     * @param data the data
     * @return boolean boolean
     */
    boolean write(String key, byte[] data);

    /**
     * 读取数据，读取失败或没有值返回空字符“”
     *
     * @param key the key
     * @return string string
     */
    String read(String key) ;

    /**
     * 读取数据，读取失败或没有值返回空字符“”
     *
     * @param key the key
     * @return byte [ ]
     */
    byte[] readBytes(String key) ;

    /**
     * 删除数据，标记删除
     *
     * @param key the key
     * @return boolean boolean
     */
    boolean cut(String key) ;

    /**
     * 移除数据实体，该函数很消耗io和计算资源，若无必要请不要使用
     *
     * @param key the key
     * @return boolean boolean
     */
    boolean remove(String key) ;

    /**
     * 清除垃圾，该函数会删除被标记为删除的数据实体，相当耗费资源，请使用线程运行
     *
     * @return boolean boolean
     */
    boolean clearRef() ;

    /**
     * 返回因各种意外导致的关闭状态
     *
     * @return boolean boolean
     */
    boolean isClose();
}
