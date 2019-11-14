package ola.com.dialogs.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import ola.com.dialog.R;
import ola.com.dialogs.callback.DialogListener;
import ola.com.dialogs.dialog.PermissionDialog;

public abstract class OlaBaseFourDialog extends OlaBaseDialog {
    private static final String TAG = PermissionDialog.class.getSimpleName();
    private View.OnClickListener onClickListener;
    private DialogListener dialogListener;

    protected String content;
    protected String title;
    protected String negative;
    protected String positive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title", "");
            content = args.getString("content", "");
            negative = args.getString("negative", "");
            positive = args.getString("positive", "");
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        setText(R.id.dia_tv_title, title);
        setText(R.id.dia_tv_content, content);
        View pos = setText(R.id.dia_tv_positive, positive);
        View neg = setText(R.id.dia_tv_negative, negative);

        if (pos != null) {
            pos.setOnClickListener(view -> {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                } else if (dialogListener != null) {
                    dialogListener.onOk();
                }
            });
        }

        if (neg != null) {
            neg.setOnClickListener(view -> {
                dismiss();
                if (dialogListener != null) {
                    dialogListener.onCancel();
                }
            });
        }
    }

    protected View setText(int id, String str) {
        TextView tv = mContentView.findViewById(id);
        if (tv != null && !TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
        return tv;
    }

    public void showDialog(FragmentManager fm){
        onClickListener = null;
        dialogListener = null;
        show(fm,TAG);
    }

    public void showDialog(FragmentManager fm, View.OnClickListener listener) {
        onClickListener = listener;
        dialogListener = null;
        show(fm, TAG);
    }

    public void showDialog(FragmentManager fm, DialogListener listener) {
        dialogListener = listener;
        onClickListener = null;
        show(fm, TAG);
    }


}
