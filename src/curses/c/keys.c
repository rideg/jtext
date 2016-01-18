#include "../headers/keys.h"
#include <curses.h>

const char* map_key(int code)
{
    if(code == KEY_RESIZE) return "RESIZE";
    if(code == ERR) return "ERR";
    if(code == 27) return "ESC";
    return "OTHER";
}