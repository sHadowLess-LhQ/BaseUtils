package cn.com.shadowless.baseutils.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.List;


/**
 * The type Base accessibility service.
 *
 * @author sHadowLess
 */
public abstract class BaseAccessibilityService extends AccessibilityService {

    /**
     * The constant mService.
     */
    private static BaseAccessibilityService mService = null;
    /**
     * The M handler.
     */
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @RequiresApi(api = Build.VERSION_CODES.R)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("TAG", "handleMessage: ");
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
                    clickTextViewByText((String) msg.obj);
                    break;
                case 11:
                    clickTextViewById((String) msg.obj);
                    break;
                case 12:
                    String[] inputText = ((String) msg.obj).split(",");
                    inputTextView(inputText[0], inputText[1]);
                    break;
                case 13:
                    String[] shortClick = ((String) msg.obj).split(",");
                    dispatchGestureClick(Integer.parseInt(shortClick[0]), Integer.parseInt(shortClick[1]));
                    break;
                case 14:
                    String[] longClick = ((String) msg.obj).split(",");
                    dispatchGestureLongClick(Integer.parseInt(longClick[0]), Integer.parseInt(longClick[1]));
                    break;
                case 15:
                    longClickTextViewByText((String) msg.obj);
                    break;
                case 16:
                    longClickTextViewById((String) msg.obj);
                    break;
                case 17:
                    String[] inputId = ((String) msg.obj).split(",");
                    inputIdView(inputId[0], inputId[1]);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        onRunning(event);
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
        return super.onUnbind(intent);
    }

    /**
     * On connected.
     */
    public abstract void onConnected();

    /**
     * On running.
     *
     * @param event the event
     */
    public abstract void onRunning(AccessibilityEvent event);

    /**
     * Gets service.
     *
     * @return the service
     */
    public static BaseAccessibilityService getService() {
        return mService;
    }

    /**
     * Find view by text un clickable accessibility node info.
     *
     * @param text the text
     * @return the accessibility node info
     */
    public AccessibilityNodeInfo findViewByTextUnClickable(String text) {
        return findViewByText(text, false);
    }

    /**
     * Find view by text clickable accessibility node info.
     *
     * @param text the text
     * @return the accessibility node info
     */
    public AccessibilityNodeInfo findViewByTextClickable(String text) {
        return findViewByText(text, true);
    }

    /**
     * Find view by text accessibility node info.
     *
     * @param text      the text
     * @param clickable the clickable
     * @return the accessibility node info
     */
    private AccessibilityNodeInfo findViewByText(String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    /**
     * Find view by text un clickable accessibility node info.
     *
     * @param id the id
     * @return the accessibility node info
     */
    public AccessibilityNodeInfo findViewByIdUnClickable(String id) {
        return findViewById(id, false);
    }

    /**
     * Find view by text clickable accessibility node info.
     *
     * @param id the id
     * @return the accessibility node info
     */
    public AccessibilityNodeInfo findViewByIdClickable(String id) {
        return findViewById(id, true);
    }

    /**
     * Find view by id accessibility node info.
     *
     * @param id        the id
     * @param clickable the clickable
     * @return the accessibility node info
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private AccessibilityNodeInfo findViewById(String id, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    /**
     * Perform view click.
     *
     * @param nodeInfo the node info
     */
    public void performViewClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
    }

    /**
     * Perform view long click.
     *
     * @param nodeInfo the node info
     */
    public void performViewLongClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
    }

    /**
     * Perform back click.
     */
    public void performBackClick() {
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    /**
     * Perform back click.
     *
     * @param milliSecond the milli second
     */
    public void performBackClick(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(1, milliSecond);
    }

    /**
     * Perform split screen.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performSplitScreen() {
        performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }

    /**
     * Perform split screen.
     *
     * @param milliSecond the milli second
     */
    public void performSplitScreen(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(2, milliSecond);
    }

    /**
     * Perform take screen shot.
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performTakeScreenShot() {
        performGlobalAction(GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * Perform take screen shot.
     *
     * @param milliSecond the milli second
     */
    public void performTakeScreenShot(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(3, milliSecond);
    }

    /**
     * Perform recent.
     */
    public void performRecent() {
        performGlobalAction(GLOBAL_ACTION_RECENTS);
    }

    /**
     * Perform recent.
     *
     * @param milliSecond the milli second
     */
    public void performRecent(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(4, milliSecond);
    }

    /**
     * Perform child statue bar.
     */
    public void performChildStatueBar() {
        performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * Perform child statue bar.
     *
     * @param milliSecond the milli second
     */
    public void performChildStatueBar(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(5, milliSecond);
    }

    /**
     * Perform power dialog.
     */
    public void performPowerDialog() {
        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * Perform power dialog.
     *
     * @param milliSecond the milli second
     */
    public void performPowerDialog(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(6, milliSecond);
    }

    /**
     * Perform statue bar.
     */
    public void performStatueBar() {
        performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * Perform statue bar.
     *
     * @param milliSecond the milli second
     */
    public void performStatueBar(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(7, milliSecond);
    }

    /**
     * Perform back home.
     */
    public void performBackHomeClick() {
        performGlobalAction(GLOBAL_ACTION_HOME);
    }

    /**
     * Perform back home.
     *
     * @param milliSecond the milli second
     */
    public void performBackHomeClick(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(8, milliSecond);
    }

    /**
     * Perform lock screen.
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performLockScreen() {
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * Perform lock screen.
     *
     * @param milliSecond the milli second
     */
    public void performLockScreen(int milliSecond) {
        mHandler.sendEmptyMessageDelayed(9, milliSecond);
    }

    /**
     * Click text view by text.
     *
     * @param text the text
     */
    public void clickTextViewByText(String text) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
        accessibilityNodeInfo.recycle();
    }

    /**
     * Click text view by text.
     *
     * @param text        the text
     * @param milliSecond the milli second
     */
    public void clickTextViewByText(String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 10;
        msg.obj = text;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Long click text view by text.
     *
     * @param text the text
     */
    public void longClickTextViewByText(String text) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
        accessibilityNodeInfo.recycle();
    }

    /**
     * Long click text view by text.
     *
     * @param text        the text
     * @param milliSecond the milli second
     */
    public void longClickTextViewByText(String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 15;
        msg.obj = text;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Click text view by id.
     *
     * @param id the id
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void clickTextViewById(String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
        accessibilityNodeInfo.recycle();
    }

    /**
     * Click text view by id.
     *
     * @param id          the id
     * @param milliSecond the milli second
     */
    public void clickTextViewById(String id, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 16;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Long click text view by id.
     *
     * @param id the id
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void longClickTextViewById(String id) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewLongClick(nodeInfo);
                    break;
                }
            }
        }
        accessibilityNodeInfo.recycle();
    }

    /**
     * Long click text view by id.
     *
     * @param id          the id
     * @param milliSecond the milli second
     */
    public void longClickTextViewById(String id, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 11;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Input text.
     *
     * @param view the view
     * @param text the text
     */
    public void inputTextView(String view, String text) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        findViewByTextClickable(view).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    /**
     * Input text.
     *
     * @param view        the view
     * @param text        the text
     * @param milliSecond the milli second
     */
    public void inputTextView(String view, String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 12;
        msg.obj = view + "," + text;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Input text.
     *
     * @param id   the id
     * @param text the text
     */
    public void inputIdView(String id, String text) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        findViewByIdClickable(id).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    /**
     * Input text.
     *
     * @param id          the id
     * @param text        the text
     * @param milliSecond the milli second
     */
    public void inputIdView(String id, String text, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 17;
        msg.obj = id + "," + text;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Dispatch gesture click.
     *
     * @param x the x
     * @param y the y
     */
    @RequiresApi(24)
    public void dispatchGestureClick(int x, int y) {
        Path path = new Path();
        path.moveTo(x - 1, y - 1);
        path.lineTo(x + 1, y + 1);
        dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                (path, 0, 100)).build(), null, null);
    }

    /**
     * Dispatch gesture click.
     *
     * @param x           the x
     * @param y           the y
     * @param milliSecond the milli second
     */
    public void dispatchGestureClick(int x, int y, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 12;
        msg.obj = x + "," + y;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Dispatch gesture long click.
     *
     * @param x the x
     * @param y the y
     */
    @RequiresApi(24)
    public void dispatchGestureLongClick(int x, int y) {
        Path path = new Path();
        path.moveTo(x - 1, y - 1);
        path.lineTo(x, y - 1);
        path.lineTo(x, y);
        path.lineTo(x - 1, y);
        dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                (path, 0, 1500)).build(), null, null);
    }

    /**
     * Dispatch gesture long click.
     *
     * @param x           the x
     * @param y           the y
     * @param milliSecond the milli second
     */
    public void dispatchGestureLongClick(int x, int y, int milliSecond) {
        Message msg = Message.obtain(mHandler);
        msg.what = 13;
        msg.obj = x + "," + y;
        mHandler.sendMessageDelayed(msg, milliSecond);
    }

    /**
     * Continue swipe.
     *
     * @param x             the x
     * @param y             the y
     * @param swipeDuration the swipe duration
     * @param stepDuration  the step duration
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void continueSwipe(int[] x, int[] y, int swipeDuration, int stepDuration) {
        if (x.length != y.length) {
            return;
        }
        Path path = new Path();
        for (int i = 0; i < x.length; i++) {
            if (i + 1 == x.length) {
                return;
            }
            path.reset();
            path.moveTo(x[i], y[i]);
            path.lineTo(x[i + 1], y[i + 1]);
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
    }
}
