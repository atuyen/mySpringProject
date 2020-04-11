package com.seasolutions.stock_management.util;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;

public class MappingUtils {

    private  static  ModelMapper modelMapper;

    public static <E> E map(Object source, Type destinationType){
        if(modelMapper==null){
            modelMapper = new ModelMapper();
        }
        return  modelMapper.map(source,destinationType);
    }



}
