package com.mindcod3r.wenera.Components;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

public class CheckBox extends LinearLayout {
    Context context;
    AttributeSet attr;

    GradientDrawable checkDrawable;
    LinearLayout check;
    TextView title;

    int currentAlpha = 0;
    int currentStrokeColor = 0;

    public int width, height;
    public boolean isChecked = false;
    ValueAnimator animator, animator2;

    public void setChecked(boolean isCheck) {
        isChecked = isCheck;
        animator = ValueAnimator.ofObject(new ArgbEvaluator(), getColor(currentAlpha), getColor(isChecked ? 255 : 0));
        animator.setDuration(300);
        animator.addUpdateListener((anim) -> {
            checkDrawable.setColor((int) anim.getAnimatedValue());
            currentAlpha = Color.alpha((int) anim.getAnimatedValue());
        });
        animator.start();

        animator2 = ValueAnimator.ofObject(new ArgbEvaluator(), currentStrokeColor, context.getColor(isChecked ? R.color.white : R.color.outline));
        animator2.setDuration(300);
        animator2.addUpdateListener((anim) -> {
            currentStrokeColor = (int) anim.getAnimatedValue();
            checkDrawable.setStroke(Utils.dp(context, 2), (int)anim.getAnimatedValue());
        });
        animator2.start();
    }

    public int getColor(int alpha) {
        int red = Color.red(context.getColor(R.color.accent));
        int green = Color.green(context.getColor(R.color.accent));
        int blue = Color.blue(context.getColor(R.color.accent));

        return Color.argb(alpha, red, green, blue);
    }

    public CheckBox(Context context) {
        super(context);
        initialize(context);
    }

    public CheckBox(Context context, AttributeSet attr) {
        super(context, attr);
        this.attr = attr;
        initialize(context);
    }

    public void initialize(Context context) {
        this.context = context;

        width  = Utils.dp(context, 25);
        height = Utils.dp(context, 25);

        currentStrokeColor = context.getColor(R.color.outline);

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(LinearLayout.HORIZONTAL);

        check = new LinearLayout(context);
        {
            checkDrawable = new GradientDrawable();
            {
                checkDrawable.setColor(Color.TRANSPARENT);
                checkDrawable.setCornerRadius(10f);
                checkDrawable.setStroke(Utils.dp(context, 2), context.getColor(R.color.outline));
                check.setBackground(checkDrawable);
            }

            addView(check, width, height);
        }

        title = new TextView(context);
        {
            TypedArray array = context.obtainStyledAttributes(attr, R.styleable.CheckBoxW);
            String titleText = array.getString(R.styleable.CheckBoxW_title);
            array.recycle();
            {
                title.setText(titleText);
                title.setTextColor(context.getColor(R.color.outline));
                title.setTextSize(Utils.dp(context, 7));
                title.setPadding(Utils.dp(context, 10), 0, 0, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    title.setTypeface(context.getResources().getFont(R.font.sans));
                }
            }

            addView(title, -2, -2);
        }

        setOnClickListener((v) -> setChecked(!isChecked));

    }

}
