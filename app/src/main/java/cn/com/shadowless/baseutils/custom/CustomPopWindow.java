package cn.com.shadowless.baseutils.custom;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
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
     * popWindow对象
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
     */
    public CustomPopWindow(View popView, Context context, int layout, boolean isSetAnim, int anim, Drawable background, boolean isSystemPopWindow) {
        this.popView = popView;
        this.context = context;
        this.layout = layout;
        this.isSetAnim = isSetAnim;
        this.anim = anim;
        this.background = background;
        this.isSystemPopWindow = isSystemPopWindow;
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
            return new CustomPopWindow(this.popView, this.context, this.layout, this.isSetAnim, this.anim, this.background, this.isSystemPopWindow);
        }

        @Override
        public String toString() {
            return "CustomPopWindowBuilder{" +
                    "popView=" + popView +
                    ", context=" + context +
                    ", layout=" + layout +
                    ", isSetAnim=" + isSetAnim +
                    ", anim=" + anim +
                    ", background=" + background +
                    ", isSystemPopWindow=" + isSystemPopWindow +
                    '}';
        }
    }

    /**
     * Show.
     *
     * @param view the view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDropDown(View view) {
        if (null == popView) {
            popView = LayoutInflater.from(context).inflate(layout, null);
        }
        popupWindow = new PopupWindow();
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
        popupWindow.showAsDropDown(view);
    }

    /**
     * Show drop down.
     *
     * @param view the view
     * @param x    the x
     * @param y    the y
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDropDown(View view, int x, int y) {
        if (null == popView) {
            popView = LayoutInflater.from(context).inflate(layout, null);
        }
        popupWindow = new PopupWindow();
        popupWindow.setContentView(popView);
        if (isSetAnim) {
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
        popupWindow.showAsDropDown(view, x, y);
    }

    /**
     * Show drop down.
     *
     * @param view     the view
     * @param x        the x
     * @param y        the y
     * @param location the location
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDropDown(View view, int x, int y, int location) {
        if (null == popView) {
            popView = LayoutInflater.from(context).inflate(layout, null);
        }
        popupWindow = new PopupWindow();
        popupWindow.setContentView(popView);
        if (isSetAnim) {
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
        popupWindow.showAsDropDown(view, x, y, location);
    }

    /**
     * Show location.
     *
     * @param view     the view
     * @param x        the x
     * @param y        the y
     * @param location the location
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showLocation(View view, int x, int y, int location) {
        if (null == popView) {
            popView = LayoutInflater.from(context).inflate(layout, null);
        }
        popupWindow = new PopupWindow();
        popupWindow.setContentView(popView);
        if (isSetAnim) {
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
        popupWindow.showAtLocation(view, location, x, y);
    }

    /**
     * Is show boolean.
     *
     * @return the boolean
     */
    public boolean isShow() {
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        popupWindow.dismiss();
        popupWindow = null;
    }
}
