package ola.com.stdialog;

import android.content.Context;
import android.os.Bundle;

import ola.com.stdialog.base.OlaBaseRootDialog;

public class OlaStandardDialog {

    public static <T extends OlaBaseRootDialog> T getInstance(
            Class<T> t, Context context,
            int pic,String title, String content, String negative, String positive) {
        T dialog = null;

        try {
            dialog = t.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (dialog != null) {

            Bundle bundle = new Bundle();
            bundle.putInt("pic", pic);
            bundle.putString("title", title);
            bundle.putString("content", content);
            bundle.putString("negative", negative);
            bundle.putString("positive", positive);
            dialog.setArguments(bundle);
            return dialog;
        } else {
            return null;
        }
    }

}
