#include "../headers/keys.h"
#include <curses.h>

const char* map_key(int code)
{

    if (code == 0) return "NULL";
    if (code == 1) return "START_HEADING";
    if (code == 2) return "START_OF_TEXT";
    if (code == 3) return "END_OF_TEXT";
    if (code == 4) return "END_OF_TRANSMISSION";
    if (code == 5) return "ENQUIRY";
    if (code == 6) return "ACKNOWLEDGE";
    if (code == 7) return "BELL";
    if (code == 8) return "CTRL_BACKSPACE";
    if (code == 9) return "HORIZONTAL_TAB";
    if (code == 10) return "NEW_LINE";
    if (code == 11) return "VERTICAL_TAB";
    if (code == 12) return "FROM_FEED";
    if (code == 13) return "CARRIAGE_RETURN";
    if (code == 14) return "SHIFT_OUT";
    if (code == 15) return "SHIFT_IN";
    if (code == 16) return "DATA_LINK_ESCAPE";
    if (code == 17) return "DEVICE_CTRL_ONE";
    if (code == 18) return "DEVICE_CTRL_TWO";
    if (code == 19) return "DEVICE_CTRL_THREE";
    if (code == 20) return "DEVICE_CTRL_FOUR";
    if (code == 21) return "NEGATIVE_ACK";
    if (code == 22) return "SYNC_IDLE";
    if (code == 23) return "EOF_TRANSMISSION_BLOCK";
    if (code == 24) return "CANCEL";
    if (code == 25) return "EOF_MEDIUM";
    if (code == 26) return "SUBSTITUTE";
    if (code == 27) return "ESCAPE";
    if (code == 28) return "FILE_SEPARATOR";
    if (code == 29) return "GROUP_SEPARATOR";
    if (code == 30) return "RECORD_SEPARATOR";
    if (code == 31) return "UNIT_SEPARATOR";
    if (code == 32) return "SPACE";

    if(code >= KEY_F0 && code < KEY_F0 + 64) {
        return "FUNCTION_KEY";
    }

    if (code == KEY_BACKSPACE) return "BACKSPACE";
    if (code == KEY_DC) return "DELETE";
    if (code == KEY_IC) return "INSERT";
    if (code == KEY_HOME) return "HOME";
    if (code == KEY_END) return "END";
    if (code == KEY_NPAGE) return "NEXT_PAGE";
    if (code == KEY_PPAGE) return "PREVIOUS_PAGE";
    if (code == KEY_UP) return "UP";
    if (code == KEY_DOWN) return "DOWN";
    if (code == KEY_LEFT) return "LEFT";
    if (code == KEY_RIGHT) return "RIGHT";

    if (code == KEY_BTAB) return "SHIFT_TAB";
    if (code == KEY_SDC) return "SHIFT_DELETE";
    if (code == KEY_SIC) return "SHIFT_HOME";
    if (code == KEY_SNEXT) return "SHIFT_NEXT_PAGE";
    if (code == KEY_SPREVIOUS) return "SHIFT_PREVIOUS_PAGE";

    if (code == KEY_SR) return "SHIFT_UP";
    if (code == KEY_SF) return "SHIFT_DOWN";
    if (code == KEY_SLEFT) return "SHIFT_LEFT";
    if (code == KEY_SRIGHT) return "SHIFT_RIGHT";

    if (code == 567) return "CTRL_UP";
    if (code == 526) return "CTRL_DOWN";
    if (code == 546) return "CTRL_LEFT";
    if (code == 561) return "CTRL_RIGHT";

    if (code == 536) return "CTRL_HOME";
    if (code == 531) return "CTRL_END";
    if (code == 520) return "CTRL_DELETE";
    if (code == 551) return "CTRL_NEXT_PAGE";
    if (code == 556) return "CTRL_PREVIOUS_PAGE";

    if (code == 539) return "ALT_INSERT";
    if (code == 534) return "ALT_HOME";
    if (code == 518) return "ALT_DELETE";
    if (code == 529) return "ALT_END";
    if (code == 549) return "ALT_NEXT_PAGE";
    if (code == 554) return "ALT_PREVIOUS_PAGE";

    if( code == 565) return "ALT_UP";
    if( code == 524) return "ALT_DOWN";
    if( code == 544) return "ALT_LEFT";
    if( code == 559) return "ALT_RIGHT";

    if (code == 569) return "CTRL_ALT_UP";
    if (code == 528) return "CTRL_ALT_DOWN";
    if (code == 548) return "CTRL_ALT_LEFT";
    if (code == 563) return "CTRL_ALT_RIGHT";
    if (code == 553) return "CTRL_ALT_NEXT_PAGE";
    if (code == 558) return "CTRL_ALT_PREVIOUS_PAGE";

    if (code == 568) return "SHIFT_CTRL_UP";
    if (code == 527) return "SHIFT_CTRL_DOWN";
    if (code == 562) return "SHIFT_CTRL_RIGHT";
    if (code == 547) return "SHIFT_CTRL_LEFT";
    if (code == 537) return "SHIFT_CTRL_HOME";
    if (code == 532) return "SHIFT_CTRL_END";
    if (code == 521) return "SHIFT_CTRL_DELETE";

    if (code == 566) return "SHIFT_ALT_UP";
    if (code == 525) return "SHIFT_ALT_DOWN";
    if (code == 545) return "SHIFT_ALT_LEFT";
    if (code == 560) return "SHIFT_ALT_RIGHT";

    if( code == 535) return "SHIFT_ALT_HOME";
    if( code == 530) return "SHIFT_ALT_END";
    if( code == 519) return "SHIFT_ALT_DELETE";
    if( code == 550) return "SHIFT_ALT_NEXT_PAGE";
    if( code == 555) return "SHIFT_ALT_PREVIOUS_PAG";

    if( code == KEY_RESIZE) return "RESIZE";
    if( code == ERR) return "ERR";
    return "UNKNOWN";
}