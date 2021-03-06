#ifndef __JTEXT_ATTRIBUTES
#define __JTEXT_ATTRIBUTES

#include <jni.h>
#include <ncursesw6/curses.h>

#ifdef __cpluplus
extern "C" {
#endif

attr_t get_attribute(JNIEnv*, jobject /*attributes*/);

attr_t get_color_pair(JNIEnv*, jobject /*fg*/, jobject /*bg*/);

jint get_color_id(JNIEnv*, jobject);

attr_t get_attribute_value(JNIEnv *, jobject /*attribute*/);


#ifdef __cpluplus
}
#endif

#endif