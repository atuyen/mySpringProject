package com.seasolutions.stock_management.util;

import java.lang.reflect.InvocationTargetException;

public class GenericUtils {
   public static  <T>  T createObject(Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
     return  (T) tClass.getDeclaredConstructor().newInstance();
    }


}
