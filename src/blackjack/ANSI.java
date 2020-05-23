package blackjack;

public final class ANSI {
    //Code taken from http://www.topmudsites.com/forums/showthread.php?t=413#post4076
    public static final String DEFAULT = "\u001B[0m";

    public static final String LIGHTER = "\u001B[1m";
    public static final String DARKER = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String STOP_UNDERLINE = "\u001B[24m";
    public static final String BLINK = "\u001B[5m";
    public static final String RAPID_BLINK = "\u001B[6m";
    public static final String REVERSE_VIDEO = "\u001B[7m";
    public static final String INVISIBLE_TEXT = "\u001B[8m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BACKGROUND_BLACK = "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_MAGENTA = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";
    
    public static final String HOUSE_COLOUR = ANSI.DARKER+ ANSI.GREEN;
    public static final String AI_COLOUR = ANSI.DARKER+ANSI.CYAN;
    public static final String USER_COLOUR = ANSI.MAGENTA;
    
    public static void testPrint() {
        System.out.println(DEFAULT+"Default"+DEFAULT);
        System.out.println(LIGHTER+"Lighter"+DEFAULT);
        System.out.println(DARKER+"Darker"+DEFAULT);
        System.out.println(ITALIC+"Italic"+DEFAULT);
        System.out.println(UNDERLINE+"Underline"+DEFAULT);
        System.out.println(STOP_UNDERLINE+"Stop underline"+DEFAULT);
        System.out.println(BLINK+"Blink"+DEFAULT);
        System.out.println(RAPID_BLINK+"Rapid Blink"+DEFAULT);
        System.out.println(REVERSE_VIDEO+"Reverse Video"+DEFAULT);
        System.out.println(INVISIBLE_TEXT+"Invisible text"+DEFAULT);
        System.out.println(BLACK+"Black"+DEFAULT);
        System.out.println(RED+"Red"+DEFAULT);
        System.out.println(GREEN+"Green"+DEFAULT);
        System.out.println(YELLOW+"Yellow"+DEFAULT);
        System.out.println(BLUE+"Blue"+DEFAULT);
        System.out.println(MAGENTA+"Magenta"+DEFAULT);
        System.out.println(CYAN+"Cyan"+DEFAULT);
        System.out.println(WHITE+"White"+DEFAULT);
        System.out.println(BACKGROUND_BLACK+"BG Black"+DEFAULT);
        System.out.println(BACKGROUND_RED+"BG Red"+DEFAULT);
        System.out.println(BACKGROUND_GREEN+"BG Green"+DEFAULT);
        System.out.println(BACKGROUND_YELLOW+"BG Yellow"+DEFAULT);
        System.out.println(BACKGROUND_BLUE+"BG Blue"+DEFAULT);
        System.out.println(BACKGROUND_MAGENTA+"BG Magenta"+DEFAULT);
        System.out.println(BACKGROUND_CYAN+"BG Cyan"+DEFAULT);
        System.out.println(BACKGROUND_WHITE+"BG White"+DEFAULT);
    }
}