#ifndef _CURSES
#define _CURSES

#include <jni.h>

#ifdef __cpluplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_org_jtext_system_CursesImpl_getScreenWidth  (JNIEnv *, jobject);

JNIEXPORT jint JNICALL Java_org_jtext_system_CursesImpl_getScreenHeight  (JNIEnv *, jobject);

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_init  (JNIEnv *, jobject);

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_clearScreen  (JNIEnv *, jobject);

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_shutdown  (JNIEnv *, jobject);

JNIEXPORT jobject JNICALL Java_org_jtext_system_CursesImpl_getCh  (JNIEnv *, jobject);


JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_printString  (JNIEnv *, jobject, jstring, jobject, jobject);


JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_setBackground  (JNIEnv *, jobject, jchar, jobject);




#ifdef __cpluplus
}
#endif
#endif