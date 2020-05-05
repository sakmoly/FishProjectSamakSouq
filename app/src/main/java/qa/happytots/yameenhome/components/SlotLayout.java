package qa.happytots.yameenhome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import qa.happytots.yameenhome.R;

public class SlotLayout extends ConstraintLayout {

    private YameenTextView tvDay;
    private YameenTextView tvDayNo;
    private YameenTextView tvMonth;
    private YameenTextView tvUnderline;

    private String mWeekDay = "";
    private int mMonthDay;
    private String mMonth = "";
    private int colorCode;

    private boolean isSelected;

    public SlotLayout(Context context) {
        super(context);
        init(null);
    }

    public SlotLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        inflate(getContext(), R.layout.layout_delivery_days, this);

        tvDay = findViewById(R.id.tv_day_short);
        tvDayNo = findViewById(R.id.tv_day_no);
        tvMonth = findViewById(R.id.tv_month);
        tvUnderline = findViewById(R.id.tv_underline);

        colorCode = ContextCompat.getColor(getContext(), R.color.gray);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SlotLayout);
            mWeekDay = array.getString(R.styleable.SlotLayout_weekDay);
            mMonthDay = array.getInt(R.styleable.SlotLayout_monthDay, -1);
            mMonth = array.getString(R.styleable.SlotLayout_month);
            colorCode = array.getColor(R.styleable.SlotLayout_underlineColor, ContextCompat.getColor(getContext(), R.color.gray));

            array.recycle();

            setValues();
            colorViews();
        }
    }

    private void setValues() {
        tvDay.setText(mWeekDay);
        tvDayNo.setText(String.valueOf(mMonthDay));
        tvMonth.setText(mMonth);
        tvUnderline.setBackgroundColor(colorCode);
    }

    private void colorViews() {
        tvDay.setTextColor(colorCode);
        tvDayNo.setTextColor(colorCode);
        tvMonth.setTextColor(colorCode);
        tvUnderline.setTextColor(colorCode);
    }

    public String getWeekDay() {
        return mWeekDay;
    }

    public void setWeekDay(String mWeekDay) {
        this.mWeekDay = mWeekDay;
        setValues();
    }

    public int getMonthDay() {
        return mMonthDay;
    }

    public void setMonthDay(int mMonthDay) {
        this.mMonthDay = mMonthDay;
        setValues();
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
        setValues();
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = ContextCompat.getColor(getContext(), colorCode);
        setValues();
        colorViews();
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        colorViews();
    }
}
