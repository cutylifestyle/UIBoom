package com.sixin.uiboom;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhou
 */

public class ReflectUtil {

    private ReflectUtil(){}

    /**
     * 通过反射解析注解的相关方法
     * @param t 传入的对象
     * @param annotationClass 注解的类类型对象
     * @param args 被执行方法的形式参数
     * */
    public static <T> void parseAnnotation(T t, Class annotationClass, Object... args){
        Class clazz = t.getClass();
        if(clazz != null){
            Method[] methods = clazz.getDeclaredMethods();
            if(methods != null && methods.length > 0){
                for(Method method:methods){
                    Annotation annotation = method.getAnnotation(annotationClass);
                    if(annotation != null){
                        method.setAccessible(true);
                        try {
                            method.invoke(t, args);
                            return;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
