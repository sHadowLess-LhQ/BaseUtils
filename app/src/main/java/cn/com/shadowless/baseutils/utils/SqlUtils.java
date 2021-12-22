package cn.com.shadowless.baseutils.utils;

import android.content.Context;

import java.io.IOException;

import cn.com.shadowless.baseutils.sql.Sql;

/**
 * The type Sql utils.
 *
 * @author sHadowLess
 */
public class SqlUtils {
    /**
     * Instantiates a new Sql utils.
     */
    private SqlUtils() {
    }

    /**
     * 初始化数据库
     *
     * @param context the 上下文
     * @param dbName  the 数据库名
     */
    public static void initDateBase(Context context, String dbName) {
        Sql.getInstance().init(context, dbName + ".db");
    }

    /**
     * 写入字符串数据
     *
     * @param key   the 键
     * @param value the 值
     * @return the 是否成功
     */
    public static boolean writeString(String key, String value) {
        return Sql.getInstance().getSqlServer().write(key, value);
    }

    /**
     * 写入字节数组数据
     *
     * @param key   the 键
     * @param value the 值
     * @return the 是否成功
     */
    public static boolean writeByteList(String key, byte[] value) {
        return Sql.getInstance().getSqlServer().write(key, value);
    }

    /**
     * 获取字符串数据
     *
     * @param key the 键
     * @return the 数据
     */
    public static String getString(String key) {
        return Sql.getInstance().getSqlServer().read(key);
    }

    /**
     * 获取字节数组数据
     *
     * @param key the 键
     * @return the 数据
     */
    public static byte[] getByteList(String key) {
        return Sql.getInstance().getSqlServer().readBytes(key);
    }

    /**
     * 标记需要删除的数据
     *
     * @param key the 键
     * @return the 是否成功
     */
    public static boolean deleteAndSign(String key) {
        return Sql.getInstance().getSqlServer().cut(key);
    }

    /**
     * 直接删除数据
     *
     * @param key the 键
     * @return the 是否成功
     */
    public static boolean removeData(String key) {
        return Sql.getInstance().getSqlServer().remove(key);
    }

    /**
     * 删除标记的数据
     *
     * @return the 是否成功
     */
    public static boolean clearSignData() {
        return Sql.getInstance().getSqlServer().clearRef();
    }

    /**
     * 关闭数据库
     *
     * @throws IOException the io exception
     */
    public static void closeDb() throws IOException {
        Sql.getInstance().close();
    }
}
