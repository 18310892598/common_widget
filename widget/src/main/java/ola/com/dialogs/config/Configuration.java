package ola.com.dialogs.config;

import ola.com.dialog.R;

public class Configuration {

    public static final short APP_DRIVER = 1;
    public static final short APP_USER = 0;

    public static int getVersionHead(short app) {
        switch (app) {
            case APP_DRIVER:
                return R.drawable.dia_pic_version_update_driver;
            case APP_USER:
                return R.drawable.dia_pic_version_update_user;
            default:
                return 0;
        }
    }

    public static int getVersionButton(short app) {
        switch (app) {
            case APP_DRIVER:
                return R.drawable.shape_update_button_driver;
            case APP_USER:
                return R.drawable.shape_update_button_user;
            default:
                return 0;
        }
    }
}
