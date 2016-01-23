#include <jni.h>
#include <curses.h>


#ifndef __JTEXT_CONVERTER
#define __JTEXT_CONVERTER

#ifdef __cpluplus
extern "C" {
#endif

const cchar_t* convert_jchar(JNIEnv* , jchar );

wchar_t* convert_string(JNIEnv*, jobject);


#ifdef __cpluplus
}
#endif
#endif