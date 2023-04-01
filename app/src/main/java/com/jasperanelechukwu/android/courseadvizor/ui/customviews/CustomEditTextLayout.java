package com.jasperanelechukwu.android.courseadvizor.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.R;

public class CustomEditTextLayout extends LinearLayout {
    private final EditText editText;

    private final TextView errorTextView;

    public CustomEditTextLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String label;

        int maxLength;

        int inputType;

        String autoFillHint;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomEditTextLayout, 0, 0);

        try {
            label = a.getString(R.styleable.CustomEditTextLayout_android_hint);
            autoFillHint = a.getString(R.styleable.CustomEditTextLayout_android_autofillHints);
            inputType = a.getInt(R.styleable.CustomEditTextLayout_android_inputType, EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            maxLength = a.getInt(R.styleable.CustomEditTextLayout_android_maxLength, 0);
        } finally {
            a.recycle();
        }

        inflate(context, R.layout.custom_edit_text_layout, this);

        ((TextView) findViewById(R.id.custom_edit_text_text_view)).setText(label);

        errorTextView = findViewById(R.id.custom_edit_text_layout_error_text_view);

        editText = findViewById(R.id.custom_edit_text_input);

        editText.setInputType(inputType);

        if (maxLength > 0) {
            InputFilter[] editFilters = editText.getFilters();
            InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
            newFilters[editFilters.length] = new InputFilter.LengthFilter(maxLength);
            editText.setFilters(newFilters);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && autoFillHint != null) {
            editText.setAutofillHints(autoFillHint);
        }

        setError(0);
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setError(@StringRes int error) {
        if (error == 0) {
            errorTextView.setVisibility(GONE);
        } else {
            errorTextView.setText(error);
            errorTextView.setVisibility(VISIBLE);
        }
    }

    public void setEnabled(boolean enabled) {
        editText.setEnabled(enabled);
    }

    public void setTextChangedListener(OnTextChangedListener textChangedListener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textChangedListener.onTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence value);
    }
}
