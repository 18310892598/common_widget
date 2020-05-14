package ola.com.pickerview.timer;



import java.util.TimerTask;

import ola.com.pickerview.view.WheelView;


/**
 * 平滑滚动的实现
 */
public final class SmoothScrollTimerTask<T> extends TimerTask {

    private int realTotalOffset;
    private int realOffset;
    private int offset;
    private WheelView wheelView;
    private WheelTimerView wheelTimerView;

    private T view;

    public SmoothScrollTimerTask(T view, int offset) {
        this.view = view;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滚动的范围细分成10小份，按10小份单位来重绘
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        todo();

    }

    private void todo() {
        if (view instanceof WheelTimerView) {
            wheelTimerView = (WheelTimerView) view;

            if (Math.abs(realTotalOffset) <= 1) {
                wheelTimerView.cancelFuture();
                wheelTimerView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
            } else {

                wheelTimerView.totalScrollY = wheelTimerView.totalScrollY + realOffset;

                //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的 情况
                if (!wheelTimerView.isLoop) {
                    float itemHeight = wheelTimerView.itemHeight;
                    float top = (float) (-wheelTimerView.initPosition) * itemHeight;
                    float bottom = (float) (wheelTimerView.getItemsCount() - 1 - wheelTimerView.initPosition) * itemHeight;
                    if (wheelTimerView.totalScrollY <= top || wheelTimerView.totalScrollY >= bottom) {
                        wheelTimerView.totalScrollY = wheelTimerView.totalScrollY - realOffset;
                        wheelTimerView.cancelFuture();
                        wheelTimerView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                        return;
                    }
                }
                wheelTimerView.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
                realTotalOffset = realTotalOffset - realOffset;
            }

        } else if (view instanceof WheelView) {
            wheelView = (WheelView) view;

            if (Math.abs(realTotalOffset) <= 1) {
                wheelView.cancelFuture();
                wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
            } else {
                wheelView.setTotalScrollY(wheelView.getTotalScrollY() + realOffset);

                //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的 情况
                if (!wheelView.isLoop()) {
                    float itemHeight = wheelView.getItemHeight();
                    float top = (float) (-wheelView.getInitPosition()) * itemHeight;
                    float bottom = (float) (wheelView.getItemsCount() - 1 - wheelView.getInitPosition()) * itemHeight;
                    if (wheelView.getTotalScrollY() <= top || wheelView.getTotalScrollY() >= bottom) {
                        wheelView.setTotalScrollY(wheelView.getTotalScrollY() - realOffset);
                        wheelView.cancelFuture();
                        wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                        return;
                    }
                }
                wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
                realTotalOffset = realTotalOffset - realOffset;
            }
        }

    }
}
