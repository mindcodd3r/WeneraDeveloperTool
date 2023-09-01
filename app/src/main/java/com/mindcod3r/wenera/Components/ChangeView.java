package com.mindcod3r.wenera.Components;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class ChangeView extends LinearLayout {
    Context context;
    ImageView refresh;

    boolean currentPick = false;

    TextView title;
    GradientDrawable background;

    int currentColor;
    ValueAnimator colorAnim;

    public void setFocus(boolean isFocus) {
        colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, context.getColor(isFocus ? R.color.accent : R.color.outline));
        colorAnim.setDuration(200);
        colorAnim.addUpdateListener((anim) -> {
            background.setStroke(Utils.dp(context, 3), (int)anim.getAnimatedValue());
            refresh.setColorFilter((int)anim.getAnimatedValue());
            currentColor = (int)anim.getAnimatedValue();

            title.setTextColor((int)anim.getAnimatedValue());
        });
        colorAnim.start();
    }
    public void changeValue() {
        this.currentPick = !this.currentPick;
        title.setText(String.valueOf(currentPick));
        if (callback != null) callback.onChange(currentPick);
    }

    public ChangeView(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public ChangeView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initialize();
    }

    public static interface Callback {
        public void onChange(boolean isEnable);
    }
    public Callback callback;
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public boolean getValue() {
        return currentPick;
    }

    public void setValue(boolean isCheck) {
        currentPick = isCheck;
        title.setText(String.valueOf(isCheck));
    }

    public void initialize() {
        background = new GradientDrawable();
        {
            background.setCornerRadius(Utils.dp(context, 10));
            background.setStroke(Utils.dp(context, 3), context.getColor(R.color.outline));
            setBackground(background);
        }

        currentColor = context.getColor(R.color.outline);

        title = new TextView(context);
        {
            title.setText("false");
            title.setTextSize(Utils.dp(context, 5));
            title.setTextColor(context.getColor(R.color.outline));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                title.setTypeface(context.getResources().getFont(R.font.sans));
            }
            title.setGravity(Gravity.CENTER_VERTICAL);
            title.setPadding(Utils.dp(context, 10), 0, 0, 0);
            addView(title, new LayoutParams(-1, -1,1));
        }

        refresh = new ImageView(context);
        {
            refresh.setPadding(Utils.dp(context, 10), Utils.dp(context, 10), Utils.dp(context, 10), Utils.dp(context, 10));
            refresh.setImageResource(R.drawable.refresh);
            refresh.setColorFilter(context.getColor(R.color.outline));
            addView(refresh, Utils.dp(context, 35), Utils.dp(context, 35));
        }

        setOnClickListener((v) -> {
            changeValue();
        });

        setLayoutParams(new LayoutParams(-1, Utils.dp(context, 35)));
    }
}
