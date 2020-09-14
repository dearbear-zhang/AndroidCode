package com.mine.dearbear.utils;

import android.content.Context;
import android.os.PowerManager;

/**
 * 控制Android系统休眠类
 *
 * @author qisk
 * @date 2012-1-19
 */
public final class PowerManagerHelper {

    private static PowerManager.WakeLock mWakeLock = null;

    /**
     * @param context 应用程序上下文对象
     * @throws
     * @Title acquireWakeLock
     * @Description TODO 防止Android系统因为长期不操作而进入休眠,屏幕亮度最暗显示(该方法无法屏蔽按下电源键进入休眠)
     */
    public static void acquireWakeLock(Context context) {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
                        context.getClass().getCanonicalName());
                mWakeLock.acquire();
            }
        }
    }

    /**
     * @param context 应用程序上下文对象
     * @throws
     * @Title acquireWakeLock
     * @Description TODO 防止Android系统因为长期不操作而进入休眠,屏幕亮度最亮显示(该方法无法屏蔽按下电源键进入休眠)
     */
    public static void acquireFullWakeLock(Context context) {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                        context.getClass().getCanonicalName());
                mWakeLock.acquire();
            }
        }
    }

    /**
     * @param context 应用程序上下文对象
     * @throws
     * @Title acquireWakeLock
     * @Description TODO 打开屏幕，防止Android系统因为长期不操作而进入休眠,屏幕亮度最暗显示(该方法无法屏蔽按下电源键进入休眠)
     */
    public static void acquireWakeLockWithWakeUp(Context context) {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                                | PowerManager.SCREEN_DIM_WAKE_LOCK,
                        context.getClass().getCanonicalName());
                mWakeLock.acquire();
            }
        }
    }

    /**
     * @param context 应用程序上下文对象
     * @throws
     * @Title acquireWakeLock
     * @Description TODO 防止Android系统因为长期不操作/按下电源键而进入休眠
     */
    public static void acquirePowerKeyWakeLock(Context context) {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                        context.getClass().getCanonicalName());
                mWakeLock.acquire();
            }
        }
    }

    /**
     * @throws
     * @Title releaseWakeLock
     * @Description TODO  恢复Android系统正常休眠功能
     */
    public static void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
