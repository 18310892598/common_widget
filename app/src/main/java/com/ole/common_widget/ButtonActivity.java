package com.ole.common_widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    }
}
