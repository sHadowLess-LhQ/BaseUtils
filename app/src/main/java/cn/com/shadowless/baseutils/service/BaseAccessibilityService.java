package cn.com.shadowless.baseutils.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;


/**
 * 辅助服务
 *
 * @author sHadowLess
 */
public abstract class BaseAccessibilityService extends AccessibilityService {

    /**
     * 服务对象实例
     */
    private static BaseAccessibilityService mService = null;
    /**
     * 事件处理
     */
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @RequiresApi(api = Build.VERSION_CODES.R)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    performBackClick();
                    break;
                case 2:
                    performSplitScreen();
                    break;
                case 3:
                    performTakeScreenShot();
                    break;
                case 4:
                    performRecent();
                    break;
                case 5:
                    performChildStatueBar();
                    break;
                case 6:
                    performPowerDialog();
                    break;
                case 7:
                    performStatueBar();
                    break;
                case 8:
                    performBackHomeClick();
                    break;
                case 9:
                    performLockScreen();
                    break;
                case 10:
                    clickViewByText((String) msg.obj);
                    break;
                case 11:
                    clickViewById((String) msg.obj);
                    break;
                case 12:
                    String[] inputText = ((String) msg.obj).split(",");
                    inputTextToEtView(inputText[0], inputText[1]);
                    break;
                case 13:
                    String[] shortClick = ((String) msg.obj).split(",");
                    dispatchGestureClick(Integer.parseInt(shortClick[0]), Integer.parseInt(shortClick[1]));
                    break;
                case 14:
                    String[] longClick = ((String) msg.obj).split(",");
                    dispatchGestureLongClick(Integer.parseInt(longClick[0]), Integer.parseInt(longClick[1]), Integer.parseInt(longClick[2]));
                    break;
                case 15:
                    longClickViewByText((String) msg.obj);
                    break;
                case 16:
                    longClickViewById((String) msg.obj);
                    break;
                case 17:
                    String[] inputId = ((String) msg.obj).split(",");
                    inputIdToEtView(inputId[0], inputId[1]);
                    break;
                case 18:
                    AccessibilityNodeInfo clickInfo = (AccessibilityNodeInfo) msg.obj;
                    performViewClick(clickInfo);
                    break;
                case 19:
                    AccessibilityNodeInfo longClickInfo = (AccessibilityNodeInfo) msg.obj;
                    performViewLongClick(longClickInfo);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                //界面点击
                viewClickListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                //界面长按
                viewLongClickListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                //界面选择
                viewSelectListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                //界面聚焦
                viewFocusListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                //编辑框文本改变
                etTextChangedListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                //编辑框选中文本改变
                etSelectedTextChangedListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                //界面文滚动
                viewScrollListener(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                //窗口状态改变（Dialog、PopWindow）
                windowStatusChangedListener(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                //窗口内容改变
                windowContentChangedListener(event);
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                //窗口变化
                windowChangedListener(event);
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                //通知栏
                notificationStatusChangedListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                //界面悬停进入
                viewHoverEnterListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                //界面悬停退出
                viewHoverExitListener(event);
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                //触摸交互开始
                touchInteractionStartedListener(event);
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                //触摸交互结束
                touchInteractionEndedListener(event);
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                //触摸浏览手势开始
                touchExploredStartedListener(event);
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                //触摸浏览手势结束
                touchExploredEndedListener(event);
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                //触摸手势检测开始
                gestureDetectedStartListener(event);
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                //触摸手势检测结束
                gestureDetectedEndListener(event);
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                gestureAnnouncementListener(event);
                break;
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:
                gestureAssistReadingContextListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                gestureAccessibilityFocusedListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                gestureAccessibilityFocusClearedListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
                gestureContextClickedListener(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                gestureTextTraversedAtMovementGranularityListener(event);
                break;
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {
        mService = null;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mService = this;
        onConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mService = null;
        onDisConnected();
        return super.onUnbind(intent);
    }

    /**
     * 服务启动
     */
    protected abstract void onConnected();

    /**
     * 服务解绑
     */
    protected abstract void onDisConnected();

    /**
     * 界面点击
     *
     * @param event the event
     */
    protected abstract void viewClickListener(AccessibilityEvent event);

    /**
     * 界面长按
     *
     * @param event the event
     */
    protected abstract void viewLongClickListener(AccessibilityEvent event);

    /**
     * 界面选择
     *
     * @param event the event
     */
    protected abstract void viewSelectListener(AccessibilityEvent event);

    /**
     * 界面聚焦
     *
     * @param event the event
     */
    protected abstract void viewFocusListener(AccessibilityEvent event);

    /**
     * 编辑框文本改变
     *
     * @param event the event
     */
    protected abstract void etTextChangedListener(AccessibilityEvent event);

    /**
     * 编辑框选中文本改变
     *
     * @param event the event
     */
    protected abstract void etSelectedTextChangedListener(AccessibilityEvent event);

    /**
     * 界面文滚动
     *
     * @param event the event
     */
    protected abstract void viewScrollListener(AccessibilityEvent event);

    /**
     * 窗口状态改变（Dialog、PopWindow）
     *
     * @param event the event
     */
    protected abstract void windowStatusChangedListener(AccessibilityEvent event);

    /**
     * 窗口内容改变
     *
     * @param event the event
     */
    protected abstract void windowContentChangedListener(AccessibilityEvent event);

    /**
     * 窗口变化
     *
     * @param event the event
     */
    protected abstract void windowChangedListener(AccessibilityEvent event);

    /**
     * 通知栏
     *
     * @param event the event
     */
    protected abstract void notificationStatusChangedListener(AccessibilityEvent event);

    /**
     * 界面悬停进入
     *
     * @param event the event
     */
    protected abstract void viewHoverEnterListener(AccessibilityEvent event);

    /**
     * 界面悬停退出
     *
     * @param event the event
     */
    protected abstract void viewHoverExitListener(AccessibilityEvent event);

    /**
     * 触摸交互开始
     *
     * @param event the event
     */
    protected abstract void touchInteractionStartedListener(AccessibilityEvent event);

    /**
     * 触摸交互结束
     *
     * @param event the event
     */
    protected abstract void touchInteractionEndedListener(AccessibilityEvent event);

    /**
     * 触摸浏览手势开始
     *
     * @param event the event
     */
    protected abstract void touchExploredStartedListener(AccessibilityEvent event);

    /**
     * 触摸浏览手势结束
     *
     * @param event the event
     */
    protected abstract void touchExploredEndedListener(AccessibilityEvent event);

    /**
     * 触摸手势检测开始
     *
     * @param event the event
     */
    protected abstract void gestureDetectedStartListener(AccessibilityEvent event);

    /**
     * 触摸手势检测结束
     *
     * @param event the event
     */
    protected abstract void gestureDetectedEndListener(AccessibilityEvent event);

    /**
     * 公布公告
     *
     * @param event the event
     */
    protected abstract void gestureAnnouncementListener(AccessibilityEvent event);

    /**
     * 屏幕阅读
     *
     * @param event the event
     */
    protected abstract void gestureAssistReadingContextListener(AccessibilityEvent event);

    /**
     * 获取焦点
     *
     * @param event the event
     */
    protected abstract void gestureAccessibilityFocusedListener(AccessibilityEvent event);

    /**
     * 焦点清除
     *
     * @param event the event
     */
    protected abstract void gestureAccessibilityFocusClearedListener(AccessibilityEvent event);

    /**
     * 上下文点击
     *
     * @param event the event
     */
    protected abstract void gestureContextClickedListener(AccessibilityEvent event);

    /**
     * 移动粒度遍历文本
     *
     * @param event the event
     */
    protected abstract void gestureTextTraversedAtMovementGranularityListener(AccessibilityEvent event);

    /**
     * 获取服务对象实例
     *
     * @return the service
     */
    public static BaseAccessibilityService getService() {
        return mService;
    }

    /**
     * 查找指定文本不可点击可访问节点信息
     *
     * @param text the 文本
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByTextUnClickable(String text) {
        return findViewByText(text, false);
    }

    /**
     * 延迟查找指定文本不可点击可访问节点信息
     *
     * @param text        the 文本
     * @param milliSecond the milli second
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByTextUnClickable(String text, int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return findViewByText(text, false);
    }

    /**
     * 查找指定文本可点击可访问节点信息
     *
     * @param text the 文本
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByTextClickable(String text) {
        return findViewByText(text, true);
    }

    /**
     * 延迟查找指定文本可点击可访问节点信息
     *
     * @param text        the 文本
     * @param milliSecond the milli second
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByTextClickable(String text, int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return findViewByText(text, true);
    }

    /**
     * 查找指定文本的可访问节点信息
     *
     * @param text      the 文本
     * @param clickable the 是否可点击
     * @return the 可访问节点信息
     */
    private AccessibilityNodeInfo findViewByText(String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                    if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                        return nodeInfo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 查找指定id不可点击的可访问节点信息
     *
     * @param id the 视图id
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByIdUnClickable(String id) {
        return findViewById(id, false);
    }

    /**
     * 延迟查找指定id不可点击的可访问节点信息
     *
     * @param id          the 视图id
     * @param milliSecond the milli second
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByIdUnClickable(String id, int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return findViewById(id, false);
    }

    /**
     * 查找指定id可点击的可访问节点信息
     *
     * @param id the 视图id
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByIdClickable(String id) {
        return findViewById(id, true);
    }

    /**
     * 延迟查找指定id可点击的可访问节点信息
     *
     * @param id          the 视图id
     * @param milliSecond the milli second
     * @return the 可访问节点信息
     */
    public AccessibilityNodeInfo findViewByIdClickable(String id, int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return findViewById(id, true);
    }

    /**
     * 查找指定id的可访问节点信息
     *
     * @param id        the 视图id
     * @param clickable the 是否可点击
     * @return the 可访问节点信息
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private AccessibilityNodeInfo findViewById(String id, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                    if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                        return nodeInfo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 单击视图
     *
     * @param nodeInfo the 可访问节点信息
     * @return the base accessibility service
     */
    public BaseAccessibilityService performViewClick(AccessibilityNodeInfo nodeInfo) {
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
        if (nodeInfo != null) {
            nodeInfo.recycle();
        }
        return this;
    }

    /**
     * 延迟单击视图
     *
     * @param nodeInfo    the node info
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performViewClick(AccessibilityNodeInfo nodeInfo, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 18;
        msg.obj = nodeInfo;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 长按视图
     *
     * @param nodeInfo the 可访问节点信息
     * @return the base accessibility service
     */
    public BaseAccessibilityService performViewLongClick(AccessibilityNodeInfo nodeInfo) {
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
        if (nodeInfo != null) {
            nodeInfo.recycle();
        }
        return this;
    }

    /**
     * 延迟长按视图
     *
     * @param nodeInfo    the node info
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performViewLongClick(AccessibilityNodeInfo nodeInfo, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 19;
        msg.obj = nodeInfo;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 单击返回键
     *
     * @return the base accessibility service
     */
    public BaseAccessibilityService performBackClick() {
        performGlobalAction(GLOBAL_ACTION_BACK);
        return this;
    }

    /**
     * 延迟单击返回键
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performBackClick(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(1, milliSecond);
        return this;
    }

    /**
     * 分屏
     *
     * @return the base accessibility service
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BaseAccessibilityService performSplitScreen() {
        performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
        return this;
    }

    /**
     * 延迟分屏
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performSplitScreen(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(2, milliSecond);
        return this;
    }

    /**
     * 截屏
     *
     * @return the base accessibility service
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public BaseAccessibilityService performTakeScreenShot() {
        performGlobalAction(GLOBAL_ACTION_TAKE_SCREENSHOT);
        return this;
    }

    /**
     * 延迟截屏
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performTakeScreenShot(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(3, milliSecond);
        return this;
    }

    /**
     * 任务视图
     *
     * @return the base accessibility service
     */
    public BaseAccessibilityService performRecent() {
        performGlobalAction(GLOBAL_ACTION_RECENTS);
        return this;
    }

    /**
     * 延迟任务视图
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performRecent(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(4, milliSecond);
        return this;
    }

    /**
     * 下拉二级状态栏
     *
     * @return the base accessibility service
     */
    public BaseAccessibilityService performChildStatueBar() {
        performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS);
        return this;
    }

    /**
     * 延迟下拉二级状态栏
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performChildStatueBar(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(5, milliSecond);
        return this;
    }

    /**
     * 电源管理框
     *
     * @return the base accessibility service
     */
    public BaseAccessibilityService performPowerDialog() {
        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
        return this;
    }

    /**
     * 延迟打开电源管理框
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performPowerDialog(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(6, milliSecond);
        return this;
    }

    /**
     * 下拉状态栏
     *
     * @return the base accessibility service
     */
    public BaseAccessibilityService performStatueBar() {
        performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
        return this;
    }

    /**
     * 延迟下拉状态栏
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performStatueBar(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(7, milliSecond);
        return this;
    }

    /**
     * 单击home键
     *
     * @return the base accessibility service
     */
    public BaseAccessibilityService performBackHomeClick() {
        performGlobalAction(GLOBAL_ACTION_HOME);
        return this;
    }

    /**
     * 延迟单击home键
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performBackHomeClick(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(8, milliSecond);
        return this;
    }

    /**
     * 锁屏
     *
     * @return the base accessibility service
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public BaseAccessibilityService performLockScreen() {
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
        return this;
    }

    /**
     * 延迟锁屏
     *
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService performLockScreen(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(9, milliSecond);
        return this;
    }

    /**
     * 单击指定文本视图
     *
     * @param text the text
     * @return the base accessibility service
     */
    public BaseAccessibilityService clickViewByText(String text) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                    if (nodeInfo != null) {
                        CharSequence nodeText = nodeInfo.getText();
                        if (null != nodeText && nodeText.toString().equals(text)) {
                            performViewClick(nodeInfo);
                        } else {
                            CharSequence content = nodeInfo.getContentDescription();
                            if (null != content && content.toString().equals(text)) {
                                performViewClick(nodeInfo);
                            }
                        }
                        performViewClick(nodeInfo);
                        break;
                    }
                }
            }
        }
        return this;
    }

    /**
     * 延迟单击指定文本视图
     *
     * @param text        the text
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService clickViewByText(String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 10;
        msg.obj = text;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 长按指定文本视图
     *
     * @param text the text
     * @return the base accessibility service
     */
    public BaseAccessibilityService longClickViewByText(String text) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                    if (nodeInfo != null) {
                        CharSequence nodeText = nodeInfo.getText();
                        if (null != nodeText && nodeText.toString().equals(text)) {
                            performViewLongClick(nodeInfo);
                        } else {
                            CharSequence content = nodeInfo.getContentDescription();
                            if (null != content && content.toString().equals(text)) {
                                performViewLongClick(nodeInfo);
                            }
                        }
                        performViewLongClick(nodeInfo);
                        break;
                    }
                }
            }
        }
        return this;
    }

    /**
     * 延迟长按指定文本视图
     *
     * @param text        the text
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService longClickViewByText(String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 15;
        msg.obj = text;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 单击指定id视图
     *
     * @param id the id
     * @return the base accessibility service
     */
    public BaseAccessibilityService clickViewById(String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                    if (nodeInfo != null) {
                        String nodeId = nodeInfo.getViewIdResourceName();
                        if (null != nodeId && nodeId.equals(id)) {
                            performViewClick(nodeInfo);
                        } else {
                            CharSequence content = nodeInfo.getContentDescription();
                            if (null != content && content.toString().equals(id)) {
                                performViewClick(nodeInfo);
                            }
                        }
                        performViewClick(nodeInfo);
                        break;
                    }
                }
            }
        }
        return this;
    }

    /**
     * 延迟单击指定id视图
     *
     * @param id          the id
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService clickViewById(String id, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 16;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 长按指定id视图
     *
     * @param id the id
     * @return the base accessibility service
     */
    public BaseAccessibilityService longClickViewById(String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return this;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    String nodeId = nodeInfo.getViewIdResourceName();
                    if (null != nodeId && nodeId.equals(id)) {
                        performViewLongClick(nodeInfo);
                    } else {
                        CharSequence content = nodeInfo.getContentDescription();
                        if (null != content && content.toString().equals(id)) {
                            performViewLongClick(nodeInfo);
                        }
                    }
                    performViewLongClick(nodeInfo);
                    break;
                }
            }
        }
        return this;
    }

    /**
     * 延迟长按指定id视图
     *
     * @param id          the id
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService longClickViewById(String id, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 11;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 输入指定文本到指定文本编辑框
     *
     * @param view the view
     * @param text the text
     * @return the base accessibility service
     */
    public BaseAccessibilityService inputTextToEtView(String view, String text) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        findViewByTextClickable(view).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        return this;
    }

    /**
     * 延迟输入指定文本到指定文本编辑框
     *
     * @param view        the view
     * @param text        the text
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService inputTextToEtView(String view, String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 12;
        msg.obj = view + "," + text;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 输入指定文本到指定id编辑框
     *
     * @param id   the id
     * @param text the text
     * @return the base accessibility service
     */
    public BaseAccessibilityService inputIdToEtView(String id, String text) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        findViewByIdClickable(id).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        return this;
    }

    /**
     * 延迟输入指定文本到指定id编辑框
     *
     * @param id          the id
     * @param text        the text
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService inputIdToEtView(String id, String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 17;
        msg.obj = id + "," + text;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 单击指定坐标
     *
     * @param x the x
     * @param y the y
     * @return the base accessibility service
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BaseAccessibilityService dispatchGestureClick(int x, int y) {
        Path path = new Path();
        path.moveTo(x - 1, y - 1);
        path.lineTo(x + 1, y + 1);
        dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                (path, 0, 300)).build(), null, null);
        return this;
    }

    /**
     * 延迟单击指定坐标
     *
     * @param x           the x
     * @param y           the y
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService dispatchGestureClick(int x, int y, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 13;
        msg.obj = x + "," + y;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 长按指定坐标
     *
     * @param x     the x
     * @param y     the y
     * @param delay the delay
     * @return the base accessibility service
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BaseAccessibilityService dispatchGestureLongClick(int x, int y, int delay) {
        Path path = new Path();
        path.moveTo(x - 1, y - 1);
        path.lineTo(x, y - 1);
        path.lineTo(x, y);
        path.lineTo(x - 1, y);
        dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                (path, 0, delay)).build(), null, null);
        return this;
    }

    /**
     * 延迟长按指定坐标
     *
     * @param x           the x
     * @param y           the y
     * @param delay       the delay
     * @param milliSecond the milli second
     * @return the base accessibility service
     */
    public BaseAccessibilityService dispatchGestureLongClick(int x, int y, int delay, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 14;
        msg.obj = x + "," + y + "," + delay;
        mHandler.sendMessageDelayed(msg, milliSecond);
        return this;
    }

    /**
     * 连续滑动
     *
     * @param x             the x
     * @param y             the y
     * @param swipeDuration the swipe duration
     * @param stepDuration  the step duration
     * @return the base accessibility service
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BaseAccessibilityService continueSwipe(List<Integer> x, List<Integer> y, int swipeDuration, int stepDuration) {
        if (x.size() != y.size()) {
            return this;
        }
        Path path = new Path();
        for (int i = 0; i < x.size(); i++) {
            if (i + 1 == x.size()) {
                return this;
            }
            path.reset();
            path.moveTo(x.get(i), y.get(i));
            path.lineTo(x.get(i + 1), y.get(i + 1));
            dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                    (path, 0, swipeDuration)).build(), new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    try {
                        Thread.sleep(stepDuration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }
        return this;
    }

    /**
     * 判断辅助服务是否开启
     *
     * @param mContext the 上下文
     * @param cls      the 辅助服务类
     * @return the boolean
     */
    public static boolean isAccessibilitySettingsOn(Context mContext, Class<?> cls) {
        try {
            String service = mContext.getPackageName() + "/" + cls.getCanonicalName();
            int accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(
                        mContext.getApplicationContext().getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue);
                    while (mStringColonSplitter.hasNext()) {
                        String accessibilityService = mStringColonSplitter.next();
                        if (accessibilityService.equalsIgnoreCase(service)) {
                            return true;
                        }
                    }
                }
            }
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
