package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 沙盒工具类
 *
 * @author sHadowLess
 */
public class PreferencesUtils {

    /**
     * SP文件名
     */
    public static String PREFERENCE_NAME = "APP_INFO";

    /**
     * Instantiates a new Preferences utils.
     */
    private PreferencesUtils() {
    }

    /**
     * 存入字符串数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @param value   The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 存入字符串集合数据
     *
     * @param context the 上下文
     * @param key     the 索引
     * @param value   the 存入值
     * @return the boolean
     */
    public static boolean putStringSet(Context context, String key, Set<String> value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putStringSet(key, value);
        return editor.commit();
    }

    /**
     * 追加字符串集合数据
     *
     * @param context the 上下文
     * @param key     the 索引
     * @param value   the 存入值
     * @return the boolean
     */
    public static boolean addStringSet(Context context, String key, String value) {
        Set<String> strings = getStringSet(context, key);
        if (null != strings) {
            strings = new HashSet<>(strings);
            strings.add(value);
        } else {
            strings = new HashSet<>();
            strings.add(value);
        }
        return putStringSet(context, key, strings);
    }

    /**
     * 删除指定字符串集合数据
     *
     * @param context the 上下文
     * @param key     the 索引
     * @param value   the 删除值
     * @return the boolean
     */
    public static boolean removeStringSet(Context context, String key, String value) {
        Set<String> strings = getStringSet(context, key);
        if (null != strings && 0 != strings.size()) {
            strings = new HashSet<>(strings);
            Iterator<String> iterator = strings.iterator();
            for (int i = 0; i < strings.size(); i++) {
                String name = iterator.next();
                if (value.equals(name)) {
                    iterator.remove();
                }
            }
        }
        return putStringSet(context, key, strings);
    }

    /**
     * 获取字符串数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this name that is not a string
     * @see #getString(Context, String, String) #getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * 获取字符串集合
     *
     * @param context the 上下文
     * @param key     the 索引
     * @return the string set
     */
    public static Set<String> getStringSet(Context context, String key) {
        return getStringSet(context, key, null);
    }

    /**
     * 获取字符串数据
     *
     * @param context      the 上下文
     * @param key          the 索引
     * @param defaultValue the 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * 获取字符串集合
     *
     * @param context      the 上下文
     * @param key          the 索引
     * @param defaultValue the 默认值
     * @return the string set
     */
    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getStringSet(key, defaultValue);
    }

    /**
     * 存入整型数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @param value   The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 获取整型数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a int
     * @see #getInt(Context, String, int) #getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * 获取整形数据
     *
     * @param context      the 上下文
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * 存入长整型数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @param value   The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 获取长整型数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a long
     * @see #getLong(Context, String, long) #getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * 获取长整型数据
     *
     * @param context      the 上下文
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * 存入单精度浮点数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @param value   The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 获取单精度浮点数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a float
     * @see #getFloat(Context, String, float) #getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * 获取单精度浮点数据
     *
     * @param context      the 上下文
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * 存入布尔类型数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @param value   The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 获取布尔类型数据
     *
     * @param context the 上下文
     * @param key     The 索引
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this name that is not a boolean
     * @see #getBoolean(Context, String, boolean) #getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * 获取布尔类型数据
     *
     * @param context      the 上下文
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 清空SP
     *
     * @param context the 上下文
     * @return the boolean
     */
    public static boolean clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();
    }

}