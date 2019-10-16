package ola.com.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author liujia
 */
public class DialogHelper {


    private static Dialog dialog;

    public void showDialog(Context context, String title, String content) {
        showDialog(context, title, content, null);
    }

    public static void showDialog(Context context, String title, String content, final DialogListener dialogListener) {
        showDialog(context, title, content, context.getString(android.R.string.ok), context.getString(android.R.string.cancel), dialogListener);
    }


    public static void showDialog(Context context, String title, String content, String okTitle, String cancelTitle, final DialogListener dialogListener) {
        showDialog(context, false, title, content, okTitle, cancelTitle, dialogListener);
    }

    public static void showDialog(Context cont, boolean notCanceled, String title, String content, String okTitle, String cancelTitle, final DialogListener dialogListener) {
        if (cont instanceof Activity && ((Activity) cont).isFinishing()) {
            return;
        }
        final Context context = cont;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(okTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!((Activity) context).isDestroyed()) {
                    stopDialog(context);
                }

                if (null != dialogListener) {
                    dialogListener.onOk();
                }
            }
        });
        builder.setNegativeButton(cancelTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!((Activity) context).isDestroyed()) {
                    stopDialog(context);
                }

                if (null != dialogListener) {
                    dialogListener.onCancel();
                }
            }
        });

        dialog = builder.create();
        if (notCanceled) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    private static void showDialogBase(int layout, Context context, boolean notCanceled, String title, String content, String okTitle, String cancelTitle, final DialogListener dialoglListener) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        OleDialog oleDialog = OleDialog.getInstance(context, layout)
                .setResource(title, content, okTitle, cancelTitle)
                .setListener(dialoglListener)
                .setTouchCancelable(!notCanceled);
        dialog = oleDialog.dialog;
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * @param context     上下文
     * @param title       标题
     * @param content     内容
     * @param okTitle     确定按钮
     * @param cancelTitle 取消按钮
     * @desc 自定义dialog布局（注意布局需要ID以为dialog_common的设置保持一致）
     * @authro hyx
     */
    public static void showDialogRequest(Context context, String title, String content, String okTitle, String cancelTitle, final DialogListener dialoglListener) {
        showDialogRequest(context, false, title, content, okTitle, cancelTitle, dialoglListener);
    }

    public static void showDialogRequest(Context context, boolean notCanceled, String title, String content, String okTitle, String cancelTitle, final DialogListener dialoglListener) {
        showDialogBase(R.layout.dialog_custom_request, context, notCanceled, title, content, okTitle, cancelTitle, dialoglListener);
    }

    /**
     * @param context     上下文
     * @param title       标题
     * @param content     内容
     * @param okTitle     确定按钮
     * @param cancelTitle 取消按钮
     * @desc 自定义dialog布局（注意布局需要ID以为dialog_common的设置保持一致）
     * @authro hyx
     */
    public static void showDialogCommon(Context context, String title, String content, String okTitle, String cancelTitle, final DialogListener dialoglListener) {
        showDialogBase(ola.com.dialog.R.layout.dialog_custom, context, true, title, content, okTitle, cancelTitle, dialoglListener);
    }

    public static void showDialogImgCommon(final Context context, String content, String okbtn, String cancelBtn) {
        showDialogImgCommon(context, content, okbtn, cancelBtn, 0, true, null);
    }

    public static void showDialogImgCommon(final Context context, String content, String okbtn, String cancelBtn, final DialogListener dialoglListener) {
        showDialogImgCommon(context, content, okbtn, cancelBtn, 0, true, dialoglListener);
    }

    //
