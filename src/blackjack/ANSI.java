package blackjack;

public final class ANSI {
    //Code taken from http://www.topmudsites.com/forums/showthread.php?t=413#post4076
	//Black and white version, colour codes are removed
    public static final String DEFAULT = "";

    public static final String LIGHTER = "";
    public static final String DARKER = "";
    public static final String ITALIC = "";
    public static final String UNDERLINE = "";
    public static final String STOP_UNDERLINE = "";
    public static final String BLINK = "";
    public static final String RAPID_BLINK = "";
    public static final String REVERSE_VIDEO = "";
    public static final String INVISIBLE_TEXT = "";

    public static final String BLACK = "";
    public static final String RED = "";
    public static final String GREEN = "";
    public static final String YELLOW = "";
    public static final String BLUE = "";
    public static final String MAGENTA = "";
    public static final String CYAN = "";
    public static final String WHITE = "";

    public static final String BACKGROUND_BLACK = "";
    public static final String BACKGROUND_RED = "";
    public static final String BACKGROUND_GREEN = "";
    public static final String BACKGROUND_YELLOW = "";
    public static final String BACKGROUND_BLUE = "";
    public static final String BACKGROUND_MAGENTA = "";
    public static final String BACKGROUND_CYAN = "";
    public static final String BACKGROUND_WHITE = "";
    
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