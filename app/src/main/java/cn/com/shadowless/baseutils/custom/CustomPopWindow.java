package cn.com.shadowless.baseutils.custom;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;

import cn.com.shadowless.baseutils.R;

/**
 * The type Custom pop window.
 *
 * @author sHadowLess
 */
public class CustomPopWindow extends PopupWindow {

    /**
     * 自定义视图
     */
    private View popView;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 自定义布局文件
     */
    private int layout;
    /**
     * 是否设置动画
     */
    private boolean isSetAnim;
    /**
     * 动画值
     */
    private int anim;
    /**
     * 背景
     */
    private Drawable background;
    /**
     * 是否全局显示
     */
    private boolean isSystemPopWindow;
    /**
     * The Width.
     */
    private int width;
    /**
     * The Height.
     */
    private int height;
    /**
     * The Is focus.
     */
    private boolean isFocus;

    /**
     * 构造
     *
     * @param popView           the pop view
     * @param context           the context
     * @param layout            the layout
     * @param isSetAnim         the is set anim
     * @param anim              the anim
     * @param background        the background
     * @param isSystemPopWindow the is system pop window
     * @param width             the width
     * @param height            the height
     * @param isFocus           the is focus
     */
    public CustomPopWindow(View popView, Context context, int layout, boolean isSetAnim, int anim, Drawable background, boolean isSystemPopWindow, int width, int height, boolean isFocus) {
        this.popView = popView;
        this.context = context;
        this.layout = layout;
        this.isSetAnim = isSetAnim;
        this.anim = anim;
        this.background = background;
        this.isSystemPopWindow = isSystemPopWindow;
        this.width = width;
        this.height = height;
        this.isFocus = isFocus;
    }

    /**
     * 构造
     *
     * @param context the context
     */
    public CustomPopWindow(Context context) {
        super(context);
    }

    /**
     * 构造
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 构造
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public CustomPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 构造
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    public CustomPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 构造
     */
    public CustomPopWindow() {
        super();
    }

    /**
     * 构造
     *
     * @param contentView the content view
     */
    public CustomPopWindow(View contentView) {
        super(contentView);
    }

    /**
     * 构造
     *
     * @param width  the width
     * @param height the height
     */
    public CustomPopWindow(int width, int height) {
        super(width, height);
    }

    /**
     * 构造
     *
     * @param contentView the content view
     * @param width       the width
     * @param height      the height
     */
    public CustomPopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    /**
     * 构造
     *
     * @param contentView the content view
     * @param width       the width
     * @param height      the height
     * @param focusable   the focusable
     */
    public CustomPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    /**
     * 构造者
     *
     * @return the custom pop window . custom pop window builder
     */
    public static CustomPopWindowBuilder builder() {
        return new CustomPopWindowBuilder();
    }

    /**
     * 构造者实体类
     */
    public static class CustomPopWindowBuilder {
        /**
         * 自定义视图
         */
        private View popView;
        /**
         * 上下文
         */
        private Context context;
        /**
         * 自定义布局文件
         */
        private int layout;
        /**
         * 是否设置动画
         */
        private boolean isSetAnim;
        /**
         * 动画值
         */
        private int anim;
        /**
         * The Background.
         */
        private Drawable background;
        /**
         * The Is system pop window.
         */
        private boolean isSystemPopWindow;
        /**
         * The Width.
         */
        private int width;
        /**
         * The Height.
         */
        private int height;
        /**
         * The Is focus.
         */
        private boolean isFocus;

        /**
         * Pop view custom pop window builder.
         *
         * @param popView the pop view
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder popView(View popView) {
            this.popView = popView;
            return this;
        }

        /**
         * Width custom pop window builder.
         *
         * @param width the width
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder width(int width) {
            this.width = width;
            return this;
        }

        /**
         * Height custom pop window builder.
         *
         * @param height the height
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * Is focus custom pop window builder.
         *
         * @param isFocus the is focus
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder isFocus(boolean isFocus) {
            this.isFocus = isFocus;
            return this;
        }

        /**
         * Context custom pop window builder.
         *
         * @param context the context
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder context(Context context) {
            this.context = context;
            return this;
        }

        /**
         * Layout custom pop window builder.
         *
         * @param layout the layout
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder layout(int layout) {
            this.layout = layout;
            return this;
        }

        /**
         * Is set anim custom pop window builder.
         *
         * @param isSetAnim the is set anim
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder isSetAnim(boolean isSetAnim) {
            this.isSetAnim = isSetAnim;
            return this;
        }

        /**
         * Anim custom pop window builder.
         *
         * @param anim the anim
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder anim(int anim) {
            this.anim = anim;
            return this;
        }

        /**
         * Background custom pop window builder.
         *
         * @param background the background
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder background(Drawable background) {
            this.background = background;
            return this;
        }

        /**
         * Is system pop window custom pop window builder.
         *
         * @param isSystemPopWindow the is system pop window
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder isSystemPopWindow(boolean isSystemPopWindow) {
            this.isSystemPopWindow = isSystemPopWindow;
            return this;
        }

        /**
         * Build custom pop window.
         *
         * @return the custom pop window
         */
        public CustomPopWindow build() {
            return new CustomPopWindow(this.popView, this.context, this.layout, this.isSetAnim, this.anim, this.background, this.isSystemPopWindow, this.width, this.height, this.isFocus);
        }
    }

    /**
     * Show.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initPopWindow() {
        if (null == popView) {
            popView = LayoutInflater.from(context).inflate(layout, null);
        }
        if (0 == width) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (0 == height) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        setWidth(width);
        setHeight(height);
        setContentView(popView);
        if (isSetAnim && 0 != anim) {
            setAnimationStyle(anim);
        }
        if (null == background) {
            background = new ColorDrawable(context.getResources().getColor(R.color.transparent));
        }
        setBackgroundDrawable(background);
        if (isSystemPopWindow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                setWindowLayoutType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }
        }
    }
}
