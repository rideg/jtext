#include "../headers/jtext_curses.h"
#include "../headers/jtext_priv.h"
#include <curses.h>
#include <signal.h>
#include <locale.h>
#include <stdlib.h>
#include <string.h>

bool no_current_refresh = true;
WINDOW* screen;
WINDOW* pad;

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
    use_tioctl(TRUE);
    setlocale(LC_ALL, "");
    screen = initscr();
    pad = newpad(1, 1);
    nodelay(pad, TRUE);
    set_escdelay(0);
    configure_signal_handling();
    start_color();
    use_default_colors();
    nonl();
    raw();
    meta(pad, TRUE);
    keypad(pad, TRUE);
    noecho();
    curs_set(FALSE);

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_initColorPair
  (JNIEnv * env, jobject self, jint pair_id, jint fg, jint bg)
 {
   init_pair((NCURSES_PAIRS_T) pair_id,
            (NCURSES_COLOR_T) fg,
            (NCURSES_COLOR_T) bg);
 }

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_initColor
  (JNIEnv * env, jobject self, jint color_id, jint red_component, jint green_component, jint blue_component)
 {
    init_color((NCURSES_COLOR_T) color_id,
    (NCURSES_COLOR_T) red_component,
    (NCURSES_COLOR_T) green_component,
    (NCURSES_COLOR_T) blue_component);
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

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_shutdown
 (JNIEnv * env, jobject obj)
{
    delwin(screen);
    delwin(pad);
    endwin();
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setColor
  (JNIEnv * env, jobject self, jint color_pair)
{
   wcolor_set(screen, color_pair, NULL);
//   wattron(screen, COLOR_PAIR(color_pair));
}


JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getForegroundColor
  (JNIEnv * env, jobject self)
{
    NCURSES_COLOR_T fg;
    NCURSES_COLOR_T bg;
    NCURSES_PAIRS_T pair_id;
    attr_t t;
    wattr_get(screen, &t, &pair_id, NULL);
    pair_content(pair_id, &fg, &bg);
    return fg;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getBackgroundColor
  (JNIEnv * env, jobject self)
{
    NCURSES_COLOR_T fg;
    NCURSES_COLOR_T bg;
    NCURSES_PAIRS_T pair_id;
    attr_t t;
    wattr_get(screen, &t, &pair_id, NULL);
    pair_content(pair_id, &fg, &bg);
    return bg;
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_onAttributes
  (JNIEnv *env, jobject self, jobject attr_array)
{
    attr_t attr;
    attr = get_attribute(env, attr_array);
    wattron(screen, attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_onAttribute
  (JNIEnv * env, jobject self, jobject attribute)
{
    attr_t attr;
    attr = get_attribute_value(env, attribute);
    wattron(screen, attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_offAttribute
  (JNIEnv * env, jobject self, jobject attribute)
{
    attr_t attr;
    attr = get_attribute_value(env, attribute);
    wattroff(screen, attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawHorizontalLineAt
  (JNIEnv * env, jobject self, jint x, jint y, jchar ch, jint length)
{
    const cchar_t* cch;
    cch = convert_jchar(ch);
    int result;
    result = mvwhline_set(screen, y, x, cch, length);
    free(cch);
    if(result == ERR) {
        throw_exception(env, "java/lang/IllegalStateException", "problem with drawing line!");
    }

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawVerticalLineAt
  (JNIEnv * env, jobject self, jint x, jint y, jchar ch, jint length)
{
    const cchar_t* cch;
    cch = convert_jchar(ch);
    mvwvline_set(screen, y, x, cch, length);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_printStringAt
  (JNIEnv * env, jobject self, jint x, jint y, jstring text)
{
     wchar_t* buff;
     buff = convert_string(env, text);
     mvwaddwstr(screen, y, x, buff);
     free(buff);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_putCharAt
  (JNIEnv * env, jobject self, jint x, jint y, jchar ch)
{
    const cchar_t* cch;
    cch = convert_jchar(ch);
    mvwvline_set(screen, y, x, cch, 1);
    free(cch);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_changeAttributeAt
  (JNIEnv * env, jobject self, jint x, jint y,
    jint length, jint color_pair, jobjectArray attributes)
{
    attr_t attr;
    attr = get_attribute(env, attributes);
    mvwchgat(screen, y, x, length, attr, color_pair, NULL);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_bell
  (JNIEnv * env, jobject self)
{
    beep();
}

JNIEXPORT jobject JNICALL Java_org_jtext_curses_CursesDriver_getCh
 (JNIEnv * env, jobject obj)
{
    jclass readKeyClass = (*env)->FindClass(env, "org/jtext/curses/ReadKey");
    jclass controlKeyClass = (*env)->FindClass(env, "org/jtext/curses/ControlKey");
    jmethodID readKeyConstructor = (*env)->GetMethodID(env, readKeyClass, "<init>", "(Lorg/jtext/curses/ControlKey;I)V");

    while(!no_current_refresh);

    wint_t ch;
    int res = wget_wch(pad, &ch);

    const char* name;
    if(res == ERR) {
        name = "ERR";
    } else if(res == KEY_CODE_YES || ch < 32 || ch == 127) {
        name = map_key(ch);
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
    wclear(screen);
}


JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_refresh
  (JNIEnv * env, jobject self)
{
    wrefresh(screen);
}

  JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_clearStyle
    (JNIEnv * env, jobject self)
{
    wstandend(screen);
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getCursorX
  (JNIEnv * env, jobject self)
{
    int x;
    int y;
    getyx(screen, y, x);
    return x;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getCursorY
  (JNIEnv * env, jobject self)
{
    int x;
    int y;
    getyx(screen, y, x);
    return y;
}

