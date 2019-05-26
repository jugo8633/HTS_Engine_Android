
#include <string>
#include "libTTS.h"
#include "TextProcess.h"
#include <android/log.h>

using namespace std;

static TextProcess textProcess;

JNIEXPORT jshortArray JNICALL
Java_org_iii_htsengine_TTSEngine_synthesize(JNIEnv *env, jobject instance, jstring strUtterance_, jstring strRate_, jstring strPath_, jstring strLanType_)
{
    __android_log_print(ANDROID_LOG_DEBUG, "TTS", "%s", "call Java_org_iii_htsengine_TTSEngine_synthesize");

    const char *strUtterance = env->GetStringUTFChars(strUtterance_, 0);
    const char *strRate = env->GetStringUTFChars(strRate_, 0);
    const char *strPath = env->GetStringUTFChars(strPath_, 0);
    const char *strLanType = env->GetStringUTFChars(strLanType_, 0);

    short *sa = 0;
    textProcess.synthesize(strUtterance, &sa);
    __android_log_print(ANDROID_LOG_DEBUG, "TTS", "WAVE %d %d", sa[0], sa[1]);
    if (!sa) {
        delete sa;
    }

    env->ReleaseStringUTFChars(strUtterance_, strUtterance);
    env->ReleaseStringUTFChars(strRate_, strRate);
    env->ReleaseStringUTFChars(strPath_, strPath);
    env->ReleaseStringUTFChars(strLanType_, strLanType);
    return NULL;
}

JNIEXPORT jstring
JNICALL Java_org_iii_htsengine_TTSEngine_TextProcessVersion(JNIEnv *env, jobject instance)
{
    return env->NewStringUTF(textProcess.getVersion().c_str());
}