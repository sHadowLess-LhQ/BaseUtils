package cn.com.shadowless.baseutils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Word utils.
 *
 * @author sHadowLess
 */
public class WordUtils {

    /**
     * Instantiates a new Word utils.
     */
    private WordUtils() {
    }

    /**
     * Contain boolean.
     *
     * @param input the input
     * @param regex the regex
     * @return the boolean
     */
    public static boolean contain(String input, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.find();
    }
}
