package ola.com.dialogs;

import android.content.Context;
import android.os.Bundle;

import ola.com.dialogs.base.OlaBaseDialog;

public class OlaDialog {

    public static <T extends OlaBaseDialog> T getInstance(
            Class<T> t, Context context,
            String title, String content, String negative, String positive) {
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

    public static <T extends OlaBaseDialog> T getInstance(Class<T> t, Context context) {
        return getInstance(t, context, null, null, null, null);
    }

}
