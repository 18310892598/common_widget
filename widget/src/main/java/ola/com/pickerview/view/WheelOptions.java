package ola.com.pickerview.view;

import android.graphics.Typeface;
import android.view.View;


import java.util.List;

import ola.com.dialog.R;
import ola.com.pickerview.adapter.ArrayWheelAdapter;
import ola.com.pickerview.listener.OnItemSelectedListener;
import ola.com.pickerview.listener.OnOptionsSelectChangeListener;

public class WheelOptions<T> {
    private View view;
    private WheelView wvOption1;
    private WheelView wvOption2;
    private WheelView wvOption3;

    private List<T> mOptions1Items;
    private List<List<T>> mOptions2Items;
    private List<List<List<T>>> mOptions3Items;

    private boolean linkage = true;//默认联动
    private boolean isRestoreItem; //切换时，还原第一项
    private OnItemSelectedListener wheelListenerOption1;
    private OnItemSelectedListener wheelListenerOption2;

    private OnOptionsSelectChangeListener optionsSelectChangeListener;

    //文字的颜色和分割线的颜色
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;

    private WheelView.DividerType dividerType;

    // 条目间距倍数
    private float lineSpacingMultiplier;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelOptions(View view, boolean isRestoreItem) {
        super();
        this.isRestoreItem = isRestoreItem;
        this.view = view;
        wvOption1 = (WheelView) view.findViewById(R.id.options1);// 初始化时显示的数据
        wvOption2 = (WheelView) view.findViewById(R.id.options2);
        wvOption3 = (WheelView) view.findViewById(R.id.options3);
    }


