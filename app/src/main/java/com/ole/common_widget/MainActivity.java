package com.ole.common_widget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ola.com.dialog.OleDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OleDialog.loggg();
    }
}