package com.sixin.uiboom;

import java.util.List;

/**
 * @author zhou
 */

public class ConvertUtil {

    private ConvertUtil(){}

    public static <T> T[] listToArray(List<T> list, T[] t){
        return list.toArray(t);
    }
}
