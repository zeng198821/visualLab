/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yunjuanyunshu.util;

import java.lang.reflect.Field;

/**
 *
 * @author zeng
 */
public class ReflectUtil {
    
    public static void getMethods(String[] args) {
        
    }
    
    

    


    public static Object getValueFromPropName(Object object,String propName){
        Field fields[]=object.getClass().getDeclaredFields();//获得对象所有属性
        Field field=null;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            field.setAccessible(true);//修改访问权限
            if (propName.equals(field.getName())) {
                try {
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return  null;
    }

}
