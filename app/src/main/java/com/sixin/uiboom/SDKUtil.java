package com.sixin.uiboom;

import android.os.Build;

/**
 * @author zhou
 */

public class SDKUtil {

    private SDKUtil(){}

    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
