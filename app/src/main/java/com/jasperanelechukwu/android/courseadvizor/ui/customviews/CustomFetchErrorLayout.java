package com.jasperanelechukwu.android.courseadvizor.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jasperanelechukwu.android.courseadvizor.R;

public class CustomFetchErrorLayout extends LinearLayout {
    private final Button button;

    private final TextView textView;

    public CustomFetchErrorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.custom_fetch_error_layout, this);

        button = findViewById(R.id.custom_fetch_error_layout_button);

        textView = findViewById(R.id.custom_fetch_error_layout_text_view);
    }

    public void setErrorText(final String text) {
        textView.setText(text);
    }

    public void setRetryClickListener(OnClickListener listener) {
        button.setOnClickListener(listener);
    }
}
