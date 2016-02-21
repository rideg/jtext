#include <jni.h>

#ifndef __JTEXT_UTIL
#define __JTEXT_UTIL

#ifdef __cpluplus
extern "C" {
#endif

void throw_exception(JNIEnv*, const char*, const char*);

#ifdef __cpluplus
}
#endif
#endif
