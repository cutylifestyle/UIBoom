package com.sixin.uiboom.permissionsutil;

import android.app.Activity;

import com.sixin.uiboom.ReflectUtil;

import java.util.List;

/**
 * @author zhou
 */

 class PermissionsResult {

    private PermissionsResult(){}

    /**
     * 申请的权限全部被授予时执行的方法
     * */
     static void executeGranted(Activity activity){
        ReflectUtil.parseAnnotation(activity, PermissionsGranted.class);
    }

    /**
     * 申请的权限中有任何一项权限被拒执行的方法
     * @param deniedPermissions 被拒权限集合
     * */
     static void executeDenied(Activity activity, List<String> deniedPermissions){
        ReflectUtil.parseAnnotation(activity, PermissionsDenied.class,deniedPermissions);
    }

    /**
     * 在6.0以下的安卓设备上无需动态申请权限
     * */
     static void executeNotNeedPermissions(Activity activity){
        ReflectUtil.parseAnnotation(activity, PermissionsNoNeeded.class);
    }
}
