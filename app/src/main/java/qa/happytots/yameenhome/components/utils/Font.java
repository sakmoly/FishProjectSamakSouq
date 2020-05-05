package qa.happytots.yameenhome.components.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by yadhukrishnan.e@oneteam.us
 */

public class Font {


    private final static int MONTSERRAT_BLACK = 0;
    private final static int MONTSERRAT_BLACK_ITALIC = 1;
    private final static int MONTSERRAT_BOLD = 2;
    private final static int MONTSERRAT_BOLD_ITALIC = 3;
    private final static int MONTSERRAT_EXTRA_BOLD = 4;
    private final static int MONTSERRAT_EXTRA_BOLD_ITALIC = 5;
    private final static int MONTSERRAT_EXTRA_LIGHT = 6;
    private final static int MONTSERRAT_LIGHT = 7;
    private final static int MONTSERRAT_SEMI_BOLD = 8;
    private final static int MONTSERRAT_MEDIUM = 9;
    private final static int MONTSERRAT_REGULAR = 10;



    public static Typeface getFontType(Context context, int fontSetInXML) {
        String fontName = null;
        switch (fontSetInXML) {
            case MONTSERRAT_BLACK:
                fontName = "montserrat_black.ttf";
                break;
            case MONTSERRAT_BLACK_ITALIC:
                fontName = "montserrat_black_italic.ttf";
                break;
            case MONTSERRAT_BOLD:
                fontName = "montserrat_bold.ttf";
                break;
            case MONTSERRAT_BOLD_ITALIC:
                fontName = "montserrat_bold_italic.ttf";
                break;
            case MONTSERRAT_EXTRA_BOLD:
                fontName = "montserrat_extra_bold.ttf";
                break;
            case MONTSERRAT_EXTRA_BOLD_ITALIC:
                fontName = "montserrat_extra_bold_italic.ttf";
                break;
            case MONTSERRAT_EXTRA_LIGHT:
                fontName = "montserrat_extra_light.ttf";
                break;
            case MONTSERRAT_LIGHT:
                fontName = "montserrat_light.ttf";
                break;
            case MONTSERRAT_SEMI_BOLD:
                fontName = "montserrat_semi_bold.ttf";
                break;
            case MONTSERRAT_MEDIUM:
                fontName = "montserrat_medium.ttf";
                break;
            case MONTSERRAT_REGULAR:
                fontName = "montserrat_regular.ttf";
                break;
        }
        if (fontName == null) {
            return null;
        } else {
            return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
        }
    }
}
