package com.mindcod3r.wenera.Components;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class EditText extends LinearLayout {
    Context context;
    GradientDrawable background;

    android.widget.EditText textInput;
    int currentColor;
    ValueAnimator colorAnim;
    LinearLayout input;

    public void setFocus(boolean isFocus) {
        colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, context.getColor(isFocus ? R.color.accent : R.color.outline));
        colorAnim.setDuration(200);
        colorAnim.addUpdateListener((anim) -> {
            background.setStroke(Utils.dp(context, 3), (int)anim.getAnimatedValue());
            currentColor = (int)anim.getAnimatedValue();
            textInput.setTextColor((int)anim.getAnimatedValue());
        });
        colorAnim.start();
    }

    public EditText(Context context) {
        super(context);
        initialize(context);
    }

    AttributeSet attr;
    public EditText(Context context, AttributeSet attr) {
        super(context, attr);
        this.attr = attr;
        initialize(context);
    }

    public void setText(String text) {
        textInput.setText(text);
    }

    public String getText() {
        return textInput.getText().toString();
    }

    public void initialize(Context context) {
        this.context = context;
        this.currentColor = context.getColor(R.color.outline);

        setOrientation(VERTICAL);

        input = new LinearLayout(context);
        {
            addView(input, new LayoutParams(-1, Utils.dp(context, 35), 1));
        }

        background = new GradientDrawable();
        {
            background.setColor(Color.TRANSPARENT);
            background.setCornerRadius(Utils.dp(context, 10));

            input.setBackground(background);
        }

        textInput = new android.widget.EditText(context);
        {
            textInput.setBackground(null);
            textInput.setTextSize(Utils.dp(context, 6));
            textInput.setPadding(Utils.dp(context, 10),0,0,0);

            textInput.setSingleLine(true);
            input.addView(textInput, -1, -1);

            textInput.setOnFocusChangeListener((v, f) -> {
                setFocus(f);
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                textInput.setTypeface(context.getResources().getFont(R.font.sans));
            }
        }

        setFocus(false);

    }
}
