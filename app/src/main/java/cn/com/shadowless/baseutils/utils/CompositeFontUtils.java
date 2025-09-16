package cn.com.shadowless.baseutils.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 复合字体工具类
 * <p>
 * 该工具类用于处理多种字体的组合显示，能够根据字符内容自动选择合适的字体进行显示。
 * 主要用于解决在Android中显示多语言文本时，不同语言需要使用不同字体的问题。
 * </p>
 */
public class CompositeFontUtils {
    /**
     * 字体列表，用于按顺序查找适合显示特定字符的字体
     */
    private final List<Typeface> typefaceList;

    /**
     * 默认字体，当字体列表中没有适合的字体时使用
     */
    private final Typeface defaultTypeface;

    /**
     * 字符到字体的缓存映射，用于提高查找性能
     * 使用LruCache限制缓存大小，避免内存无限增长
     */
    private final LruCache<Integer, Typeface> fontCache;

    /**
     * 用于测量字符的Paint对象，复用以减少对象创建开销
     */
    private final ThreadLocal<Paint> paintThreadLocal = new ThreadLocal<Paint>() {
        @Override
        protected Paint initialValue() {
            return new Paint();
        }
    };

    /**
     * 用于测量字符边界的Rect对象，复用以减少对象创建开销
     */
    private final ThreadLocal<Rect> rectThreadLocal = new ThreadLocal<Rect>() {
        @Override
        protected Rect initialValue() {
            return new Rect();
        }
    };

    /**
     * 构造函数
     *
     * @param typefaceList 字体列表，用于按顺序查找适合显示特定字符的字体
     */
    public CompositeFontUtils(@NonNull List<Typeface> typefaceList) {
        this(null, typefaceList);
    }

    /**
     * 构造函数
     *
     * @param defaultTypeface 默认字体，当字体列表中没有适合的字体时使用
     * @param typefaceList    字体列表，用于按顺序查找适合显示特定字符的字体
     */
    public CompositeFontUtils(@Nullable Typeface defaultTypeface, @NonNull List<Typeface> typefaceList) {
        this(defaultTypeface, typefaceList, 500);
    }


    /**
     * 构造函数
     *
     * @param defaultTypeface 默认字体，当字体列表中没有适合的字体时使用
     * @param typefaceList    字体列表，用于按顺序查找适合显示特定字符的字体
     * @param cacheSize       缓存大小
     */
    public CompositeFontUtils(@Nullable Typeface defaultTypeface, @NonNull List<Typeface> typefaceList, int cacheSize) {
        if (defaultTypeface == null) {
            defaultTypeface = Typeface.DEFAULT;
        }
        this.defaultTypeface = defaultTypeface;
        this.typefaceList = new ArrayList<>();
        this.typefaceList.addAll(typefaceList);
        this.fontCache = new LruCache<>(cacheSize);
    }

    /**
     * 创建复合文本
     * <p>
     * 根据输入的文本内容，为每个字符查找最适合的字体，并生成带有相应字体样式的SpannableStringBuilder
     * </p>
     *
     * @param text 需要处理的文本内容
     * @return 带有字体样式标记的SpannableStringBuilder
     */
    public SpannableStringBuilder createCompositeText(String text) {
        return createCompositeText(text, null);
    }

