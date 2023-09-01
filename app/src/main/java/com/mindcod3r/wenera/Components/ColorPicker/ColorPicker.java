package com.mindcod3r.wenera.Components.ColorPicker;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.Components.TitleArg;
import com.mindcod3r.wenera.Utils;

public class ColorPicker extends LinearLayout {
    Context context;
    int currentColor = 0;

    TitleArg title;
    ColorPickerComponent cp;

    public void setColor(int color) {
        currentColor = color;
        cp.setColorV(color);
    }

    public void setCallback(ColorPickerComponent.Callback callback) {
        if (cp != null) cp.callback = callback;
    }

    public ColorPicker(Context context, String text, int startColor) {
        super(context);
        this.context = context;
        this.currentColor = startColor;

        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(-1, Utils.dp(context, 35)));

        title = new TitleArg(context, text);
        {
            title.setPadding(Utils.dp(context, 10), 0, 0, 0);
            addView(title, new LayoutParams(-1, -1, 1));
        }

        cp = new ColorPickerComponent(context);
        {
            cp.setColor(currentColor);
            addView(cp, Utils.dp(context, 35), Utils.dp(context, 35));
        }
    }
}
