package com.mindcod3r.wenera.Components.BottomBar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class BottomBarItem extends androidx.appcompat.widget.AppCompatImageView {
    Context context;
    public ValueAnimator animator;
    public int currentColor;
    public String hint;

    public BottomBarItem(Context context, int resource, String hint) {
        super(context);
        this.context = context;

        this.hint = hint;

        setImageResource(resource);
        initialize();
    }

    public void anim(int color) {
        animator = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, color);
        animator.setDuration(200);
        animator.addUpdateListener((anim) -> {
            setColorFilter((int) anim.getAnimatedValue());
            currentColor = (int) anim.getAnimatedValue();
        });
        animator.start();
    }

    public BottomBarItem set() {
        anim(context.getColor(R.color.accent));
        return this;
    }

    public void initialize() {
        this.currentColor = context.getColor(R.color.outline);

        setColorFilter(context.getColor(R.color.outline));
        setLayoutParams(new LinearLayout.LayoutParams(Utils.dp(context, 25), Utils.dp(context, 25)));
    }
}
