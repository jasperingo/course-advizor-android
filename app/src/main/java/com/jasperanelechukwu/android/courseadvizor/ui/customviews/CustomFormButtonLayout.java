package com.jasperanelechukwu.android.courseadvizor.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.jasperanelechukwu.android.courseadvizor.R;

public class CustomFormButtonLayout extends LinearLayout {
    private final MaterialButton button;

    private final ProgressBar progressBar;

    public CustomFormButtonLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String text;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomFormButtonLayout, 0, 0);

        try {
            text = a.getString(R.styleable.CustomFormButtonLayout_android_text);
        } finally {
            a.recycle();
        }

        inflate(context, R.layout.custom_form_button_layout, this);

        button = findViewById(R.id.custom_form_button_layout_button);

        button.setText(text);

        progressBar = findViewById(R.id.custom_form_button_layout_progress_bar);
    }

    public void setLoading(boolean loading) {
        if (loading) {
            button.setVisibility(GONE);
            progressBar.setVisibility(VISIBLE);
        } else {
            button.setVisibility(VISIBLE);
            progressBar.setVisibility(GONE);
        }
    }

    public void setOnClick(OnClickListener listener) {
        button.setOnClickListener(listener);
    }
}
