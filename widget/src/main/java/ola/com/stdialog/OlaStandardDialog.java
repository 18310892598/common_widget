package ola.com.stdialog;

import android.content.Context;
import android.os.Bundle;

import ola.com.stdialog.base.OlaBaseRootDialog;

public class OlaStandardDialog {

    public static <T extends OlaBaseRootDialog> T getInstance(
            Class<T> t, Context context,
            int pic, CharSequence title, CharSequence content, CharSequence negative, CharSequence positive) {
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
            bundle.putCharSequence("title", title);
            bundle.putCharSequence("content", content);
            bundle.putCharSequence("negative", negative);
            bundle.putCharSequence("positive", positive);
            dialog.setArguments(bundle);
            return dialog;
        } else {
            return null;
        }
    }

    public static <T extends OlaBaseRootDialog> T getInstance(
            Class<T> t, Context context,
            int pic, CharSequence title, CharSequence content, CharSequence negative, CharSequence positive, int negcolor) {
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
            bundle.putCharSequence("title", title);
            bundle.putCharSequence("content", content);
            bundle.putCharSequence("negative", negative);
            bundle.putCharSequence("positive", positive);
            bundle.putInt("negcolor", negcolor);
            dialog.setArguments(bundle);
            return dialog;
        } else {
            return null;
        }
    }

}
