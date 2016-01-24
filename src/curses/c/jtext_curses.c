#include "../headers/jtext_curses.h"
#include "../headers/jtext_priv.h"
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

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_setBackgroundColor
  (JNIEnv * env, jobject self, jobject color)
{

    int attr;
    short pair_id;
    attr_get(&attr, &pair_id, NULL);

    jint fgId = pair_id / 8;
    jint bgId = get_color_id(env, color);

    attron(COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]));

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_setForegroundColor
  (JNIEnv * env, jobject self, jobject color)
{

    int attr;
    short pair_id;
    attr_get(&attr, &pair_id, NULL);

    jint fgId = get_color_id(env, color);
    jint bgId = pair_id % 8;

    attron(COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]));

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_onAttributes
  (JNIEnv *env, jobject self, jobject attr_array)
{
    int attr = get_attribute(env, attr_array);
    attron(attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_onAttribute
  (JNIEnv * env, jobject self, jobject attribute)
{
    int attr = get_attribute_value(env, attribute);
    attron(attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_offAttribute
  (JNIEnv * env, jobject self, jobject attribute)
{
    int attr = get_attribute_value(env, attribute);
    attroff(attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_drawHorizontalLineAt
  (JNIEnv * env, jobject self, jint x, jint y, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);

    mvhline_set(y, x, cch, (int) length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_drawVerticalLineAt
  (JNIEnv * env, jobject self, jint x, jint y, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    mvvline_set(y, x, cch, (int) length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_printStringAt
  (JNIEnv * env, jobject self, jint x, jint y, jstring text)
{
     wchar_t* buff = convert_string(env, text);
     mvaddwstr(y, x, buff);
     free(buff);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_putCharAt
  (JNIEnv * env, jobject self, jint x, jint y, jchar ch)
{
    const cchar_t* cch = convert_jchar(ch);
    mvdelch(y,x);
    mvins_wch(y, x, cch);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_moveCursor
  (JNIEnv * env, jobject self, jint x, jint y)
{
    move(y, x);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_drawVerticalLine
  (JNIEnv * env, jobject self, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    vline_set(cch, (int) length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_drawHorizontalLine
  (JNIEnv * env, jobject self, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    hline_set(cch, (int) length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_printString
(JNIEnv * env, jobject self, jstring text)
 {
    wchar_t* buff = convert_string(env, text);
    addwstr(buff);
    free(buff);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_putChar
  (JNIEnv * env, jobject self, jchar ch)
{
    const cchar_t* cch = convert_jchar(ch);
    ins_wch(cch);
    free(cch);
}

JNIEXPORT jobject JNICALL Java_org_jtext_curses_CursesImpl_getCh
 (JNIEnv * env, jobject obj)
{
    jclass readKeyClass = (*env)->FindClass(env, "org/jtext/curses/ReadKey");
    jclass controlKeyClass = (*env)->FindClass(env, "org/jtext/curses/ControlKey");
    jmethodID readKeyConstructor = (*env)->GetMethodID(env, readKeyClass, "<init>", "(Lorg/jtext/curses/ControlKey;I)V");

    while(!no_current_refresh);

    wint_t ch;
    get_wch(&ch);

    jfieldID fieldNumber = (*env)->GetStaticFieldID(env, controlKeyClass, map_key(ch), "Lorg/jtext/curses/ControlKey;");
    jobject enumConst = (*env)->GetStaticObjectField(env, controlKeyClass, fieldNumber);

    return (*env)->NewObject(env, readKeyClass, readKeyConstructor, enumConst, ch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_clearScreen
(JNIEnv * env, jobject obj)
{
    clear();
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

  JNIEXPORT void JNICALL Java_org_jtext_curses_CursesImpl_clearStyle
    (JNIEnv * env, jobject self)
{
    standend();
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesImpl_getCursorX
  (JNIEnv * env, jobject self)
{
    int x;
    int y;
    getyx(stdscr, y, x);
    return x;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesImpl_getCursorY
  (JNIEnv * env, jobject self)
{
    int x;
    int y;
    getyx(stdscr, y, x);
    return y;
}