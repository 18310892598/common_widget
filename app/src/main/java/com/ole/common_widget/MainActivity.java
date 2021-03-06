package com.ole.common_widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ola.com.pickerview.builder.OptionsPickerBuilder;
import ola.com.pickerview.builder.TimePickerBuilder;
import ola.com.pickerview.interfaces.IPickerViewData;
import ola.com.pickerview.listener.OnOptionsSelectListener;
import ola.com.pickerview.listener.OnTimeSelectListener;
import ola.com.pickerview.utils.Methods;
import ola.com.pickerview.utils.TimeBean;
import ola.com.pickerview.view.OptionsPickerView;
import ola.com.pickerview.view.TimePickerView;
import ola.com.stdialog.DialogListener;
import ola.com.stdialog.OlaStandardDialog;
import ola.com.stdialog.StandardDialogHpic;
import ola.com.stdialog.StandardDialogLast;
import ola.com.stdialog.StandardDialogMpic;
import ola.com.stdialog.StandardDialogXpic;
import ola.com.stdialog.base.OlaBaseRootDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ItemBean> data = new ArrayList<>();

        data.add(new ItemBean("ButtonActivity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ButtonActivity.class));
            }
        }));

        data.add(new ItemBean("ToastActivity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ToastActivity.class));
            }
        }));

        data.add(new ItemBean("showPopwindow", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, PopwindowActivity.class));
            }
        }));

        data.add(new ItemBean("showTimePicker", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showTimePicker(true);
                showTimePicker();
            }
        }));

        data.add(new ItemBean("------------------", null));

        data.add(new ItemBean("StandardDialogXpic", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardDialogXpic instance = OlaStandardDialog.getInstance(
                        StandardDialogXpic.class, getActivity(),
                        0, null, Html.fromHtml("<font color = \"#14A83E\">" + 3 + "???</font>???????????????"), "negative", "positive");
                instance.showDialog(getSupportFragmentManager(), new DialogListener() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onOk() {
                        Toast.makeText(getActivity(), "onOk", Toast.LENGTH_SHORT).show();
                    }
                });
                instance.setDialogProgressListener(new OlaBaseRootDialog.DialogProgressListener() {
                    @Override
                    public void onInitViewFinish(View contentView) {
                        instance.setText(ola.com.dialog.R.id.dia_tv_content, "6666666");
                    }
                });
            }
        }));

        data.add(new ItemBean("StandardDialogMpic",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StandardDialogMpic dialog = OlaStandardDialog.getInstance(
                                        StandardDialogMpic.class, getActivity(),
                                        R.mipmap.icon_prompt_wait_pay, "title", "content", "negative", "positive");

                                dialog.enableClose(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getActivity(), "close", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.showDialog(getSupportFragmentManager(), new DialogListener() {
                                    @Override
                                    public void onCancel() {
                                        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onOk() {
                                        Toast.makeText(getActivity(), "onOk", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                )
        );

        data.add(new ItemBean("StandardDialogHpic",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Objects.requireNonNull(OlaStandardDialog.getInstance(
                                StandardDialogHpic.class, getActivity(),
                                R.mipmap.number_pic2, "title", "content", "negative", "positive"))
                                .showDialog(getSupportFragmentManager(), new DialogListener() {
                                    @Override
                                    public void onCancel() {
                                        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onOk() {
                                        Toast.makeText(getActivity(), "onOk", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }));

        data.add(new ItemBean("StandardDialogLast",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Objects.requireNonNull(OlaStandardDialog.getInstance(
                                StandardDialogLast.class, getActivity(),
                                0, "title", "content", "negative", "positive"))
                                .showDialog(getSupportFragmentManager(), new DialogListener() {
                                    @Override
                                    public void onCancel() {
                                        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onOk() {
                                        Toast.makeText(getActivity(), "onOk", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }));


        ListView lv = findViewById(R.id.lv);
        ArrayAdapter<ItemBean> adapter = new ArrayAdapter<ItemBean>(this, R.layout.item) {

            @Override
            public int getCount() {
                return data.size();
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final ItemBean item = data.get(position);
                @SuppressLint("ViewHolder") View view = LayoutInflater.from(getActivity()).inflate(R.layout.item, parent, false);
                TextView tv = view.findViewById(R.id.tv);
                tv.setText(item.name);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.onClickListener != null)
                            item.onClickListener.onClick(v);
                    }
                });
                return view;
            }
        };
        lv.setAdapter(adapter);

    }

    private void showTimePicker() {
        TimePickerView pvTime;
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");
            }
        })
                .setLineSpacingMultiplier(2.0f)
                .isDialog(false)
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//???????????????????????????????????????
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private MainActivity getActivity() {
        return this;
    }

    public void showTimePicker(final boolean resetPrice) {

        final List<TimeBean> dayList = new ArrayList<>();
        final List<List<String>> hourList = new ArrayList<>();
        final ArrayList<ArrayList<ArrayList<IPickerViewData>>> minuteList = new ArrayList<>();

        dayList.add(new TimeBean(Methods.getmoutianMD(0)));
        dayList.add(new TimeBean(Methods.getmoutianMD(1)));
        dayList.add(new TimeBean(Methods.getmoutianMD(2)));


        //?????? 2
        ArrayList<String> options2Items02 = Methods.getTodayHourData();
        ArrayList<String> options2Items03 = Methods.getHourData();
        ArrayList<String> options2Items04 = Methods.getHourData();
        hourList.add(options2Items02);
        hourList.add(options2Items03);
        hourList.add(options2Items04);


        //??????3
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

                //?????????????????????????????????????????????
                //??????????????????
                String days = dayList.get(options1).getPickerViewText();
                //0-23???
                String when = hourList.get(options1).get(options2);
                //0-50???
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

                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }).setCancelColor(ContextCompat.getColor(MainActivity.this, R.color.textcolor_7B7F83))
                .setSubmitColor(ContextCompat.getColor(MainActivity.this, R.color.textcolor_ff))
                .setTitleText("?????????????????????")
                .setLayoutRes(R.layout.pickerview_timer_options, null)
                .setLineSpacingMultiplier(2.7f)
                .build();

        builder.setSelectOptions(0, 0, 0);
        builder.setPicker(dayList, hourList, minuteList);
        builder.show();
    }

    public class ItemBean {
        public String name;
        public View.OnClickListener onClickListener;

        public ItemBean(String name, View.OnClickListener onClickListener) {
            this.name = name;
            this.onClickListener = onClickListener;
        }
    }

}
