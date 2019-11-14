package ola.com.dialogs.callback;

public interface DialogListener {
    /**
     * 取消
     */
    void onCancel();

    /**
     * 确定
     */
    void onOk();
}