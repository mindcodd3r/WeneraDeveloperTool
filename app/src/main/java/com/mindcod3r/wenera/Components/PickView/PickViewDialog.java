package com.mindcod3r.wenera.Components.PickView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.mindcod3r.wenera.Components.Button.ButtonPositive;
import com.mindcod3r.wenera.Components.GridMenu.GridMenu;
import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;


public class PickViewDialog extends Dialog {
    Context context;

    LinearLayout main;
    GradientDrawable background;

    public static interface Callback {
        public void onItemPick(int idx, String str);
        public void onCancelled();
    }

    private Callback callback;
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public PickViewDialog(Context context, String[] items, int itemsW, int itemsH) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        main = new LinearLayout(context);
        {
            background = new GradientDrawable();
            {
                background.setColor(context.getColor(R.color.dark_gray));
                background.setCornerRadius(Utils.dp(context, 10));
                main.setBackground(background);
            }
        }

        GridMenu grid = new GridMenu(context, itemsH, itemsW, 45, 4);
        int i = -1;
        for (String item: items) {
            i++;
            ButtonPositive button = new ButtonPositive(context);
            button.setText(item);

            int finalI = i;
            button.setOnClickListener((v) -> {
                if (callback != null) callback.onItemPick(finalI, item);
                dismiss();
            });

            grid.getCell(i).addView(button, -1, -1);
        }

        setOnCancelListener((v) -> {
            if (callback != null) callback.onCancelled();
        });
        ScrollView scroll = new ScrollView(context);
        scroll.setFillViewport(true);

        main.addView(scroll, Utils.dp(context, 400), Math.min(Utils.dp(context, 45 * itemsH), Utils.dp(context, 45*4)));
        scroll.addView(grid, -1, -1);
        setContentView(main);
        show();
    }
}
