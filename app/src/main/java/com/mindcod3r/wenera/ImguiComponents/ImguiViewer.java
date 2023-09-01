package com.mindcod3r.wenera.ImguiComponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;

import com.mindcod3r.wenera.ImguiEditor;
import com.mindcod3r.wenera.R;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ImguiViewer extends LinearLayout {
    Context context;

    public boolean isDashVisible = true;

    public ArrayList<ImguiShape> shapes = new ArrayList<>();

    public ImguiViewer(Context context) {
        super(context);
    }

    public ImguiViewer(Context context, AttributeSet set) {
        super(context, set);
    }

    public void updateScreen() {
        setBackgroundColor(Color.TRANSPARENT);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (ImguiShape shape: shapes) {
            shape.onDraw(canvas);
        }

        if (isDashVisible) {
            Drawable dash = ImguiEditor.globalContext.getResources().getDrawable(R.drawable.dashstroke);
            dash.setBounds(0, 0, getWidth(), getHeight());
            dash.draw(canvas);
        }
    }

}
