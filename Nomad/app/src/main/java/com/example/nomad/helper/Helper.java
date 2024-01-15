package com.example.nomad.helper;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.Date;

public class Helper {
    public static Date toDate(CalendarDay calendarDay){
// Extract year, month, and day from CalendarDay
        int year = calendarDay.getYear();
        int month = calendarDay.getMonth();
        int day = calendarDay.getDay();


// Create a Calendar instance and set the extracted values
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0,0); // Month is 0-based in Calendar
        calendar.set(Calendar.MILLISECOND, 0);
// Convert Calendar to Date
        Date date = calendar.getTime();
        Log.i("ShopApp", "toDate: " + date.getTime());

        return date;
    }
}
