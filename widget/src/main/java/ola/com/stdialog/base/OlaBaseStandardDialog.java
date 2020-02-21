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
    protected String content;
    protected String title;
    protected String negative;
    protected String positive;
    protected View.OnClickListener closeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            pic = args.getInt("pic", 0);
            title = args.getString("title", "");
            content = args.getString("content", "");
            negative = args.getString("negative", "");
            positive = args.getString("positive", "");
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
    }

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

    public void setNegative(String negative, DialogListener dialogListener) {
        final DialogListener fDialogListener = dialogListener;
        TextView neg = setText(R.id.dia_tv_negative, negative);
        if (neg != null) {
            if (TextUtils.isEmpty(negative)) {
                neg.setVisibility(View.GONE);
                mContentView.findViewById(R.id.divider_line2).setVisibility(View.GONE);
            } else {
                neg.setOnClickListener(view -> {
                    dismiss();
                    if (fDialogListener != null) {
                        fDialogListener.onCancel();
                    }
                });

                if (getContext() != null) {
                    neg.setTextColor(ContextCompat.getColor(getContext(), Configuration.getNegativeColor(APP)));
                }
            }
        }
    }

    public void setPositive(String positive, DialogListener dialogListener) {
        final DialogListener fDialogListener = dialogListener;
        TextView pos = setText(R.id.dia_tv_positive, positive);
        if (pos != null) {
            pos.setOnClickListener(view -> {
                dismiss();
                if (fDialogListener != null) {
                    fDialogListener.onOk();
                }
            });
            if (getContext() != null) {
                pos.setTextColor(ContextCompat.getColor(getContext(), Configuration.getPositiveColor(APP)));
            }
        }
    }

    public OlaBaseStandardDialog enableClose(View.OnClickListener onClickListener) {
        closeListener = onClickListener;
        return this;
    }

    public void setContent(String title, String content) {
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
        if (getContext() != null) {
            contentView.setTextColor(ContextCompat.getColor(getContext(), Configuration.getContentColor(APP)));
        }
    }

    public TextView setText(int id, String str) {
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
