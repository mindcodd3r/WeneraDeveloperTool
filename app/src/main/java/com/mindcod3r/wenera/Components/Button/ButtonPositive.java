package com.mindcod3r.wenera.Components.Button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class ButtonPositive extends androidx.appcompat.widget.AppCompatButton {
    Context context;

    public ButtonPositive(Context context){
        super(context);
        this.context = context;
        initialize();
    }
    public ButtonPositive(Context context, AttributeSet attr){
        super(context, attr);
        this.context = context;
        initialize();
    }

    private void initialize() {
        GradientDrawable draw = new GradientDrawable();
        {
            draw.setColor(context.getColor(R.color.accent));
            draw.setCornerRadius(Utils.dp(context, 333));
            setBackground(draw);
        }

        setPadding(0,0,0,0);

        {
            setTextColor(Color.WHITE);
            setTextSize(Utils.dp(context, 5));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setTypeface(context.getResources().getFont(R.font.sans));
            }
        }
    }
}
