package com.mindcod3r.wenera.Components;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class EditTextIcon extends LinearLayout {
    Context context;
    GradientDrawable background;

    EditText textInput;
    ImageView icon;
    View space;
    GradientDrawable spaceDrawable;

    int currentColor;
    ValueAnimator colorAnim;

    public void setFocus(boolean isFocus) {
        colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, context.getColor(isFocus ? R.color.accent : R.color.outline));
        colorAnim.setDuration(200);
        colorAnim.addUpdateListener((anim) -> {
            background.setStroke(Utils.dp(context, 3), (int)anim.getAnimatedValue());
            icon.setColorFilter((int)anim.getAnimatedValue());
            currentColor = (int)anim.getAnimatedValue();
            spaceDrawable.setColor((int)anim.getAnimatedValue());

            textInput.setTextColor((int)anim.getAnimatedValue());
        });
        colorAnim.start();
    }

    public EditTextIcon(Context context) {
        super(context);
        initialize(context);
    }

    AttributeSet attr;
    public EditTextIcon(Context context, AttributeSet attr) {
        super(context, attr);
        this.attr = attr;
        initialize(context);
    }

    public void initialize(Context context) {
        this.context = context;
        this.currentColor = context.getColor(R.color.outline);

        setOrientation(LinearLayout.HORIZONTAL);
        background = new GradientDrawable();
        {
            background.setColor(Color.TRANSPARENT);
            background.setCornerRadius(Utils.dp(context, 10));

            setBackground(background);
        }

        icon = new ImageView(context);
        {
            icon.setPadding(Utils.dp(context, 15),Utils.dp(context, 15),Utils.dp(context, 15),Utils.dp(context, 15));
            addView(icon, -2, -1);
            icon.post(() -> {
               int height = icon.getHeight();
               icon.getLayoutParams().width = height;
               ((LayoutParams) icon.getLayoutParams()).rightMargin = -Utils.dp(context, 5);
               icon.requestLayout();
            });


        }

        space = new View(context);
        {
            spaceDrawable = new GradientDrawable();
            spaceDrawable.setCornerRadius(999f);
            space.setBackground(spaceDrawable);

            addView(space, Utils.dp(context, 3), -1);
            ((LayoutParams) space.getLayoutParams()).topMargin = Utils.dp(context, 15);
            ((LayoutParams) space.getLayoutParams()).bottomMargin = Utils.dp(context, 15);
            ((LayoutParams) space.getLayoutParams()).rightMargin = Utils.dp(context, 10);
            space.requestLayout();
        }

        textInput = new EditText(context);
        {
            textInput.setBackground(null);

            textInput.setSingleLine(true);
            addView(textInput, -1, -1);

            textInput.setOnFocusChangeListener((v, f) -> {
                setFocus(f);
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                textInput.setTypeface(context.getResources().getFont(R.font.sans));
            }
        }

        TypedArray a = context.obtainStyledAttributes(attr, R.styleable.EditTextIcon);

        icon.setImageDrawable(a.getDrawable(R.styleable.EditTextIcon_src));
        boolean password = attr.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "password", false);

        textInput.setInputType(password ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        setFocus(false);

    }
}
