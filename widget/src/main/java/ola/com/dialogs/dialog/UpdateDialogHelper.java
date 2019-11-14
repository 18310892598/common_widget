package ola.com.dialogs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ola.com.dialog.R;
import ola.com.dialogs.config.Configuration;

/**
 * @author liujia
 * @desc 升级弹出框
 */
public class UpdateDialogHelper {

    private static Dialog dialog;
    private DialogListener dialogCancelListener;

    /**
     * 升级弹出框
     *
     * @param context
     * @param dialogListener 回调
     * @param versionStr
     * @param sizeStr
     * @param timeStr
     * @param contentStr
     * @param isMust
     */
    public static void showDialogCheckVersion(Context context, final DialogListener dialogListener, String versionStr,
                                              String sizeStr, String timeStr, String contentStr, boolean isMust, short app) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        LayoutInflater inLayout = LayoutInflater.from(context);
        View v = inLayout.inflate(R.layout.dia_check_version, null);
        TextView version = v.findViewById(R.id.version);
        TextView size = v.findViewById(R.id.size);
        TextView time = v.findViewById(R.id.time);
        TextView content = v.findViewById(R.id.content);
        TextView btUpdate = v.findViewById(R.id.bt_update);
        TextView btCancle = v.findViewById(R.id.bt_cancle);
        ImageView head = v.findViewById(R.id.head);

        head.setImageResource(Configuration.getVersionHead(app));
        btUpdate.setBackgroundResource(Configuration.getVersionButton(app));

        version.setText("版本号：" + versionStr);
        size.setText("安装包大小：" + Long.valueOf(sizeStr) / 1000000 + "MB");
        time.setText("更新时间：" + getFullDateText(Long.valueOf(timeStr)));
//        content.setText(Html.fromHtml(contentStr));
        content.setText(contentStr.replace("\\n", "\n"));
        if (isMust) {
            btCancle.setVisibility(View.GONE);
        }
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListener.onOk();
                btUpdate.setText("正在下载...");
                if (!isMust) {
                    UpdateDialogHelper.stopDialog();
                }
            }
        });
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        builder.setView(v);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public synchronized static void stopDialog() {
        if (null != dialog) {
            dialog.dismiss();
            dialog = null;
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

    private static String getFullDateText(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(time);
    }


}
