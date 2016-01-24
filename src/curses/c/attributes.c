#include "../headers/attributes.h"
#include <string.h>
#include <curses.h>

void init_color_pairs()
{
    short pair_id = 0;
    for(short i=0; i< 8; i++) {
        for(short j = 0; j<8; j++) {
            init_pair(pair_id, i, j);
            __COLOR_PAIRS[i][j] = pair_id;
            pair_id++;
        }
    }
}

int get_attribute_code(const char* name)
{
    if(strcmp(name, "NORMAL") == 0) return A_NORMAL;
    if(strcmp(name, "STANDOUT") == 0) return A_STANDOUT;
    if(strcmp(name, "UNDERLINE") == 0) return A_UNDERLINE;
    if(strcmp(name, "REVERSE") == 0) return A_REVERSE;
    if(strcmp(name, "BLINK") == 0) return A_BLINK;
    if(strcmp(name, "DIM") == 0) return A_DIM;
    if(strcmp(name, "BOLD") == 0) return A_BOLD;
    if(strcmp(name, "INVIS") == 0) return A_INVIS;

    return 0;
}

int get_attribute_value(JNIEnv * env, jobject attribute)
{
    jclass charAttrCls = (*env)->GetObjectClass(env, attribute);
    jmethodID nameM = (*env)->GetMethodID(env, charAttrCls, "name", "()Ljava/lang/String;");
    jobject attributeName = (*env)->CallObjectMethod(env, attribute, nameM);

    const char* name = (*env)->GetStringUTFChars(env, attributeName, NULL);
    int value = get_attribute_code(name);
    (*env)->ReleaseStringUTFChars(env, attributeName, name);
    return value;
}

jint get_color_id(JNIEnv* env, jobject color)
{
    jclass colorCls = (*env)->GetObjectClass(env, color);
    jmethodID ordinalM = (*env)->GetMethodID(env, colorCls, "ordinal", "()I");

    return (*env)->CallIntMethod(env, color, ordinalM);
}

int get_color_pair(JNIEnv* env, jobject fg, jobject bg)
{
    jint fgId = get_color_id(env, fg);
    jint bgId = get_color_id(env, bg);

    return COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]);
}

int get_attribute(JNIEnv * env, jobject attributes)
{
    jsize size = (*env)->GetArrayLength(env, attributes);
    int attributeValue = 0;
    for(int i=0; i < size; i++) {
        jobject attribute = (*env)->GetObjectArrayElement(env, attributes, i);
        attributeValue |= get_attribute_value(env, attribute);
    }
    return attributeValue;
}


jPoint get_point(JNIEnv* env, jobject point)
{
    jclass pcls = (*env)->GetObjectClass(env, point);
    jmethodID xMethod = (*env)->GetMethodID(env, pcls, "getX", "()I");
    jmethodID yMethod = (*env)->GetMethodID(env, pcls, "getY", "()I");

    jPoint p = {
             .x = (*env)->CallIntMethod(env, point, xMethod),
             .y = (*env)->CallIntMethod(env, point, yMethod)
           };
     return p;
}


