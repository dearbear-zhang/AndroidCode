package com.mine.dearbear.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mine.dearbear.bean.AppKeyGson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class Utils {
    private static final String TAG = Utils.class.getName();
    public static final int ACCESSALARM = 1;
    private final static String sDATE_FORMATE = "yyyy-MM-dd HH:mm:ss";                              // 日期固定格式
    private final static String sDATE_FORMATE_yyyy_MM_dd_start = "yyyy-MM-dd 00:00:00";             // 日期固定格式,一天开始
    private final static String sDATE_FORMATE_yyyy_MM_dd_end = "yyyy-MM-dd 23:59:59";               // 日期固定格式,一天结束


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.d("Utils", scale + "");
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /***
     * 字符串时间格式 ==> 时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static long dateStringToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sDATE_FORMATE);
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    /***
     * 时间戳 ==> 字符串时间格式
     * @param time
     * @return
     */
    public static String stampToDateString(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sDATE_FORMATE);
        return simpleDateFormat.format(date);
    }

    /***
     * 根据时间戳,返回今日开始的字符串格式
     * @param time
     * @return
     */
    public static String stampToDateString_yyyymmddStart(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sDATE_FORMATE_yyyy_MM_dd_start);
        return simpleDateFormat.format(date);
    }

    /***
     * 根据时间戳,返回今日结束的字符串格式
     * @param time
     * @return
     */
    public static String stampToDateString_yyyymmddEnd(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sDATE_FORMATE_yyyy_MM_dd_end);
        return simpleDateFormat.format(date);
    }

    /***
     * 获取当前系统时间, 以 yyyy-MM-dd HH:mm:ss 格式输出
     * @return
     */
    public static String getCurrentDateFormat() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(sDATE_FORMATE);
        return format.format(date);
    }

    /***
     * 获取今日开始时间戳
     * @return
     */
    public static long getTodayStartTime() {
        long time = 0;
        try {
            time = dateStringToStamp(stampToDateString_yyyymmddStart(System.currentTimeMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /***
     * 获取今日结束时间戳
     * @return
     */
    public static long getTodayEndTime() {
        long time = 0;
        try {
            time = dateStringToStamp(stampToDateString_yyyymmddEnd(System.currentTimeMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    /***
     * json格式Gson解析
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parsonJson(String json, Class<T> clazz) {
//		Gson gson = new Gson();
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new JsonDeserializer<List<?>>() {
            @Override
            public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (json.isJsonArray()) {
                    JsonArray array = json.getAsJsonArray();
                    Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                    List list = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        JsonElement element = array.get(i);
                        Object item = context.deserialize(element, itemType);
                        list.add(item);
                    }
                    return list;
                } else {
                    //和接口类型不符，返回空List
                    return Collections.EMPTY_LIST;
                }
            }
        }).create();
        return gson.fromJson(json, clazz);
    }

    /***
     * 获取appToken
     */
    public static void getAppToken(final Context context, final CallBack callBack) {
        Observer<AppKeyGson> observer = new Observer<AppKeyGson>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull AppKeyGson appKeyGson) {
                Constants.sAppToken = appKeyGson.getToken();
                if (callBack != null) {
                    callBack.onSuccess(appKeyGson);
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("获取token失败")
                        .setMessage(throwable.getMessage())
                        .setPositiveButton("重新连接?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getAppToken(context, callBack);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                // 点击对话框外不取消
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                if (callBack != null) {
                    callBack.onError(throwable);
                }
            }

            @Override
            public void onComplete() {

            }
        };
//		HttpMethods.getInstance(context).getAppToken(subscriber, Constants.sHttpAppKey);
    }

    /***
     * 获取app版本名
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /***
     * 获取运行模式对应字符串
     * @param motionMode
     * @return
     */
    public static String getRobotMotionMode(int motionMode) {
        String message = "未知";
        switch (motionMode) {
            case 0:
                message = "未知";
                break;
            case 1:
                message = "空闲模式";
                break;
            case 2:
                message = "手动模式";
                break;
            case 3:
                message = "采集模式";
                break;
            case 4:
                message = "自动模式";
                break;
            case 5:
                message = "充电模式";
                break;
            case 6:
                message = "异常模式";
                break;
            default:
                break;
        }
        return message;
    }

    public interface CallBack {
        void onError(Throwable throwable);

        void onSuccess(AppKeyGson appKeyGson);
    }
}
