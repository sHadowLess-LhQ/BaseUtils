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
public enum PreferencesUtils {

    /**
     * Instance preferences utils.
     */
    INSTANCE;

    /**
     * SP文件名
     */
    private static final String PREFERENCE_NAME = "APP_INFO";

    /**
     * The constant editor.
     */
    private Editor editor = null;

    /**
     * The constant settings.
     */
    private SharedPreferences settings = null;

    /**
     * Init preferences.
     *
     * @param context the context
     */
    public void initPreferences(Context context) {
        initPreferences(context, PREFERENCE_NAME);
    }

    /**
     * Init preferences.
     *
     * @param context the context
     * @param name    the name
     */
    public void initPreferences(Context context, String name) {
        initPreferences(context, name, Context.MODE_PRIVATE);
    }

    /**
     * Init preferences.
     *
     * @param context the context
     * @param name    the name
     * @param mode    the mode
     */
    public void initPreferences(Context context, String name, int mode) {
        settings = context.getSharedPreferences(name, mode);
        editor = settings.edit();
    }

    /**
     * 存入字符串数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putStringNow(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 存入字符串数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public void putString(String key, String value) {
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
    private boolean putStringSetNow(String key, Set<String> value) {
        editor.putStringSet(key, value);
        return editor.commit();
    }

    /**
     * 存入字符串集合数据
     *
     * @param key   the 索引
     * @param value the 存入值
     */
    private void putStringSet(String key, Set<String> value) {
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
    public boolean addStringSetNow(String key, String value) {
        Set<String> strings = getStringSet(key);
        if (null != strings) {
            strings = new HashSet<>(strings);
        } else {
            strings = new HashSet<>();
        }
        strings.add(value);
        return putStringSetNow(key, strings);
    }

    /**
     * 追加字符串集合数据
     *
     * @param key   the 索引
     * @param value the 存入值
     */
    public void addStringSet(String key, String value) {
        Set<String> strings = getStringSet(key);
        if (null != strings) {
            strings = new HashSet<>(strings);
        } else {
            strings = new HashSet<>();
        }
        strings.add(value);
        putStringSet(key, strings);
    }

    /**
     * 删除指定字符串集合数据
     *
     * @param key   the 索引
     * @param value the 删除值
     * @return the boolean
     */
    public boolean removeStringSetNow(String key, String value) {
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
    public void removeStringSet(String key, String value) {
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
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * 获取字符串数据
     *
     * @param key          the 索引
     * @param defaultValue the 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a string
     */
    public String getString(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    /**
     * 获取字符串集合
     *
     * @param key the 索引
     * @return the string set
     */
    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    /**
     * 获取字符串集合
     *
     * @param key          the 索引
     * @param defaultValue the 默认值
     * @return the string set
     */
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return settings.getStringSet(key, defaultValue);
    }

    /**
     * 存入整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putIntNow(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 存入整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取整型数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a int
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * 获取整形数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     */
    public int getInt(String key, int defaultValue) {
        return settings.getInt(key, defaultValue);
    }

    /**
     * 存入长整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putLongNow(String key, long value) {
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 存入长整型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取长整型数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a long
     */
    public long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * 获取长整型数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a long
     */
    public long getLong(String key, long defaultValue) {
        return settings.getLong(key, defaultValue);
    }

    /**
     * 存入单精度浮点数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putFloatNow(String key, float value) {
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 存入单精度浮点数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取单精度浮点数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a float
     */
    public float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * 获取单精度浮点数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a float
     */
    public float getFloat(String key, float defaultValue) {
        return settings.getFloat(key, defaultValue);
    }

    /**
     * 存入布尔类型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putBooleanNow(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 存入布尔类型数据
     *
     * @param key   The 索引
     * @param value The 存入值
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取布尔类型数据
     *
     * @param key The 索引
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this name that is not a boolean
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 获取布尔类型数据
     *
     * @param key          The 索引
     * @param defaultValue The 默认值
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 清空SP
     *
     * @return the boolean
     */
    public boolean clearNow() {
        editor.clear();
        return editor.commit();
    }

    /**
     * 清空SP
     */
    public void clear() {
        editor.clear();
        editor.apply();
    }

    /**
     * 删除指定数据
     *
     * @param key the 索引
     * @return the 是否删除
     */
    public boolean removeNow(String key) {
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 删除指定数据
     *
     * @param key the 索引
     */
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }
}