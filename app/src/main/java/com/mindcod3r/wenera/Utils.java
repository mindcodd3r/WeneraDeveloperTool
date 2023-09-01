package com.mindcod3r.wenera;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
    public static Context context;
    public static int dp(Context context, float dp)  {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

    public static int pd(Context context, float dp)  {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) Math.round(dp / scale);
    }

    public static int dpm(Context context, float dp)  {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * 2);
    }

    public static float pdm(Context context, int dp)  {
        float scale = context.getResources().getDisplayMetrics().density;
        return (float) (dp / 2);
    }
}
