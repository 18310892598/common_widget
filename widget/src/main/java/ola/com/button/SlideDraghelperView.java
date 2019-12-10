package ola.com.button;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import androidx.customview.widget.ViewDragHelper;

/**
 * Create by wyman
 * Email  piitw@qq.com
 * Create at 2019-05-31 10:17
 * Desc :
 */
public class SlideDraghelperView extends LinearLayout {

    private ViewDragHelper viewDragHelper;
    private View child;
    private Point childPosition = new Point();
    private Point childEndPosition = new Point();
    private OnReleasedListener onReleasedListener;
    private int oldX;

    public SlideDraghelperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTouchable(true);
    }

    public void setTouchable(boolean isTouch) {
        if (isTouch) {
            //新建viewDragHelper ,viewGroup, 灵敏度，回调(子view的移动)
            viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    oldX = 0;
                    return true;
                }

                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    oldX = left;
                    return Math.max(0, left);
                }

                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    if (oldX > getWidth() / 3 * 2) {
                        TranslateAnimation translateAnimation =
                                new TranslateAnimation(0, childEndPosition.x, childPosition.y, childPosition.y);
                        translateAnimation.setDuration(300);
                        child.startAnimation(translateAnimation);
                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onReleasedListener.onReleased(0, 0, getWidth(), getHeight());
                                    }
                                }, 10);

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    } else {
                        //滑动距离不够一半，回到初始位置
                        viewDragHelper.settleCapturedViewAt(childPosition.x, childPosition.y);
                        //反弹
                        invalidate();
                    }


                    super.onViewReleased(releasedChild, xvel, yvel);
                }

                @Override
                public void onViewCaptured(View capturedChild, int activePointerId) {
                    super.onViewCaptured(capturedChild, activePointerId);
                }


                @Override
                public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                    super.onViewPositionChanged(changedView, left, top, dx, dy);
                }

            });
        } else {
            viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return false;
                }
            });
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = getChildAt(0);
    }

    /**
     * 用viewDragHelper拦截-true
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * viewDragHelper拦截事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            viewDragHelper.processTouchEvent(event);
            return true;
        } catch (IllegalArgumentException e) {
            return super.onTouchEvent(event);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //定位左侧的坐标
        childPosition.x = child.getLeft();
        childPosition.y = child.getTop();

        //定位右侧的坐标
        childEndPosition.x = child.getRight();
        childEndPosition.y = child.getTop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setOnReleasedListener(OnReleasedListener onReleasedListener) {
        this.onReleasedListener = onReleasedListener;
    }

    public interface OnReleasedListener {
        void onReleased(int left, int top, int right, int bottom);
    }
}
