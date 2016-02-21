/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_jtext_curses_CursesDriver */

#ifndef _Included_org_jtext_curses_CursesDriver
#define _Included_org_jtext_curses_CursesDriver
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_init
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    getScreenWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getScreenWidth
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    getScreenHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getScreenHeight
  (JNIEnv *, jobject);


/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    setColor
 * Signature: (Lorg/jtext/curses/CharacterColor;Lorg/jtext/curses/CharacterColor;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setColor
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    setBackgroundColor
 * Signature: (Lorg/jtext/curses/CharacterColor;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setBackgroundColor
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    setForegroundColor
 * Signature: (Lorg/jtext/curses/CharacterColor;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setForegroundColor
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    onAttributes
 * Signature: ([Lorg/jtext/curses/CharacterAttribute;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_onAttributes
  (JNIEnv *, jobject, jobjectArray);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    onAttribute
 * Signature: (Lorg/jtext/curses/CharacterAttribute;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_onAttribute
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    offAttribute
 * Signature: (Lorg/jtext/curses/CharacterAttribute;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_offAttribute
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    drawHorizontalLineAt
 * Signature: (IICI)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawHorizontalLineAt
  (JNIEnv *, jobject, jint, jint, jchar, jint);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    drawVerticalLineAt
 * Signature: (IICI)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawVerticalLineAt
  (JNIEnv *, jobject, jint, jint, jchar, jint);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    printStringAt
 * Signature: (IILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_printStringAt
  (JNIEnv *, jobject, jint, jint, jstring);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    putCharAt
 * Signature: (IIC)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_putCharAt
  (JNIEnv *, jobject, jint, jint, jchar);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    changeAttributeAt
 * Signature: (IIILorg/jtext/curses/CharacterColor;Lorg/jtext/curses/CharacterColor;[Lorg/jtext/curses/CharacterAttribute;)V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_changeAttributeAt
  (JNIEnv *, jobject, jint, jint, jint, jobject, jobject, jobjectArray);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    bell
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_bell
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    shutdown
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_shutdown
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    getCh
 * Signature: ()Lorg/jtext/curses/ReadKey;
 */
JNIEXPORT jobject JNICALL Java_org_jtext_curses_CursesDriver_getCh
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    refresh
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_refresh
  (JNIEnv *, jobject);


/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    clearScreen
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_clearScreen
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    clearStyle
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_clearStyle
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    getCursorX
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getCursorX
  (JNIEnv *, jobject);

/*
 * Class:     org_jtext_curses_CursesDriver
 * Method:    getCursorY
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getCursorY
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif