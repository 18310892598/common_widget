package ola.com.dialogs.dialog;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class ToPickUpPassengerTipDialog extends OlaBaseFourDialog {

    @Override
    protected int getContentView() {
        return R.layout.dia_pick_up_passenger;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}
