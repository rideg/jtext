#include "../headers/jtext_curses.h"
#include "../headers/jtext_priv.h"
#include <curses.h>
#include <signal.h>


bool no_current_refresh = true;

void handle_winch(int sig)
{
   if(no_current_refresh) {
    no_current_refresh = false;
    endwin();
    refresh();
    no_current_refresh = true;
   }
}

void configure_signal_handling()
{
    struct sigaction sa;
    memset(&sa, 0, sizeof(struct sigaction));
    sa.sa_handler = handle_winch;
    sigaction(SIGWINCH, &sa, NULL);
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_init (JNIEnv * env, jobject obj)
{
    initscr();
    nodelay(stdscr, TRUE);
    configure_signal_handling();
    start_color();
    use_default_colors();
    init_color_pairs();
    raw();
    keypad(stdscr, TRUE);
    noecho();
    curs_set(FALSE);
}

JNIEXPORT jint JNICALL Java_org_jtext_system_CursesImpl_getScreenWidth  (JNIEnv * env, jobject obj)
{
  return COLS;
}

JNIEXPORT jint JNICALL Java_org_jtext_system_CursesImpl_getScreenHeight  (JNIEnv * env, jobject obj)
{
    return LINES;
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_shutdown  (JNIEnv * env, jobject obj)
{
    endwin();
}

JNIEXPORT jobject JNICALL Java_org_jtext_system_CursesImpl_getCh  (JNIEnv * env, jobject obj)
{
    jclass readKeyClass = (*env)->FindClass(env, "org/jtext/system/ReadKey");
    jclass controlKeyClass = (*env)->FindClass(env, "org/jtext/system/ControlKey");
    jmethodID readKeyConstructor = (*env)->GetMethodID(env, readKeyClass, "<init>", "(Lorg/jtext/system/ControlKey;I)V");

    while(!no_current_refresh);

    int ch = getch();

    jfieldID fieldNumber = (*env)->GetStaticFieldID(env, controlKeyClass, map_key(ch), "Lorg/jtext/system/ControlKey;");
    jobject enumConst = (*env)->GetStaticObjectField(env, controlKeyClass, fieldNumber);

    return (*env)->NewObject(env, readKeyClass, readKeyConstructor, enumConst, ch);
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_clearScreen  (JNIEnv * env, jobject obj) {
    clear();
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_printString (JNIEnv * env, jobject self, jstring text,
                                                                    jobject point, jobject descriptor)
{
    const char * string = (*env)->GetStringUTFChars(env, text, NULL);
    const jPoint jp = get_point(env, point);

    attron(get_attribute(env, descriptor));
    mvprintw(jp.y, jp.x, string);

    attrset(A_NORMAL);

    (*env)->ReleaseStringUTFChars(env, text, string);
    refresh();
}

JNIEXPORT void JNICALL Java_org_jtext_system_CursesImpl_setBackground( JNIEnv* env, jobject self, jchar ch,
            jobject descriptor)
{


}


