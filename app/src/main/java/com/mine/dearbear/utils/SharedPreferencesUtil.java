package com.mine.dearbear.utils;

import android.content.Context;

public class SharedPreferencesUtil {
    // 联机模式下的热点账号:
    private static final String SP_KEY_AP_SSID = "ApSsid";
    // 联机模式下的热点密码:
    private static final String SP_KEY_AP_PASSWORD = "ApPassword";
    // 联机模式下的热点账号默认值:
    private static final String SP_AP_SSID_DEFAULT = "ssid";
    // 联机模式下的热点密码默认值:
    private static final String SP_AP_PASSWORD_DEFAULT = "password";


    public static void setApSsid(Context context, String ssid) {
        CustomSharedPreferences.setStringPrefsByKey(context, SP_KEY_AP_SSID, ssid);
    }

    public static String getApSsid(Context context) {
        return CustomSharedPreferences.getStringPrefsByKey(context, SP_KEY_AP_SSID, SP_AP_SSID_DEFAULT);
    }

    public static void setApPwd(Context context, String pwd) {
        CustomSharedPreferences.setStringPrefsByKey(context, SP_KEY_AP_PASSWORD, pwd);
    }

    public static String getApPwd(Context context) {
        return CustomSharedPreferences.getStringPrefsByKey(context, SP_KEY_AP_PASSWORD, SP_AP_PASSWORD_DEFAULT);
    }

}
