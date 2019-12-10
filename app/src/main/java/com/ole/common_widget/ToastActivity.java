package com.ole.common_widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ole.libtoast.T;

public class ToastActivity extends Activity implements View.OnClickListener{



        private Button one,one1,one2,one3,one4,one5,close;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_toast);
            close=findViewById(R.id.close);
            close.setOnClickListener(this);
            one=findViewById(R.id.one);
            one.setOnClickListener(this);
            one1=findViewById(R.id.one1);
            one2=findViewById(R.id.one2);
            one3=findViewById(R.id.one3);
            one4=findViewById(R.id.one4);
            one5=findViewById(R.id.one5);
            one1.setOnClickListener(this);
            one2.setOnClickListener(this);
            one3.setOnClickListener(this);
            one4.setOnClickListener(this);
            one5.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.close:
                    finish();
                    break;
                case R.id.one:
                    try {
                        T.showFailCustomImage(this, "000000", 1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.one1:
                    T.showSuccessCustomImage(this,"123123123",1);
                    break;
                case R.id.one2:
                    T.showloadingCustomImage(this,"123123123",0);
                    break;
                case R.id.one3:
                    T.showWrongCustomImage(this,"123123123",0);
                    break;
                case R.id.one4:
                    T.showCustomView(this,"123123123",0);
                    break;
                case R.id.one5:
                    T.showWrongCustomImage(this,"欢迎来到我的大世界\n一起疯狂",0);
                    break;
            }
        }

}
