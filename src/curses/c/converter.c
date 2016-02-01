#include "../headers/converter.h"
#include <stdlib.h>
#include <iconv.h>


const cchar_t* convert_jchar_base(jchar ch)
{
 char* p_ch = (char*) &ch;
 size_t s = sizeof(jchar);
 cchar_t* ret = malloc(sizeof(cchar_t));
 memset(ret, 0, sizeof(cchar_t));
 size_t ws = sizeof(wchar_t);
 char* wchars = ret->chars;
 iconv_t cd = iconv_open("WCHAR_T", "UTF-16LE");
 iconv(cd, &p_ch, &s, &wchars, &ws);
 iconv_close(cd);

 return ret;
}

const cchar_t* convert_jchar(jchar ch)
{
    const cchar_t* ret = convert_jchar_base(ch);
    short cp;
    attr_get(&ret->attr, &cp, NULL);
    return ret;
}

const cchar_t* convert_jchar_with_attributes(jchar ch, attr_t* attrs)
{
    cchar_t* ret = convert_jchar_base(ch);
    ret->attr = attrs;
    return ret;
}

wchar_t* convert_string(JNIEnv* env, jobject text)
{
    jsize length = (*env)->GetStringLength(env, text);
    jchar* string = (*env)->GetStringChars(env, text, NULL);

    size_t this_size = length * sizeof(jchar);
    size_t other_size = (size_t) (length + 1) * sizeof(wchar_t) ;
    wchar_t* other_buffer = (wchar_t*) malloc(other_size);
    other_buffer[length] = 0;
    char* buff = (char*)other_buffer;
    char* string_c = string;

    iconv_t cd = iconv_open ("WCHAR_T", "UTF-16LE");
    size_t result = iconv(cd, (char **) &string, &this_size, &buff, &other_size);
    iconv_close(cd);
    (*env)->ReleaseStringChars(env, text, string_c);

    if( result == (size_t) -1) {
        free(buff);
        return -1;
    } else {
        return other_buffer;
    }
}

const cchar_t* convert_cell_descriptor(JNIEnv* env, jobject cell_descriptor)
{
    jclass cd_class = (*env)->GetObjectClass(env, cell_descriptor);
    jmethodID get_character_method = (*env)->GetMethodID(env, cd_class, "getCharacter", "()C");

    jchar ch = (jchar) (*env)->CallObjectMethod(env, cell_descriptor, get_character_method);

    jmethodID get_foreground_color_method = (*env)->GetMethodID(env, cd_class, "getForegroundColor", "()L/org/jtext/curses/CellDescriptor;");
    jmethodID get_background_color_method = (*env)->GetMethodID(env, cd_class, "getBackgroundColor", "()L/org/jtext/curses/CellDescriptor;");

    jobject fg = (*env)->CallObjectMethod(env, cell_descriptor, get_foreground_color_method);
    jobject bg = (*env)->CallObjectMethod(env, cell_descriptor, get_background_color_method);

    jmethodID get_attributes_method = (*env)->GetMethodID(env, cd_class, "getAttributes", "[Lorg/jtext/curses/CharacterAttribute;");
    jobject attrs = (*env)->CallObjectMethod(env, cell_descriptor, get_attributes_method);

     attr_t attributes =  get_attribute(env, attrs) | get_color_pair(env, fg, bg);

     return convert_jchar_with_attributes(ch, &attributes);
}