//    /**
//     * @param offset 指需要改变文字颜色的长度 不改变传0即可
//     * @param hideCloseImg 是否需要显示closeImg
//     * @desc 通用带黄感叹号弹出框
//     */
    public static void showDialogImgCommon(final Context context, String content, String okbtn,
                                           String cancelBtn, int offset, boolean hideCloseImg,
                                           final DialogListener dialoglListener) {

        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        OleDialog oleDialog = OleDialog.getInstance(context, R.layout.dialog_img_custom)
                .setResource(null, content, okbtn, cancelBtn)
                .setListener(dialoglListener)
                .setTouchCancelable(false);

        if (hideCloseImg && oleDialog.ivClose != null) {
            oleDialog.ivClose.setVisibility(View.GONE);
        }

        if (content != null && oleDialog.tvContent != null) {
            SpannableString ss = new SpannableString(content);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.textcolor_3E)),
                    content.length() - offset, content.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            oleDialog.tvContent.setText(ss);
        }

        if (TextUtils.isEmpty(okbtn)) {
            oleDialog.btOk.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(cancelBtn)) {
            oleDialog.btCancel.setVisibility(View.GONE);
        }

        dialog = oleDialog.dialog;

        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * @param context
     * @param dialoglListener
     * @desc 赠送优惠券弹出框
     */

    public static void giveCoupon(final Context context, final DialogListener dialoglListener) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        OleDialog oleDialog = OleDialog.getInstance(context, R.layout.dialog_img_custom_coupon)
                .setListener(dialoglListener)
                .setTouchCancelable(false);

        dialog = oleDialog.dialog;
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void showDialogCheckVersion(Context context, final DialogListener dialoglListener,
                                              String versionCode, String packSize, String updateTime,
                                              String detail, boolean isMust) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        final boolean isMustFinal = isMust;

        final OleDialog oleDialog = OleDialog.getInstance(context, R.layout.dialog_check_version)
                .setTouchCancelable(false);

        TextView version = oleDialog.findViewById(R.id.version);
        TextView size = oleDialog.findViewById(R.id.size);
        TextView time = oleDialog.findViewById(R.id.time);
        TextView content = oleDialog.findViewById(R.id.content);

        final TextView btUpdate = oleDialog.findViewById(R.id.bt_update);
        TextView btCancle = oleDialog.findViewById(R.id.bt_cancel);

        version.setText("版本号：" + versionCode);
        size.setText("安装包大小：" + Long.valueOf(packSize) / 1000000 + "MB");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time.setText("更新时间：" + format.format(Long.valueOf(updateTime)));

//        content.setText(Html.fromHtml(detail));
        content.setText(detail.replace("\\n", "\n"));
        if (isMust) {
            btCancle.setVisibility(View.GONE);
        }
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglListener.onOk();
                btUpdate.setText("正在下载...");
                if (!isMustFinal) {
                    DialogHelper.stopDialog(oleDialog.context);
                }
            }
        });
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = oleDialog.dialog;
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    //todo ok替换
    public static void showDialogCheckNotify(Context context, DialogListener listener) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        final DialogListener dialogListener = listener;
        final OleDialog oleDialog = OleDialog.getInstance(context, R.layout.dialog_check_notify)
                .setTouchCancelable(false);

        TextView btOk = oleDialog.findViewById(R.id.bt_ok);
        ImageView btCancle = oleDialog.findViewById(R.id.close_img);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppUtils.gotoNotifySetting(context);
                if (dialogListener != null) {
                    dialogListener.onOk();
                }
                stopDialog(oleDialog.context);
            }
        });
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogListener != null) {
                    dialogListener.onCancel();
                }
                stopDialog(oleDialog.context);
            }
        });
        dialog = oleDialog.dialog;
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void showCallPhoneDialog(Context context, final DialogListener dialoglListener) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        OleDialog oleDialog = OleDialog.getInstance(context, R.layout.dialog_callphone)
                .setListener(dialoglListener);
        dialog = oleDialog.dialog;
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * @param context
     * @desc 支付优化loading弹出框
     */
    public static void showPayLoading(final Context context) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        LayoutInflater inLayout = LayoutInflater.from(context);
        View v = inLayout.inflate(R.layout.dialog_pay_loading, null);

        builder.setView(v);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public synchronized static void stopDialog(Context context) {
        if (null != dialog) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            } else {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    public interface DialogListener {
        /**
         * 取消
         */
        void onCancel();

        /**
         * 确定
         */
        void onOk();
    }


}
