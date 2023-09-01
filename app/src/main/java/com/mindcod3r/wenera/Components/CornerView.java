package com.mindcod3r.wenera.Components;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Space;

import com.mindcod3r.wenera.Utils;

public class CornerView extends LinearLayout {
    Context context;

    public EditTextTitle leftTop, rightTop, rightBottom, leftBottom;

    public CornerView(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);

        LinearLayout line1 = new LinearLayout(context);
        {
            line1.setOrientation(LinearLayout.HORIZONTAL);
            leftTop = new EditTextTitle(context);
            leftTop.setTitle("LT");
            line1.addView(leftTop, new LayoutParams(-1, -1, 1));

            line1.addView(new Space(context), Utils.dp(context, 10), -1);

            rightTop = new EditTextTitle(context);
            rightTop.setTitle("RT");
            line1.addView(rightTop, new LayoutParams(-1, -1, 1));

            addView(line1);
        }

        LinearLayout line2 = new LinearLayout(context);
        {
            line2.setOrientation(LinearLayout.HORIZONTAL);
            leftBottom = new EditTextTitle(context);
            leftBottom.setTitle("LB");
            line2.addView(leftBottom, new LayoutParams(-1, -1, 1));

            line2.addView(new Space(context), Utils.dp(context, 10), -1);

            rightBottom = new EditTextTitle(context);
            rightBottom.setTitle("RB");
            line2.addView(rightBottom, new LayoutParams(-1, -1, 1));

            addView(line2);
        }
    }
}
