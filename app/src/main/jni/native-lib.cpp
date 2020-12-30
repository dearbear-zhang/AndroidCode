//
// Created by Administrator on 2019/9/22.
//
#include "com_mine_dearbear_utils_JniUtilTest.h"

extern "C"
JNIEXPORT jstring JNICALL Java_com_mine_dearbear_utils_JniUtilTest_stringFromJNI
        (JNIEnv *env, jobject thiz) {
    jclass xx = env->GetObjectClass(thiz);
    char password[10] = "dfad";
    return env->NewStringUTF("Hello from C++");
}
