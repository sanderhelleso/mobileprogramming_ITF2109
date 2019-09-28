package com.iifym.classes;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class WindowHelper {

    public static void setTransparentNav(Window w) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
