package com.mindcod3r.wenera.Components.ColorPicker;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.graphics.drawable.GradientDrawable;

public class ColorSeekBar extends SeekBar {
	Paint rectPaint;
	public int currentColor = Color.RED;
	
	public ColorSeekBar(Context context) {
		super(context);
		
		rectPaint = new Paint();
		setBackgroundColor(Color.TRANSPARENT);
		
		final ColorSeekBar instance = this;
		setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent mevent)  {
					switch (mevent.getAction()) {
						case MotionEvent.ACTION_MOVE:
							float x2 = mevent.getX();
							float y2 = mevent.getY();
							try {

								Bitmap bitmap = ColorPickerView.getBitmapFromView(instance);

								int pixel = bitmap.getPixel((int) x2, (int) getHeight()/2);

								int redValue = Color.red(pixel);
								int greenValue = Color.green(pixel);
								int blueValue = Color.blue(pixel);

								currentColor = Color.rgb(redValue, greenValue, blueValue);
								if (listener != null) listener.onColorChanged(currentColor);

								break;
							} catch (Exception e) {

							}
					}
					return true;
				}
			});
	}

	public static interface OnChangeColorListener {
		public void onColorChanged(int color);
	}
	public OnChangeColorListener listener;
	
	@Override
	public void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		
		int width = getWidth();
		int height = getHeight();
		
		LinearGradient shader = new LinearGradient(0, 0, width, 0, new int[] {
			Color.RED,
			Color.parseColor("#FF33FF"),
			Color.BLUE,
			Color.CYAN,
			Color.GREEN,
			Color.YELLOW,
			Color.RED
		}, null, Shader.TileMode.CLAMP);
		
		rectPaint.setShader(shader);
		
		canvas.drawRect(0, 0, width, height, rectPaint);
	}
	 
}