    /**
     * 创建复合文本
     * <p>
     * 根据输入的文本内容和字体比较规则，为每个字符查找最适合的字体，
     * 并生成带有相应字体样式的SpannableStringBuilder
     * </p>
     *
     * @param text 需要处理的文本内容
     * @param rule 字体比较规则，用于自定义字体选择逻辑
     * @return 带有字体样式标记的SpannableStringBuilder
     */
    public SpannableStringBuilder createCompositeText(String text, FontCompareRule rule) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            int charCount = Character.charCount(codePoint);
            Typeface suitableTypeface = findSuitableTypefaceByMeasurement(codePoint, rule);
            if (suitableTypeface != null) {
                builder.setSpan(
                        new CustomTypefaceSpan(suitableTypeface),
                        i, i + charCount,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            } else {
                builder.setSpan(
                        new CustomTypefaceSpan(defaultTypeface),
                        i, i + charCount,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
            i += charCount;
        }
        return builder;
    }

    /**
     * 通过测量查找适合的字体
     * <p>
     * 遍历字体列表，查找能够正确显示指定字符的字体
     * </p>
     *
     * @param codePoint 字符的Unicode码点
     * @param rule      字体比较规则，用于自定义字体选择逻辑
     * @return 适合显示该字符的字体，如果找不到则返回null
     */
    private Typeface findSuitableTypefaceByMeasurement(int codePoint, FontCompareRule rule) {
        Typeface cachedTypeface = fontCache.get(codePoint);
        if (cachedTypeface != null) {
            return cachedTypeface;
        }

        String charString = new String(new int[]{codePoint}, 0, 1);
        for (Typeface typeface : typefaceList) {
            if (rule != null && rule.compare(typeface, codePoint)) {
                fontCache.put(codePoint, typeface);
                return typeface;
            }
            if (isCharacterVisibleInFont(typeface, charString)) {
                fontCache.put(codePoint, typeface);
                return typeface;
            }
        }

        if (rule != null && rule.compareDefault(defaultTypeface, codePoint)) {
            fontCache.put(codePoint, defaultTypeface);
            return defaultTypeface;
        }
        if (isCharacterVisibleInFont(defaultTypeface, charString)) {
            fontCache.put(codePoint, defaultTypeface);
            return defaultTypeface;
        }

        return null;
    }

    /**
     * 检查字符在指定字体中是否可见
     * <p>
     * 通过测量字符的宽度和边界来判断字符在指定字体中是否可见
     * </p>
     *
     * @param typeface   要检查的字体
     * @param charString 字符串
     * @return 如果字符在字体中可见则返回true，否则返回false
     */
    private boolean isCharacterVisibleInFont(Typeface typeface, String charString) {
        Paint paint = paintThreadLocal.get();
        if (paint != null) {
            paint.setTypeface(typeface);

            float width = paint.measureText(charString);
            if (width <= 0) {
                return false;
            }

            Rect bounds = rectThreadLocal.get();
            if (bounds != null) {
                bounds.setEmpty();
                paint.getTextBounds(charString, 0, charString.length(), bounds);
                if (bounds.width() <= 0 || bounds.height() <= 0) {
                    return false;
                }
            }

            int codePoint = charString.codePointAt(0);
            if (codePoint == 0xFFFD) {
                return false;
            }

            float normalCharWidth = paint.measureText("中");
            return !(width < normalCharWidth * 0.1f);
        }
        return false;
    }

    /**
     * 字体比较规则接口
     * <p>
     * 用于自定义字体选择逻辑的接口
     * </p>
     */
    public interface FontCompareRule {
        /**
         * 比较字体和字符码点
         *
         * @param typeface  字体
         * @param codePoint 字符码点
         * @return 如果字体适合显示该字符则返回true，否则返回false
         */
        boolean compare(Typeface typeface, int codePoint);

        /**
         * 比较默认字体和字符码点
         *
         * @param typeface  字体
         * @param codePoint 字符码点
         * @return 如果字体适合显示该字符则返回true，否则返回false
         */
        boolean compareDefault(Typeface typeface, int codePoint);
    }


    /**
     * 自定义字体Span
     * <p>
     * 继承自TypefaceSpan，用于应用自定义字体到文本的一部分
     * </p>
     */
    private static class CustomTypefaceSpan extends TypefaceSpan {
        /**
         * 要应用的新字体
         */
        private final Typeface newType;

        /**
         * 构造函数
         *
         * @param type 要应用的字体
         */
        public CustomTypefaceSpan(Typeface type) {
            super("");
            newType = type;
        }

        /**
         * 更新绘制状态时应用自定义字体
         *
         * @param ds 文本绘制参数
         */
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        /**
         * 更新测量状态时应用自定义字体
         *
         * @param paint 文本绘制参数
         */
        @Override
        public void updateMeasureState(@NonNull TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        /**
         * 应用自定义字体到Paint对象
         *
         * @param paint Paint对象
         * @param tf    要应用的字体
         */
        private void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }

}