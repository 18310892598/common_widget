package com.ole.common_widget;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ola.com.titlebar.OleTitleBar;

public class TitleActivity extends AppCompatActivity {

    OleTitleBar title;
    boolean rightEnable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        title = findViewById(R.id.title);
        title.setTitleClick(new OleTitleBar.OnTitleBarClickListener() {
            @Override
            public void onBackClick() {
                rightEnable = !rightEnable;
                title.setRightEnable(rightEnable);
            }

            @Override
            public void onRightClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });
    }
}
