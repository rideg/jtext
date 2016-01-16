#include "../headers/jtext_curses.h"
#include <curses.h>
#include <string.h>

char color_pairs[8][8];

void init_color_pairs() {
    short pair_id = 0;
    for(short i=0; i< 8; i++) {
        for(short j = 0; j<8; j++) {
            init_pair(pair_id, i, j);
            color_pairs[i][j] = pair_id;
            pair_id++;
        }
    }
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_init (JNIEnv * env, jobject obj) {
    initscr();
    start_color();
    init_color_pairs();
    cbreak();
    keypad(stdscr, TRUE);
    noecho();
}

JNIEXPORT jint JNICALL Java_org_jtext_system_CursesImpl_getScreenWidth  (JNIEnv * env, jobject obj) {
  int row, col;
  getmaxyx(stdscr,row,col);
  return col;
}

JNIEXPORT jint JNICALL Java_org_jtext_system_CursesImpl_getScreenHeight  (JNIEnv * env, jobject obj) {
    int row, col;
    getmaxyx(stdscr,row,col);
    return row;
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_shutdown  (JNIEnv * env, jobject obj) {
    endwin();
}

JNIEXPORT jobject JNICALL Java_org_jtext_system_CursesImpl_getCh  (JNIEnv * env, jobject obj) {
    jclass readKeyClass = (*env)->FindClass(env, "org/jtext/system/ReadKey");
    jclass controlKeyClass = (*env)->FindClass(env, "org/jtext/system/ControlKey");
    jmethodID readKeyConstructor = (*env)->GetMethodID(env, readKeyClass, "<init>", "(Lorg/jtext/system/ControlKey;)V");

    int ch = getch();

    const char * fieldName = ch == 27 ? "ESC" : "OTHER";
    jfieldID fieldNumber = (*env)->GetStaticFieldID(env, controlKeyClass, fieldName, "Lorg/jtext/system/ControlKey;");
    jobject enumConst = (*env)->GetStaticObjectField(env, controlKeyClass, fieldNumber);

    return (*env)->NewObject(env, readKeyClass, readKeyConstructor, enumConst);
}


int get_attribute(const char* name) {

    if(strcmp(name, "NORMAL")) return A_NORMAL;
    if(strcmp(name, "STANDOUT")) return A_STANDOUT;
    if(strcmp(name, "UNDERLINE")) return A_UNDERLINE;
    if(strcmp(name, "REVERSE")) return A_REVERSE;
    if(strcmp(name, "BLINK")) return A_BLINK;
    if(strcmp(name, "DIM")) return A_DIM;
    if(strcmp(name, "BOLD")) return A_BOLD;
    if(strcmp(name, "INVIS")) return A_INVIS;

    return 0;
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_printString (JNIEnv * env, jobject self, jstring text,
                                                                    jobject point, jobject descriptor) {
    jclass pcls = (*env)->GetObjectClass(env, point);
    jmethodID xMethod = (*env)->GetMethodID(env, pcls, "getX", "()I");
    jmethodID yMethod = (*env)->GetMethodID(env, pcls, "getY", "()I");

    jclass ccCls = (*env)->FindClass(env, "org/jtext/system/CharacterColor");
    jmethodID ordinalM = (*env)->GetMethodID(env, ccCls, "ordinal", "()I");

    jclass caCls = (*env)->FindClass(env, "org/jtext/system/CharacterAttribute");
    jmethodID nameM = (*env)->GetMethodID(env, caCls, "name", "()Ljava/lang/String;");

    jclass desCls = (*env)->GetObjectClass(env, descriptor);
    jmethodID forColM = (*env)->GetMethodID(env, desCls, "getForegroundColor", "()Lorg/jtext/system/CharacterColor;");
    jmethodID backColM = (*env)->GetMethodID(env, desCls, "getBackgroundColor", "()Lorg/jtext/system/CharacterColor;");
    jmethodID attribM = (*env)->GetMethodID(env, desCls, "getCharacterAttribute", "()Lorg/jtext/system/CharacterAttribute;");

    jobject fg = (*env)->CallObjectMethod(env, descriptor, forColM);
    jobject bg = (*env)->CallObjectMethod(env, descriptor,  backColM);
    jobject attr = (*env)->CallObjectMethod(env, descriptor,  attribM);

    jint fgId = (*env)->CallIntMethod(env, fg, ordinalM);
    jint bgId = (*env)->CallIntMethod(env, bg, ordinalM);
//
    jobject attrName = (*env)->CallObjectMethod(env, attr, nameM);
    const char *  name = (*env)->GetStringUTFChars(env, attrName, NULL);


    int attrValue = get_attribute(name);
    char pairId = color_pairs[fgId][bgId];
    const char * string = (*env)->GetStringUTFChars(env, text, NULL);
    jint x = (*env)->CallIntMethod(env, point, xMethod);
    jint y = (*env)->CallIntMethod(env, point, yMethod);


    attron(attrValue | COLOR_PAIR(pairId));

    mvprintw(y, x, string);
    attrset(A_NORMAL);

    (*env)->ReleaseStringUTFChars(env, attrName, name);
    (*env)->ReleaseStringUTFChars(env, text, string);
    refresh();
}




