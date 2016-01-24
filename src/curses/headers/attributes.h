#ifndef __JTEXT_ATTRIBUTES
#define __JTEXT_ATTRIBUTES

#include <jni.h>

#ifdef __cpluplus
extern "C" {
#endif

char __COLOR_PAIRS[8][8];

typedef struct {
    jint x;
    jint y;
} jPoint;

void init_color_pairs(); // creates all possible 256 color pairs

int get_attribute(JNIEnv*, jobject /*attributes*/);

int get_color_pair(JNIEnv*, jobject /*fg*/, jobject /*bg*/);

jPoint get_point(JNIEnv*, jobject /*point*/);

jint get_color_id(JNIEnv*, jobject);

int get_attribute_value(JNIEnv *, jobject /*attribute*/);


#ifdef __cpluplus
}
#endif

#endif