package com.mindcod3r.wenera.Components.GridMenu;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class GridMenu extends LinearLayout {
    Context context;

    ArrayList<LinearLayout> cells = new ArrayList<>();
    int rows, columns, height, padding;
    public LinearLayout getCell(int cellIdx) {
        return cells.get(cellIdx);
    }

    public GridMenu(Context context, int r, int c, int h, int p) {
        super(context);
        this.context = context;

        this.rows = r;
        this.columns = c;
        this.height = h;
        this.padding = p;

        initialize();
    }

    private void initialize() {
        setOrientation(LinearLayout.VERTICAL);

        for (int y = 0; y < rows; y++) {
            LinearLayout row = new LinearLayout(context);
            {
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(Utils.dp(context, padding), Utils.dp(context, padding), Utils.dp(context, padding), Utils.dp(context, 0));
            }
            for (int x = 0; x < columns; x++) {
                LinearLayout col = new LinearLayout(context);
                {
                    col.setPadding(Utils.dp(context, padding), Utils.dp(context, padding), Utils.dp(context, padding), Utils.dp(context, padding));
                    cells.add(col);
                }
                row.addView(col, new LinearLayout.LayoutParams(-1, -1, 1));
            }
            addView(row, new LinearLayout.LayoutParams(-1, Utils.dp(context, height)));
        }
    }
}
