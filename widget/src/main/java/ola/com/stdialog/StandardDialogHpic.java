package ola.com.stdialog;

import ola.com.dialog.R;
import ola.com.stdialog.base.OlaBaseStandardDialog;

public class StandardDialogHpic extends OlaBaseStandardDialog {
    @Override
    protected int getContentView() {
        return R.layout.dialog_standard_hpic;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}
