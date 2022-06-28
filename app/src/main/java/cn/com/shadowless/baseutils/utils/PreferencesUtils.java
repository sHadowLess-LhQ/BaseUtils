package cn.com.shadowless.baseutils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

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
     * 单例
     */
    private static PreferencesUtils preferencesUtils;

    /**
     * 上下文
     */
    private static Context context;

    /**
     * Instantiates a new Preferences utils.
     */
    private PreferencesUtils() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PreferencesUtils getInstance() {
        if (preferencesUtils == null) {
            preferencesUtils = new PreferencesUtils();
        }
        return preferencesUtils;
    }

    /**
     * Init preferences.
     *
     * @param context the context
     */
    public void initPreferences(Context context) {
        PreferencesUtils.context = context;
    }

    /**
     * 存入字符串数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putStringNow(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 存入字符串数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public static void putString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 存入字符串集合数据
     *
     * @param key   the 索引
     * @param value the 存入值
     * @return the boolean
     */
    private static boolean putStringSetNow(String key, Set<String> value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putStringSet(key, value);
        return editor.commit();
    }

    /**
     * 存入字符串集合数据
     *
     * @param key   the 索引
     * @param value the 存入值
     */
    private static void putStringSet(String key, Set<String> value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * 追加字符串集合数据
     *
     * @param key   the 索引
     * @param value the 存入值
     * @return the boolean
     */
    public static boolean addStringSetNow(String key, String value) {
        Set<String> strings = getStringSet(key);
        if (null != strings) {
            strings = new HashSet<>(strings);
            strings.add(value);
        } else {
            strings = new HashSet<>();
            strings.add(value);
        }
        return putStringSetNow(key, strings);
    }

    /**
     * 追加字符串集合数据
     *
     * @param key   the 索引
     * @param value the 存入值
     */
    public static void addStringSet(String key, String value) {
        Set<String> strings = getStringSet(key);
        if (null != strings) {
            strings = new HashSet<>(strings);
            strings.add(value);
        } else {
            strings = new HashSet<>();
            strings.add(value);
        }
        putStringSet(key, strings);
    }

    /**
     * 删除指定字符串集合数据
     *
     * @param key   the 索引
     * @param value the 删除值
     * @return the boolean
     */
    public static boolean removeStringSetNow(String key, String value) {
        Set<String> strings = getStringSet(key);
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
        return putStringSetNow(key, strings);
    }

    /**
     * 删除指定字符串集合数据
     *
     * @param key   the 索引
     * @param value the 删除值
     */
    public static void removeStringSet(String key, String value) {
        Set<String> strings = getStringSet(key);
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
        putStringSet(key, strings);
    }


    /**
     * 获取字符串数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this name that is not a string
     * @see #getString(Context, String, String) #getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * 获取字符串数据
     *
     * @param key          the 索引
     * @param defaultValue the 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a string
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * 获取字符串集合
     *
     * @param key the 索引
     * @return the string set
     */
    public static Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    /**
     * 获取字符串集合
     *
     * @param key          the 索引
     * @param defaultValue the 默认值
     * @return the string set
     */
    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getStringSet(key, defaultValue);
    }

    /**
     * 存入整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putIntNow(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 存入整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public static void putInt(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取整型数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a int
     * @see #getInt(Context, String, int) #getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * 获取整形数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a int
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * 存入长整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLongNow(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 存入长整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public static void putLong(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取长整型数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a long
     * @see #getLong(Context, String, long) #getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * 获取长整型数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a long
     */
    public static long getLong(String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * 存入单精度浮点数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloatNow(String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 存入单精度浮点数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public static void putFloat(String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取单精度浮点数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a float
     * @see #getFloat(Context, String, float) #getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)
     */
    public static float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * 获取单精度浮点数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a float
     */
    public static float getFloat(String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * 存入布尔类型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBooleanNow(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 存入布尔类型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取布尔类型数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this name that is not a boolean
     * @see #getBoolean(Context, String, boolean) #getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 获取布尔类型数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 清空SP
     *
     * @return the boolean
     */
    public static boolean clearNow() {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 清空SP
     */
    public static void clear() {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 删除指定数据
     *
     * @param key the 索引
     * @return the 是否删除
     */
    public static boolean removeNow(String key) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 删除指定数据
     *
     * @param key the 索引
     */
    public static void remove(String key) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}