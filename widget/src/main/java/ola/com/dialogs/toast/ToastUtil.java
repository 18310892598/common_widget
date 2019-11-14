package ola.com.dialogs.toast;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * @author zhangzheng
 * @Date 2019-07-24 11:25
 * @ClassName ToastUtil
 * <p>
 * Desc :fix Android7.1 上的 BadTokenException 异常
 */
public class ToastUtil {

    private static final String TAG = "TAG";
    private static Toast mToast;
    private static Field sField_TN;
    private static Field sField_TN_Handler;
    private static boolean sIsHookFieldInit = false;
    private static final String FIELD_NAME_TN = "mTN";
    private static final String FIELD_NAME_HANDLER = "mHandler";

    /**
     * 显示Toast
     *
     * @param context  context，Application or Activity
     * @param text     the text show on the Toast
     * @param duration Toast.LENGTH_SHORT（default,2s） or Toast.LENGTH_LONG（3.5s）
     * @version
     * @date 2019-07-24 11:46
     * @author zhangzheng
     */
    public static void showToast(final Context context, final CharSequence text, final int duration) {
        ToastRunnable toastRunnable = new ToastRunnable(context, text, duration);
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(toastRunnable);
            }
        } else {
            Handler handler = new Handler(context.getMainLooper());
            handler.post(toastRunnable);
        }
    }

    /**
     * 显示Toast 默认时间为Toast.LENGTH_SHORT（default,2s）
     *
     * @param context context，Application or Activity
     * @param text    the text show on the Toast
     * @version
     * @date 2019-07-24 11:46
     * @author zhangzheng
     */
    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 关闭toast
     */
    public static void cancelToast() {
        Looper looper = Looper.getMainLooper();
        if (looper.getThread() == Thread.currentThread()) {
            mToast.cancel();
        } else {
            new Handler(looper).post(new Runnable() {
                @Override
                public void run() {
                    mToast.cancel();
                }
            });
        }
    }

    /**
     * @author zhangzheng
     * @date 2019-07-24 11:52
     */
    private static class ToastRunnable implements Runnable {
        private Context context;
        private CharSequence text;
        private int duration;

        public ToastRunnable(Context context, CharSequence text, int duration) {
            this.context = context;
            this.text = text;
            this.duration = duration;
        }

        @Override
        public void run() {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration);
            } else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            hookToast(mToast);
            mToast.show();
        }
    }

    /**
     * 拦截异常
     * 通过反射注入自己的handler
     *
     * @param toast
     * @version
     * @date 2019-07-24 11:48
     * @author zhangzheng
     */
    private static void hookToast(Toast toast) {
        if (isNeedHook()) {
            try {
                if (!sIsHookFieldInit) {
                    sField_TN = Toast.class.getDeclaredField(FIELD_NAME_TN);
                    sField_TN.setAccessible(true);
                    sField_TN_Handler = sField_TN.getType().getDeclaredField(FIELD_NAME_HANDLER);
                    sField_TN_Handler.setAccessible(true);
                    sIsHookFieldInit = true;
                }
                Object tn = sField_TN.get(toast);
                Handler originHandler = (Handler) sField_TN_Handler.get(tn);
                sField_TN_Handler.set(tn, new RepairHandler(originHandler));
            } catch (Exception e) {
                Log.e(TAG, "Hook toast exception=" + e);
            }
        }
    }


    /**
     * 修复后的Handler，拦截BadTokenException
     *
     * @author zhangzheng
     * @date 2019-07-24 11:49
     */
    private static class RepairHandler extends Handler {
        private Handler originHandler;

        public RepairHandler(Handler originHandler) {
            this.originHandler = originHandler;
        }

        @Override
        public void dispatchMessage(Message msg) {
            //在这里来拦截BadTokenException
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                Log.e(TAG, "Catch system toast exception:" + e);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            if (originHandler != null) {
                originHandler.handleMessage(msg);
            }
        }
    }


    /**
     * 判断当前版本是否需要Hook
     *
     * @return 版本判断结果
     */
    private static boolean isNeedHook() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 ||
                Build.VERSION.SDK_INT == Build.VERSION_CODES.N;
    }

}
