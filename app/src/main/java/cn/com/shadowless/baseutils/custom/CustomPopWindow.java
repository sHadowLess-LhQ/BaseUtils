package cn.com.shadowless.baseutils.custom;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
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
public class CustomPopWindow {

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
     * The Is outside.
     */
    private boolean isOutside;
    /**
     * The Popup window.
     */
    private PopupWindow popupWindow;

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
     * @param isOutside         the is outside
     */
    public CustomPopWindow(View popView, Context context, int layout, boolean isSetAnim, int anim, Drawable background, boolean isSystemPopWindow, int width, int height, boolean isFocus, boolean isOutside) {
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
        this.isOutside = isOutside;
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
         * The Is outside.
         */
        private boolean isOutside;

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
         * Is outside custom pop window builder.
         *
         * @param isOutside the is outside
         * @return the custom pop window builder
         */
        public CustomPopWindowBuilder isOutside(boolean isOutside) {
            this.isOutside = isOutside;
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
            return new CustomPopWindow(this.popView, this.context, this.layout, this.isSetAnim, this.anim, this.background, this.isSystemPopWindow, this.width, this.height, this.isFocus, this.isOutside);
        }
    }

    /**
     * 初始化PopWindow
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
        popupWindow = new PopupWindow();
        popupWindow.setWidth(width);
        popupWindow.setFocusable(isFocus);
        popupWindow.setOutsideTouchable(isOutside);
        popupWindow.setHeight(height);
        popupWindow.setContentView(popView);
        if (isSetAnim && 0 != anim) {
            popupWindow.setAnimationStyle(anim);
        }
        if (null == background) {
            background = new ColorDrawable(context.getResources().getColor(R.color.transparent));
        }
        popupWindow.setBackgroundDrawable(background);
        if (isSystemPopWindow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                popupWindow.setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                popupWindow.setWindowLayoutType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }
        }
    }

    /**
     * 显示
     *
     * @param view     the view
     * @param location the location
     * @param x        the x
     * @param y        the y
     */
    public void showLocation(View view, int location, int x, int y) {
        popupWindow.showAtLocation(view, location, x, y);
    }

    /**
     * 显示
     *
     * @param view the view
     */
    public void showDropDown(View view) {
        popupWindow.showAsDropDown(view);
    }

    /**
     * 显示
     *
     * @param view the view
     * @param x    the x
     * @param y    the y
     */
    public void showDropDown(View view, int x, int y) {
        popupWindow.showAsDropDown(view, x, y);
    }

    /**
     * 显示
     *
     * @param view     the view
     * @param x        the x
     * @param y        the y
     * @param location the location
     */
    public void showDropDown(View view, int x, int y, int location) {
        popupWindow.showAsDropDown(view, x, y, location);
    }

    /**
     * 关闭
     */
    public void dismiss() {
        popupWindow.dismiss();
    }

    /**
     * 获取PopWindow对象
     *
     * @return the pop window
     */
    public PopupWindow getPopWindow() {
        return popupWindow;
    }

    /**
     * 是否显示
     *
     * @return the boolean
     */
    public boolean isShowing() {
        return popupWindow.isShowing();
    }
}
