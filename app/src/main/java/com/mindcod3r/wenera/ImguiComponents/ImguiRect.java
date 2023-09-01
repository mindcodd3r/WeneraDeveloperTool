package com.mindcod3r.wenera.ImguiComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;

import com.mindcod3r.wenera.Components.Button.ButtonDefaultOutline;
import com.mindcod3r.wenera.Components.Button.ButtonPositive;
import com.mindcod3r.wenera.Components.Button.ButtonPositiveOutline;
import com.mindcod3r.wenera.Components.ColorPicker.ColorPicker;
import com.mindcod3r.wenera.Components.ColorPicker.ColorPickerComponent;
import com.mindcod3r.wenera.Components.ColorPicker.ColorPickerView;
import com.mindcod3r.wenera.Components.CornerView;
import com.mindcod3r.wenera.Components.EditTextTitle;
import com.mindcod3r.wenera.ImguiEditor;
import com.mindcod3r.wenera.Utils;

public class ImguiRect extends ImguiShape {
    Paint paint;
    Context context;
    public int currentColor = 0;
    public void setColor(int color) {
        currentColor = color;
        paint.setColor(color);
        colorArg.setColor(color);
    }

    public void setCorner(float[] newCorners) {
        this.corners = newCorners;
        this.cornerArg.leftTop.setText(Integer.toString(Utils.pd(context, newCorners[0])));
        this.cornerArg.rightTop.setText(Integer.toString(Utils.pd(context, newCorners[2])));
        this.cornerArg.rightBottom.setText(Integer.toString(Utils.pd(context, newCorners[4])));
        this.cornerArg.leftBottom.setText(Integer.toString(Utils.pd(context, newCorners[6])));
    }

    LinearLayout argumentsLayout;
    EditTextTitle xArg, yArg, widthArg, heightArg;
    CornerView cornerArg;

    float[] corners = new float[] {0, 0, 0, 0, 0, 0, 0, 0};

    ColorPicker colorArg;

