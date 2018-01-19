package com.sixin.uiboom.permissionsutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhou
 * 安卓6.0以下设备使用该注解，无需动态申请权限
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionsNoNeeded {

}
