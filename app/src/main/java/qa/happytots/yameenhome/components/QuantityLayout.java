package qa.happytots.yameenhome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import qa.happytots.yameenhome.R;

public class QuantityLayout extends LinearLayout {

    public static final int TYPE_UNDERLINE = 0;
    public static final int TYPE_UNDERLINE_RED = 1;
    private static final int TYPE_MORE = 2;
    private static final int TYPE_LESS = 3;

    private static final String QUANTITY_UNIT = "%s KG";

    private YameenTextView tvQuantity;
    private YameenTextView tvUnderline;

    private int tagPosition;

    private int mQuantity;
    private int mtype = TYPE_UNDERLINE;

    public QuantityLayout(Context context) {
        super(context);
        init(null);
    }

    public QuantityLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            inflate(getContext(), R.layout.layout_quantity, this);

            tvQuantity = findViewById(R.id.tv1);
            tvUnderline = findViewById(R.id.bar_view);

            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.QuantityLayout);

            mQuantity = array.getInt(R.styleable.QuantityLayout_Quantity, -1);
            mtype = array.getInt(R.styleable.QuantityLayout_type, -1);

            array.recycle();

            setValues();
        }
    }

    private void setValues() {
        setQuantity();
        setUnderLineBg();
    }

    private void setQuantity() {
        tvQuantity.setText(String.format(QUANTITY_UNIT, mQuantity));
    }

    private void setUnderLineBg() {
        Drawable drawable = null;
        switch (mtype) {
            case TYPE_UNDERLINE:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_rounded_gray);
                break;
            case TYPE_UNDERLINE_RED:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_rounded_red);
                break;
            case TYPE_MORE:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_rounded_gray);
                tvQuantity.setText(R.string.label_quantity_more);
                break;
            case TYPE_LESS:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_rounded_gray);
                tvQuantity.setText(R.string.label_quantity_less);
                break;
        }

        if (drawable != null) {
            tvUnderline.setBackgroundDrawable(drawable);
        }
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
        setQuantity();
    }

    public int getType() {
        return mtype;
    }

    public void setType(int mtype) {
        this.mtype = mtype;
        setUnderLineBg();
    }

    public int getTagPosition() {
        return tagPosition;
    }

    public void setTagPosition(int tagPosition) {
        this.tagPosition = tagPosition;
    }
}
