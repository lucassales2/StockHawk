package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by lucas on 09/01/17.
 */

public class AppFonts {
    private static Typeface lightTypeFace;

    private static Typeface mediumTypeFace;

    private static Typeface boldTypeFace;

    public static Typeface getLight(Context context) {
        if (lightTypeFace == null) {
            lightTypeFace = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_light));
        }
        return lightTypeFace;
    }

    public static Typeface getMedium(Context context) {
        if (mediumTypeFace == null) {
            mediumTypeFace = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_medium));
        }
        return mediumTypeFace;
    }

    public static Typeface getBold(Context context) {
        if (boldTypeFace == null) {
            boldTypeFace = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_bold));
        }
        return boldTypeFace;
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, FontStyle font) {
        switch (font) {
            case Ligh:
                textView.setTypeface(getLight(textView.getContext()));
                break;
            case Medium:
                textView.setTypeface(getMedium(textView.getContext()));
                break;
            case Bold:
                textView.setTypeface(getBold(textView.getContext()));
                break;
        }

    }

    public enum FontStyle {
        Ligh,
        Medium,
        Bold
    }
}
