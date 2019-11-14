package ola.com.dialogs.base;


import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;
import java.util.Objects;

import ola.com.dialog.R;
import ola.com.dialogs.util.DialogUiUtil;

public abstract class OlaBaseDialog extends DialogFragment {
    protected View mContentView;

    protected abstract int getContentView();

    protected abstract boolean dismissWhenTouchOutside();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(dismissWhenTouchOutside());
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.OlaBaseDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getContentView(), container);
        initViews();
        return mContentView;
    }

    protected void initViews() {
        mContentView.setBackgroundResource(setBackground());
    }

    protected int setBackground() {
        return R.drawable.bg_base_dialog;
    }

    protected int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    private int getMargins() {
        return DialogUiUtil.dp2px(Objects.requireNonNull(getContext()), 20);
    }

    protected int getWidth() {
        return DialogUiUtil.getScreenWidth(Objects.requireNonNull(getContext())) - 2 * getMargins();
    }

    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isVisible()) {
            return;
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
        changeDialogFragmentFieldValue("mDismissed", false);
        changeDialogFragmentFieldValue("mShownByMe", true);
    }

    private void changeDialogFragmentFieldValue(String fn, boolean b) {
        try {
            Field field = DialogFragment.class.getDeclaredField(fn);
            field.setAccessible(true);
            field.set(this, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int height = getHeight();
            if (height == 0) {
                height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(getGravity());
                window.setLayout(getWidth(), height);
            }
        }
    }

    protected void showSoftKeyboard(View v) {
        v.postDelayed(() -> ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(v, 0), 300);
    }

    @Override
    public void dismiss() {
        flushStackLocalLeaks(Looper.myLooper());
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (isDetached()) {
            return;
        }
        if (Build.VERSION.SDK_INT > 17) {
            if (getActivity().isDestroyed()) {
                return;
            }
        }
        dismissAllowingStateLoss();
    }

    private static void flushStackLocalLeaks(Looper looper) {
        final Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        handler.sendMessageDelayed(handler.obtainMessage(), 1000);
                        return true;
                    }
                });
            }
        });
    }
}
