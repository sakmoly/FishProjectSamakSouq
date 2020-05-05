package qa.happytots.yameenhome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.utils.Font;


/**
 * Created by  melvin.john@oneteam.us on 1/6/17.
 */

public class YameenEditText extends AppCompatEditText {

    private int mFont;

    public YameenEditText(Context context) {
        super(context);
    }

    public YameenEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context, attrs);
    }

    private void setFont(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.AppsTeamView);
            mFont = array.getInt(R.styleable.AppsTeamView_font_name, -1);
            setCustomTypeface(context);
            array.recycle();
        }
    }

    public void setCustomTypeface(Context context) {
        Typeface typeface = Font.getFontType(context, mFont);
        if (typeface != null) {
            setTypeface(typeface);
        }
    }

    public int getFont() {
        return mFont;
    }

    public void setFont(int mFont) {
        this.mFont = mFont;

        setCustomTypeface(getContext());
    }
}