    public void setPicker(List<T> options1Items,
                          List<List<T>> options2Items,
                          List<List<List<T>>> options3Items) {
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;

        // 选项1
        wvOption1.setAdapter(new ArrayWheelAdapter(mOptions1Items));// 设置显示数据
        wvOption1.setCurrentItem(0);// 初始化时显示的数据
        // 选项2
        if (mOptions2Items != null) {
            wvOption2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(0)));// 设置显示数据
        }
        wvOption2.setCurrentItem(wvOption2.getCurrentItem());// 初始化时显示的数据
        // 选项3
        if (mOptions3Items != null) {
            wvOption3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(0).get(0)));// 设置显示数据
        }
        wvOption3.setCurrentItem(wvOption3.getCurrentItem());
        wvOption1.setIsOptions(true);
        wvOption2.setIsOptions(true);
        wvOption3.setIsOptions(true);

        if (this.mOptions2Items == null) {
            wvOption2.setVisibility(View.GONE);
        } else {
            wvOption2.setVisibility(View.VISIBLE);
        }
        if (this.mOptions3Items == null) {
            wvOption3.setVisibility(View.GONE);
        } else {
            wvOption3.setVisibility(View.VISIBLE);
        }

        // 联动监听器
        wheelListenerOption1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt2Select = 0;
                if (mOptions2Items == null) {//只有1级联动数据
                    if (optionsSelectChangeListener != null) {
                        optionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), 0, 0);
                    }
                } else {
                    if (!isRestoreItem) {
                        opt2Select = wvOption2.getCurrentItem();//上一个opt2的选中位置
                        //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                        opt2Select = opt2Select >= mOptions2Items.get(index).size() - 1 ? mOptions2Items.get(index).size() - 1 : opt2Select;
                    }
                    wvOption2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(index)));
                    wvOption2.setCurrentItem(opt2Select);

                    if (mOptions3Items != null) {
                        wheelListenerOption2.onItemSelected(opt2Select);
                    } else {//只有2级联动数据，滑动第1项回调
                        if (optionsSelectChangeListener != null) {
                            optionsSelectChangeListener.onOptionsSelectChanged(index, opt2Select, 0);
                        }
                    }
                }
            }
        };

        wheelListenerOption2 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                if (mOptions3Items != null) {
                    int opt1Select = wvOption1.getCurrentItem();
                    opt1Select = opt1Select >= mOptions3Items.size() - 1 ? mOptions3Items.size() - 1 : opt1Select;
                    index = index >= mOptions2Items.get(opt1Select).size() - 1 ? mOptions2Items.get(opt1Select).size() - 1 : index;
                    int opt3 = 0;
                    if (!isRestoreItem) {
                        // wvOption3.getCurrentItem() 上一个opt3的选中位置
                        //新opt3的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                        opt3 = wvOption3.getCurrentItem() >= mOptions3Items.get(opt1Select).get(index).size() - 1 ?
                                mOptions3Items.get(opt1Select).get(index).size() - 1 : wvOption3.getCurrentItem();
                    }
                    wvOption3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(wvOption1.getCurrentItem()).get(index)));
                    wvOption3.setCurrentItem(opt3);

                    //3级联动数据实时回调
                    if (optionsSelectChangeListener != null) {
                        optionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), index, opt3);
                    }
                } else {//只有2级联动数据，滑动第2项回调
                    if (optionsSelectChangeListener != null) {
                        optionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), index, 0);
                    }
                }
            }
        };

        // 添加联动监听
        if (options1Items != null && linkage) {
            wvOption1.setOnItemSelectedListener(wheelListenerOption1);
        }
        if (options2Items != null && linkage) {
            wvOption2.setOnItemSelectedListener(wheelListenerOption2);
        }
        if (options3Items != null && linkage && optionsSelectChangeListener != null) {
            wvOption3.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    optionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), wvOption2.getCurrentItem(), index);
                }
            });
        }
    }


    //不联动情况下
    public void setNPicker(List<T> options1Items, List<T> options2Items, List<T> options3Items) {

        // 选项1
        wvOption1.setAdapter(new ArrayWheelAdapter(options1Items));// 设置显示数据
        wvOption1.setCurrentItem(0);// 初始化时显示的数据
        // 选项2
        if (options2Items != null) {
            wvOption2.setAdapter(new ArrayWheelAdapter(options2Items));// 设置显示数据
        }
        wvOption2.setCurrentItem(wvOption2.getCurrentItem());// 初始化时显示的数据
        // 选项3
        if (options3Items != null) {
            wvOption3.setAdapter(new ArrayWheelAdapter(options3Items));// 设置显示数据
        }
        wvOption3.setCurrentItem(wvOption3.getCurrentItem());
        wvOption1.setIsOptions(true);
        wvOption2.setIsOptions(true);
        wvOption3.setIsOptions(true);

        if (optionsSelectChangeListener != null) {
            wvOption1.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    optionsSelectChangeListener.onOptionsSelectChanged(index, wvOption2.getCurrentItem(), wvOption3.getCurrentItem());
                }
            });
        }

        if (options2Items == null) {
            wvOption2.setVisibility(View.GONE);
        } else {
            wvOption2.setVisibility(View.VISIBLE);
            if (optionsSelectChangeListener != null) {
                wvOption2.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        optionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), index, wvOption3.getCurrentItem());
                    }
                });
            }
        }
        if (options3Items == null) {
            wvOption3.setVisibility(View.GONE);
        } else {
            wvOption3.setVisibility(View.VISIBLE);
            if (optionsSelectChangeListener != null) {
                wvOption3.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        optionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), wvOption2.getCurrentItem(), index);
                    }
                });
            }
        }
    }

    public void setTextContentSize(int textSize) {
        wvOption1.setTextSize(textSize);
        wvOption2.setTextSize(textSize);
        wvOption3.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wvOption1.setTextColorOut(textColorOut);
        wvOption2.setTextColorOut(textColorOut);
        wvOption3.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wvOption1.setTextColorCenter(textColorCenter);
        wvOption2.setTextColorCenter(textColorCenter);
        wvOption3.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wvOption1.setDividerColor(dividerColor);
        wvOption2.setDividerColor(dividerColor);
        wvOption3.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wvOption1.setDividerType(dividerType);
        wvOption2.setDividerType(dividerType);
        wvOption3.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wvOption1.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvOption2.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvOption3.setLineSpacingMultiplier(lineSpacingMultiplier);

    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null) {
            wvOption1.setLabel(label1);
        }
        if (label2 != null) {
            wvOption2.setLabel(label2);
        }
        if (label3 != null) {
            wvOption3.setLabel(label3);
        }
    }

    /**
     * 设置x轴偏移量
     */
    public void setTextXOffset(int xOffsetOne, int xOffsetTwo, int xOffsetThree) {
        wvOption1.setTextXOffset(xOffsetOne);
        wvOption2.setTextXOffset(xOffsetTwo);
        wvOption3.setTextXOffset(xOffsetThree);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wvOption1.setCyclic(cyclic);
        wvOption2.setCyclic(cyclic);
        wvOption3.setCyclic(cyclic);
    }

    /**
     * 设置字体样式
     *
     * @param font 系统提供的几种样式
     */
    public void setTypeface(Typeface font) {
        wvOption1.setTypeface(font);
        wvOption2.setTypeface(font);
        wvOption3.setTypeface(font);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3 是否循环
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wvOption1.setCyclic(cyclic1);
        wvOption2.setCyclic(cyclic2);
        wvOption3.setCyclic(cyclic3);
    }


    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2。
     * 在快速滑动未停止时，点击确定按钮，会进行判断，如果匹配数据越界，则设为0，防止index出错导致崩溃。
     *
     * @return 索引数组
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wvOption1.getCurrentItem();

        if (mOptions2Items != null && mOptions2Items.size() > 0) {//非空判断
            currentItems[1] = wvOption2.getCurrentItem() > (mOptions2Items.get(currentItems[0]).size() - 1) ? 0 : wvOption2.getCurrentItem();
        } else {
            currentItems[1] = wvOption2.getCurrentItem();
        }

        if (mOptions3Items != null && mOptions3Items.size() > 0) {//非空判断
            currentItems[2] = wvOption3.getCurrentItem() > (mOptions3Items.get(currentItems[0]).get(currentItems[1]).size() - 1) ? 0 : wvOption3.getCurrentItem();
        } else {
            currentItems[2] = wvOption3.getCurrentItem();
        }

        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        } else {
            wvOption1.setCurrentItem(option1);
            wvOption2.setCurrentItem(option2);
            wvOption3.setCurrentItem(option3);
        }
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (mOptions1Items != null) {
            wvOption1.setCurrentItem(opt1Select);
        }
        if (mOptions2Items != null) {
            wvOption2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(opt1Select)));
            wvOption2.setCurrentItem(opt2Select);
        }
        if (mOptions3Items != null) {
            wvOption3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(opt1Select).get(opt2Select)));
            wvOption3.setCurrentItem(opt3Select);
        }
    }

    /**
     * 设置间距倍数,但是只能在1.2-4.0f之间
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
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */

    public void isCenterLabel(boolean isCenterLabel) {
        wvOption1.isCenterLabel(isCenterLabel);
        wvOption2.isCenterLabel(isCenterLabel);
        wvOption3.isCenterLabel(isCenterLabel);
    }

    public void setOptionsSelectChangeListener(OnOptionsSelectChangeListener optionsSelectChangeListener) {
        this.optionsSelectChangeListener = optionsSelectChangeListener;
    }

    public void setLinkage(boolean linkage) {
        this.linkage = linkage;
    }

    public void setitemsVisibleCount(int itemsVisible){
        wvOption1.setitemsVisibleCount(itemsVisible);
        wvOption2.setitemsVisibleCount(itemsVisible);
        wvOption3.setitemsVisibleCount(itemsVisible);
    }
}
