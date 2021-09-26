package cn.com.shadowless.baseutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * The type Preferences utils.
 *
 * @author sHadowLess
 */
public class PreferencesUtils {

    /**
     * The constant PREFERENCE_NAME.
     */
    public static String PREFERENCE_NAME = "APP_INFO";

    private PreferencesUtils() {
    }

    /**
     * put string preferences
     *
     * @param context the context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * Put string set boolean.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     * @return the boolean
     */
    public static boolean putStringSet(Context context, String key, Set<String> value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putStringSet(key, value);
        return editor.commit();
    }

    /**
     * Add string set boolean.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
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
     * Remove string set boolean.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
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
     * get string preferences
     *
     * @param context the context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this name that is not a string
     * @see #getString(Context, String, String) #getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)#getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * Gets string set.
     *
     * @param context the context
     * @param key     the key
     * @return the string set
     */
    public static Set<String> getStringSet(Context context, String key) {
        return getStringSet(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context      the context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * Gets string set.
     *
     * @param context      the context
     * @param key          the key
     * @param defaultValue the default value
     * @return the string set
     */
    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getStringSet(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context the context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context the context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a int
     * @see #getInt(Context, String, int) #getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)#getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context      the context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context the context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context the context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a long
     * @see #getLong(Context, String, long) #getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)#getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context      the context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context the context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context the context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this name that is not a float
     * @see #getFloat(Context, String, float) #getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)#getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context      the context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context the context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context the context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this name that is not a boolean
     * @see #getBoolean(Context, String, boolean) #getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)#getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context      the context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * Clear.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();
    }

}