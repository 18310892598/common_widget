package ola.com.dialogs.dialog;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;
import ola.com.dialogs.util.DialogUiUtil;

public class CustomRequestDialog extends OlaBaseFourDialog {
    @Override
    protected int getContentView() {
        return R.layout.dia_custom_request;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }

    @Override
    protected int getWidth() {
        if (getActivity() == null)
            return super.getWidth();
        else
            return DialogUiUtil.dp2px(getActivity(), 240);
    }
}
