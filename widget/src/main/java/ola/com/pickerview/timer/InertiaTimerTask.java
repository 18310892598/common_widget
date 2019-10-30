package ola.com.pickerview.timer;



import java.util.TimerTask;

import ola.com.pickerview.view.WheelView;

/**
 * 滚动惯性的实现
 */
public final class InertiaTimerTask<T> extends TimerTask {

    private float mCurrentVelocityY; //当前滑动速度
    private final float mFirstVelocityY;//手指离开屏幕时的初始速度
    private WheelView mWheelView;
    private WheelTimerView wheelTimerView;

    private T view;
    /**
     * @param view 滚轮对象
     * @param velocityY Y轴滑行速度
     */
    public InertiaTimerTask(T view, float velocityY) {
        super();
        this.view = view;
        this.mFirstVelocityY = velocityY;
        mCurrentVelocityY = Integer.MAX_VALUE;
    }


    @Override
    public final void run() {

        //防止闪动，对速度做一个限制。
        if (mCurrentVelocityY == Integer.MAX_VALUE) {
            if (Math.abs(mFirstVelocityY) > 2000F) {
                mCurrentVelocityY = mFirstVelocityY > 0 ? 2000F : -2000F;
            } else {
                mCurrentVelocityY = mFirstVelocityY;
            }
        }

        if (view instanceof WheelTimerView) {
            wheelTimerView = (WheelTimerView) view;

            //发送handler消息 处理平顺停止滚动逻辑
            if (Math.abs(mCurrentVelocityY) >= 0.0F && Math.abs(mCurrentVelocityY) <= 20F) {
                wheelTimerView.cancelFuture();
                wheelTimerView.getHandler().sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
                return;
            }

            int dy = (int) (mCurrentVelocityY / 100F);
            wheelTimerView.totalScrollY = (wheelTimerView.totalScrollY - dy);
            if (!wheelTimerView.isLoop) {
                float itemHeight = wheelTimerView.itemHeight;
                float top = (-wheelTimerView.initPosition) * itemHeight;
                float bottom = (wheelTimerView.getItemsCount() - 1 - wheelTimerView.initPosition) * itemHeight;
                if (wheelTimerView.totalScrollY - itemHeight * 0.25 < top) {
                    top = wheelTimerView.totalScrollY + dy;
                } else if (wheelTimerView.totalScrollY + itemHeight * 0.25 > bottom) {
                    bottom = wheelTimerView.totalScrollY + dy;
                }

                if (wheelTimerView.totalScrollY <= top) {
                    mCurrentVelocityY = 40F;
                    wheelTimerView.totalScrollY = (int) top;
                } else if (wheelTimerView.totalScrollY >= bottom) {
                    wheelTimerView.totalScrollY = (int) bottom;
                    mCurrentVelocityY = -40F;
                }
            }

            if (mCurrentVelocityY < 0.0F) {
                mCurrentVelocityY = mCurrentVelocityY + 20F;
            } else {
                mCurrentVelocityY = mCurrentVelocityY - 20F;
            }

            //刷新UI
            wheelTimerView.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);

        } else if (view instanceof WheelView) {
            mWheelView = (WheelView) view;

            //发送handler消息 处理平顺停止滚动逻辑
            if (Math.abs(mCurrentVelocityY) >= 0.0F && Math.abs(mCurrentVelocityY) <= 20F) {
                mWheelView.cancelFuture();
                mWheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
                return;
            }

            int dy = (int) (mCurrentVelocityY / 100F);
            mWheelView.setTotalScrollY(mWheelView.getTotalScrollY() - dy);
            if (!mWheelView.isLoop()) {
                float itemHeight = mWheelView.getItemHeight();
                float top = (-mWheelView.getInitPosition()) * itemHeight;
                float bottom = (mWheelView.getItemsCount() - 1 - mWheelView.getInitPosition()) * itemHeight;
                if (mWheelView.getTotalScrollY() - itemHeight * 0.25 < top) {
                    top = mWheelView.getTotalScrollY() + dy;
                } else if (mWheelView.getTotalScrollY() + itemHeight * 0.25 > bottom) {
                    bottom = mWheelView.getTotalScrollY() + dy;
                }

                if (mWheelView.getTotalScrollY() <= top) {
                    mCurrentVelocityY = 40F;
                    mWheelView.setTotalScrollY((int) top);
                } else if (mWheelView.getTotalScrollY() >= bottom) {
                    mWheelView.setTotalScrollY((int) bottom);
                    mCurrentVelocityY = -40F;
                }
            }

            if (mCurrentVelocityY < 0.0F) {
                mCurrentVelocityY = mCurrentVelocityY + 20F;
            } else {
                mCurrentVelocityY = mCurrentVelocityY - 20F;
            }

            //刷新UI
            mWheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
        }


    }
}
