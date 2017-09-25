package com.cheng.scaleseekbar;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

/**
 * Created by baina on 17-9-25.
 */




public class Util {

    public static float DENSITY = Resources.getSystem().getDisplayMetrics().density; // 得到的结果是1.0，2.0, 3.0这种形式

    public static int dipToPixel(int dip) {
        if (dip < 0) {
            return -(int) (-dip * DENSITY + 0.5f);
        } else {
            return (int) (dip * DENSITY + 0.5f);
        }
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

}
