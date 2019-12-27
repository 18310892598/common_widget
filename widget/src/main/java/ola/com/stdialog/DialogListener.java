package ola.com.stdialog;

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