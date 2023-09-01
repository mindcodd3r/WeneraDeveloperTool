package com.mindcod3r.wenera.Components.ColorPicker;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;

import com.mindcod3r.wenera.CanvasUtils;
import com.mindcod3r.wenera.R;

public class ColorPickerWindow extends LinearLayout {
	public ColorPickerView colorPickerView;
	public ColorSeekBar colorSeekBar;
	public LinearLayout colorPickerViewLayout, colorSeekBarLayout;
	
	@SuppressWarnings("deprecation")
	public ColorPickerWindow(Context context, ColorPickerView.OnChangeColorListener listener) {
		super(context);
		
		setOrientation(LinearLayout.VERTICAL);
		GradientDrawable draw = new GradientDrawable();
		{
			draw.setColor(Color.parseColor("#202125"));
			draw.setCornerRadius(CanvasUtils.dpi(8f));
			draw.setStroke(CanvasUtils.dpi(1f), context.getColor(R.color.accent));
			setBackgroundDrawable(draw);
		}
		
		colorPickerView = new ColorPickerView(context);
		colorPickerView.listener = listener;


		colorSeekBar = new ColorSeekBar(context);
		colorSeekBar.listener = new ColorSeekBar.OnChangeColorListener() {
			public void onColorChanged(int color) {
				colorPickerView.setHue(color);
			}
		};
		
		colorPickerViewLayout = new LinearLayout(context);
		{
			colorPickerViewLayout.setPadding(CanvasUtils.dpi(10f),CanvasUtils.dpi(10f),CanvasUtils.dpi(10f),CanvasUtils.dpi(10f));
			colorPickerViewLayout.addView(colorPickerView, -1, -1);
		}
		colorSeekBarLayout = new LinearLayout(context);
		{
			colorSeekBarLayout.setPadding(CanvasUtils.dpi(10f), 0, CanvasUtils.dpi(10f), CanvasUtils.dpi(10f));
			colorSeekBarLayout.addView(colorSeekBar, -1, -1);
		}
		addView(colorPickerViewLayout, CanvasUtils.dpi(100f), CanvasUtils.dpi(100f));
		addView(colorSeekBarLayout, CanvasUtils.dpi(100f), CanvasUtils.dpi(25f));
	}
}
