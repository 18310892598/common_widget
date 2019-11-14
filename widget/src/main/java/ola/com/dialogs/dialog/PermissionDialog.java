package ola.com.dialogs.dialog;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class PermissionDialog extends OlaBaseFourDialog {

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.dia_permission;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}