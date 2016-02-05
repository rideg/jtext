#include "../headers/jtext_curses.h"
#include "../headers/jtext_priv.h"
#include <curses.h>
#include <signal.h>
#include <locale.h>
#include <stdlib.h>

bool no_current_refresh = true;
WINDOW** __WINDOWS;
const int MAX_WINDOW = 501;
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
    __WINDOWS = malloc(MAX_WINDOW*sizeof(WINDOW*));
    memset(__WINDOWS, 0, MAX_WINDOW*sizeof(WINDOW*));

    setlocale(LC_ALL, "");
    screen = initscr();
    __WINDOWS[0] = screen;
    pad = newpad(1, 1);
    nodelay(pad, TRUE);
    set_escdelay(0);
    configure_signal_handling();
    start_color();
    use_default_colors();
    init_color_pairs();
    nonl();
    raw();
    meta(pad, TRUE);
    keypad(pad, TRUE);
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
   int i;
   for(i = 1; i < MAX_WINDOW; i++) {
        if(__WINDOWS[i] == NULL) {
          __WINDOWS[i] = win;
           return i;
        }
   }
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_shutdown
 (JNIEnv * env, jobject obj)
{
    int i;
    for(i = 1; i < MAX_WINDOW; i++) {
        if(__WINDOWS[i] != NULL) {
           delwin(__WINDOWS[i]);
        }
    }
    delwin(pad);
    endwin();
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setColor
  (JNIEnv * env, jobject self, jint window_id, jobject fg, jobject bg)
{
    attr_t attr = get_color_pair(env, fg, bg);
    wattron(__WINDOWS[window_id], attr);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setBackgroundColor
  (JNIEnv * env, jobject self, jint window_id, jobject color)
{

    attr_t attr;
    short pair_id;
    wattr_get(__WINDOWS[window_id], &attr, &pair_id, NULL);

    jint fgId = pair_id >> 3;
    jint bgId = get_color_id(env, color);

    wattron(__WINDOWS[window_id], COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]));

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setForegroundColor
  (JNIEnv * env, jobject self, jint window_id, jobject color)
{

    attr_t attr;
    short pair_id;
    wattr_get(__WINDOWS[window_id], &attr, &pair_id, NULL);

    jint fgId = get_color_id(env, color);
    jint bgId = pair_id % 8;

    wattron(__WINDOWS[window_id], COLOR_PAIR(__COLOR_PAIRS[fgId][bgId]));

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
    mvwchgat(__WINDOWS[window_id], y, x, length, attr, color, NULL);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_bell
  (JNIEnv * env, jobject self)
{
    beep();
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
    clear();
}


JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_clear
  (JNIEnv *env, jobject self, jint window_id)
{
    wclear(__WINDOWS[window_id]);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_refresh
  (JNIEnv * env, jobject self, jint window_id)
{
    wnoutrefresh(__WINDOWS[window_id]);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_doUpdate
  (JNIEnv *env, jobject self)
{
    doupdate();
}

  JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_clearStyle
    (JNIEnv * env, jobject self, jint window_id)
{
    wstandend(__WINDOWS[window_id]);
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getCursorX
  (JNIEnv * env, jobject self, jint window_id)
{
    int x;
    int y;
    getyx(__WINDOWS[window_id], y, x);
    return x;
}

JNIEXPORT jint JNICALL Java_org_jtext_curses_CursesDriver_getCursorY
  (JNIEnv * env, jobject self, jint window_id)
{
    int x;
    int y;
    getyx(__WINDOWS[window_id], y, x);
    return y;
}

JNIEXPORT jboolean JNICALL Java_org_jtext_curses_CursesDriver_moveWindow
  (JNIEnv * env, jobject self, jint window_id, jint x, jint y)
{
    int result;
    result = mvwin(__WINDOWS[window_id], y, x);
    return result == ERR ? JNI_FALSE : JNI_TRUE;
}

JNIEXPORT jboolean JNICALL Java_org_jtext_curses_CursesDriver_resizeWindow
  (JNIEnv * env, jobject self, jint window_id, jint width, jint height)
{
    int result;
    result = wresize(__WINDOWS[window_id], height, width);
    return result == ERR ? JNI_FALSE : JNI_TRUE;
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_deleteWindow
  (JNIEnv * env, jobject self, jint window_id)
{
    delwin(__WINDOWS[window_id]);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_setBackground
  (JNIEnv * env, jobject self, jint window_id, jobject cell_descriptor)
{
    const cchar_t* bg = convert_cell_descriptor(env, cell_descriptor);
    wbkgrnd(__WINDOWS[window_id], bg);
    free(bg);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_changeBackground
  (JNIEnv * env, jobject self, jint window_id, jobject cell_descriptor)
{

    const cchar_t* bg = convert_cell_descriptor(env, cell_descriptor);
    wbkgrndset(__WINDOWS[window_id], bg);
    free(bg);
}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawBox__ILorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2Lorg_jtext_curses_CellDescriptor_2
  (JNIEnv * env, jobject self, jint window_id,
  jobject top_left,
  jobject top,
  jobject top_right,
  jobject right,
  jobject bottom_right,
  jobject bottom,
  jobject bottom_left,
  jobject left)
{

    const cchar_t* top_left_c = convert_cell_descriptor(env, top_left);
    const cchar_t* top_c = convert_cell_descriptor(env, top);
    const cchar_t* top_right_c = convert_cell_descriptor(env, top_right);
    const cchar_t* right_c = convert_cell_descriptor(env, right);
    const cchar_t* bottom_right_c = convert_cell_descriptor(env, bottom_right);
    const cchar_t* bottom_c = convert_cell_descriptor(env, bottom);
    const cchar_t* bottom_left_c = convert_cell_descriptor(env, bottom_left);
    const cchar_t* left_c = convert_cell_descriptor(env, left);

    wborder_set(__WINDOWS[window_id], left_c, right_c, top_c, bottom_c, top_left_c, top_right_c, bottom_left_c, bottom_right_c);

    free(top_left_c);
    free(top_c);
    free(top_right_c);
    free(right_c);
    free(bottom_right_c);
    free(bottom_c);
    free(bottom_left_c);
    free(left_c);

}

JNIEXPORT void JNICALL Java_org_jtext_curses_CursesDriver_drawBox__ICCCCCCCC
  (JNIEnv * env, jobject self, jint window_id,
  jchar top_left,
  jchar top,
  jchar top_right,
  jchar right,
  jchar bottom_right,
  jchar bottom,
  jchar bottom_left,
  jchar left)
{

    const cchar_t* top_left_c = convert_jchar(top_left);
    const cchar_t* top_c = convert_jchar(top);
    const cchar_t* top_right_c = convert_jchar(top_right);
    const cchar_t* right_c = convert_jchar(right);
    const cchar_t* bottom_right_c = convert_jchar(bottom_right);
    const cchar_t* bottom_c = convert_jchar(bottom);
    const cchar_t* bottom_left_c = convert_jchar(bottom_left);
    const cchar_t* left_c = convert_jchar(left);

    wborder_set(__WINDOWS[window_id], left_c, right_c, top_c, bottom_c, top_left_c, top_right_c, bottom_left_c, bottom_right_c);

    free(top_left_c);
    free(top_c);
    free(top_right_c);
    free(right_c);
    free(bottom_right_c);
    free(bottom_c);
    free(bottom_left_c);
    free(left_c);
}
