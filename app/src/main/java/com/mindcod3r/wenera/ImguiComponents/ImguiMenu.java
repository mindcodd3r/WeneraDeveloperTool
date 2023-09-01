package com.mindcod3r.wenera.ImguiComponents;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.Components.ChangeView;
import com.mindcod3r.wenera.Components.EditTextTitle;
import com.mindcod3r.wenera.Components.TitleArg;
import com.mindcod3r.wenera.ImguiEditor;
import com.mindcod3r.wenera.Utils;

public class ImguiMenu extends ImguiShape {
    LinearLayout argumentsLayout;
    Context context;

    EditTextTitle widthArg, heightArg;
    ChangeView borderArg;

    public ImguiMenu(int w, int h, int x, int y) {
        super(w, h, x, y);
        this.context = ImguiEditor.globalContext;

        setId("Menu");

        argumentsLayout = new LinearLayout(context);
        {
            argumentsLayout.setOrientation(LinearLayout.VERTICAL);
        }

        widthArg = new EditTextTitle(context);
        {
            widthArg.setTitle("Menu width");
            widthArg.setText(String.valueOf(Utils.pdm(context, ImguiEditor.viewer.getLayoutParams().width)));
            setWidth(ImguiEditor.viewer.getLayoutParams().width);
            argumentsLayout.addView(widthArg, -1, -2);
        }

        heightArg = new EditTextTitle(context);
        {
            heightArg.setTitle("Menu height");
            heightArg.setText(String.valueOf(Utils.pdm(context, ImguiEditor.viewer.getLayoutParams().height)));
            setHeight(ImguiEditor.viewer.getLayoutParams().height);
            argumentsLayout.addView(heightArg, -1, -2);
        }

        argumentsLayout.addView(new TitleArg(context, "Menu dash stroke"));

        borderArg = new ChangeView(context);
        {
            argumentsLayout.addView(borderArg);
            borderArg.setValue(true);
        }
    }

    @Override
    public void onConfirm() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ImguiEditor.viewer.getLayoutParams();

        try {
            int width = Integer.parseInt(widthArg.getText());
            lp.width = Utils.dpm(context, width);
            this.setWidth(width);
        } catch (Exception e) {}

        try {
            int height = Integer.parseInt(heightArg.getText());
            lp.height = Utils.dpm(context, height);
            this.setHeight(height);
        } catch (Exception e) {}

        ImguiEditor.viewer.isDashVisible = borderArg.getValue();

        ImguiEditor.viewer.setLayoutParams(lp);
    }

    @Override
    public View getArguments() {
        return argumentsLayout;
    }
}
