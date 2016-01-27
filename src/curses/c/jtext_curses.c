#include "../headers/jtext_curses.h"
#include "../headers/jtext_priv.h"
#include <curses.h>
#include <signal.h>
#include <locale.h>
#include <stdlib.h>


bool no_current_refresh = true;
WINDOW** __WINDOWS;
const int MAX_WINDOW = 500;

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

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_init
 (JNIEnv * env, jobject obj)
{
    __WINDOWS = malloc(MAX_WINDOW*sizeof(WINDOW*));
    memset(__WINDOWS, 0, MAX_WINDOW*sizeof(WINDOW*));

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
    meta(stdscr, TRUE);
    keypad(stdscr, TRUE);
    noecho();
    curs_set(FALSE);
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getScreenWidth
 (JNIEnv * env, jobject obj)
{
  return COLS;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getScreenHeight
 (JNIEnv * env, jobject obj)
{
    return LINES;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_createWindow
  (JNIEnv * env, jobject self, jint x, jint y, jint width, jint height)
{
   WINDOW* win = newwin(height, width, y, x);
   for(int i=0; i< MAX_WINDOW; i++) {
        if(__WINDOWS[i] != NULL) {
          __WINDOWS[i] = win;
           return i;
        }
   }

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_shutdown
 (JNIEnv * env, jobject obj)
{

    for(int i=0; i< MAX_WINDOW; i++) {
        if(__WINDOWS[i] != NULL) {
           delwin(__WINDOWS[i]);
        }
    }
    endwin();
}


JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setColor
  (JNIEnv * env, jobject self, jint window_id, jobject fg , jobject bg);
{
    attr_t attr = get_color_pair(env, fg, bg);
    wattron(__WINDOWS[window_id], attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setBackgroundColor
  (JNIEnv * env, jobject self, jint window_id, jobject color)
{

    attr_t attr;
    short pair_id;
    attr_get(&attr, &pair_id, NULL);

    jint fgId = pair_id / 8;
    jint bgId = get_color_id(env, color);

    wattron(__WINDOWS[window_id], COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]));

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setForegroundColor
  (JNIEnv * env, jobject self, jint window_id, jobject color)
{

    attr_t attr;
    short pair_id;
    attr_get(&attr, &pair_id, NULL);

    jint fgId = get_color_id(env, color);
    jint bgId = pair_id % 8;

    attron(__WINDOWS[window_id], COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]));

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_onAttributes
  (JNIEnv *env, jobject self, jint window_id, jobject attr_array)
{
    attr_t attr = get_attribute(env, attr_array);
    wattron(__WINDOWS[window_id], attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_onAttribute
  (JNIEnv * env, jobject self, jint window_id, jobject attribute)
{
    attr_t attr = get_attribute_value(env, attribute);
    wattron(__WINDOWS[window_id], attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_offAttribute
  (JNIEnv * env, jobject self, jint window_id, jobject attribute)
{
    attr_t attr = get_attribute_value(env, attribute);
    wattroff(__WINDOWS[window_id], attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawHorizontalLineAt
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    mvwhline_set(__WINDOWS[window_id], y, x, cch, length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawVerticalLineAt
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    mvwvline_set(__WINDOWS[window_id], y, x, cch, length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_printStringAt
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y, jstring text)
{
     wchar_t* buff = convert_string(env, text);
     mvwaddwstr(__WINDOWS[window_id], y, x, buff);
     free(buff);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_putCharAt
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y, jchar ch)
{
    const cchar_t* cch = convert_jchar(ch);
    mvwvline_set(__WINDOWS[window_id], y, x, cch, 1);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_changeAttributeAt
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y,
    jint length, jobject fg, jobject bg, jobjectArray attributes)
{
    attr_t attr = get_attribute(env, attributes);
    short color = get_color_pair(env, fg, bg);
    mvwchgat(__WINDOWS[window_id], y, x, length, attr_t, color, NULL);
}


JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_moveCursor
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y)
{
    wmove(__WINDOWS[window_id], y, x);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawVerticalLine
  (JNIEnv * env, jobject self, jint window_id, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    wvline(__WINDOWS[window_id], cch, (int) length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawHorizontalLine
  (JNIEnv * env, jobject self, jint window_id, jchar ch, jint length)
{
    const cchar_t* cch = convert_jchar(ch);
    whline(__WINDOWS[window_id], cch, (int) length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_printString
(JNIEnv * env, jobject self, jint window_id, jstring text)
 {
    wchar_t* buff = convert_string(env, text);
    waddwstr(__WINDOWS[window_id], buff);
    free(buff);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_putChar
  (JNIEnv * env, jobject self, jint window_id, jchar ch)
{
    const cchar_t* cch = convert_jchar(ch);
    wins_wch(__WINDOWS[window_id], cch);
    free(cch);
}

JNIEXPORT jobject JNICALL Java_org_jtext_curses_CursesDriver_getCh
 (JNIEnv * env, jobject obj)
{
    jclass readKeyClass = (*env)->FindClass(env, "org/jtext/curses/ReadKey");
    jclass controlKeyClass = (*env)->FindClass(env, "org/jtext/curses/ControlKey");
    jmethodID readKeyConstructor = (*env)->GetMethodID(env, readKeyClass, "<init>", "(Lorg/jtext/curses/ControlKey;I)V");

    while(!no_current_refresh);

    wint_t ch;
    int res = get_wch(&ch);

    const char* name;
    if(res == ERR) {
        name = "ERR";
    } else if(res == KEY_CODE_YES || ch < 32 || ch == 127) {
        name = map
    } else {
        name = "NORMAL";
    }

    jfieldID fieldNumber = (*env)->GetStaticFieldID(env, controlKeyClass, name, "Lorg/jtext/curses/ControlKey;");
    jobject enumConst = (*env)->GetStaticObjectField(env, controlKeyClass, fieldNumber);

    return (*env)->NewObject(env, readKeyClass, readKeyConstructor, enumConst, ch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_clearScreen
(JNIEnv * env, jobject obj)
{
    clear();
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_refresh
  (JNIEnv * env, jobject self, jint window_id)
{
    wrefresh(__WINDOWS[window_id]);
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
