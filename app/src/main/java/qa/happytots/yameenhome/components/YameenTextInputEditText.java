package qa.happytots.yameenhome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.utils.Font;

public class YameenTextInputEditText extends TextInputEditText {
    public YameenTextInputEditText(Context context) {
        super(context);
        setFont(context, null);
    }

    public YameenTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context, attrs);
    }

    public YameenTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
