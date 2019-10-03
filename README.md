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
2.showDialogCommon
3.showDialogImgCommon
4.giveCoupon
5.showDialogCheckVersion
6.showDialogCheckNotify
7.showCallPhoneDialog
8.showPayLoading
