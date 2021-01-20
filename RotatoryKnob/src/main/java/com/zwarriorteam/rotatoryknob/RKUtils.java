package com.zwarriorteam.rotatoryknob;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class RKUtils {
    public static float dp2px(Context context, float dp){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT); //https://stackoverflow.com/questions/4605527/converting-pixels-to-dp
    }

    public static float px2dp(Context context, float px){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT); //https://stackoverflow.com/questions/4605527/converting-pixels-to-dp
    }
}