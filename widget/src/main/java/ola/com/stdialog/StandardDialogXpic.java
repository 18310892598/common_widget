package ola.com.stdialog;

import ola.com.dialog.R;
import ola.com.stdialog.base.OlaBaseStandardDialog;

public class StandardDialogXpic extends OlaBaseStandardDialog {
    @Override
    protected int getContentView() {
        return R.layout.dialog_standard_xpic;
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return false;
    }
}
