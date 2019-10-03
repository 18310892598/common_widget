package ola.com.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

public class OleDialog {

    public View holder;
    public AppCompatTextView btOk;
    public AppCompatTextView btCancel;
    public TextView tvTitle;
    public TextView tvContent;

    public ImageView ivClose;
    public ImageView ivImg;

    public AlertDialog dialog;

    /**
     * 获得实体
     *
     * @param context
     * @param layout
     * @return
     */
    public static OleDialog getInstance(Context context, int layout) {
        OleDialog dialog = new OleDialog();

        LayoutInflater inLayout = LayoutInflater.from(context);
        dialog.holder = inLayout.inflate(layout, null);
        dialog.btOk = dialog.holder.findViewById(R.id.bt_ok);
        dialog.btCancel = dialog.holder.findViewById(R.id.bt_cancel);
        dialog.tvTitle = dialog.holder.findViewById(R.id.tv_title);
        dialog.tvContent = dialog.holder.findViewById(R.id.tv_content);

        dialog.ivClose = dialog.holder.findViewById(R.id.iv_close);
        dialog.ivImg = dialog.holder.findViewById(R.id.iv_img);

        if (dialog.ivClose != null) {
            dialog.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.stopDialog();
                }
            });
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        builder.setView(dialog.holder);

        dialog.dialog = builder.create();
        return dialog;
    }

    /**
     * 设置标题、内容、正向和负向按钮文本
     *
     * @param title
     * @param content
     * @param okTitle
     * @param cancelTitle
     * @return
     */
    public OleDialog setResource(String title, String content, String okTitle, String cancelTitle) {
        setText(btOk, okTitle);
        setText(btCancel, cancelTitle);
        setText(tvTitle, title);
        setText(tvContent, content);
        return this;
    }

    /**
     * 添加点击事件
     *
     * @param listener
     * @return
     */
    public OleDialog setListener(DialogHelper.DialogListener listener) {
        final DialogHelper.DialogListener dialogListener = listener;
        if (btOk != null) {
            btOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogHelper.stopDialog();
                    if (dialogListener != null) {
                        dialogListener.onOk();
                    }
                }
            });
        }
        if (btCancel != null) {
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogHelper.stopDialog();
                    if (dialogListener != null) {
                        dialogListener.onCancel();
                    }
                }
            });
        }
        return this;
    }

    /**
     * 是否可在区域外点击关闭
     *
     * @param cancelable
     * @return
     */
    public OleDialog setTouchCancelable(boolean cancelable) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(cancelable);
            dialog.setCancelable(cancelable);
        }
        return this;
    }

    /**
     * 寻找定义成员以外的View
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int id) {
        return holder.findViewById(id);
    }

    /**
     * 设置文字安全方案封装
     *
     * @param tv
     * @param txt
     */
    private void setText(TextView tv, String txt) {
        if (tv != null) {
            if (txt != null) {
                tv.setText(txt);
            } else {
                tv.setVisibility(View.GONE);
            }
        }
    }

}
