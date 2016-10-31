package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by lucas on 28/10/16.
 */

public class AppFonts {
    private static Typeface robotoLight;

    public static Typeface getRobotoLight(Context context) {
        if (robotoLight == null) {
            robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        }
        return robotoLight;
    }
}
