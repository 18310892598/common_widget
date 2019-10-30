1.对话框
对话框上，封装后的使用方式是尽可能贴近此前的使用方式的，因此，此前使用的代码大都只需要修改DialogHelper的引用路径即可。

被封装的核心类是OleDialog类，可以实现生成对话框、设置文本及点击事件等操作。该类的简单实用如下：
```javascript
OleDialog oleDialog = OleDialog.getInstance(context, layout)
        .setResource(title, content, okTitle, cancelTitle)
        .setListener(dialoglListener)
        .setTouchCancelable(!notCanceled);
```
该代码不包含展示操作，oleDialog.dialog即为对应的alertdialog，调用对应的show()方法即可展示。相关建议代码如下：
```javascript
dialog = oleDialog.dialog;
if (null != dialog && !dialog.isShowing()) {
    dialog.show();
}
```
被封装的布局文件中，尺寸相关的单位都从此前dimen中引入的单位改为了dp，计算方式为dp=dimen/2。

另外，总结一下DialogHelper中，各种方法的对应样式

1.showDialogRequest

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l2zo8egqj30cy0pumxr.jpg)

2.showDialogCommon

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l32epp7yj30co0pbdgf.jpg)

3.showDialogImgCommon

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l33mm70wj30cm0p9jrz.jpg)

4.giveCoupon

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l34nc9f8j30cm0p8t9g.jpg)

5.showDialogCheckVersion

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l2vhce1yj30cu0psq4j.jpg)

6.showDialogCheckNotify

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l35qcr4yj30cn0pawfz.jpg)

7.showCallPhoneDialog

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l36r9s2uj30cn0p9abw.jpg)

8.showPayLoading

![avatar](https://tva1.sinaimg.cn/large/006y8mN6ly1g7l37vmmy1j30cm0pddgj.jpg)


二、时间选择器
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