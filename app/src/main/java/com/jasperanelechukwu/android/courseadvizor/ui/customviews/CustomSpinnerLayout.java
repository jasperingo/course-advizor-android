package com.jasperanelechukwu.android.courseadvizor.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.jasperanelechukwu.android.courseadvizor.R;

import java.util.List;

public class CustomSpinnerLayout extends LinearLayout {
    private final Spinner spinner;

    private final TextView errorTextView;

    public CustomSpinnerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String label;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomSpinnerLayout, 0, 0);

        try {
            label = a.getString(R.styleable.CustomSpinnerLayout_android_hint);
        } finally {
            a.recycle();
        }

        inflate(context, R.layout.custom_spinner_layout, this);

        spinner = findViewById(R.id.custom_spinner_input);

        errorTextView = findViewById(R.id.custom_spinner_layout_error_text_view);

        ((TextView) findViewById(R.id.custom_spinner_text_view)).setText(label);

        setError(0);
    }

    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
    }

    public void setOptions(List<?> options) {
        final ArrayAdapter<?> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void setError(@StringRes int error) {
        if (error == 0) {
            errorTextView.setVisibility(GONE);
        } else {
            errorTextView.setText(error);
            errorTextView.setVisibility(VISIBLE);
        }
    }

    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelectedListener.onItemSelected(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Object value);
    }
}
