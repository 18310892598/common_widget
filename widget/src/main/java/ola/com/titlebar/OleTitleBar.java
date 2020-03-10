package ola.com.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import ola.com.dialog.R;


public class OleTitleBar extends RelativeLayout {

    /**
     * 背景白色，字体黑色的风格
     */
    public static final int STYLE_WHITE = 0;
    /**
     * 背景透明，字体白色的风格
     */
    public static final int STYLE_TRANS = 1;

    private ImageView backImage, rightImage, closeImage;
    private TextView tvTitle, rightText;

    private boolean rightEnable = true;
    private int visualStyle = 0;

    private OnTitleBarClickListener mListener;

    public OleTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.ole_title_bar, this);

        String centerTxt = "", rightTxt = "";
        Drawable rightSrc = null;
        boolean closeVisible = false;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Title_bar);

            centerTxt = ta.getString(R.styleable.Title_bar_centerTitle);
            rightTxt = ta.getString(R.styleable.Title_bar_rightText);
            rightSrc = ta.getDrawable(R.styleable.Title_bar_rightSrc);
            visualStyle = ta.getInt(R.styleable.Title_bar_visualStyle, 0);
            rightEnable = ta.getBoolean(R.styleable.Title_bar_rightTextEnable, true);
            closeVisible = ta.getBoolean(R.styleable.Title_bar_closeVisible, false);
            ta.recycle();
        }

        backImage = findViewById(R.id.oletitle_left_button);
        rightImage = findViewById(R.id.oletitle_right_image);
        closeImage = findViewById(R.id.oletitle_left_close);
        tvTitle = findViewById(R.id.oletitle_center_title);
        rightText = findViewById(R.id.oletitle_right_text);

        tvTitle.setText(centerTxt);

        switchVisualStyle(visualStyle);

        if (!TextUtils.isEmpty(rightTxt)) {
            rightText.setText(rightTxt);
            rightText.setVisibility(VISIBLE);
        }

        if (rightSrc != null) {
            rightImage.setImageDrawable(rightSrc);
            rightImage.setVisibility(VISIBLE);
        }

        backImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onBackClick();
                }
            }
        });

        closeImage.setVisibility(closeVisible ? VISIBLE : GONE);

        setRightEnable(rightEnable);

        closeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCloseClick();
                }
            }
        });
        rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightEnable && mListener != null) {
                    mListener.onRightClick();
                }
            }
        });
        rightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick();
                }
            }
        });
    }

    public void switchVisualStyle(int style) {
        switch (style) {
            case STYLE_WHITE:
                setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dia_white));
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.dia_black));
                rightText.setTextColor(ContextCompat.getColor(getContext(), R.color.dia_black));
                backImage.setImageResource(R.mipmap.title_back_black);
                closeImage.setImageResource(R.mipmap.title_close_black);
                rightImage.setImageResource(R.mipmap.title_more_black);
                break;
            case STYLE_TRANS:
                setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.dia_white));
                rightText.setTextColor(ContextCompat.getColor(getContext(), R.color.dia_white));
                backImage.setImageResource(R.mipmap.title_back_white);
                closeImage.setImageResource(R.mipmap.title_close_white);
                rightImage.setImageResource(R.mipmap.title_more_white);
                break;
            default:
                break;
        }
    }

    public void setRightEnable(boolean enable){
        rightEnable = enable;

        if (!rightEnable) {
            rightText.setTextColor(ContextCompat.getColor(
                    getContext(), R.color.textcolor_7B7F83));
        }else{
            switchVisualStyle(visualStyle);
        }

        rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightEnable && mListener != null) {
                    mListener.onRightClick();
                }
            }
        });
    }

    public void setTitleClick(OnTitleBarClickListener l) {
        this.mListener = l;
    }

    public ImageView getBack() {
        return backImage;
    }

    public TextView getRightText() {
        return rightText;
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    public ImageView getClose() {
        return closeImage;
    }

    public void setTitle(CharSequence text) {
        tvTitle.setText(text);
    }

    public void setTitle(int resid) {
        tvTitle.setText(resid);
    }

    public TextView getCenter() {
        return tvTitle;
    }

    public interface OnTitleBarClickListener {
        void onBackClick();

        void onRightClick();

        void onCloseClick();
    }
}