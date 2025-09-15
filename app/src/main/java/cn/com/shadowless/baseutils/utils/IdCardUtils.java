package cn.com.shadowless.baseutils.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证工具类
 *
 * @author sHadowLess
 */
public class IdCardUtils {

    /**
     * 私有构造函数，不允许外部实例化
     */
    private IdCardUtils() {

    }

    /**
     * 从身份证获取性别
     *
     * @param idCard 身份证号码
     * @return 性别代码（奇数为男，偶数为女）
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
