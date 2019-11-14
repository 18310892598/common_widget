package ola.com.dialogs.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ola.com.dialog.R;
import ola.com.dialogs.base.OlaBaseFourDialog;

public class RestTimeTipDialog extends OlaBaseFourDialog {
    private long countdownTime;
    private CountDownTimer countDownTimer;

    @Override
    protected int getContentView() {
        return R.layout.dia_rest_time;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            countdownTime = arguments.getLong("time");
        }
    }

    @Override
    protected boolean dismissWhenTouchOutside() {
        return true;
    }


    @Override
    protected void initViews() {
        super.initViews();
        setText(R.id.dia_tv_content,String.format(getString(R.string.dia_content_rest_time), millisToMinute(countdownTime)));
        countDownTimer = new CountDownTimer(countdownTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView) mContentView.findViewById(R.id.tv_countdown_display)).setText(countdownFormat(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        }.start();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public static String countdownFormat(long time) {
        return new SimpleDateFormat("mm:ss", Locale.CHINA).format(time);
    }

    public static String millisToMinute(long time) {
        return new SimpleDateFormat("m", Locale.CHINA).format(time);
    }
}
