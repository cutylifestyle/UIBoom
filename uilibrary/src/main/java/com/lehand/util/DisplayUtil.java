package com.lehand.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author zhou
 */

public class DisplayUtil {

    private DisplayUtil(){
        throw new UnsupportedOperationException("u can't initiate me...");
    }

    public static int getDisplayWidth(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}
