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
import java.util.HashMap;

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
    private static Method getSetMethod(Class<?> class_param,String fieldName){
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
    public static void setFieldValue(Object object,String fieldName,Object val) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String tmpFieldType = object.getClass().getDeclaredField(fieldName).getType().getTypeName();
        Method method = getSetMethod(object.getClass(),fieldName);
        if(tmpFieldType.equals("java.lang.String")) {
            method.invoke(object, new Object[]{(String) val});
        }
        if(tmpFieldType.equals("java.lang.Integer") || tmpFieldType.equals("int")) {
            method.invoke(object, new Object[]{(int) val});
        }
        if(tmpFieldType.equals("java.lang.Byte")|| tmpFieldType.equals("byte")) {
            method.invoke(object, new Object[]{(byte) val});
        }
        if(tmpFieldType.equals("java.lang.Short")|| tmpFieldType.equals("short")) {
            method.invoke(object, new Object[]{(short) val});
        }
        if(tmpFieldType.equals("java.lang.Float")|| tmpFieldType.equals("float")) {
            method.invoke(object, new Object[]{(float) val});
        }
        if(tmpFieldType.equals("java.lang.Double")|| tmpFieldType.equals("double")) {
            method.invoke(object, new Object[]{(double) val});
        }
        if(val.getClass().getSuperclass().getName().equals("com.yunjuanyunshu.modules.packet.PkgInfoBase")) {
            method.invoke(object, new Object[]{(com.yunjuanyunshu.modules.packet.PkgInfoBase) val});
        }
    }




    public static Field[] getPrivateFields(Class<?> class_para){
        Field[] fields = class_para.getDeclaredFields();
        HashMap<Integer,Field> tmpMap = new HashMap<Integer,Field>();
        int tmpCount=0;
        for (Field tmp : fields) {
            TcpPkgAnno tcp = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            if(tcp == null)
                continue;
            else{
                tmpMap.put(tmpCount,tmp);
                tmpCount++;
            }
            //System.out.println(tmp.getName()+":pkgIdx="+tcp.pkgIdx()+",pkgName=" + tcp.pkgName());
        }
        Field[] annoFields = new Field[tmpMap.size()];
        for(int i =0;i<tmpMap.size();i++){
            annoFields[i]=tmpMap.get(i);
        }
        return annoFields;
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
            try {
                setFieldValue(o,fields[i].getName(),i);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

}
