package ola.com.stdialog.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import ola.com.Configuration;
import ola.com.dialog.R;
import ola.com.stdialog.DialogListener;

public abstract class OlaBaseStandardDialog extends OlaBaseRootDialog {
    private static final String TAG = OlaBaseStandardDialog.class.getSimpleName();

    public static short APP = Configuration.APP_DRIVER;

    private DialogListener dialogListener;

    protected int pic;
    protected CharSequence content;
    protected CharSequence title;
    protected CharSequence negative;
    protected CharSequence positive;
    protected View.OnClickListener closeListener;
    protected boolean touchOutsideCancel = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            pic = args.getInt("pic", 0);
            title = args.getCharSequence("title", "");
            content = args.getCharSequence("content", "");
            negative = args.getCharSequence("negative", "");
            positive = args.getCharSequence("positive", "");
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        setImage(pic);
        setContent(title, content);
        setPositive(positive, dialogListener);
        setNegative(negative, dialogListener);
        setClose(closeListener);
        refreshConfigs();
    }

    private void refreshConfigs() {
        if (getDialog() != null) {
            setCancelable(touchOutsideCancel);
            getDialog().setCanceledOnTouchOutside(touchOutsideCancel);
        }
    }

    /**
     * 设置点击外部是否关闭对话框
     *
     * @param cancel true 关闭 false 不关闭
     */
    public OlaBaseStandardDialog setCanceledOnTouchOutside(boolean cancel) {
        touchOutsideCancel = cancel;
        if (getDialog() != null) {
            refreshConfigs();
        }
        return this;
    }

    /**
     * 设置关闭按钮
     * 目前只对mpic有效
     * 适用于对话框初始化后
     *
     * @param onClickListener 关闭按钮点击事件
     */
    public void setClose(View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            return;
        }
        ImageView close = mContentView.findViewById(R.id.dia_iv_close);
        if (close != null) {
            close.setOnClickListener(onClickListener);
            close.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置否定案件文字及点击事件
     *
     * @param negative
     * @param dialogListener
     */
    public void setNegative(CharSequence negative, DialogListener dialogListener) {
        final DialogListener fDialogListener = dialogListener;
        TextView neg = setText(R.id.dia_tv_negative, negative);
        if (neg != null) {
            if (TextUtils.isEmpty(negative)) {
                neg.setVisibility(View.GONE);
                mContentView.findViewById(R.id.divider_line2).setVisibility(View.GONE);
            } else {
                neg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (fDialogListener != null) {
                            fDialogListener.onCancel();
                        }
                    }
                });

                if (getContext() != null) {
                    neg.setTextColor(ContextCompat.getColor(getContext(), Configuration.getNegativeColor(APP)));
                }
            }
        }
    }

    /**
     * 设置肯定按钮文字及点击事件
     *
     * @param positive
     * @param dialogListener
     */
    public void setPositive(CharSequence positive, DialogListener dialogListener) {
        final DialogListener fDialogListener = dialogListener;
        TextView pos = setText(R.id.dia_tv_positive, positive);
        if (pos != null) {
            pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (fDialogListener != null) {
                        fDialogListener.onOk();
                    }
                }
            });
            if (getContext() != null) {
                pos.setTextColor(ContextCompat.getColor(getContext(), Configuration.getPositiveColor(APP)));
            }
        }
    }

    /**
     * 设置关闭按钮
     * 目前只对mpic有效
     * 适用于对话框初始化前
     *
     * @param onClickListener 关闭按钮点击事件
     */
    public OlaBaseStandardDialog enableClose(View.OnClickListener onClickListener) {
        closeListener = onClickListener;
        return this;
    }

    public void setContent(CharSequence content){
        this.content = content;
    }

    /**
     * 设置标题及内容
     * 为空则会gone掉对应文本
     *
     * @param title
     * @param content
     */
    public void setContent(CharSequence title, CharSequence content) {
        TextView contentView = setText(R.id.dia_tv_content, content);

        TextView titleView = setText(R.id.dia_tv_title, title);
        if (titleView != null) {
            if (TextUtils.isEmpty(title)) {
                titleView.setVisibility(View.GONE);
            } else {
                if (getContext() != null) {
                    titleView.setTextColor(ContextCompat.getColor(getContext(), Configuration.getTitleColor(APP)));
                }
            }
        }

        if (contentView != null && TextUtils.isEmpty(content)) {
            contentView.setVisibility(View.GONE);
        }
        if (getContext() != null) {
            contentView.setTextColor(ContextCompat.getColor(getContext(), Configuration.getContentColor(APP)));
        }
    }

    public TextView setText(int id, CharSequence str) {
        TextView tv = mContentView.findViewById(id);
        if (tv != null && !TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
        return tv;
    }

    public View setImage(int pic) {
        ImageView iv = mContentView.findViewById(R.id.dia_iv_img);
        if (iv != null && pic != 0) {
            iv.setImageResource(pic);
            this.pic = pic;
        }
        return iv;
    }

    public void showDialog(FragmentManager fm) {
        dialogListener = null;
        show(fm, TAG);
    }


    public void showDialog(FragmentManager fm, DialogListener listener) {
        dialogListener = listener;
        show(fm, TAG);
    }


}
