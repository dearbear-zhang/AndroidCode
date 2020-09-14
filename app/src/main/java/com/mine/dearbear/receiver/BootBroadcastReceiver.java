package com.mine.dearbear.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BootBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            return;
        }
//        Log.d(TAG, "屏幕常量设置开始");
//        try {
//            PowerManagerHelper.acquireFullWakeLock(context);
//        } catch (Exception e) {
//            Log.e(TAG, "屏幕常量异常: " + e.getMessage());
//        }
//        Log.d(TAG, "屏幕常量设置完成");
    }
}