package com.ole.common_widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ola.com.button.SlideDraghelperView;

/**
 * @author yang
 */
public class ButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.button2).setSelected(false);
            }
        });
        findViewById(R.id.button3).setSelected(true);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.button3).setSelected(!findViewById(R.id.button3).isSelected());
            }
        });
        findViewById(R.id.button4).setSelected(true);
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.button4).setSelected(!findViewById(R.id.button4).isSelected());
            }
        });
        findViewById(R.id.button5).setSelected(true);
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.button5).setSelected(!findViewById(R.id.button5).isSelected());
            }
        });
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_navi_trip_opt, null);

        this.addContentView(view,findViewById(R.id.layout).getLayoutParams());
        ((SlideDraghelperView)findViewById(R.id.slide_right_view_draghelper)).setOnReleasedListener(new SlideDraghelperView.OnReleasedListener() {
            @Override
            public void onReleased(int left, int top, int right, int bottom) {
                Toast.makeText(ButtonActivity.this,"滑动成功",Toast.LENGTH_SHORT).show();
                ((SlideDraghelperView)findViewById(R.id.slide_right_view_draghelper)).layout( 0,  0,  0,  0);
            }
        });

    }
}
