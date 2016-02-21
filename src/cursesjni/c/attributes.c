#include "../headers/attributes.h"
#include <string.h>

void init_color_pairs()
{
    short pair_id = 0;
    short i;
    short j;
    for(i = 0; i < 8; i++) {
        for(j = 0; j < 8; j++) {
            init_pair(pair_id, i, j);
            __COLOR_PAIRS[i][j] = pair_id;
            pair_id++;
        }
    }
}

attr_t get_attribute_code(const char* name)
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

attr_t get_attribute_value(JNIEnv * env, jobject attribute)
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

attr_t get_color_pair(JNIEnv* env, jobject fg, jobject bg)
{
    jint fgId = get_color_id(env, fg);
    jint bgId = get_color_id(env, bg);

    return COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]);
}

attr_t get_attribute(JNIEnv * env, jobject attributes)
{
    jsize size = (*env)->GetArrayLength(env, attributes);
    attr_t attributeValue = 0;
    int i;
    for(i=0; i < size; i++) {
        jobject attribute = (*env)->GetObjectArrayElement(env, attributes, i);
        attributeValue |= get_attribute_value(env, attribute);
    }
    return attributeValue;
}