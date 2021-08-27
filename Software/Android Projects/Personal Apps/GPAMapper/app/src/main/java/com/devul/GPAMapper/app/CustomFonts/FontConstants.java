package com.devul.GPAMapper.app.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;


public class FontConstants {

    public static final String FONT_BOLD = "Fonts/DefaultFonts/MavenPro-Bold.ttf";
    public static final String FONT_NORMAL = "Fonts/DefaultFonts/MavenPro-Regular.ttf";
    public static final String FONT_MEDIUM = "Fonts/DefaultFonts/MavenPro-Medium.ttf";

    public static Typeface getfontNormal(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FontConstants.FONT_NORMAL);
    }

    public static Typeface getfontBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FontConstants.FONT_BOLD);
    }
}
