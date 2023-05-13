package com.jasperanelechukwu.android.courseadvizor.entities.ui;

import com.jasperanelechukwu.android.courseadvizor.entities.Session;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionsUiState {
    private List<Session> sessions;

    private boolean loading;

    private String error;

    private boolean loaded;

    public SessionsUiState() {
        this(new ArrayList<>(), false, null, false);
    }

    public SessionsUiState(boolean loading) {
        this(new ArrayList<>(), loading, null, false);
    }

    public SessionsUiState(String error) {
        this(new ArrayList<>(), false, error, false);
    }

    public SessionsUiState(List<Session> sessions, boolean loaded) {
        this(sessions, false, null, loaded);
    }
}
