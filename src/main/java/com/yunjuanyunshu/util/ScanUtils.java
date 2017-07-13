/**
 * @File ScanUtils.java
 * @author zeng
 * @Date 2017-04-29
 * @Time 12:31
 */

package com.yunjuanyunshu.util;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import javassist.*;
//import javassist.bytecode.CodeAttribute;
//import javassist.bytecode.LocalVariableAttribute;
//import javassist.bytecode.MethodInfo;

public class ScanUtils {


    /**
     * 获取字段的Get函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    private static String getGetMethodName(String fieldName){
        if(StringUtils.isEmpty(fieldName)){
            return null;
        }
        String getMethodName = "get";
        return getMethodName + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 获取字段的Set函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    private static String getSetMethodName(String fieldName){
        if(StringUtils.isEmpty(fieldName)){
            return null;
        }
        String getMethodName = "set";
        return getMethodName + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }


    /**
     * 获取字段的Get函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    private static Method getGetMethod(Class<?> class_param,String fieldName){
        Method method = null;
        try {
            method = class_param.getMethod(getGetMethodName(fieldName));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return  method;
    }

    /**
     * 获取字段的Set函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    private static Method getSetMethod(Class<?> class_param,String fieldName,Object val){
        try {
            Method[] tmplist = class_param.getMethods();
            for (Method tmp : tmplist){
                if(tmp.getName().equals(getSetMethodName(fieldName))){
                    return tmp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 获取字段的Get函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    public static Object getFieldValue(Object object,String fieldName) throws InvocationTargetException, IllegalAccessException {
        Method method = getGetMethod(object.getClass(),fieldName);
        return method.invoke(object);
    }

    /**
     * 获取字段的Set函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    public static void setFieldValue(Object object,String fieldName,Object val) throws InvocationTargetException, IllegalAccessException {
        Method method = getSetMethod(object.getClass(),fieldName,val);
        method.invoke(object,new Object[]{val});
    }




    public static void getPrivateFields(Class<?> class_para){
        Field[] fields = class_para.getDeclaredFields();
        for (Field tmp : fields) {
            TcpPkgAnno tcp = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            System.out.println(tmp.getName()+":pkgIdx="+tcp.pkgIdx()+",pkgName=" + tcp.pkgName());
        }
    }

    public static int getObjectSize(Object object){
        int tmpCountSize=0;
        if(object == null){
            return  tmpCountSize;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field tmp : fields) {
            TcpPkgAnno tcp = tmp.getDeclaredAnnotation(TcpPkgAnno.class);

            int tmpsize= tcp.pkgLength();
            if(tmpsize <= 0){
                try {
                    tmpsize = getObjectSize(getFieldValue(object,tmp.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tmpCountSize = tmpCountSize + tmpsize;
        }
        return tmpCountSize;
    }


    public static Object makeFieldInst(Class<?> class_para) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = class_para.newInstance();
        Field[] fields = class_para.getDeclaredFields();
        for (int i =0;i<fields.length;i++){
            setFieldValue(o,fields[i].getName(),(byte)i);
        }
        return o;
    }

}
