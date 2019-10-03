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
