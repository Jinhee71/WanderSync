package com.example.sprintproject;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WSBotNavView extends BottomNavigationView {

    public WSBotNavView(@NonNull Context context) {
        super(context);
    }

    public WSBotNavView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WSBotNavView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WSBotNavView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getMaxItemCount() {
        return 6;
    }
}
