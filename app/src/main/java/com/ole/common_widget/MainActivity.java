package com.ole.common_widget;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ola.com.dialog.DialogHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DialogHelper.showDialogCheckVersion(this, new DialogHelper.DialogListener() {
//            @Override
//            public void onCancel() {
//                Toast.makeText(MainActivity.this,"onCancel",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onOk() {
//                Toast.makeText(MainActivity.this,"onOk",Toast.LENGTH_LONG).show();
//            }
//        },"ver","123","0","content",false);

        DialogHelper.showDialogCheckNotify(this);
    }
}
