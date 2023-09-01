package com.mindcod3r.wenera.Components.GridMenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Build;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class MenuItem extends LinearLayout {
    Context context;
    ImageView icon;
    TextView title, version;
    GradientDrawable draw;

    public MenuItem disable() {
        int color = Color.parseColor("#88929292");
        title.setTextColor(color);
        version.setTextColor(color);
        icon.setColorFilter(color);
        return this;
    }

    public MenuItem(Context context, int src, String name, String version) {
        super(context);
        this.context = context;

        draw = new GradientDrawable();
        {
            draw.setCornerRadius(Utils.dp(context, 20));
            draw.setColor(context.getColor(R.color.dark_gray));
            setBackground(draw);
        }

        setOrientation(LinearLayout.VERTICAL);
        {
            setGravity(Gravity.CENTER);
        }

        icon = new ImageView(context);
        {
            icon.setColorFilter(context.getColor(R.color.accent));
            icon.setImageResource(src);
            addView(icon, Utils.dp(context, 50), Utils.dp(context, 50));
        }

        title = new TextView(context);
        {
            title.setText(name);
            title.setTextSize(Utils.dp(context, 8));
            title.setTextColor(context.getColor(R.color.accent));
            title.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                title.setTypeface(context.getResources().getFont(R.font.sans));
            }
            addView(title, -1, Utils.dp(context, 35));
        }

        this.version = new TextView(context);
        {
            this.version.setText(version);
            this.version.setTextSize(Utils.dp(context, 6));
            this.version.setTextColor(context.getColor(R.color.outline));
            this.version.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.version.setTypeface(context.getResources().getFont(R.font.sans));
            }
            addView(this.version, -1, Utils.dp(context, -2));
        }

    }
}
