package ola.com.dialogs.dialog;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class LocationFailedDialog extends OlaBaseFourDialog {
    @Override
    protected int getContentView() {
        return R.layout.dia_location_failed;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }

//    @Override
//    protected int getWidth() {
//        return DialogUiUtil.dp2px(getActivity(),250);
//    }
}
