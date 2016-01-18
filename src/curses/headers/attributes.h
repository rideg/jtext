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

int get_attribute(JNIEnv*, jobject /*descriptor*/);

jPoint get_point(JNIEnv*, jobject /*point*/);


#ifdef __cpluplus
}
#endif

#endif