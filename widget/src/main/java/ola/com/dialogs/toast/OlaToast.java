package ola.com.dialogs.toast;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by Wyman on 15/12/31.
 * update：添加系统版本的判断，7.0 & 7.1版本使用ToastUtil规避 BadTokenException 异常
 */
public class OlaToast {
    /**
     * 是否显示Toast,可在application初始化，默认为显示--true
     */
    public static boolean isShow = true;

    private OlaToast() {
        /** cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast str
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, message, Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 短时间显示Toast int
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, context.getResources().getText(message), Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 长时间显示Toast str
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, message, Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 长时间显示Toast int
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, context.getResources().getText(message), Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义显示Toast时间 str
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showCustom(Context context, CharSequence message, int duration) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, message, duration);
                } else {
                    Toast.makeText(context, message, duration).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义显示Toast时间 int
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showCustom(Context context, int message, int duration) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, context.getResources().getText(message), duration);
                } else {
                    Toast.makeText(context, message, duration).show();
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认str显示Toast int
     *
     * @param context
     * @param message
     */
    public static void show(Context context, int message) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, context.getResources().getText(message), Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认str显示Toast String
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        try {
            if (isShow) {
                if (isNeedHook()) {
                    ToastUtil.showToast(context, message, Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
