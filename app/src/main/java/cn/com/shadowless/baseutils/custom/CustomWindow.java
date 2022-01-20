package cn.com.shadowless.baseutils.custom;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import lombok.Builder;

/**
 * 自定义窗口工具类
 *
 * @author sHadowLess
 */
public class CustomWindow {

    /**
     * 上下文
     */
    private Context context;
    /**
     * 是否全局显示
     */
    private boolean isSystem;
    /**
     * 是否设置动画
     */
    private boolean isAnimation;
    /**
     * 动画值
     */
    private int anim;
    /**
     * 窗口标志
     */
    private int windowFlag;
    /**
     * 显示位置
     */
    private int location;
    /**
     * 窗口宽度
     */
    private int width;
    /**
     * 窗口高度
     */
    private int height;
    /**
     * 自定义布局文件
     */
    private int layout;
    /**
     * 自定义视图
     */
    private View view;
    /**
     * 窗口生成坐标X
     */
    private int x;
    /**
     * 窗口生成坐标Y
     */
    private int y;
    /**
     * 窗口服务管理
     */
    private WindowManager windowManager;

    /**
     * 构造
     *
     * @param context     the context
     * @param isSystem    the is system
     * @param isAnimation the is animation
     * @param anim        the anim
     * @param windowFlag  the window flag
     * @param location    the location
     * @param width       the width
     * @param height      the height
     * @param layout      the layout
     * @param view        the view
     * @param x           the x
     * @param y           the y
     */
    public CustomWindow(Context context, boolean isSystem, boolean isAnimation, int anim, int windowFlag, int location, int width, int height, int layout, View view, int x, int y) {
        this.context = context;
        this.isSystem = isSystem;
        this.isAnimation = isAnimation;
        this.anim = anim;
        this.windowFlag = windowFlag;
        this.location = location;
        this.width = width;
        this.height = height;
        this.layout = layout;
        this.view = view;
        this.x = x;
        this.y = y;
    }

    /**
     * 构造者
     *
     * @return the custom window builder
     */
    public static CustomWindowBuilder builder() {
        return new CustomWindowBuilder();
    }

    /**
     * 构造者实体
     */
    public static class CustomWindowBuilder {
        /**
         * 上下文
         */
        private Context context;
        /**
         * 是否全局显示
         */
        private boolean isSystem;
        /**
         * 是否设置动画
         */
        private boolean isAnimation;
        /**
         * 动画值
         */
        private int anim;
        /**
         * 窗口标志
         */
        private int windowFlag;
        /**
         * 显示位置
         */
        private int location;
        /**
         * 窗口宽度
         */
        private int width;
        /**
         * 窗口高度
         */
        private int height;
        /**
         * 自定义布局文件
         */
        private int layout;
        /**
         * 自定义视图
         */
        private View view;
        /**
         * 窗口生成坐标X
         */
        private int x;
        /**
         * 窗口生成坐标Y
         */
        private int y;

        /**
         * Context custom window builder.
         *
         * @param context the context
         * @return the custom window builder
         */
        public CustomWindowBuilder context(Context context) {
            this.context = context;
            return this;
        }

        /**
         * Is system custom window builder.
         *
         * @param isSystem the is system
         * @return the custom window builder
         */
        public CustomWindowBuilder isSystem(boolean isSystem) {
            this.isSystem = isSystem;
            return this;
        }

        /**
         * Is animation custom window builder.
         *
         * @param isAnimation the is animation
         * @return the custom window builder
         */
        public CustomWindowBuilder isAnimation(boolean isAnimation) {
            this.isAnimation = isAnimation;
            return this;
        }

        /**
         * Anim custom window builder.
         *
         * @param anim the anim
         * @return the custom window builder
         */
        public CustomWindowBuilder anim(int anim) {
            this.anim = anim;
            return this;
        }

        /**
         * Window flag custom window builder.
         *
         * @param windowFlag the window flag
         * @return the custom window builder
         */
        public CustomWindowBuilder windowFlag(int windowFlag) {
            this.windowFlag = windowFlag;
            return this;
        }

        /**
         * Location custom window builder.
         *
         * @param location the location
         * @return the custom window builder
         */
        public CustomWindowBuilder location(int location) {
            this.location = location;
            return this;
        }

        /**
         * Width custom window builder.
         *
         * @param width the width
         * @return the custom window builder
         */
        public CustomWindowBuilder width(int width) {
            this.width = width;
            return this;
        }

        /**
         * Height custom window builder.
         *
         * @param height the height
         * @return the custom window builder
         */
        public CustomWindowBuilder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * Layout custom window builder.
         *
         * @param layout the layout
         * @return the custom window builder
         */
        public CustomWindowBuilder layout(int layout) {
            this.layout = layout;
            return this;
        }

        /**
         * Y custom window builder.
         *
         * @param y the y
         * @return the custom window builder
         */
        public CustomWindowBuilder y(int y) {
            this.y = y;
            return this;
        }

        /**
         * View custom window builder.
         *
         * @param view the view
         * @return the custom window builder
         */
        public CustomWindowBuilder view(View view) {
            this.view = view;
            return this;
        }

        /**
         * X custom window builder.
         *
         * @param x the x
         * @return the custom window builder
         */
        public CustomWindowBuilder x(int x) {
            this.x = x;
            return this;
        }

        /**
         * Build custom window.
         *
         * @return the custom window
         */
        public CustomWindow build() {
            return new CustomWindow(this.context, this.isSystem, this.isAnimation, this.anim, this.windowFlag, this.location, this.width, this.height, this.layout, this.view, this.x, this.y);
        }

    }

    /**
     * 显示
     */
    public void show() {

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        if (isAnimation && 0 != anim) {
            layoutParams.windowAnimations = anim;
        }

        if (isSystem) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = location;
        layoutParams.flags = windowFlag;
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.x = x;
        layoutParams.y = y;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(layout, null, false);
        }

        if (windowManager == null) {
            windowManager = getWindowManager();
        }
        windowManager.addView(view, layoutParams);
    }

    /**
     * Remove.
     */
    public void remove() {
        if (null != view) {
            if (null == windowManager) {
                getWindowManager().removeView(view);
            } else {
                windowManager.removeView(view);
            }
            view = null;
        }
    }

    /**
     * Is show boolean.
     *
     * @return the boolean
     */
    public boolean isShow() {
        return null != view;
    }

    /**
     * Gets window manager.
     *
     * @return the window manager
     */
    private WindowManager getWindowManager() {
        if (null == windowManager) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }
}
