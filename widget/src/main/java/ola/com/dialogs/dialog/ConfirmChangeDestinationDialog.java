package ola.com.dialogs.dialog;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class ConfirmChangeDestinationDialog extends OlaBaseFourDialog {

    @Override
    protected int getContentView() {
        return R.layout.dia_confirm_change_dest;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}
