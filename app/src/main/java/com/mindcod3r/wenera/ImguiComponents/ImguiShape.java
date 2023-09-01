package com.mindcod3r.wenera.ImguiComponents;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.ImguiEditor;

import java.util.logging.Logger;

public class ImguiShape {

    private String id;

    public ImguiShape(int w, int h, int x, int y) {
        this.width = w;
        this.height= h;
        this.x = x;
        this.y = y;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getId() {
        return this.id;
    }

    private int width, height, x, y;
    private boolean showBorders = false;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getX() {return x;}
    public int getY() {return y;}

    public void confirm() {}

    public View getArguments() {return new View(ImguiEditor.globalContext);}

    public void onConfirm() {}

    public void setShowBorders(boolean isShow) {
        showBorders = isShow;
    }

    Paint paint_dash;

    public void onDraw(Canvas canvas) {}
}
