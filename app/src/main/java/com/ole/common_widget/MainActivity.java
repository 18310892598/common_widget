package com.ole.common_widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import ola.com.dialog.DialogHelper;
import ola.com.pickerview.builder.OptionsPickerBuilder;
import ola.com.pickerview.interfaces.IPickerViewData;
import ola.com.pickerview.listener.OnOptionsSelectListener;
import ola.com.pickerview.utils.Methods;
import ola.com.pickerview.utils.TimeBean;
import ola.com.pickerview.view.OptionsPickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(false);
            }
        });
//        showTimePicker(false);


        DialogHelper.showDialogCheckNotify(this,null);
    }


    public void showTimePicker(final boolean resetPrice) {

        final List<TimeBean> dayList = new ArrayList<>();
        final List<List<String>> hourList = new ArrayList<>();
        final ArrayList<ArrayList<ArrayList<IPickerViewData>>> minuteList = new ArrayList<>();

        dayList.add(new TimeBean(Methods.getmoutianMD(0)));
        dayList.add(new TimeBean(Methods.getmoutianMD(1)));
        dayList.add(new TimeBean(Methods.getmoutianMD(2)));


        //选项 2
        ArrayList<String> options2Items02 = Methods.getTodayHourData();
        ArrayList<String> options2Items03 = Methods.getHourData();
        ArrayList<String> options2Items04 = Methods.getHourData();
        hourList.add(options2Items02);
        hourList.add(options2Items03);
        hourList.add(options2Items04);


        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items02 = Methods.getmD2();
        ArrayList<ArrayList<IPickerViewData>> options3Items03 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items04 = new ArrayList<>();

        options3Items03 = Methods.getmD();
        options3Items04 = Methods.getmD();

        minuteList.add(options3Items02);
        minuteList.add(options3Items03);
        minuteList.add(options3Items04);


        OptionsPickerView builder = new OptionsPickerBuilder(MainActivity.this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = dayList.get(options1)
                        + hourList.get(options1).get(options2)
                        + minuteList.get(options1).get(options2).get(options3);

                //返回的分别是三个级别的选中位置
                //今天明天后天
                String days = dayList.get(options1).getPickerViewText();
                //0-23点
                String when = hourList.get(options1).get(options2);
                //0-50分
                String points = minuteList.get(options1).get(options2).get(options3).getPickerViewText();
                String result = "";

                result = days;
                if (when.length() == 2) {
                    result += " 0" + when.substring(0, 1) + ":";
                } else if (when.length() == 3) {
                    result += " " + when.substring(0, 2) + ":";
                }
                if (points.length() == 2) {
                    result += "0" + points.substring(0, 1);
                } else if (points.length() == 3) {
                    result += points.substring(0, 2);
                }

                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        }).setCancelColor(ContextCompat.getColor(MainActivity.this, R.color.textcolor_7B7F83))
                .setSubmitColor(ContextCompat.getColor(MainActivity.this, R.color.textcolor_ff))
                .setTitleText("请选择用车时间")
                .setLayoutRes(R.layout.pickerview_timer_options3, null)
                .setLineSpacingMultiplier(2.7f)
                .build();

        builder.setSelectOptions(0, 0, 0);
        builder.setPicker(dayList, hourList, minuteList);
        builder.show();
    }

}
