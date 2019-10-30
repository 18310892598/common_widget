package ola.com.pickerview.timer;

import android.os.Handler;
import android.os.Message;

import ola.com.pickerview.view.WheelView;


/**
 * Handler 消息类
 */
public final class MessageHandler<T> extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_ITEM_SELECTED = 3000;

    private final T view;

    private WheelTimerView wheelTimerView;
    private WheelView wheelView;

    public MessageHandler(T view) {
        this.view = view;
    }

    @Override
    public final void handleMessage(Message msg) {
        if (view instanceof WheelTimerView) {
            wheelTimerView = (WheelTimerView) view;

            switch (msg.what) {
                case WHAT_INVALIDATE_LOOP_VIEW:
                    wheelTimerView.invalidate();
                    break;

                case WHAT_SMOOTH_SCROLL:
                    wheelTimerView.smoothScroll(WheelTimerView.ACTION.FLING);
                    break;

                case WHAT_ITEM_SELECTED:
                    wheelTimerView.onItemSelected();
                    break;
                default:
                    break;
            }
        } else if (view instanceof WheelView) {
            wheelView = (WheelView) view;

            switch (msg.what) {
                case WHAT_INVALIDATE_LOOP_VIEW:
                    wheelView.invalidate();
                    break;

                case WHAT_SMOOTH_SCROLL:
                    wheelView.smoothScroll(WheelView.ACTION.FLING);
                    break;

                case WHAT_ITEM_SELECTED:
                    wheelView.onItemSelected();
                    break;
                default:
                    break;
            }
        }


    }

}
