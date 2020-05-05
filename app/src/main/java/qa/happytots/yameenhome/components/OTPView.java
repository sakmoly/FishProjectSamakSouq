package qa.happytots.yameenhome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import qa.happytots.yameenhome.R;

public class OTPView extends LinearLayout {

    @IntegerRes
    private int mFieldCount;

    private int mCurrentEditTextPosition = 0;

    private EditText mCurrentEditText;

    public OTPView(Context context) {
        super(context);
        init(context, null);
    }

    public OTPView(Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OTPView);
            mFieldCount = array.getInt(R.styleable.OTPView_field_count, 4);
            array.recycle();

        }

        setupOTPView();
        setFocus();
    }

    private void setupOTPView() {
        for (int i = 0; i < mFieldCount; i++) {
            YameenEditText editText = new YameenEditText(getContext());
            editText.setWidth(getDpAsPixel(50));
            editText.setHeight(getDpAsPixel(40));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setMaxLines(1);
            setMaxLength(editText);
            editText.setId(i);
            editText.setCursorVisible(false);
            editText.setFont(2);
            editText.setGravity(Gravity.CENTER);
            editText.setTextSize(getDpAsPixel(18));
            editText.setPadding(getDpAsPixel(10), getDpAsPixel(10), getDpAsPixel(10), getDpAsPixel(10));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, getDpAsPixel(16), 0);
            lp.weight = 1;
            lp.gravity = Gravity.CENTER;
            editText.setOnKeyListener(mBackKeyListener);
            editText.addTextChangedListener(mEditTextWatcher);
            addView(editText);
        }

        mCurrentEditText = (EditText) getChildAt(0);
        mCurrentEditTextPosition = 0;
    }

    private TextWatcher mEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && mCurrentEditTextPosition < mFieldCount) {
                EditText view = (EditText) getChildAt(mCurrentEditTextPosition);
                view.focusSearch(View.FOCUS_RIGHT).requestFocus();
                mCurrentEditTextPosition++;
                mCurrentEditText = (EditText) getChildAt(mCurrentEditTextPosition);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0 && mCurrentEditTextPosition > 0) {
                mCurrentEditTextPosition = mCurrentEditTextPosition - 1;
                if (mCurrentEditTextPosition < 0) {
                    mCurrentEditTextPosition = 0;
                }
                mCurrentEditText = (EditText) getChildAt(mCurrentEditTextPosition);
                mCurrentEditText.requestFocus();
            }
        }
    };

    private OnKeyListener mBackKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                YameenEditText editText = (YameenEditText) v;
                int textLength = editText.getText().length();
                if (textLength == 0 && editText.getId() != 0) {
                    mCurrentEditTextPosition = mCurrentEditTextPosition - 1;

                    if (mCurrentEditTextPosition < 0) {
                        mCurrentEditTextPosition = 0;
                    }
                    EditText edtText = (EditText) getChildAt(mCurrentEditTextPosition);
                    edtText.requestFocus();
                }
            }
            return false;
        }
    };

    private void setMaxLength(EditText editText) {
        int maxLength = 1;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(fArray);
    }

    private void setFocus() {
        for (int i = 0; i < mFieldCount; i++) {
            EditText view = (EditText) getChildAt(i);
            int j = i + 1;

            view.setNextFocusRightId(j >= mFieldCount ? i : j);
            if (i == 0) continue;
            view.setNextFocusLeftId(i - 1);
        }
    }

    private int getDpAsPixel(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return  (int) (dp * scale + 0.5f);
    }

    public String getOTP() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getChildCount(); i++) {
            YameenEditText editText = (YameenEditText) getChildAt(i);
            String otp = editText.getText().toString();
            builder.append(otp);
        }
        return builder.toString();
    }

}
