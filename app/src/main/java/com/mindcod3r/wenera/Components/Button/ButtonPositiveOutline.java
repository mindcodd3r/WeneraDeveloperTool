package com.mindcod3r.wenera.Components.Button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class ButtonPositiveOutline extends androidx.appcompat.widget.AppCompatButton {
    Context context;

    public ButtonPositiveOutline(Context context){
        super(context);
        this.context = context;
        initialize();
    }
    public ButtonPositiveOutline(Context context, AttributeSet attr){
        super(context, attr);
        this.context = context;
        initialize();
    }

    private void initialize() {
        GradientDrawable draw = new GradientDrawable();
        {
            draw.setStroke(Utils.dp(context, 3), context.getColor(R.color.accent));
            draw.setCornerRadius(Utils.dp(context, 333));
            setBackground(draw);
        }

        setPadding(0,0,0,0);

        {
            setTextColor(context.getColor(R.color.accent));
            setTextSize(Utils.dp(context, 5));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setTypeface(context.getResources().getFont(R.font.sans));
            }
        }
    }
}
