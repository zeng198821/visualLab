/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yunjuanyunshu.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zeng
 */
public class ReflectUtil {
    
    public static void getMethods(String[] args) {
        
    }
    
    
    /**
     * 获取参数列表
     * @param className_para 类名
     * @param methondName_para 函数名
     * @return  函数的参数列表
     */
    public static ParameterInfo[] getParamaters(String className_para, String methondName_para) {
        ParameterInfo[] tmprtn = null;
        
        Class<?> clazz = null;
        int tmpParameterListLength=0;
        try {
            clazz = Class.forName(className_para);  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReflectUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(clazz == null){            
            return tmprtn;
        }
        Method[] methods = clazz.getMethods();
        Method m =null;
        for (Method method : methods) {
            if(methondName_para.equals(method.getName())){
                m = method;
                break;
            }
        }
        if(m==null){
            return tmprtn;
        }
        Class[] ParameterList =  m.getParameterTypes();
        
        if(ParameterList == null ||  ParameterList.length == 0){
            return tmprtn;            
        }
        tmprtn = new  ParameterInfo[ParameterList.length];
//        try {
//            ClassPool pool = ClassPool.getDefault();
//            CtClass cc = pool.getOrNull(clazz.getName());
//            if(cc == null){
//                pool.insertClassPath(new ClassClassPath(clazz));
//                cc = pool.get(clazz.getName());
//            }
//            CtMethod cm = cc.getDeclaredMethod(methondName_para);
//            // 使用javaassist的反射方法获取方法的参数名
//            MethodInfo methodInfo = cm.getMethodInfo();
//            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//            if (attr == null) {
//                return tmprtn;
//            }
//            tmpParameterListLength = cm.getParameterTypes().length;
//            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
//            for (int i = 0; i < tmpParameterListLength; i++) {
//                ParameterInfo tmpinfo = new ParameterInfo();
//                tmpinfo.setIndex(i);
//                tmpinfo.setParamaterName(attr.variableName(i + pos));
//                tmpinfo.setParameterClass(ParameterList[i]);
//                tmprtn[i] =  tmpinfo;
//            }
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }
        return  tmprtn;
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
