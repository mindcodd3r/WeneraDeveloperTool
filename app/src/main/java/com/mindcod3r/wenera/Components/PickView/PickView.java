package com.mindcod3r.wenera.Components.PickView;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.telecom.Call;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class PickView extends LinearLayout {
    Context context;
    String[] values = new String[]{};
    ImageView arrow;

    int currentPick = 0;

    TextView title;
    GradientDrawable background;

    int currentColor;
    ValueAnimator colorAnim;

    public void setFocus(boolean isFocus) {
        colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, context.getColor(isFocus ? R.color.accent : R.color.outline));
        colorAnim.setDuration(200);
        colorAnim.addUpdateListener((anim) -> {
            background.setStroke(Utils.dp(context, 3), (int)anim.getAnimatedValue());
            arrow.setColorFilter((int)anim.getAnimatedValue());
            currentColor = (int)anim.getAnimatedValue();

            title.setTextColor((int)anim.getAnimatedValue());
        });
        colorAnim.start();
    }
    public void setValues(String[] newValues) {
        this.values = newValues;
        this.currentPick = 0;
        if (newValues.length > 0) {
            title.setText(values[currentPick]);
            if (callback != null) callback.onPick(currentPick, newValues[currentPick]);
        }
    }

    public void setText(String text) {
        title.setText(text);
    }

    public void setValues(String[] newValues, boolean last) {
        this.values = newValues;
        this.currentPick = newValues.length-1;
        if (newValues.length > 0) {
            title.setText(values[currentPick]);
            if (callback != null) callback.onPick(currentPick, newValues[currentPick]);
        }
    }

    public int columns = 4, rows = 4;

    public PickView(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public PickView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initialize();
    }

    public static interface Callback {
        public void onPick(int idx, String name);
    }
    public Callback callback;
    public void setCallback(Callback callback) {
        this.callback = callback;
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
            title.setText("null");
            title.setTextSize(Utils.dp(context, 5));
            title.setTextColor(context.getColor(R.color.outline));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                title.setTypeface(context.getResources().getFont(R.font.sans));
            }
            title.setGravity(Gravity.CENTER_VERTICAL);
            title.setPadding(Utils.dp(context, 10), 0, 0, 0);
            addView(title, new LayoutParams(-1, -1,1));
        }

        arrow = new ImageView(context);
        {
            arrow.setPadding(Utils.dp(context, 5), Utils.dp(context, 5), Utils.dp(context, 5), Utils.dp(context, 5));
            arrow.setImageResource(R.drawable.uparrow);
            arrow.setColorFilter(context.getColor(R.color.outline));

            addView(arrow, Utils.dp(context, 35), Utils.dp(context, 35));
        }

        setOnClickListener((v) -> {
            setFocus(true);
            new PickViewDialog(context, values, rows, columns).setCallback(
                new PickViewDialog.Callback() {
                    @Override
                    public void onItemPick(int idx, String str) {
                        title.setText(str);
                        if (callback != null) callback.onPick(idx, str);
                        setFocus(false);
                    }

                    @Override
                    public void onCancelled() {
                        setFocus(false);
                    }
                });
        });

        setLayoutParams(new LayoutParams(-1, Utils.dp(context, 35)));
    }
}
