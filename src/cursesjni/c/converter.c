#include "../headers/converter.h"


cchar_t* convert_jchar_base(jchar ch)
{
 char* p_ch = (char*) &ch;
 size_t s = sizeof(jchar);
 cchar_t* ret = malloc(sizeof(cchar_t));
 memset(ret, 0, sizeof(cchar_t));
 size_t ws = sizeof(wchar_t);
 char* wchars = (char*) ret->chars;
 iconv_t cd = iconv_open("WCHAR_T", "UTF-16LE");
 iconv(cd, &p_ch, &s, &wchars, &ws);
 iconv_close(cd);

 return ret;
}

const cchar_t* convert_jchar(jchar ch)
{
    cchar_t* ret = convert_jchar_base(ch);
    short cp;
    attr_get(&ret->attr, &cp, NULL);
    return ret;
}

const cchar_t* convert_jchar_with_attributes(jchar ch, attr_t attrs)
{
    cchar_t* ret = convert_jchar_base(ch);
    ret->attr = attrs;
    return ret;
}

wchar_t* convert_string(JNIEnv* env, jobject text)
{
    jsize length = (*env)->GetStringLength(env, text);
    const jchar* string = (*env)->GetStringChars(env, text, NULL);

    size_t this_size = length * sizeof(jchar);
    size_t other_size = (size_t) (length + 1) * sizeof(wchar_t) ;
    wchar_t* other_buffer = (wchar_t*) malloc(other_size);
    other_buffer[length] = 0;
    char* buff = (char*)other_buffer;
    const jchar* string_c = string;

    iconv_t cd = iconv_open ("WCHAR_T", "UTF-16LE");
    size_t result = iconv(cd, (char **) &string, &this_size, &buff, &other_size);
    iconv_close(cd);
    (*env)->ReleaseStringChars(env, text, string_c);

    if( result == (size_t) -1) {
        free(buff);
        return (wchar_t*)-1;
    } else {
        return other_buffer;
    }
}