package ola.com.pickerview.view;

import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ola.com.dialog.R;
import ola.com.pickerview.adapter.ArrayWheelAdapter;
import ola.com.pickerview.adapter.NumericWheelAdapter;
import ola.com.pickerview.listener.ISelectTimeCallback;
import ola.com.pickerview.listener.OnItemSelectedListener;
import ola.com.pickerview.utils.ChinaDate;
import ola.com.pickerview.utils.LunarCalendar;


public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private View view;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private WheelView wvHours;
    private WheelView wvMinutes;
    private WheelView wvSeconds;
    private int gravity;

    private boolean[] type;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;

    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    private int endDay = DEFAULT_END_DAY; //表示31天的
    private int currentYear;

    private int textSize;

    //文字的颜色和分割线的颜色
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;

    private float lineSpacingMultiplier;
    private WheelView.DividerType dividerType;
    private boolean isLunarCalendar = false;
    private ISelectTimeCallback mSelectChangeCallback;

    public WheelTime(View view, boolean[] type, int gravity, int textSize) {
        super();
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
    }

    public void setLunarMode(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public boolean isLunarMode() {
        return isLunarCalendar;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0, 0);
    }

    public void setPicker(int year, final int month, int day, int h, int m, int s) {
        if (isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1] - 1, lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s);
        }
    }

    /**
     * 设置农历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setLunar(int year, final int month, int day, boolean isLeap, int h, int m, int s) {
        // 年
        wvYear = (WheelView) view.findViewById(R.id.year);
        wvYear.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(startYear, endYear)));// 设置"年"的显示数据
        wvYear.setLabel("");// 添加文字
        wvYear.setCurrentItem(year - startYear);// 初始化时显示的数据
        wvYear.setGravity(gravity);

        // 月
        wvMonth = (WheelView) view.findViewById(R.id.month);
        wvMonth.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year)));
        wvMonth.setLabel("");
        
        int leapMonth = ChinaDate.leapMonth(year);
        if (leapMonth != 0 && (month > leapMonth - 1 || isLeap)) { //选中月是闰月或大于闰月
            wvMonth.setCurrentItem(month + 1);
        } else {
            wvMonth.setCurrentItem(month);
        }
        
        wvMonth.setGravity(gravity);

        // 日
        wvDay = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (ChinaDate.leapMonth(year) == 0) {
            wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wvDay.setLabel("");
        wvDay.setCurrentItem(day - 1);
        wvDay.setGravity(gravity);

        wvHours = (WheelView) view.findViewById(R.id.hour);
        wvHours.setAdapter(new NumericWheelAdapter(0, 23));
        //wvHours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wvHours.setCurrentItem(h);
        wvHours.setGravity(gravity);

        wvMinutes = (WheelView) view.findViewById(R.id.min);
        wvMinutes.setAdapter(new NumericWheelAdapter(0, 59));
        //wvMinutes.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wvMinutes.setCurrentItem(m);
        wvMinutes.setGravity(gravity);

        wvSeconds = (WheelView) view.findViewById(R.id.second);
        wvSeconds.setAdapter(new NumericWheelAdapter(0, 59));
        //wvSeconds.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wvSeconds.setCurrentItem(m);
        wvSeconds.setGravity(gravity);

        // 添加"年"监听
        wvYear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int yearNum = index + startYear;
                // 判断是不是闰年,来确定月和日的选择
                wvMonth.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(yearNum)));
                if (ChinaDate.leapMonth(yearNum) != 0 && wvMonth.getCurrentItem() > ChinaDate.leapMonth(yearNum) - 1) {
                    wvMonth.setCurrentItem(wvMonth.getCurrentItem() + 1);
                } else {
                    wvMonth.setCurrentItem(wvMonth.getCurrentItem());
                }

                int maxItem = 29;
                if (ChinaDate.leapMonth(yearNum) != 0 && wvMonth.getCurrentItem() > ChinaDate.leapMonth(yearNum) - 1) {
                    if (wvMonth.getCurrentItem() == ChinaDate.leapMonth(yearNum) + 1) {
                        wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(yearNum))));
                        maxItem = ChinaDate.leapDays(yearNum);
                    } else {
                        wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem()))));
                        maxItem = ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem());
                    }
                } else {
                    wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem() + 1))));
                    maxItem = ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem() + 1);
                }

                if (wvDay.getCurrentItem() > maxItem - 1) {
                    wvDay.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"月"监听
        wvMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int monthNum = index;
                int yearNum = wvYear.getCurrentItem() + startYear;
                int maxItem = 29;
                if (ChinaDate.leapMonth(yearNum) != 0 && monthNum > ChinaDate.leapMonth(yearNum) - 1) {
                    if (wvMonth.getCurrentItem() == ChinaDate.leapMonth(yearNum) + 1) {
                        wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(yearNum))));
                        maxItem = ChinaDate.leapDays(yearNum);
                    } else {
                        wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, monthNum))));
                        maxItem = ChinaDate.monthDays(yearNum, monthNum);
                    }
                } else {
                    wvDay.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, monthNum + 1))));
                    maxItem = ChinaDate.monthDays(yearNum, monthNum + 1);
                }

                if (wvDay.getCurrentItem() > maxItem - 1) {
                    wvDay.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangedListener(wvDay);
        setChangedListener(wvHours);
        setChangedListener(wvMinutes);
        setChangedListener(wvSeconds);

        if (type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        wvYear.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wvMonth.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wvDay.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wvHours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wvMinutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wvSeconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    /**
     * 设置公历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setSolar(int year, final int month, int day, int h, int m, int s) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] monthsBig = {"1", "3", "5", "7", "8", "10", "12"};
        String[] monthsLittle = {"4", "6", "9", "11"};

        final List<String> listBig = Arrays.asList(monthsBig);
        final List<String> listLittle = Arrays.asList(monthsLittle);

        currentYear = year;
        // 年
        wvYear = (WheelView) view.findViewById(R.id.year);
        wvYear.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据


        wvYear.setCurrentItem(year - startYear);// 初始化时显示的数据
        wvYear.setGravity(gravity);
        // 月
        wvMonth = (WheelView) view.findViewById(R.id.month);
        if (startYear == endYear) {//开始年等于终止年
            wvMonth.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wvMonth.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            //起始日期的月份控制
            wvMonth.setAdapter(new NumericWheelAdapter(startMonth, 12));
            wvMonth.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            //终止日期的月份控制
            wvMonth.setAdapter(new NumericWheelAdapter(1, endMonth));
            wvMonth.setCurrentItem(month);
        } else {
            wvMonth.setAdapter(new NumericWheelAdapter(1, 12));
            wvMonth.setCurrentItem(month);
        }
        wvMonth.setGravity(gravity);
        // 日
        wvDay = (WheelView) view.findViewById(R.id.day);

        if (startYear == endYear && startMonth == endMonth) {
            if (listBig.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wvDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (listLittle.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wvDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wvDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wvDay.setAdapter(new NumericWheelAdapter(startDay, endDay));
                }
            }
            wvDay.setCurrentItem(day - startDay);
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (listBig.contains(String.valueOf(month + 1))) {

                wvDay.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (listLittle.contains(String.valueOf(month + 1))) {

                wvDay.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wvDay.setAdapter(new NumericWheelAdapter(startDay, 29));
                } else {

                    wvDay.setAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            wvDay.setCurrentItem(day - startDay);
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (listBig.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wvDay.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (listLittle.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wvDay.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wvDay.setAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wvDay.setAdapter(new NumericWheelAdapter(1, endDay));
                }
            }
            wvDay.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (listBig.contains(String.valueOf(month + 1))) {

                wvDay.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (listLittle.contains(String.valueOf(month + 1))) {

                wvDay.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wvDay.setAdapter(new NumericWheelAdapter(1, 29));
                } else {

                    wvDay.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            wvDay.setCurrentItem(day - 1);
        }

        wvDay.setGravity(gravity);
        //时
        wvHours = (WheelView) view.findViewById(R.id.hour);
        wvHours.setAdapter(new NumericWheelAdapter(0, 23));

        wvHours.setCurrentItem(h);
        wvHours.setGravity(gravity);
        //分
        wvMinutes = (WheelView) view.findViewById(R.id.min);
        wvMinutes.setAdapter(new NumericWheelAdapter(0, 59));

        wvMinutes.setCurrentItem(m);
        wvMinutes.setGravity(gravity);
        //秒
        wvSeconds = (WheelView) view.findViewById(R.id.second);
        wvSeconds.setAdapter(new NumericWheelAdapter(0, 59));

        wvSeconds.setCurrentItem(s);
        wvSeconds.setGravity(gravity);

        // 添加"年"监听
        wvYear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int yearNum = index + startYear;
                currentYear = yearNum;
                int currentMonthItem = wvMonth.getCurrentItem();//记录上一次的item位置
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (startYear == endYear) {
                    //重新设置月份
                    wvMonth.setAdapter(new NumericWheelAdapter(startMonth, endMonth));

                    if (currentMonthItem > wvMonth.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wvMonth.getAdapter().getItemsCount() - 1;
                        wvMonth.setCurrentItem(currentMonthItem);
                    }

                    int monthNum = currentMonthItem + startMonth;

                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, startDay, endDay, listBig, listLittle);
                    } else if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, startDay, 31, listBig, listLittle);
                    } else if (monthNum == endMonth) {
                        setReDay(yearNum, monthNum, 1, endDay, listBig, listLittle);
                    } else {//重新设置日
                        setReDay(yearNum, monthNum, 1, 31, listBig, listLittle);
                    }
                } else if (yearNum == startYear) {//等于开始的年
                    //重新设置月份
                    wvMonth.setAdapter(new NumericWheelAdapter(startMonth, 12));

                    if (currentMonthItem > wvMonth.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wvMonth.getAdapter().getItemsCount() - 1;
                        wvMonth.setCurrentItem(currentMonthItem);
                    }

                    int month = currentMonthItem + startMonth;
                    if (month == startMonth) {
                        //重新设置日
                        setReDay(yearNum, month, startDay, 31, listBig, listLittle);
                    } else {
                        //重新设置日
                        setReDay(yearNum, month, 1, 31, listBig, listLittle);
                    }

                } else if (yearNum == endYear) {
                    //重新设置月份
                    wvMonth.setAdapter(new NumericWheelAdapter(1, endMonth));
                    if (currentMonthItem > wvMonth.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wvMonth.getAdapter().getItemsCount() - 1;
                        wvMonth.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + 1;

                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, 1, endDay, listBig, listLittle);
                    } else {
                        //重新设置日
                        setReDay(yearNum, monthNum, 1, 31, listBig, listLittle);
                    }

                } else {
                    //重新设置月份
                    wvMonth.setAdapter(new NumericWheelAdapter(1, 12));
                    //重新设置日
                    setReDay(yearNum, wvMonth.getCurrentItem() + 1, 1, 31, listBig, listLittle);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });


        // 添加"月"监听
        wvMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int monthNum = index + 1;

                if (startYear == endYear) {
                    monthNum = monthNum + startMonth - 1;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(currentYear, monthNum, startDay, endDay, listBig, listLittle);
                    } else if (startMonth == monthNum) {

                        //重新设置日
                        setReDay(currentYear, monthNum, startDay, 31, listBig, listLittle);
                    } else if (endMonth == monthNum) {
                        setReDay(currentYear, monthNum, 1, endDay, listBig, listLittle);
                    } else {
                        setReDay(currentYear, monthNum, 1, 31, listBig, listLittle);
                    }
                } else if (currentYear == startYear) {
                    monthNum = monthNum + startMonth - 1;
                    if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(currentYear, monthNum, startDay, 31, listBig, listLittle);
                    } else {
                        //重新设置日
                        setReDay(currentYear, monthNum, 1, 31, listBig, listLittle);
                    }

                } else if (currentYear == endYear) {
                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(currentYear, wvMonth.getCurrentItem() + 1, 1, endDay, listBig, listLittle);
                    } else {
                        setReDay(currentYear, wvMonth.getCurrentItem() + 1, 1, 31, listBig, listLittle);
                    }

                } else {
                    //重新设置日
                    setReDay(currentYear, monthNum, 1, 31, listBig, listLittle);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangedListener(wvDay);
        setChangedListener(wvHours);
        setChangedListener(wvMinutes);
        setChangedListener(wvSeconds);

        if (type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        wvYear.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wvMonth.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wvDay.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wvHours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wvMinutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wvSeconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    private void setChangedListener(WheelView wheelView) {
        if (mSelectChangeCallback != null) {
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            });
        }

    }


    private void setReDay(int yearNum, int monthNum, int startD, int endD, List<String> listBig, List<String> listLittle) {
        int currentItem = wvDay.getCurrentItem();

//        int maxItem;
        if (listBig.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            wvDay.setAdapter(new NumericWheelAdapter(startD, endD));
//            maxItem = endD;
        } else if (listLittle.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            wvDay.setAdapter(new NumericWheelAdapter(startD, endD));
//            maxItem = endD;
        } else {
            if ((yearNum % 4 == 0 && yearNum % 100 != 0)
                    || yearNum % 400 == 0) {
                if (endD > 29) {
                    endD = 29;
                }
                wvDay.setAdapter(new NumericWheelAdapter(startD, endD));
//                maxItem = endD;
            } else {
                if (endD > 28) {
                    endD = 28;
                }
                wvDay.setAdapter(new NumericWheelAdapter(startD, endD));
//                maxItem = endD;
            }
        }

        if (currentItem > wvDay.getAdapter().getItemsCount() - 1) {
            currentItem = wvDay.getAdapter().getItemsCount() - 1;
            wvDay.setCurrentItem(currentItem);
        }
    }


    private void setContentTextSize() {
        wvDay.setTextSize(textSize);
        wvMonth.setTextSize(textSize);
        wvYear.setTextSize(textSize);
        wvHours.setTextSize(textSize);
        wvMinutes.setTextSize(textSize);
        wvSeconds.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wvDay.setTextColorOut(textColorOut);
        wvMonth.setTextColorOut(textColorOut);
        wvYear.setTextColorOut(textColorOut);
        wvHours.setTextColorOut(textColorOut);
        wvMinutes.setTextColorOut(textColorOut);
        wvSeconds.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wvDay.setTextColorCenter(textColorCenter);
        wvMonth.setTextColorCenter(textColorCenter);
        wvYear.setTextColorCenter(textColorCenter);
        wvHours.setTextColorCenter(textColorCenter);
        wvMinutes.setTextColorCenter(textColorCenter);
        wvSeconds.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wvDay.setDividerColor(dividerColor);
        wvMonth.setDividerColor(dividerColor);
        wvYear.setDividerColor(dividerColor);
        wvHours.setDividerColor(dividerColor);
        wvMinutes.setDividerColor(dividerColor);
        wvSeconds.setDividerColor(dividerColor);
    }

    private void setDividerType() {

        wvDay.setDividerType(dividerType);
        wvMonth.setDividerType(dividerType);
        wvYear.setDividerType(dividerType);
        wvHours.setDividerType(dividerType);
        wvMinutes.setDividerType(dividerType);
        wvSeconds.setDividerType(dividerType);

    }

    private void setLineSpacingMultiplier() {
        wvDay.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvMonth.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvYear.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvHours.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvMinutes.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvSeconds.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    public void setLabels(String labelYear, String labelMonth, String labelDay, String labelHours, String labelMins, String labelSeconds) {
        if (isLunarCalendar) {
            return;
        }

        if (labelYear != null) {
            wvYear.setLabel(labelYear);
        } else {
            wvYear.setLabel(view.getContext().getString(R.string.pickerview_year));
        }
        if (labelMonth != null) {
            wvMonth.setLabel(labelMonth);
        } else {
            wvMonth.setLabel(view.getContext().getString(R.string.pickerview_month));
        }
        if (labelDay != null) {
            wvDay.setLabel(labelDay);
        } else {
            wvDay.setLabel(view.getContext().getString(R.string.pickerview_day));
        }
        if (labelHours != null) {
            wvHours.setLabel(labelHours);
        } else {
            wvHours.setLabel(view.getContext().getString(R.string.pickerview_hours));
        }
        if (labelMins != null) {
            wvMinutes.setLabel(labelMins);
        } else {
            wvMinutes.setLabel(view.getContext().getString(R.string.pickerview_minutes));
        }
        if (labelSeconds != null) {
            wvSeconds.setLabel(labelSeconds);
        } else {
            wvSeconds.setLabel(view.getContext().getString(R.string.pickerview_seconds));
        }

    }

    public void setTextXOffset(int xOffsetYear, int xOffsetMonth, int xOffsetDay,
                               int xOffsetHours, int xOffsetMinutes, int xOffsetSeconds) {
        wvDay.setTextXOffset(xOffsetYear);
        wvMonth.setTextXOffset(xOffsetMonth);
        wvYear.setTextXOffset(xOffsetDay);
        wvHours.setTextXOffset(xOffsetHours);
        wvMinutes.setTextXOffset(xOffsetMinutes);
        wvSeconds.setTextXOffset(xOffsetSeconds);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wvYear.setCyclic(cyclic);
        wvMonth.setCyclic(cyclic);
        wvDay.setCyclic(cyclic);
        wvHours.setCyclic(cyclic);
        wvMinutes.setCyclic(cyclic);
        wvSeconds.setCyclic(cyclic);
    }

    public String getTime() {
        if (isLunarCalendar) {
            //如果是农历 返回对应的公历时间
            return getLunarTime();
        }
        StringBuilder sb = new StringBuilder();
        if (currentYear == startYear) {
           /* int i = wvMonth.getCurrentItem() + startMonth;
            System.out.println("i:" + i);*/
            if ((wvMonth.getCurrentItem() + startMonth) == startMonth) {
                sb.append((wvYear.getCurrentItem() + startYear)).append("-")
                        .append((wvMonth.getCurrentItem() + startMonth)).append("-")
                        .append((wvDay.getCurrentItem() + startDay)).append(" ")
                        .append(wvHours.getCurrentItem()).append(":")
                        .append(wvMinutes.getCurrentItem()).append(":")
                        .append(wvSeconds.getCurrentItem());
            } else {
                sb.append((wvYear.getCurrentItem() + startYear)).append("-")
                        .append((wvMonth.getCurrentItem() + startMonth)).append("-")
                        .append((wvDay.getCurrentItem() + 1)).append(" ")
                        .append(wvHours.getCurrentItem()).append(":")
                        .append(wvMinutes.getCurrentItem()).append(":")
                        .append(wvSeconds.getCurrentItem());
            }

        } else {
            sb.append((wvYear.getCurrentItem() + startYear)).append("-")
                    .append((wvMonth.getCurrentItem() + 1)).append("-")
                    .append((wvDay.getCurrentItem() + 1)).append(" ")
                    .append(wvHours.getCurrentItem()).append(":")
                    .append(wvMinutes.getCurrentItem()).append(":")
                    .append(wvSeconds.getCurrentItem());
        }

        return sb.toString();
    }


    /**
     * 农历返回对应的公历时间
     *
     * @return
     */
    private String getLunarTime() {
        StringBuilder sb = new StringBuilder();
        int year = wvYear.getCurrentItem() + startYear;
        int month = 1;
        boolean isLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0) {
            month = wvMonth.getCurrentItem() + 1;
        } else {
            if ((wvMonth.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                month = wvMonth.getCurrentItem() + 1;
            } else if ((wvMonth.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                month = wvMonth.getCurrentItem();
                isLeapMonth = true;
            } else {
                month = wvMonth.getCurrentItem();
            }
        }
        int day = wvDay.getCurrentItem() + 1;
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);

        sb.append(solar[0]).append("-")
                .append(solar[1]).append("-")
                .append(solar[2]).append(" ")
                .append(wvHours.getCurrentItem()).append(":")
                .append(wvMinutes.getCurrentItem()).append(":")
                .append(wvSeconds.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }


    public void setRangDate(Calendar startDate, Calendar endDate) {

        if (startDate == null && endDate != null) {
            int year = endDate.get(Calendar.YEAR);
            int month = endDate.get(Calendar.MONTH) + 1;
            int day = endDate.get(Calendar.DAY_OF_MONTH);
            if (year > startYear) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (year == startYear) {
                if (month > startMonth) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                } else if (month == startMonth) {
                    if (day > startDay) {
                        this.endYear = year;
                        this.endMonth = month;
                        this.endDay = day;
                    }
                }
            }

        } else if (startDate != null && endDate == null) {
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH) + 1;
            int day = startDate.get(Calendar.DAY_OF_MONTH);
            if (year < endYear) {
                this.startMonth = month;
                this.startDay = day;
                this.startYear = year;
            } else if (year == endYear) {
                if (month < endMonth) {
                    this.startMonth = month;
                    this.startDay = day;
                    this.startYear = year;
                } else if (month == endMonth) {
                    if (day < endDay) {
                        this.startMonth = month;
                        this.startDay = day;
                        this.startYear = year;
                    }
                }
            }

        } else if (startDate != null && endDate != null) {
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        }

    }

    /**
     * 设置间距倍数,但是只能在1.0-4.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * @param isCenterLabel 是否只显示中间选中项的
     */
    public void isCenterLabel(boolean isCenterLabel) {
        wvDay.isCenterLabel(isCenterLabel);
        wvMonth.isCenterLabel(isCenterLabel);
        wvYear.isCenterLabel(isCenterLabel);
        wvHours.isCenterLabel(isCenterLabel);
        wvMinutes.isCenterLabel(isCenterLabel);
        wvSeconds.isCenterLabel(isCenterLabel);
    }

    public void setSelectChangeCallback(ISelectTimeCallback mSelectChangeCallback) {
        this.mSelectChangeCallback = mSelectChangeCallback;
    }
}
