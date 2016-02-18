#include "../headers/util.h"


void throw_exception( JNIEnv *env, const char* class_name, const char *message )
{
    jclass ex_class;
    ex_class = (*env)->FindClass( env, class_name);
    (*env)->ThrowNew( env, ex_class, message);
}