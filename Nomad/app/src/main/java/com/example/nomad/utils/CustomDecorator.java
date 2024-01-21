package com.example.nomad.utils;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

public class CustomDecorator implements DayViewDecorator {
    private final HashSet<CalendarDay> dates;
    private final String text;
    private final int textColor;

    public CustomDecorator(CalendarDay date, String text, int textColor) {
        this.dates = new HashSet<>();
        this.dates.add(date);
        this.text = text;
        this.textColor = textColor;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        view.addSpan(new AddTextToDates(text));
    }
}
