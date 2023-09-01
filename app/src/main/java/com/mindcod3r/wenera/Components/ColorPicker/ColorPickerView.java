package com.mindcod3r.wenera.Components.ColorPicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mindcod3r.wenera.CanvasUtils;

public class ColorPickerView extends View {
    Paint paint;
    Shader luar;
	
	public static interface OnChangeColorListener {
		public void onColorChanged(int color);
	}
	public OnChangeColorListener listener;
	
	float x = 0, y = 0;
	
	public int hColor = Color.RED;
    final float[] color = { 1.f, 1.f, 1.f };
   	public int currentColor;
	
	public void setHue(int c) {
		hColor = c;
		setBackgroundColor(Color.TRANSPARENT);
		invalidate();
		
		Bitmap bitmap = getBitmapFromView(this);

		int pixel = bitmap.getPixel((int) x, (int) y);

		int redValue = Color.red(pixel);
		int greenValue = Color.green(pixel);
		int blueValue = Color.blue(pixel);

		currentColor = Color.rgb(redValue, greenValue, blueValue);
		if (listener != null) listener.onColorChanged(currentColor);
	}
	
	public static Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable =view.getBackground();
		if (bgDrawable!=null) 
			bgDrawable.draw(canvas);
		else 
			canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		return returnedBitmap;
	}
	
	public Canvas canvas;
    public ColorPickerView(Context context) {
        super(context);
		
		final ColorPickerView instance = this;
		setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent mevent)  {
				switch (mevent.getAction()) {
					case MotionEvent.ACTION_MOVE:
						float x2 = mevent.getX();
						float y2 = mevent.getY();
						
						if (x2 < 0) x2 = 0;
						if (x2 > getWidth()) x2 = getWidth()-1;
						
						if (y2 < 0) y2 = 0;
						if (y2 > getHeight()) y2 = getHeight()-1;
						
						try {
						
						Bitmap bitmap = getBitmapFromView(instance);

						int pixel = bitmap.getPixel((int) x2, (int) y2);

						int redValue = Color.red(pixel);
						int greenValue = Color.green(pixel);
						int blueValue = Color.blue(pixel);
						
						
						currentColor = Color.rgb(redValue, greenValue, blueValue);
						if (listener != null) listener.onColorChanged(currentColor);
						
						x = x2;
						y = y2;
						
						setBackgroundColor(Color.TRANSPARENT);
						invalidate();
						
						break;
						} catch (Exception e) {
							
						}
				}
				return true;
			}
		});
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
	public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        if (paint == null) {
            paint = new Paint();
            luar = new LinearGradient(0.f, 0.f, 0.f, this.getMeasuredHeight(), 0xffffffff, 0xff000000, TileMode.CLAMP);
        }
        int rgb = hColor;
       
		paint.setStyle(Paint.Style.FILL);
		Shader dalam = new LinearGradient(0.f, 0.f, this.getMeasuredWidth(), 0.f, 0xffffffff, rgb, TileMode.CLAMP);
        ComposeShader shader = new ComposeShader(luar, dalam, PorterDuff.Mode.MULTIPLY);
        paint.setShader(shader);
        canvas.drawRect(0.f, 0.f, this.getMeasuredWidth(), this.getMeasuredHeight(), paint);
		
		paint.setShader(null);
		paint.setColor(Color.TRANSPARENT);
		paint.setColor(Color.WHITE); 
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		canvas.drawCircle(x, y, CanvasUtils.dpi(7.5f), paint);
		
    }

    
}
