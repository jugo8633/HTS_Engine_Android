//
// Created by root on 2018/8/22.
//

#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jshortArray JNICALL Java_org_iii_htsengine_TTSEngine_synthesize(JNIEnv *, jobject, jstring, jstring, jstring, jstring);
JNIEXPORT jstring JNICALL Java_org_iii_htsengine_TTSEngine_TextProcessVersion(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif