package ola.com.pickerview.utils;


import ola.com.pickerview.timer.WheelTimerView;

public class OnItemSelectedRunnable implements Runnable {
    final WheelTimerView loopView;

    public OnItemSelectedRunnable(WheelTimerView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        if (null != loopView.onItemSelectedListener) {
            loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
        }
    }
}
