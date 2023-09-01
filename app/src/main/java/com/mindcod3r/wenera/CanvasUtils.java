package com.mindcod3r.wenera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.mindcod3r.wenera.Utils;

public class CanvasUtils {
    public static float destiny = 0;

    public static int dpi(float dp) {
        float scale = Utils.context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

    public static void drawRightLine(Canvas canvas, View v, float size, int color) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(color);
        int dp = dpi(size);
        canvas.drawRect(v.getWidth() - dp, destiny, v.getWidth(), v.getHeight(), rectPaint);
    }


    public static void drawBottomLine(Canvas canvas, View v, float size, int color) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(color);
        int dp = dpi(size);
        canvas.drawRect(0, v.getHeight() - dp, v.getWidth(), v.getHeight(), rectPaint);
    }

    public static void drawLeftLine(Canvas canvas, View v, float size, int color) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(color);
        int dp = dpi(size);
        canvas.drawRect(0, destiny, dp, v.getHeight(), rectPaint);
    }

    public static int[] getTextSize(Paint paint, String text) {
        Rect bounds = new Rect();

        int text_height = 0;
        int text_width = 0;

        paint.getTextBounds(text,0,text.length(),bounds);

        text_height =bounds.height();
        text_width =bounds.width();

        return new int[] {text_width, text_height};
    }

    public static void drawTopLine(Canvas canvas, View v, float size, int color) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(color);
        int dp = dpi(size);
        canvas.drawRect(0, 0, v.getWidth(), dp, rectPaint);
    }
}
