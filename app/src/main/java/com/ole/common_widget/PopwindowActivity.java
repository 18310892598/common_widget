package com.ole.common_widget;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ola.com.button.SlideDraghelperView;
import ola.com.popwindow.CommonPopupWindow;

/**
 * @author yang
 */
public class PopwindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow(v);
            }
        });
//        findViewById(R.id.bt2).setSelected(true);

    }

    CommonPopupWindow popupWindow;

    private void showPopwindow(View v) {
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.home_show_choice_number_view)
                .setWidthAndHeight(1, 0)
                .setAnimationStyle(R.style.picker_view_slide_anim)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView choice_one = view.findViewById(R.id.choice_one);
                        TextView choice_two = view.findViewById(R.id.choice_two);
                        ImageView close = view.findViewById(R.id.close);

                        choice_one.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                choice_one.setSelected(true);
                                choice_two.setSelected(false);
                            }
                        });
                        choice_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                choice_one.setSelected(false);
                                choice_two.setSelected(true);
                            }
                        });
                        close.setOnClickListener(view1 -> popupWindow.dismiss());
                    }
                })
                .setOutsideTouchable(true)
                .create();

        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

}
