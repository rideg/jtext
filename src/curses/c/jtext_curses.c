#include "../headers/jtext_curses.h"
#include "../headers/jtext_priv.h"
#include "../headers/converter.h"
#include <curses.h>
#include <signal.h>
#include <locale.h>
#include <stdlib.h>


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

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_init
 (JNIEnv * env, jobject obj)
{
    setlocale(LC_ALL, "");
    initscr();
    nodelay(stdscr, TRUE);
    set_escdelay(0);
    configure_signal_handling();
    start_color();
    use_default_colors();
    init_color_pairs();
    nonl();
    raw();
    keypad(stdscr, TRUE);
    noecho();
    curs_set(FALSE);
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesImpl_getScreenWidth
 (JNIEnv * env, jobject obj)
{
  return COLS;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesImpl_getScreenHeight
 (JNIEnv * env, jobject obj)
{
    return LINES;
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_shutdown
 (JNIEnv * env, jobject obj)
{
    endwin();
}



JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_drawHorizontalLineAt
  (JNIEnv * env, jobject self, jobject point, jchar ch, jint length)
{
    jPoint p = get_point(env, point);
    const cchar_t* cch = convert_jchar(env, ch);

    mvhline_set(p.y, p.x, cch, (int) length);
    free(cch);
}

JNIEXPORT jobject JNICALL Java_org_jtext_curses_CursesImpl_getCh
 (JNIEnv * env, jobject obj)
{
    jclass readKeyClass = (*env)->FindClass(env, "org/jtext/curses/ReadKey");
    jclass controlKeyClass = (*env)->FindClass(env, "org/jtext/curses/ControlKey");
    jmethodID readKeyConstructor = (*env)->GetMethodID(env, readKeyClass, "<init>", "(Lorg/jtext/curses/ControlKey;I)V");

    while(!no_current_refresh);

    int ch = getch();

    jfieldID fieldNumber = (*env)->GetStaticFieldID(env, controlKeyClass, map_key(ch), "Lorg/jtext/curses/ControlKey;");
    jobject enumConst = (*env)->GetStaticObjectField(env, controlKeyClass, fieldNumber);

    return (*env)->NewObject(env, readKeyClass, readKeyConstructor, enumConst, ch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_clearScreen
(JNIEnv * env, jobject obj)
{
    clear();
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_printString
(JNIEnv * env, jobject self, jstring text)
 {
    wchar_t* buff = convert_string(env, text);
    addwstr(buff);
    free(buff);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_moveCursor
  (JNIEnv * env, jobject self, jobject point)
{
    jPoint p = get_point(env, point);
    move(p.y, p.x);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_refresh
  (JNIEnv * env, jobject self)
{
    refresh();
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_setColor
  (JNIEnv * env, jobject self, jobject fg, jobject bg)
{
    int attr = get_color_pair(env, fg, bg);
    attron(attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_onAttributes
  (JNIEnv *env, jobject self, jobject attr_array)
{
    int attr = get_attribute(env, attr_array);
    attron(attr);
}

  JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_clearStyle
    (JNIEnv * env, jobject self)
{
    standend();
}
