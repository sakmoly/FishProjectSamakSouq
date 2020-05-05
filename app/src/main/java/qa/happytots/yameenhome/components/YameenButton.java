package qa.happytots.yameenhome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.utils.Font;


/**
 * Created by yadhukrishnan.e@oneteam.us
 */

public class YameenButton extends AppCompatButton {

    public YameenButton(Context context) {
        super(context);
    }

    public YameenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context, attrs);
    }

    private void setFont(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.AppsTeamView);
            int font = array.getInt(R.styleable.AppsTeamView_font_name, -1);
            setCustomTypeface(context, font);
            array.recycle();
        }
    }

    public void setCustomTypeface(Context context, int font) {
        Typeface typeface = Font.getFontType(context, font);
        if (typeface != null) {
            setTypeface(typeface);
        }
    }
}
