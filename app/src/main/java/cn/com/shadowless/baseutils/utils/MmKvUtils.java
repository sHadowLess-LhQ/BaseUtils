package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * The enum MmKv utils.
 *
 * @author sHadowLess
 */
public class MmKvUtils {

    /**
     * 默认名
     */
    private final static String NAME = "app_info";

    /**
     * 工具类实例
     */
    private static MmKvUtils mmkvUtils = null;

    /**
     * MMKV实例
     */
    private static MMKV mmkv = null;

    /**
     * 私有构造
     */
    private MmKvUtils() {
    }

    /**
     * 获取工具类实例
     *
     * @return the instance
     */
    public static MmKvUtils getInstance() {
        if (mmkvUtils == null) {
            mmkvUtils = new MmKvUtils();
        }
        return mmkvUtils;
    }

    /**
     * 初始化MMKV
     *
     * @param context the 上下文
     */
    public void initMmKv(Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }

    /**
     * 初始化MMKV多进程
     *
     * @param context the 上下文
     */
    public void initMmKvMultipleProcesses(Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.mmkvWithID(NAME, MMKV.MULTI_PROCESS_MODE);
    }

    /**
     * 初始化MMKV，指定名称，指定是否多进程
     *
     * @param context    the 上下文
     * @param name       the 文件名
     * @param isMultiple the 是否多进程
     */
    public void initMmKv(Context context, String name, boolean isMultiple) {
        MMKV.initialize(context);
        if (isMultiple) {
            mmkv = MMKV.mmkvWithID(name, MMKV.MULTI_PROCESS_MODE);
        } else {
            mmkv = MMKV.mmkvWithID(name);
        }
    }

    /**
     * 初始化MMKV，指定路径，指定名称，指定是否多进程
     *
     * @param context    the 上下文
     * @param path       the 路径
     * @param name       the 文件名
     * @param isMultiple the 是否多进程
     */
    public void initMmKv(Context context, String path, String name, boolean isMultiple) {
        MMKV.initialize(context, path);
        if (isMultiple) {
            mmkv = MMKV.mmkvWithID(name, MMKV.MULTI_PROCESS_MODE);
        } else {
            mmkv = MMKV.mmkvWithID(name);
        }
    }

    /**
     * 放int
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putInt(String key, int value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放String
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putString(String key, String value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放boolean
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putBoolean(String key, boolean value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放long
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putLong(String key, long value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放double
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putDouble(String key, double value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放float
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putFloat(String key, float value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放bytes
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putBytes(String key, byte[] value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放StringSet
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putStringSet(String key, Set<String> value) {
        return mmkv.encode(key, value);
    }

    /**
     * 放Parcelable
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean putParcelable(String key, Parcelable value) {
        return mmkv.encode(key, value);
    }

    /**
     * 拿int
     *
     * @param key the key
     * @return the int
     */
    public static int getInt(String key) {
        return mmkv.decodeInt(key);
    }

    /**
     * 拿int，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the int with default
     */
    public static int getIntWithDefault(String key, int value) {
        return mmkv.decodeInt(key, value);
    }

    /**
     * 拿String
     *
     * @param key the key
     * @return the string
     */
    public static String getString(String key) {
        return mmkv.decodeString(key);
    }

    /**
     * 拿String，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static String getStringWithDefault(String key, String value) {
        return mmkv.decodeString(key, value);
    }

    /**
     * 拿boolean
     *
     * @param key the key
     * @return the string
     */
    public static boolean getBoolean(String key) {
        return mmkv.decodeBool(key);
    }

    /**
     * 拿boolean，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static boolean getBooleanWithDefault(String key, boolean value) {
        return mmkv.decodeBool(key, value);
    }

    /**
     * 拿long
     *
     * @param key the key
     * @return the string
     */
    public static long getLong(String key) {
        return mmkv.decodeLong(key);
    }

    /**
     * 拿long，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static long getLongWithDefault(String key, long value) {
        return mmkv.decodeLong(key, value);
    }

    /**
     * 拿double
     *
     * @param key the key
     * @return the string
     */
    public static double getDouble(String key) {
        return mmkv.decodeDouble(key);
    }

    /**
     * 拿double，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static double getDoubleWithDefault(String key, double value) {
        return mmkv.decodeDouble(key, value);
    }

    /**
     * 拿float
     *
     * @param key the key
     * @return the string
     */
    public static float getFloat(String key) {
        return mmkv.decodeFloat(key);
    }

    /**
     * 拿float，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static float getFloatWithDefault(String key, float value) {
        return mmkv.decodeFloat(key, value);
    }

    /**
     * 拿bytes
     *
     * @param key the key
     * @return the string
     */
    public static byte[] getBytes(String key) {
        return mmkv.decodeBytes(key);
    }

    /**
     * 拿bytes，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static float getBytesWithDefault(String key, float value) {
        return mmkv.decodeFloat(key, value);
    }

    /**
     * 拿StringSet
     *
     * @param key the key
     * @return the string
     */
    public static Set<String> getStringSet(String key) {
        return mmkv.decodeStringSet(key);
    }

    /**
     * 拿StringSet，有默认值
     *
     * @param key   the key
     * @param value the value
     * @return the string default
     */
    public static Set<String> getStringSetWithDefault(String key, Set<String> value) {
        return mmkv.decodeStringSet(key, value);
    }

    /**
     * 拿Parcelable
     *
     * @param <T> the type parameter
     * @param key the key
     * @param cls the cls
     * @return the string
     */
    public static <T extends Parcelable> T getParcelable(String key, Class<T> cls) {
        return mmkv.decodeParcelable(key, cls);
    }

    /**
     * 拿Parcelable，有默认值
     *
     * @param <T> the type parameter
     * @param key the key
     * @param cls the cls
     * @param t   the t
     * @return the string default
     */
    public static <T extends Parcelable> T getParcelableWithDefault(String key, Class<T> cls, T t) {
        return (T) mmkv.decodeParcelable(key, cls, t);
    }

    /**
     * 查询是否有key
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean queryKey(String key) {
        return mmkv.containsKey(key);
    }

    /**
     * 删除指定值
     *
     * @param key the key
     */
    public static void removeValue(String key) {
        mmkv.removeValueForKey(key);
    }

    /**
     * 删除多个指定值
     *
     * @param key the key
     */
    public static void removeValues(String[] key) {
        mmkv.removeValuesForKeys(key);
    }
}
