package ola.com.stdialog;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import ola.com.dialog.R;
import ola.com.stdialog.base.OlaBaseStandardDialog;

public class StandardDialogLast extends OlaBaseStandardDialog {
    @Override
    protected int getContentView() {
        return R.layout.dialog_standard_last2;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }

    @Override
    public void setPositive(String positive, DialogListener dialogListener) {
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
                pos.setTextColor(ContextCompat.getColor(getContext(), R.color.dia_white));
            }
        }
    }
}
