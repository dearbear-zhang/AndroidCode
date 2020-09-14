package com.mine.dearbear.utils;

import android.content.Context;
import android.content.SharedPreferences;


public final class CustomSharedPreferences {
    /**
     * 系统自定义Preferences名称
     */
    public static final String PREFS_NAME = "MyPrefsFile";
    /**
     * SD卡加载KEY
     */
    public static final String PREFS_SDCARDMOUNT = "sdcardMount";
    /**
     * UsbStorages加载KEY
     */
    public static final String PREFS_USBSTORAGESMOUNT = "usbstoragesMount";
    /**
     * 文件导入KEY
     */
    public static final String PREFS_FILEOPERATE = "fileOperate";

    /**
     * 通过key获取自定义属性值(boolean类型)
     *
     * @param context 应用环境上下文
     * @param key     自定义属性key
     * @return 自定义属性值(boolean类型)
     */
    public static boolean getBooleanPrefsByKey(Context context, String key) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(key, false);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @return
     * @throws
     * @Title getBooleanPrefsByKey
     * @Description TODO(这里用一句话描述这个方法的作用)
     */
    public static boolean getBooleanPrefsByKey(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 通过key设置自定义属性值(boolean类型)
     *
     * @param context 应用环境上下文
     * @param key     自定义属性key
     * @param value   要设置的自定义属性值(boolean类型)
     */
    public static void setBooleanPrefsByKey(Context context, String key,
                                            boolean value) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 通过key获取自定义属性值(int类型)
     *
     * @param context 应用环境上下文
     * @param key     自定义属性key
     * @return 自定义属性值(int类型)
     */
    public static int getIntPrefsByKey(Context context, String key) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(key, 0);
    }

    /**
     * @param context
     * @param key
     * @param defValue
     * @return
     * @throws
     * @Title getIntPrefsByKey
     * @Description TODO(这里用一句话描述这个方法的作用)
     */
    public static int getIntPrefsByKey(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(key, defValue);
    }

    /**
     * 通过key设置自定义属性值(int类型)
     *
     * @param context 应用环境上下文
     * @param key     自定义属性key
     * @param value   要设置的自定义属性值(int类型)
     */
    public static void setIntPrefsByKey(Context context, String key,
                                        int value) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 通过key设置自定义属性值（String类型）
     *
     * @param context 应用环境上下文对象
     * @param key     自定义属性key
     * @param value   要设置的自定义属性值（String类型）
     */
    public static void setStringPrefsByKey(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 通过key取得自定义属性值（String）
     *
     * @param context  应用环境上下文对象
     * @param key      自定义属性key
     * @param defValue 默认值，如果默认值为null对象，设置为空字符串
     * @return
     */
    public static String getStringPrefsByKey(Context context, String key, String defValue) {
        if (defValue == null) {
            defValue = "";
        }
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(key, defValue);
    }

    /**
     * 通过key设置自定义属性值（float类型）
     *
     * @param context 应用环境上下文对象
     * @param key     自定义属性key
     * @param value   要设置的自定义属性值（float类型）
     */
    public static void setFloatPrefsByKey(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 通过key取得自定义属性值（float）
     *
     * @param context  应用环境上下文对象
     * @param key      自定义属性key
     * @param defValue 默认值，如果默认值为null对象
     * @return
     */
    public static float getFloatPrefsByKey(Context context, String key, float defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getFloat(key, defValue);
    }
}
