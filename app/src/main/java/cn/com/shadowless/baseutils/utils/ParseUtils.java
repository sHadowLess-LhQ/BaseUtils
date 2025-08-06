package cn.com.shadowless.baseutils.utils;

import android.text.TextUtils;


/**
 * The type Parse utils.
 *
 * @author sHadowLess
 */
public class ParseUtils {

    /**
     * Instantiates a new Parse utils.
     */
    private ParseUtils() {
    }

    /**
     * Parse string value to int int.
     *
     * @param string the string
     * @return the int
     */
    public static int parseStringToInt(String string) {
        return parseStringToInt(string, 0);
    }

    /**
     * Parse string value to int int.
     *
     * @param string   the string
     * @param defValue the def value
     * @return the int
     */
    public static int parseStringToInt(String string, int defValue) {
        if (TextUtils.isEmpty(string)) {
            return defValue;
        }
        return Integer.parseInt(string);
    }

    /**
     * Parse string value to integer integer.
     *
     * @param string the string
     * @return the integer
     */
    public static Integer parseStringToInteger(String string) {
        return parseStringToInteger(string, null);
    }

    /**
     * Parse string value to integer integer.
     *
     * @param string   the string
     * @param defValue the def value
     * @return the integer
     */
    public static Integer parseStringToInteger(String string, Integer defValue) {
        if (TextUtils.isEmpty(string)) {
            return defValue;
        }
        return Integer.valueOf(string);
    }


    /**
     * Parse string to double double.
     *
     * @param value the value
     * @return the double
     */
    public static double parseStringToBasicDouble(String value) {
        return parseStringToBasicDouble(value, 0);
    }

    /**
     * Parse string to double double.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the double
     */
    public static double parseStringToBasicDouble(String value, double defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Double.parseDouble(value);
    }

    /**
     * Parse string to object double double.
     *
     * @param value the value
     * @return the double
     */
    public static Double parseStringToObjectDouble(String value) {
        return parseStringToObjectDouble(value, 0);
    }

    /**
     * Parse string to object double double.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the double
     */
    public static Double parseStringToObjectDouble(String value, double defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Double.valueOf(value);
    }

    /**
     * Parse string to basic long long.
     *
     * @param value the value
     * @return the long
     */
    public static long parseStringToBasicLong(String value) {
        return parseStringToBasicLong(value, 0L);
    }

    /**
     * Parse string to basic long long.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the long
     */
    public static long parseStringToBasicLong(String value, long defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Long.parseLong(value);
    }

    /**
     * Parse string to object long long.
     *
     * @param value the value
     * @return the long
     */
    public static Long parseStringToObjectLong(String value) {
        return parseStringToObjectLong(value, null);
    }

    /**
     * Parse string to object long long.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the long
     */
    public static Long parseStringToObjectLong(String value, Long defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Long.valueOf(value);
    }

    /**
     * Parse string to basic float float.
     *
     * @param value the value
     * @return the float
     */
    public static float parseStringToBasicFloat(String value) {
        return parseStringToBasicFloat(value, 0f);
    }

    /**
     * Parse string to basic float float.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the float
     */
    public static float parseStringToBasicFloat(String value, float defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Float.parseFloat(value);
    }

    /**
     * Parse string to object float float.
     *
     * @param value the value
     * @return the float
     */
    public static Float parseStringToObjectFloat(String value) {
        return parseStringToObjectFloat(value, null);
    }

    /**
     * Parse string to object float float.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the float
     */
    public static Float parseStringToObjectFloat(String value, Float defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Float.valueOf(value);
    }

    /**
     * Parse int to string string.
     *
     * @param value the value
     * @return the string
     */
    public static boolean parseStringToBasicBoolean(String value) {
        return parseStringToBasicBoolean(value, false);
    }

    /**
     * Parse string to basic boolean boolean.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the boolean
     */
    public static boolean parseStringToBasicBoolean(String value, boolean defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * Parse string to object boolean boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public static Boolean parseStringToObjectBoolean(String value) {
        return parseStringToObjectBoolean(value, null);
    }

    /**
     * Parse string to object boolean boolean.
     *
     * @param value    the value
     * @param defValue the def value
     * @return the boolean
     */
    public static Boolean parseStringToObjectBoolean(String value, Boolean defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        return Boolean.valueOf(value);
    }

    /**
     * Parse object to t t.
     *
     * @param <T> the type parameter
     * @param o   the o
     * @return the t
     */
    public static <T> T parseObjectToT(Object o) {
        if (o == null) {
            return null;
        }
        return (T) o;
    }
}
