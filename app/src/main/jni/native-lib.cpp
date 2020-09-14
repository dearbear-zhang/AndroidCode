//
// Created by Administrator on 2019/9/22.
//
#include "com_mine_dearbear_utils_JniUtilTest.h"

JNIEXPORT jstring JNICALL Java_com_yl_ndkdemo_MainActivity_stringFromJNI
        (JNIEnv *env, jobject obj) {
    jclass xx = env->GetObjectClass(obj);
    char password[10] = "dfad";
    return env->NewStringUTF("Hello from C++");
}

