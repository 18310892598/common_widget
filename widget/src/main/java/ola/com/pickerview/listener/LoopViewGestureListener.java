package ola.com.pickerview.listener;

import android.view.MotionEvent;

import ola.com.pickerview.timer.WheelTimerView;
import ola.com.pickerview.view.WheelView;


/**
 * 手势监听
 */
public final class LoopViewGestureListener<T> extends android.view.GestureDetector.SimpleOnGestureListener {

    private WheelView wheelView;
    private WheelTimerView wheelTimerView;

    private T view;

    public LoopViewGestureListener(T view) {
        this.view = view;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (view instanceof WheelView) {
            wheelView = (WheelView) view;
            wheelView.scrollBy(velocityY);
        } else if (view instanceof WheelTimerView) {
            wheelTimerView = (WheelTimerView) view;
            wheelTimerView.scrollBy(velocityY);
        }
        return true;
    }
}
