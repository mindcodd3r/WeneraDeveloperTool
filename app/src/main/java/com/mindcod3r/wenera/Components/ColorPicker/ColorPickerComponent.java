package com.mindcod3r.wenera.Components.ColorPicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mindcod3r.wenera.CanvasUtils;
import com.mindcod3r.wenera.ImguiEditor;
import com.mindcod3r.wenera.R;

public class ColorPickerComponent extends LinearLayout {
    ColorPickerWindow window;
    PopupWindow dialog;

    private static float boxSize = 15f;
    private static float stroke = 1.3f;
    public int color = 0;

	public static interface Callback {
		public void onChange(int c);
	}
	public Callback callback;
    Paint rectPaint;

    public void setColor(int color) {
        this.color = color;
        setBackgroundColor(Color.TRANSPARENT);
		if (callback != null) callback.onChange(color);
        invalidate();
    }

    public void setColorV(int color) {
        this.color = color;
        setBackgroundColor(Color.TRANSPARENT);
        invalidate();
    }

    public ColorPickerComponent(Context context) {
        super(context);

        rectPaint = new Paint();

        window = new ColorPickerWindow(context, new ColorPickerView.OnChangeColorListener() {
            public void onColorChanged(int color) {
                setColor(color);
            }
        });

        dialog = new PopupWindow(window, CanvasUtils.dpi(100f), CanvasUtils.dpi(125f));
        dialog.setOutsideTouchable(true);

        setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.showAsDropDown(v, -CanvasUtils.dpi((float) (boxSize*1.5)), 0, Gravity.LEFT);
			}
		});

        setLayoutParams(new LayoutParams(CanvasUtils.dpi(boxSize), CanvasUtils.dpi(boxSize)));
        setColor(Color.WHITE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        int sizeRectW = CanvasUtils.dpi((float) (boxSize*1.4));
        int sizeRectH = CanvasUtils.dpi((float) (boxSize));
        int x = width / 2 - (sizeRectW / 2);
        int y = height / 2 - (sizeRectH / 2);
        int x2 = width / 2 + (sizeRectW / 2);
        int y2 = height / 2 + (sizeRectH / 2);

        rectPaint.setShader(null);
        rectPaint.setColor(ImguiEditor.globalContext.getColor(R.color.outline));
        canvas.drawRect(x, y, x2, y2, rectPaint);


        float dp = CanvasUtils.dpi(stroke);
        rectPaint.setColor(color);
        canvas.drawRect(x + dp, y + dp, x2 - dp, y2 - dp, rectPaint);

        LinearGradient shader = new LinearGradient(x+dp, y+dp, x2-dp, y2-dp, new int[] {
                Color.argb(0, 0, 0, 0),
                Color.argb(150, 0, 0, 0)
        }, null, Shader.TileMode.CLAMP);
        rectPaint.setShader(shader);
        canvas.drawRect(x+dp, y+dp, x2-dp, y2-dp, rectPaint);

    }
}
