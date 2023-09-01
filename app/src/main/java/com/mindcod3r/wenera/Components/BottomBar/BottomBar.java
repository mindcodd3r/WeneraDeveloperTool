package com.mindcod3r.wenera.Components.BottomBar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class BottomBar extends LinearLayout {
    Context context;
    AttributeSet attr;

    Paint cursorPaint = new Paint();
    int currentCursorWidth, currentCursorPos;
    int splitSize = 0;
    ValueAnimator posAnim, widthAnim;
    int currentIdx = 0;
    ArrayList<BottomBarItem> items = new ArrayList<>();

    public BottomBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        this.attr = attr;

        initialize();
    }

    Handler handler1, handler2, handler3;
    public void moveCursor(int idx) {
        currentIdx = idx-1;
        for (BottomBarItem item: items) item.anim(context.getColor(R.color.outline));
        widthAnim = ValueAnimator.ofInt(currentCursorWidth, Utils.dp(context, 5));
        widthAnim.setDuration(200);
        widthAnim.addUpdateListener((anim) -> {
            currentCursorWidth = (int) anim.getAnimatedValue();

            setBackgroundColor(context.getColor(R.color.dark_gray));
            invalidate();

        });
        widthAnim.start();

        handler1.removeCallbacksAndMessages(null);
        handler2.removeCallbacksAndMessages(null);
        handler3.removeCallbacksAndMessages(null);

        handler1.postDelayed(() -> {
            posAnim = ValueAnimator.ofInt(currentCursorPos, splitSize*idx-(splitSize/2));
            posAnim.setDuration(300);
            posAnim.addUpdateListener((anim) -> {
                currentCursorPos = (int) anim.getAnimatedValue();

                setBackgroundColor(context.getColor(R.color.dark_gray));
                invalidate();

            });
            posAnim.start();
        }, 250);
        handler2.postDelayed(() -> {
            widthAnim = ValueAnimator.ofInt(currentCursorWidth, Utils.dp(context, 40));
            widthAnim.setDuration(200);
            items.get(idx-1).anim(context.getColor(R.color.accent));
            widthAnim.addUpdateListener((anim) -> {
                currentCursorWidth = (int) anim.getAnimatedValue();

                setBackgroundColor(context.getColor(R.color.dark_gray));
                invalidate();

            });
            widthAnim.start();
        }, 500);
        handler3.postDelayed(() -> {
            if (callback != null) callback.onPageChange(idx-1, items.get(idx-1).hint);
        },  750);
    }

    public static interface Callback {
        public void onPageChange(int index, String hint);
    }
    public Callback callback;
    public void setCallback(Callback call) {
        callback = call;
    }
    private void drawItems() {
        removeAllViews();
        for (BottomBarItem item: items) {
            LinearLayout foreground = new LinearLayout(context);
            {
                foreground.setGravity(Gravity.CENTER);
                foreground.addView(item);
                foreground.setOnClickListener((v) -> {
                    if (currentIdx != items.indexOf(item)) moveCursor(items.indexOf(item)+1);
                });
            }
            addView(foreground, new LayoutParams(-1, -1, 1));
        }
    }

    public void initialize() {
        cursorPaint.setColor(context.getColor(R.color.accent));

        handler1 = new Handler();
        handler2 = new Handler();
        handler3 = new Handler();

        post(() -> {
            setOrientation(LinearLayout.HORIZONTAL);
            setBackgroundColor(context.getColor(R.color.dark_gray));

            items.add(new BottomBarItem(context, R.drawable.apps, "Main").set());
            items.add(new BottomBarItem(context, R.drawable.user, "Profile"));
            items.add(new BottomBarItem(context, R.drawable.book, "Tutorials"));

            splitSize = (int) (getWidth() / items.size());

            drawItems();

            {
                currentCursorPos = splitSize/2;
                currentCursorWidth = Utils.dp(context, 40);
            }
            setBackgroundColor(context.getColor(R.color.dark_gray));
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(
                currentCursorPos-currentCursorWidth/2,
                getHeight()/2-Utils.dp(context, 2.5f)+Utils.dp(context, 25),
                currentCursorPos+currentCursorWidth/2,
                getHeight()/2+Utils.dp(context, 2.5f)+Utils.dp(context, 25),
                999, 999, cursorPaint
        );
    }
}
