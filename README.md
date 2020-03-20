当前最新版本:
```javascript
api 'com.ole.travel:widget:1.2.41'
```

一、时间选择器
——TimePickerView 时间选择器，支持年月日时分，年月日，年月，时分等格式。

——OptionsPickerView 选项选择器，支持一，二，三级选项选择，并且可以设置是否联动 。

//时间选择器
```javascript
TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
                           @Override
                           public void onTimeSelect(Date date, View v) {
                               Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                           }
                       }).build();
```
//条件选择器
```javascript
 OptionsPickerView pvOptions = new OptionsPickerBuilder(MainActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvOptions.setText(tx);
            }
        }).build();
 pvOptions.setPicker(options1Items, options2Items, options3Items);
 pvOptions.show(); 
 ```
 
 自定义属性
 ```javascript
 Calendar selectedDate = Calendar.getInstance();
 Calendar startDate = Calendar.getInstance();
 //startDate.set(2013,1,1);
 Calendar endDate = Calendar.getInstance();
 //endDate.set(2020,1,1);
 
  //正确设置方式 原因：注意事项有说明
  startDate.set(2013,0,1);
  endDate.set(2020,11,31);

 pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tvTime.setText(getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
```
```javascript
pvOptions = new  OptionsPickerBuilder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvOptions.setText(tx);
            }
        }) .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                              @Override
                              public void onOptionsSelectChanged(int options1, int options2, int options3) {
                                  String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                                  Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                              }
                          })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
                
    pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
```

二、标准弹窗

使用示例：

```javascript
Objects.requireNonNull(OlaStandardDialog.getInstance(
        StandardDialogMpic.class, getActivity(),
        R.mipmap.icon_prompt_wait_pay, "title", "content", "negative", "positive"))
        .showDialog(getSupportFragmentManager(), new DialogListener() {
            @Override
            public void onCancel() {
                OlaToast.show(getActivity(), "onCancel");
            }

            @Override
            public void onOk() {
                OlaToast.show(getActivity(), "onOk");
            }
        })
```

相关功能函数：
```javascript
    /**
     * 设置点击外部是否关闭对话框
     * @param cancel true 关闭 false 不关闭
     */
    public OlaBaseStandardDialog setCanceledOnTouchOutside(boolean cancel)

    /**
     * 设置关闭按钮
     * 目前只对mpic有效
     * 适用于对话框初始化前
     * @param onClickListener 关闭按钮点击事件
     */
    public OlaBaseStandardDialog enableClose(View.OnClickListener onClickListener)
```

相关样式参考：https://confluence.olafuwu.com/pages/viewpage.action?pageId=19019479#space-menu-link-content

其中：
无图片对应StandardDialogXpic
小图片对应StandardDialogMpic
大图片对应StandardDialogHpic
功能描述提醒对应StandardDialogLast

三、标准popwindow

https://confluence.olafuwu.com/display/oleTerminal/android_popup

### 1.**服务地址** 
```java 
git地址 'https://gitlab.olafuwu.com/ole-terminal/ole-arc/android/common_ui/common_widget.git'  
```

### 2.**使用介绍**
```java 
CommonPopupWindow popupWindow;

    private void showPopwindow(View v) {
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.home_show_choice_number_view)//传入自定义布局
                .setWidthAndHeight(1, 0)//设置布局宽高
                .setAnimationStyle(R.style.picker_view_slide_anim)//设置显示动画
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {//设置自定义布局点击事件
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView choice_one = view.findViewById(R.id.choice_one);
                        TextView choice_two = view.findViewById(R.id.choice_two);
                        ImageView close = view.findViewById(R.id.close);

                        choice_one.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                choice_one.setSelected(true);
                                choice_two.setSelected(false);
                            }
                        });
                        choice_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                choice_one.setSelected(false);
                                choice_two.setSelected(true);
                            }
                        });
                        close.setOnClickListener(view1 -> popupWindow.dismiss());
                    }
                })
                .setOutsideTouchable(true)//设置外部是否可触摸
                .create();

        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);//设置显示位置
    }

```

### 2.**api介绍**
```java 
//* @param layoutResId 设置PopupWindow 布局ID
public Builder setView(int layoutResId) 
//* @param view 设置PopupWindow布局
public Builder setView(View view) 
//* @param listener 字view的点击事件
public Builder setViewOnclickListener(ViewInterface listener)
// 设置宽度和高度 如果不设置 默认是wrap_content
// 1-MATCH_PARENT 0-WRAP_CONTENT
public Builder setWidthAndHeight(int width, int height)
/**
* 设置背景灰色程度
*
* @param level 0.0f-1.0f
* @return Builder
*/
public Builder setBackGroundLevel(float level)
/**
* 是否可点击Outside消失
*
* @param touchable 是否可点击
* @return Builder
*/
public Builder setOutsideTouchable(boolean touchable) 
/**
* 设置动画
*
* @return Builder
*/
public Builder setAnimationStyle(int animationStyle) 
```
