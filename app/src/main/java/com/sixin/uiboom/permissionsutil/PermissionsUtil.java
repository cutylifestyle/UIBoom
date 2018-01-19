package com.sixin.uiboom.permissionsutil;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.sixin.uiboom.ConvertUtil;
import com.sixin.uiboom.SDKUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 */

public class PermissionsUtil {
    // TODO: 2018/1/2 添加解释功能
    private static int mRequestCode;

    private PermissionsUtil(){}

    /**
     * 请求权限的相关方法
     * */
    public static void requestPermissions(final @NonNull Activity activity,
                                          final @IntRange(from = 0) int requestCode,
                                          final @NonNull String...permissions){

        mRequestCode = requestCode;

        if(!SDKUtil.isOverMarshmallow()){
            PermissionsResult.executeNotNeedPermissions(activity);
            return;
        }

        List<String> deniedPermissions = getDeniedPermissions(activity, permissions);
        if(deniedPermissions.size()>0){
            ActivityCompat.requestPermissions(activity,
                    ConvertUtil.listToArray(deniedPermissions,new String[deniedPermissions.size()]),
                    requestCode);
        }else{
            PermissionsResult.executeGranted(activity);
        }
    }

    /**
     * 权限申请之后的回调方法，需要在Activity/Fragment的
     * onRequestPermissionsResult()方法中调用
     * */
    public static void onRequestPermissionsResult(Activity activity,
                                                  int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults){
       if(requestCode == mRequestCode){
           List<String> deniedPermissions = getDeniedPermissions(permissions,grantResults);

           if(deniedPermissions.size() > 0){
               PermissionsResult.executeDenied(activity,deniedPermissions);
           }else{
               PermissionsResult.executeGranted(activity);
           }
       }
    }

    private static List<String> getDeniedPermissions(Activity activity, String...permissions){
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    private static List<String> getDeniedPermissions(String[] permissions, int[] grantResults){
        List<String> deniedPermissions = new ArrayList<>();
        for(int i = 0 ; i < permissions.length ; i++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                deniedPermissions.add(permissions[i]);
            }
        }
        return deniedPermissions;
    }
}
