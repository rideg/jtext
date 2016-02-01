#include <jni.h>
#include <curses.h>
#include "attributes.h"


#ifndef __JTEXT_CONVERTER
#define __JTEXT_CONVERTER

#ifdef __cpluplus
extern "C" {
#endif

const cchar_t* convert_jchar(jchar);

const cchar_t* convert_jchar_with_attributes(jchar, attr_t*);

wchar_t* convert_string(JNIEnv*, jobject);

const cchar_t* convert_cell_descriptor(JNIEnv*, jobject);


#ifdef __cpluplus
}
#endif
#endif