    public ImguiRect(int w, int h, int x, int y) {
        super(w, h, x, y);
        setId("Rect" + Integer.toString(ImguiEditor.getIdNumber()));
        this.context = ImguiEditor.globalContext;

        this.paint = new Paint();
        argumentsLayout = new LinearLayout(context);
        {
            argumentsLayout.setOrientation(LinearLayout.VERTICAL);
        }

        ButtonDefaultOutline zMinus = new ButtonDefaultOutline(context);
        {
            zMinus.setText("-Z layer");
            zMinus.setOnClickListener(v -> {
                try {
                    int newZ = ImguiEditor.viewer.shapes.indexOf(this) - 1;
                    if (newZ > 0){
                        ImguiEditor.viewer.shapes.remove(this);
                        ImguiEditor.viewer.shapes.add(newZ, this);
                    }

                    ImguiEditor.viewer.updateScreen();
                    ImguiEditor.instance.updateShapesList(this);
                } catch (Exception e) {}
            });
        }

        ButtonDefaultOutline zPlus = new ButtonDefaultOutline(context);
        {
            zPlus.setText("+Z layer");
            zPlus.setOnClickListener(v -> {
                try {
                    int newZ = ImguiEditor.viewer.shapes.indexOf(this) + 1;
                    ImguiEditor.viewer.shapes.remove(this);
                    if (newZ < ImguiEditor.viewer.shapes.size()) {
                        ImguiEditor.viewer.shapes.add(newZ, this);
                    } else {
                        ImguiEditor.viewer.shapes.add(this);
                    }
                    ImguiEditor.viewer.updateScreen();
                    ImguiEditor.instance.updateShapesList(this);

                } catch (Exception e) {}
            });
        }

        LinearLayout Zlayer = new LinearLayout(context);
        {
            Zlayer.setOrientation(LinearLayout.HORIZONTAL);
            Zlayer.setPadding(0, Utils.dpm(context, 5), 0, Utils.dpm(context, 5));

            Zlayer.addView(zMinus, new LinearLayout.LayoutParams(-1, -1, 1));
            Zlayer.addView(new Space(context), Utils.dpm(context, 5), -1);
            Zlayer.addView(zPlus, new LinearLayout.LayoutParams(-1, -1, 1));
        }

        argumentsLayout.addView(Zlayer, -1, Utils.dpm(context, 40));

        ButtonPositiveOutline copy = new ButtonPositiveOutline(context);
        {
            copy.setText("Copy rect");
            copy.setOnClickListener(v -> {
                ImguiRect rect = ImguiEditor.instance.newShapeRect();
                rect.setHeight(getHeight());
                rect.setWidth(getWidth());
                rect.setX(getX());
                rect.setY(getY());
                rect.setColor(currentColor);
                rect.setCorner(corners);

                rect.widthArg.setText(String.valueOf(Utils.pdm(context, getWidth())));
                rect.heightArg.setText(String.valueOf(Utils.pdm(context, getHeight())));
                rect.xArg.setText(String.valueOf(Utils.pdm(context, getX())));
                rect.yArg.setText(String.valueOf(Utils.pdm(context, getY())));

                rect.onConfirm();
            });
            argumentsLayout.addView(copy, -1, Utils.dpm(context, 30));
        }

        xArg = new EditTextTitle(context);
        {
            xArg.setTitle("Rect X");
            xArg.setText(String.valueOf(Utils.pdm(context, getX())));
            argumentsLayout.addView(xArg, -1, -2);
        }

        yArg = new EditTextTitle(context);
        {
            yArg.setTitle("Rect Y");
            yArg.setText(String.valueOf(Utils.pdm(context, getY())));
            argumentsLayout.addView(yArg, -1, -2);
        }

        widthArg = new EditTextTitle(context);
        {
            widthArg.setTitle("Rect width");
            widthArg.setText(String.valueOf(Utils.pdm(context, getHeight())));
            argumentsLayout.addView(widthArg, -1, -2);
        }

        heightArg = new EditTextTitle(context);
        {
            heightArg.setTitle("Rect height");
            heightArg.setText(String.valueOf(Utils.pdm(context, getWidth())));
            argumentsLayout.addView(heightArg, -1, -2);
        }

        cornerArg = new CornerView(context);
        argumentsLayout.addView(cornerArg);
        {
            cornerArg.rightBottom.setText("0");
            cornerArg.rightTop.setText("0");
            cornerArg.leftBottom.setText("0");
            cornerArg.leftTop.setText("0");
        }

        colorArg = new ColorPicker(context, "Rect color", currentColor);
        {
            colorArg.setCallback((color) -> {
                setColor(color);
            });
            argumentsLayout.addView(colorArg);
        }


        setColor(Color.rgb(100, 255, 100));
    }

    @Override
    public void onConfirm() {

        try {
            int width = Integer.parseInt(widthArg.getText().replace("w", Float.toString(Utils.pdm(context, ImguiEditor.mainMenu.getWidth()))).replace("h", Float.toString(Utils.pdm(context, ImguiEditor.mainMenu.getHeight()))));
            widthArg.setText(Integer.toString(width));
            setWidth(Utils.dp(context, width));
        } catch (Exception e) {}

        try {
            int height = Integer.parseInt(heightArg.getText().replace("w", Float.toString(Utils.pdm(context, ImguiEditor.mainMenu.getWidth()))).replace("h", Float.toString(Utils.pdm(context, ImguiEditor.mainMenu.getHeight()))));
            heightArg.setText(Integer.toString(height));
            setHeight(Utils.dpm(context, height));
        } catch (Exception e) {}

        try {
            int x = Integer.parseInt(xArg.getText());
            setX(Utils.dpm(context, x));
        } catch (Exception e) {}

        try {
            int y = Integer.parseInt(yArg.getText());
            setY(Utils.dpm(context, y));
        } catch (Exception e) {}

        try {
            float lt = Utils.dpm(context, Float.parseFloat(cornerArg.leftTop.getText()));
            float rt = Utils.dpm(context, Float.parseFloat(cornerArg.rightTop.getText()));
            float rb = Utils.dpm(context, Float.parseFloat(cornerArg.rightBottom.getText()));
            float rl = Utils.dpm(context, Float.parseFloat(cornerArg.leftBottom.getText()));
            setCorner(new float[] {lt, lt, rt, rt, rb, rb, rl, rl});
        } catch (Exception e) {}
    }

    @Override
    public View getArguments() {
        return argumentsLayout;
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Path path = new Path();
        path.addRoundRect(getX(), getY(), getX()+getWidth(), getY()+getHeight(), corners, Path.Direction.CW);
        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }
}
