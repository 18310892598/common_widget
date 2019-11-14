package ola.com.dialogs.dialog;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class CantChangeDestinationTipDialog extends OlaBaseFourDialog {
    @Override
    protected int getContentView() {
        return R.layout.dia_cant_change_destination;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}
