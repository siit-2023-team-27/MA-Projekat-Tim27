package com.example.nomad.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;

public class AddTextToDates implements LineBackgroundSpan {
    private String dayPrice;
    public AddTextToDates(String datePrice){
        this.dayPrice = datePrice;
    }
    @Override
    public void drawBackground(@NonNull Canvas canvas, @NonNull Paint paint, int left, int right, int top, int baseline, int bottom, @NonNull CharSequence text, int start, int end, int lineNumber) {
        canvas.drawText(dayPrice,Float.valueOf(((left+right)/4)),Float.valueOf((bottom+15)),paint);
    }
}
