package com.mine.dearbear.utils;

public class JniUtilTest {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
}
