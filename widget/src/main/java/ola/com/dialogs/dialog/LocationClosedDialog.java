package ola.com.dialogs.dialog;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class LocationClosedDialog extends OlaBaseFourDialog {

    @Override
    protected int getContentView() {
        return R.layout.dia_location_closed;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}

