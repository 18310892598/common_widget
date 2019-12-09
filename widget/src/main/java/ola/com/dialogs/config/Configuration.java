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

    public static int getTitleColor(short app) {
        switch (app) {
            case APP_DRIVER:
                return R.color.dia_title_driver;
            case APP_USER:
                return R.color.dia_title_user;
            default:
                return 0;
        }
    }

    public static int getContentColor(short app) {
        switch (app) {
            case APP_DRIVER:
                return R.color.dia_content_driver;
            case APP_USER:
                return R.color.dia_content_user;
            default:
                return 0;
        }
    }

    public static int getPositiveColor(short app) {
        switch (app) {
            case APP_DRIVER:
                return R.color.dia_pos_driver;
            case APP_USER:
                return R.color.dia_pos_user;
            default:
                return 0;
        }
    }

    public static int getNegativeColor(short app) {
        switch (app) {
            case APP_DRIVER:
                return R.color.dia_neg_driver;
            case APP_USER:
                return R.color.dia_neg_user;
            default:
                return 0;
        }
    }
}
