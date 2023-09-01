package com.mindcod3r.wenera.ImguiComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;

import com.mindcod3r.wenera.Components.Button.ButtonDefaultOutline;
import com.mindcod3r.wenera.Components.Button.ButtonPositive;
import com.mindcod3r.wenera.Components.Button.ButtonPositiveOutline;
import com.mindcod3r.wenera.Components.ChangeView;
import com.mindcod3r.wenera.Components.ColorPicker.ColorPicker;
import com.mindcod3r.wenera.Components.CornerView;
import com.mindcod3r.wenera.Components.EditTextTitle;
import com.mindcod3r.wenera.Components.TitleArg;
import com.mindcod3r.wenera.ImguiEditor;
import com.mindcod3r.wenera.R;
import com.mindcod3r.wenera.Utils;

public class ImguiText extends ImguiShape {
    Paint paint;
    Context context;

    String text;
    float textSize;

    public void setText(String newText) {
        this.text = newText;
        this.textArg.setText(newText);
    }

    public String getText() {return text;}

    public void setTextSize(float newTextSize) {
        textSize = newTextSize;
        textSizeArg.setText(Float.toString(Utils.pd(context, newTextSize)));
    }

    public float getTextSize() {
        return textSize;
    }

    public int currentColor = 0;
    public void setColor(int color) {
        currentColor = color;
        paint.setColor(color);
        colorArg.setColor(color);
    }


    LinearLayout argumentsLayout;
    EditTextTitle xArg, yArg;
    EditTextTitle textArg, textSizeArg;

    boolean isCenter;
    ChangeView centerArg;

    public boolean getIsCenter() {return isCenter;}

    public void setIsCenter(boolean isCenter) {
        this.isCenter = isCenter;
        centerArg.setValue(isCenter);
    }

    ColorPicker colorArg;

    public ImguiText(int x, int y) {
        super(0, 0, x, y);
        setId("Text" + Integer.toString(ImguiEditor.getIdNumber()));
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
            Zlayer.setPadding(0, Utils.dp(context, 5), 0, Utils.dp(context, 5));

            Zlayer.addView(zMinus, new LinearLayout.LayoutParams(-1, -1, 1));
            Zlayer.addView(new Space(context), Utils.dp(context, 5), -1);
            Zlayer.addView(zPlus, new LinearLayout.LayoutParams(-1, -1, 1));
        }

        argumentsLayout.addView(Zlayer, -1, Utils.dp(context, 40));

        ButtonPositiveOutline copy = new ButtonPositiveOutline(context);
        {
            copy.setText("Copy rect");
            copy.setOnClickListener(v -> {
                ImguiText newText = ImguiEditor.instance.newShapeText();
                newText.setX(getX());
                newText.setY(getY());
                newText.setColor(currentColor);
                newText.setTextSize(getTextSize());
                newText.setText(getText());

                newText.xArg.setText(String.valueOf(Utils.pd(context, getX())));
                newText.yArg.setText(String.valueOf(Utils.pd(context, getY())));

                newText.onConfirm();
            });
            argumentsLayout.addView(copy, -1, Utils.dp(context, 30));
        }

        xArg = new EditTextTitle(context);
        {
            xArg.setTitle("Text X");
            xArg.setText(String.valueOf(Utils.pd(context, getX())));
            argumentsLayout.addView(xArg, -1, -2);
        }

        yArg = new EditTextTitle(context);
        {
            yArg.setTitle("Text Y");
            yArg.setText(String.valueOf(Utils.pd(context, getY())));
            argumentsLayout.addView(yArg, -1, -2);
        }

        textArg = new EditTextTitle(context);
        {
            textArg.setTitle("Text");
            textArg.setText(getText());
            argumentsLayout.addView(textArg, -1, -2);
        }

        textSizeArg = new EditTextTitle(context);
        {
            textSizeArg.setTitle("Text size");
            textSizeArg.setText(Float.toString(getTextSize()));
            argumentsLayout.addView(textSizeArg, -1, -2);
        }

        argumentsLayout.addView(new TitleArg(context, "Text center horizontal"));

        centerArg = new ChangeView(context);
        {
            setIsCenter(false);
            argumentsLayout.addView(centerArg);
        }

        colorArg = new ColorPicker(context, "Text color", currentColor);
        {
            colorArg.setCallback((color) -> {
                setColor(color);
            });
            argumentsLayout.addView(colorArg);
        }


        setTextSize(Utils.dp(context, 5));
        setText("ImguiText");
        setColor(Color.BLACK);
    }

    @Override
    public void onConfirm() {

        try {
            int x = Integer.parseInt(xArg.getText());
            setX(Utils.dp(context, x));
        } catch (Exception e) {}

        try {
            int y = Integer.parseInt(yArg.getText());
            setY(Utils.dp(context, y));
        } catch (Exception e) {}

        try {
            float textSize = Float.parseFloat(textSizeArg.getText());
            setTextSize(Utils.dp(context, textSize));
        } catch (Exception e) {}

        try {
            String text = textArg.getText();
            setText(text);
        } catch (Exception e) {}

        setIsCenter(centerArg.getValue());
    }

    @Override
    public View getArguments() {
        return argumentsLayout;
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(currentColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            paint.setTypeface(context.getResources().getFont(R.font.proggy));
        }
        paint.setTextSize(textSize/1.2f/1.25f);
        Rect bounds = new Rect();
        paint.getTextBounds(getText(), 0, getText().length(), bounds);
        int height = bounds.height();
        int tempX = getX();
        int tempY = getY();
        if (getIsCenter()) {
            tempX -= bounds.width()/2;
            tempY -= bounds.height()/2;
        }
        canvas.drawText(getText(), tempX, getY()+height, paint);
        super.onDraw(canvas);
    }
}
