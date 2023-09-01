package com.mindcod3r.wenera.Components;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;

public class TitleArg extends androidx.appcompat.widget.AppCompatTextView {
    Context context;

    public TitleArg(Context context, String text) {
        super(context);

        setTextColor(context.getColor(R.color.outline));
        setTextSize(Utils.dp(context, 5));
        setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setTypeface(context.getResources().getFont(R.font.sans));
        }
        setPadding(Utils.dp(context, 10), Utils.dp(context, 5), 0, 0);
        setGravity(Gravity.CENTER_VERTICAL);

        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    }
}
