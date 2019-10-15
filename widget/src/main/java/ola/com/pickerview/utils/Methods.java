package ola.com.pickerview.utils;


import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ola.com.pickerview.interfaces.IPickerViewData;

/**
 * Created by 于浩 on 2018/9/18.
 *
 * @TODO:
 */

public class Methods {
    static int TIME = 25-2;//需要跨天的分钟数
    /**
     * 方法描述:获取某天，-1是昨天，0是今天，1是明天依次类推
     *
     * @return 返回值描述
     * @throws <异常类型> {@inheritDoc} 异常描述
     * @param参数描述 格式M月dd日这样的话显示的就是3月12日，否则 MM月dd日格式会选择03月12日
     */

    public static String getmoutianMD(int i) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日E");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String dateString = "";
        try {
            int j;
            int max = currentHour();
            if (max == 23 && currentMin() > TIME) {
                //把日期往后增加一天.整数往后推,负数往前移动
                calendar.add(Calendar.DATE, i+1);
                j = i+1;
            }else {
                calendar.add(Calendar.DATE, i);
                j = i;
            }
            //这个时间就是日期往后推一天的结果
            date = calendar.getTime();
            if (j == 0) {
                String temp = formatter.format(date);
                String temp2 = temp.substring(0, temp.length() - 2);
                temp2 += "今天";
                dateString = temp2;
            } else if (j == 1) {
                String temp = formatter.format(date);
                String temp2 = temp.substring(0, temp.length() - 2);
                temp2 += "明天";
                dateString = temp2;
            } else {
                dateString = formatter.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;


    }


    /**
     * 今天 点
     */
    public static ArrayList<String> getTodayHourData() {
        int max = currentHour();
        if (max < 23 && currentMin() > TIME ) {
            max = max + 1;
        } else if (max == 23 && currentMin() > TIME){
            max = 0;
        }
        ArrayList<String> lists = new ArrayList<>();
        for (int i = max; i < 24; i++) {
            if (i<10) {
                lists.add("0" + i + "点");
            } else {
                lists.add(i + "点");
            }
        }
        return lists;
    }

    /**
     * 明天 后天 点
     */
    public static ArrayList<String> getHourData() {
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i<10) {
                lists.add("0" + i + "点");
            } else {
                lists.add(i + "点");
            }
        }
        return lists;
    }

    /**
     * 得到分钟显示
     */
    public static ArrayList<IPickerViewData> getMinData() {
        ArrayList<IPickerViewData> dataArrayList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                dataArrayList.add(new PickerViewData("00分"));
            } else if (i == 1) {
                dataArrayList.add(new PickerViewData("05分"));
            } else {
                dataArrayList.add(new PickerViewData((i * 5) + "分"));
            }
        }
        return dataArrayList;
    }


    /**
     * 明天 后天 小时
     */
    public static ArrayList<ArrayList<IPickerViewData>> getmD() {
        ArrayList<ArrayList<IPickerViewData>> d = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            d.add(getMinData());
        }
        return d;
    }

    /**
     * 明天 后天  2222
     */
    public static ArrayList<ArrayList<IPickerViewData>> getmD2() {
        //14
        int max = currentHour();
        if (currentMin() > TIME) {
            max = max + 1;
        }
        int value = 24 - max == 0 ? 24 : 24 - max;
        ArrayList<ArrayList<IPickerViewData>> d = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            if (i == 0) {
                d.add(getTodyMinData());
            } else {
                d.add(getMinData());
            }

        }
        return d;
    }


    /**
     * 今天分钟
     */
    public static ArrayList<IPickerViewData> getTodyMinData() {

        int min = currentMin()+2;
        int current = 0;
        if (min <= 5 ) {
            current = 7;
        } else if (min <= 10 ) {
            current = 8;
        } else if (min <= 15 ) {
            current = 9;
        } else if (min <= 20) {
            current = 10;
        } else if (min <= 25) {
            current = 11;
        } else if (min <= 30) {
            current = 0;
        } else if (min <= 35) {
            current = 1;
        } else if (min <= 40) {
            current = 2;
        } else if (min <=45) {
            current = 3;
        } else if (min <= 50) {
            current = 4;
        } else if (min <= 55) {
            current = 5;
        } else if (min <= 60) {
            current = 6;
        } else {
            current = 7;
        }

        int max = currentHour();
        if (max > 23 && min > 35) {
            current = 10;
        }

        ArrayList<IPickerViewData> dataArrayList = new ArrayList<>();
        for (int i = current ; i < 12; i++) {
            if (i == 0) {
                dataArrayList.add(new PickerViewData("00分"));
            } else if (i == 1) {
                dataArrayList.add(new PickerViewData("05分"));
            } else {
                dataArrayList.add(new PickerViewData((i * 5) + "分"));
            }
        }
        return dataArrayList;
    }


    public static int currentMin() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);
    }


    public static int currentHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static String formatPlanTime(String time) {
        //11月21日今天 15:40
        String formatTime = "";
        if (null != time && !"".equals(time) && time.length()>1 ) {
            String month, day, hours, mins;
            Calendar cal = Calendar.getInstance();
            month = time.substring(0, time.indexOf("月"));
            day = time.substring(time.indexOf("月") + 1, time.indexOf("日"));
            hours = time.substring(time.indexOf(" ") + 1, time.indexOf(":"));
            mins = time.substring(time.indexOf(":") + 1, time.length());
            formatTime = cal.get(Calendar.YEAR) + "-" + month + "-" + day + " " + hours + ":" + mins + ":00";
        }
        return formatTime;
    }

    /**
     * 时间戳转为此格式  11月21日今天 15:40
     * @param time
     * @return
     */
    public static String formatPlanTime3(String time) {
        String s ;

        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            s = sdf.format(date);
            Log.i("yyy2",s);
            return fomattime(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String fomattime(String time){
        String[] s = time.split("年");
        String a = s[1];
        return a;
    }

    /**
     * 预约下单 司机时间显示样式
     * @param time
     * @return
     */
    public static String formatPlanTime2(String time) {
        //11月21日今天 15:40
        String formatTime = "";
        if (null != time && !"".equals(time) && time.length()>1 ) {
            String month, day, hours, mins;
            Calendar cal = Calendar.getInstance();
            month = time.substring(0, time.indexOf("月"));
            day = time.substring(time.indexOf("月") + 1, time.indexOf("日"));
            hours = time.substring(time.indexOf(" ") + 1, time.indexOf(":"));
            mins = time.substring(time.indexOf(":") + 1, time.length());
            formatTime = cal.get(Calendar.YEAR) + "." + month + "." + day + " " + hours + ":" + mins;
        }
        return formatTime;
    }
    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    /*
     * 判断时间是否在31分钟内
     */
    public static boolean datecheckTime(String booktime) throws ParseException {
        long currentTime = System.currentTimeMillis() + 31 * 60 * 1000;
        return currentTime < dateToStamp(booktime);
    }

    /**
     * 判断时间是否在31分钟内
     * 格式   11月21日今天 15:40
     */
    public static boolean datecheckIsOk(String time) throws ParseException {
        //11月21日今天 15:40
        String formatTime = "";
        if (null != time && !"".equals(time) && time.length()>1 ) {
            String month, day, hours, mins;
            Calendar cal = Calendar.getInstance();
            month = time.substring(0, time.indexOf("月"));
            day = time.substring(time.indexOf("月") + 1, time.indexOf("日"));
            hours = time.substring(time.indexOf(" ") + 1, time.indexOf(":"));
            mins = time.substring(time.indexOf(":") + 1, time.length());
            formatTime = cal.get(Calendar.YEAR) + "-" + month + "-" + day + " " + hours + ":" + mins + ":00";
        }
        long currentTime = System.currentTimeMillis() + 31 * 60 * 1000;
        return currentTime < dateToStamp(formatTime);
    }
}
