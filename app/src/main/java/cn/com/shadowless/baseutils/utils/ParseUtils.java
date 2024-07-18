package cn.com.shadowless.baseutils.utils;

import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    /**
     * Deep copy list list.
     *
     * @param <T>          the type parameter
     * @param originalList the original list
     * @return the list
     */
    public static <T> List<T> deepCopyList(List<T> originalList) {
        if (originalList == null) {
            return null;
        }
        List<T> copiedList = new ArrayList<>(originalList.size());
        for (T item : originalList) {
            if (item instanceof List) {
                List<?> subList = (List<?>) item;
                copiedList.add((T) deepCopyList(subList));
            } else if (item instanceof Cloneable) {
                try {
                    Method cloneMethod = item.getClass().getMethod("clone");
                    T clonedItem = (T) cloneMethod.invoke(item);
                    copiedList.add(clonedItem);
                } catch (NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                copiedList.add(item);
            }
        }
        return copiedList;
    }

    /**
     * Gets sex from id card.
     *
     * @param idCard the id card
     * @return the sex from id card
     */
    public static int getSexFromIdCard(String idCard) {
        char genderChar = 0;
        if (idCard.length() == 15) {
            genderChar = idCard.charAt(14);
        } else if (idCard.length() == 18) {
            genderChar = idCard.charAt(16);
        }
        return genderChar % 2;
    }

    /**
     * Gets sex name from id card.
     *
     * @param idCard the id card
     * @return the sex name from id card
     */
    public static String getSexNameFromIdCard(String idCard) {
        return getSexFromIdCard(idCard) != 0 ? "男" : "女";
    }

    /**
     * Gets birth day from id card.
     *
     * @param idCard    the id card
     * @param separator the separator
     * @return the birth day from id card
     */
    public static String getBirthDayFromIdCard(String idCard, String separator) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(idCard) && idCard.length() == 15) {
            String year = "19" + idCard.substring(6, 8);
            String month = idCard.substring(8, 10);
            String day = idCard.substring(10, 12);
            list.add(year);
            list.add(month);
            list.add(day);
            return TextUtils.join(separator, list);
        }
        if (!TextUtils.isEmpty(idCard) && idCard.length() == 18) {
            String year = idCard.substring(6, 10);
            String month = idCard.substring(10, 12);
            String day = idCard.substring(12, 14);
            list.add(year);
            list.add(month);
            list.add(day);
            return TextUtils.join(separator, list);
        }
        return "";
    }

    /**
     * Convert 15 to 18 string.
     *
     * @param idCard the id card
     * @return the string
     */
    public static String convertIdCard15To18(String idCard) {
        if (idCard == null || idCard.length() != 15) {
            return "";
        }
        String idCard17 = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
        char[] idCardArray = idCard17.toCharArray();
        int[] coefficient = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] checkCode = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        for (int i = 0; i < idCardArray.length; i++) {
            sum += (idCardArray[i] - '0') * coefficient[i];
        }
        char check = checkCode[sum % 11];
        return idCard17 + check;
    }


    /**
     * Match key words boolean.
     *
     * @param keyword       the keyword
     * @param needMatchWord the need match word
     * @return the boolean
     */
    public static boolean matchKeyWords(String keyword, String needMatchWord) {
        String regex = ".*" + keyword + ".*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(needMatchWord);
        return matcher.matches();
    }

    /**
     * Input judge boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isValidName(String value) {
        String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(speChat);
        Matcher matcher = pattern.matcher(value);
        return !matcher.find();
    }

    /**
     * Is legal id boolean.
     *
     * @param idCard the id card
     * @return the boolean
     */
    public static boolean isValidIdCardNum(String idCard) {
        return idCard.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}(?:\\d|X|x)$)");
    }

    /**
     * Is valid id card num with new boolean.
     *
     * @param idCard the id card
     * @return the boolean
     */
    public static boolean isValidIdCardNumWithSex(String idCard) {
        return idCard.toUpperCase().matches("^\\d{17}(\\d|X|x)$");
    }

    /**
     * Desensitized id number string.
     *
     * @param idNumber the id number
     * @return the string
     */
    public static String desensitizedIdNumber(String idNumber) {
        if (!TextUtils.isEmpty(idNumber)) {
            if (idNumber.length() == 15) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{4})", "$1******$2");
            }
            if (idNumber.length() == 18) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{4})", "$1*********$2");
            }
        }
        return idNumber;
    }

    /**
     * Desensitized phone number string.
     *
     * @param phoneNumber the phone number
     * @return the string
     */
    public static String desensitizedPhoneNumber(String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }
        return phoneNumber;
    }

    /**
     * Calculate age from id card string.
     *
     * @param idCard the id card
     * @return the string
     * @throws ParseException the parse exception
     */
    public static String calculateAgeFromIdCard(String idCard) throws ParseException {
        return calculateAgeFromIdCard(idCard, "yyyyMMdd");
    }

    /**
     * Calculate age from id card string.
     *
     * @param idCard      the id card
     * @param formatValue the format value
     * @return the string
     * @throws ParseException the parse exception
     */
    public static String calculateAgeFromIdCard(String idCard, String formatValue) throws ParseException {
        if (TextUtils.isEmpty(idCard) || idCard.length() < 6) {
            return "";
        }
        String birthdayStr = idCard.substring(6, 14);
        // 将生日信息转换成日期格式
        SimpleDateFormat format = new SimpleDateFormat(formatValue);
        Date birthday = format.parse(birthdayStr);
        // 获取当前日期
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        // 计算年龄
        int age = now.get(Calendar.YEAR) - birthday.getYear() - 1900;
        if (now.get(Calendar.MONTH) < birthday.getMonth() || (now.get(Calendar.MONTH) == birthday.getMonth() && now.get(Calendar.DAY_OF_MONTH) < birthday.getDay())) {
            age--;
        }
        return String.valueOf(age);
    }

    /**
     * Calculate age from born date string.
     *
     * @param bornDate the born date
     * @return the string
     * @throws ParseException the parse exception
     */
    public static String calculateAgeFromBornDate(String bornDate) throws ParseException {
        return calculateAgeFromBornDate(bornDate, "yyyyMMdd");
    }

    /**
     * Calculate age from born date string.
     *
     * @param bornDate    the born date
     * @param formatValue the format value
     * @return the string
     * @throws ParseException the parse exception
     */
    public static String calculateAgeFromBornDate(String bornDate, String formatValue) throws ParseException {
        if (TextUtils.isEmpty(bornDate)) {
            return "";
        }
        // 将生日信息转换成日期格式
        SimpleDateFormat format = new SimpleDateFormat(formatValue);
        Date birthday = format.parse(bornDate);
        // 获取当前日期
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        // 计算年龄
        int age = now.get(Calendar.YEAR) - birthday.getYear() - 1900;
        if (now.get(Calendar.MONTH) < birthday.getMonth() || (now.get(Calendar.MONTH) == birthday.getMonth() && now.get(Calendar.DAY_OF_MONTH) < birthday.getDay())) {
            age--;
        }
        return String.valueOf(age);
    }
}
