/**
 * @File ScanUtils.java
 * @author zeng
 * @Date 2017-04-29
 * @Time 12:31
 */

package com.yunjuanyunshu.util;

import com.yunjuanyunshu.Ao;
import com.yunjuanyunshu.annotation.*;
//import javassist.*;
//import javassist.bytecode.CodeAttribute;
//import javassist.bytecode.LocalVariableAttribute;
//import javassist.bytecode.MethodInfo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScanUtils {

    public static final Class<? extends Annotation> controlClassAnnoClass = ClassAnnot.class;
    public static final Class<? extends Annotation> controlMethodAnnoClass = MethodAnnot.class;
    public static final Class<? extends Annotation> entityInfoAnno = EntityInfoAnno.class;
    public static final Class<? extends Annotation> columnInfoAnno = ColumnInfoAnno.class;

    static final String valueMethodName = "value";



    /**
     * 扫描类中是否包含对外服务函数
     *
     * @param class_para 类对象
     * @param classAnnoClass  控制器类注解
     * @param methodAnnoClass 控制器函数注解
     * @return
     */
    public static HashMap<String, ParameterInfo[]> scanClass(Class<?> class_para, Class<? extends Annotation> classAnnoClass, Class<? extends Annotation> methodAnnoClass) {
        HashMap<String, ParameterInfo[]> tmpParameterList = new HashMap<String, ParameterInfo[]>();
        Method[] tmpMethods = null;
        boolean isClassValidator = classValidator(class_para,classAnnoClass);
        try {
            tmpMethods = Constants.VALIDATOR_CLASS.getClassLoader().loadClass(class_para.getName()).getMethods();
            for (Method method : tmpMethods) {
                if (isClassValidator || method.isAnnotationPresent(methodAnnoClass)) {
                    try {
                        String methodAnnoStr = "";//methodValidator(method,methodAnnoClass);
                        if (methodAnnoStr != null && Modifier.isStatic(method.getModifiers())) { //静态函数，并且打上注解
                            ParameterInfo[] tmpParameterInfo = getParamaters(class_para.getName(), method.getName());
                            String tmpServerName = methodAnnoStr.equals(Constants.EXT_VALIDATOR_MEDTHOD_NAME)
                                    ? class_para.getName() + "." + method.getName() : methodAnnoStr;
                            tmpParameterList.put(tmpServerName, tmpParameterInfo);
                            //System.out.println("ValidatMethodName : " + methodAnnoStr);
                        }
                    } catch (Throwable ex) {
                    }
                }
            }
        } catch (SecurityException | ClassNotFoundException e) {
        }
        return tmpParameterList;
    }

    /**
     * 扫描类列表中是否包含对外服务信息列表
     *
     * @param classList_para 待扫描类列表
     * @param classAnnoClass 控制器类注解
     * @param methodAnnoClass 控制器函数注解
     * @return 服务信息列表
     */
    public static HashMap<String, ParameterInfo[]> scanClasses(List<Class<?>> classList_para, Class<? extends Annotation> classAnnoClass, Class<? extends Annotation> methodAnnoClass) {
        HashMap<String, ParameterInfo[]> tmpAllParameterList = new HashMap<String, ParameterInfo[]>();
        HashMap<String, ParameterInfo[]> tmpParameterList = null;
        if (classList_para == null) {
            return null;
        }
        for (Class<?> tmpClass : classList_para) {
            tmpParameterList = scanClass(tmpClass,classAnnoClass,methodAnnoClass);
            if (tmpParameterList != null && tmpParameterList.size() > 0) {
                tmpAllParameterList.putAll(tmpParameterList);
            }
        }
        return tmpAllParameterList;
    }

    /**
     * 扫描类列表中是否包含对外服务信息列表
     *
     * @param classList_para 待扫描类列表
     * @return 服务信息列表
     */
    public static HashMap<String, ParameterInfo[]> scanClasses(List<Class<?>> classList_para){
        return scanClasses(classList_para,controlClassAnnoClass,controlMethodAnnoClass);
    }


    /**
     * 扫描类列表中是否包含对外服务信息列表
     *
     * @param packageName 待扫描类列表
     * @return 服务信息列表
     */
    public static HashMap<String, ParameterInfo[]> scanClasses(String packageName){
        return StringUtils.isEmpty(packageName) ? null : scanClasses(getClasses(packageName),controlClassAnnoClass,controlMethodAnnoClass);
    }

    /**
     * 扫描类中是否包含对外服务函数
     *
     * @param class_para 类对象
     * @return
     */
    public static HashMap<String, ParameterInfo[]> scanClass(Class<?> class_para,Class<? extends Annotation> classAnnoClass){
        return scanClass(class_para,controlClassAnnoClass,controlMethodAnnoClass);
    }


    // *****




    /**
     * 扫描类中是否包含对外服务函数
     *
     * @param class_para 类对象
     * @param classAnnoClass  控制器类注解
     * @param methodAnnoClass 控制器函数注解
     * @return
     */
    public static HashMap<String,HashMap<String,ParameterInfo>> scanClassMap(Class<?> class_para, Class<? extends Annotation> classAnnoClass, Class<? extends Annotation> methodAnnoClass) {
        HashMap<String,HashMap<String,ParameterInfo>> tmpParameterList = new HashMap<String,HashMap<String, ParameterInfo>>();
        Method[] tmpMethods = null;
        boolean isClassValidator = classValidator(class_para,classAnnoClass);
        try {

            tmpMethods = Constants.VALIDATOR_CLASS.getClassLoader().loadClass(class_para.getName()).getMethods();
            for (Method method : tmpMethods) {
                if (isClassValidator || method.isAnnotationPresent(methodAnnoClass)) {
                    try {
                        String methodAnnoStr ="";// methodValidator(method,methodAnnoClass);
                        if (methodAnnoStr != null && Modifier.isStatic(method.getModifiers())) { //静态函数，并且打上注解
                            //tmpParameterList = getParamatersMap(class_para.getName(), method.getName());
                            String tmpServerName = methodAnnoStr.equals(Constants.EXT_VALIDATOR_MEDTHOD_NAME)
                                    ? class_para.getName() + "." + method.getName() : methodAnnoStr;
                            tmpParameterList.put(tmpServerName, getParamatersMap(class_para.getName(), method.getName()));
                            //System.out.println("ValidatMethodName : " + methodAnnoStr);
                        }
                    } catch (Throwable ex) {
                    }
                }
            }
        } catch (SecurityException | ClassNotFoundException e) {
        }
        return tmpParameterList;
    }

    /**
     * 扫描类列表中是否包含对外服务信息列表
     *
     * @param classList_para 待扫描类列表
     * @param classAnnoClass 控制器类注解
     * @param methodAnnoClass 控制器函数注解
     * @return 服务信息列表
     */
    public static HashMap<String, HashMap<String,ParameterInfo>> scanClassesMap(List<Class<?>> classList_para, Class<? extends Annotation> classAnnoClass, Class<? extends Annotation> methodAnnoClass) {
        HashMap<String, HashMap<String,ParameterInfo>> tmpAllParameterList = new HashMap<String, HashMap<String,ParameterInfo>>();
        HashMap<String, HashMap<String,ParameterInfo>> tmpParameterList = null;
        if (classList_para == null) {
            return null;
        }
        for (Class<?> tmpClass : classList_para) {
            tmpParameterList = scanClassMap(tmpClass,classAnnoClass,methodAnnoClass);
            if (tmpParameterList != null && tmpParameterList.size() > 0) {
                tmpAllParameterList.putAll(tmpParameterList);
            }
        }
        return tmpAllParameterList;
    }

    /**
     * 扫描类列表中是否包含对外服务信息列表
     *
     * @param classList_para 待扫描类列表
     * @return 服务信息列表
     */
    public static HashMap<String,HashMap<String, ParameterInfo>> scanClassesMap(List<Class<?>> classList_para){
        return scanClassesMap(classList_para,controlClassAnnoClass,controlMethodAnnoClass);
    }


    /**
     * 扫描类列表中是否包含对外服务信息列表
     *
     * @param packageName 待扫描类列表
     * @return 服务信息列表
     */
    public static HashMap<String,HashMap<String, ParameterInfo>> scanClassesMap(String packageName){
        return StringUtils.isEmpty(packageName) ? null : scanClassesMap(getClasses(packageName),controlClassAnnoClass,controlMethodAnnoClass);
    }

    /**
     * 扫描类中是否包含对外服务函数
     *
     * @param class_para 类对象
     * @return
     */
    public static HashMap<String,HashMap<String, ParameterInfo>> scanClassMap(Class<?> class_para,Class<? extends Annotation> classAnnoClass){
        return scanClassMap(class_para,controlClassAnnoClass,controlMethodAnnoClass);
    }






    /**
     * 扫描类中是实体信息
     *
     * @param class_para 类对象
     * @return 实体信息
     */
    public static EntityInfo scanEntity(Class<?> class_para,Class<? extends Annotation> entityAnnoClass_para,Class<? extends Annotation> columnAnnoClass_para){
        if(class_para == null || entityAnnoClass_para == null || columnAnnoClass_para == null){
            return  null;
        }
        EntityInfo entityInfo = new EntityInfo();
        if(class_para.isAnnotationPresent(entityAnnoClass_para)){
            entityInfo = getTableInfoFromAnnoField(class_para.getAnnotation(entityAnnoClass_para));
            if(entityInfo != null){
                entityInfo.setColumns(scanColumn(class_para,entityAnnoClass_para,columnAnnoClass_para));
            }
        }
        return entityInfo;
    }

    /**
     * 扫描类列表中实体信息列表
     *
     * @param classList_para 待扫描类列表
     * @return 实体信息列表
     */
    public static HashMap<String,EntityInfo> scanEntitys(List<Class<?>> classList_para,Class<? extends Annotation> entityAnnoClass_para,Class<? extends Annotation> columnAnnoClass_para){
        HashMap<String,EntityInfo> tmpList = new HashMap<String,EntityInfo>();
        EntityInfo entityInfo = null;
        if (classList_para == null) {
            return null;
        }
        for (Class<?> tmpClass : classList_para) {
            entityInfo = scanEntity(tmpClass,entityAnnoClass_para,columnAnnoClass_para);
            if (entityInfo != null && entityInfo.getTableName() != null) {
                tmpList.put(entityInfo.getTableName(),entityInfo);
            }
        }
        return tmpList;
    }


    /**
     * 扫描类列表中实体信息列表
     *
     * @param classList_para 待扫描类列表
     * @return 实体信息列表
     */
    public static HashMap<String,EntityInfo> scanEntitys(List<Class<?>> classList_para){
        return  classList_para == null ? null : scanEntitys(classList_para,entityInfoAnno,columnInfoAnno);
    }


    /**
     * 扫描类列包下实体信息列表
     * @param packageName 待扫描包名
     * @return 实体信息列表
     */
    public static HashMap<String,EntityInfo> scanEntitys(String packageName){
        return  StringUtils.isEmpty(packageName) ? null : scanEntitys(getClasses(packageName),entityInfoAnno,columnInfoAnno);
    }

    /**
     * 获取实体类中实体信息
     * @param class_para 类名
     * @param entityAnnoClass 实体注解类名
     * @param columnAnnoClass 字段注解类名
     * @return 实体信息
     */
    public static HashMap<String,ColumnInfo> scanColumn(Class<?> class_para,Class<? extends Annotation> entityAnnoClass,Class<? extends Annotation> columnAnnoClass){
        HashMap<String,ColumnInfo> tmpColumns = new HashMap<String,ColumnInfo>();
        if(class_para == null || !class_para.isAnnotationPresent(entityAnnoClass)){
            return null;
        }
        ColumnInfo tmpColumn = null;
        Field[] fieldses = class_para.getDeclaredFields();
        if(fieldses == null || fieldses.length ==0){
            return null;
        }
        for (Field tmpField: fieldses) {
            if(tmpField == null || !tmpField.isAnnotationPresent(columnAnnoClass)){
                continue;
            }
            Annotation tmpAnno = tmpField.getAnnotation(columnAnnoClass);
            tmpColumn = getColInfoFromAnnoField(tmpAnno);
            if(tmpColumn != null){
                tmpColumns.put(tmpField.getName(),tmpColumn);
            }
            tmpColumn = null;
        }
        return tmpColumns;
    }


    /**
     * 获取实体类中实体信息
     * @param class_para 类名
     * @param columnAnnoClass 字段注解类名
     * @return 实体信息
     */
    public static HashMap<String,ColumnInfo> scanColumn(Class<?> class_para,Class<? extends Annotation> columnAnnoClass){
        HashMap<String,ColumnInfo> tmpColumns = new HashMap<String,ColumnInfo>();
        ColumnInfo tmpColumn = null;
        Field[] fieldses = class_para.getDeclaredFields();
        if(fieldses == null || fieldses.length ==0){
            return null;
        }
        for (Field tmpField: fieldses) {
            if(tmpField == null || !tmpField.isAnnotationPresent(columnAnnoClass)){
                continue;
            }
            Annotation tmpAnno = tmpField.getAnnotation(columnAnnoClass);
            tmpColumn = getColInfoFromAnnoField(tmpAnno);
            if(tmpColumn != null){
                tmpColumns.put(tmpField.getName(),tmpColumn);
            }
            tmpColumn = null;
        }
        return tmpColumns;
    }


    /**
     *
     * @param className_para
     * @param methondName_para
     * @return
     */
    private static ParameterInfo[] getParamaters(String className_para, String methondName_para) {
        ParameterInfo[] tmprtn = null;
        Class<?> clazz = null;
        int tmpParameterListLength = 0;
        try {
            clazz = Class.forName(className_para);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReflectUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (clazz == null) {
            return tmprtn;
        }
        Method[] methods = clazz.getMethods();
        Method m = null;
        for (Method method : methods) {
            if (methondName_para.equals(method.getName())) {
                m = method;
                break;
            }
        }
        if (m == null) {
            return tmprtn;
        }
        Class[] ParameterList = m.getParameterTypes();
        Annotation[][] an = m.getParameterAnnotations();
        if (ParameterList == null || ParameterList.length == 0) {
            return tmprtn;
        }
        tmprtn = new ParameterInfo[ParameterList.length];
//        try {
//            ClassPool pool = ClassPool.getDefault();
//            CtClass cc = pool.getOrNull(clazz.getName());
//            if (cc == null) {
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
//
//            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
//            for (int i = 0; i < tmpParameterListLength; i++) {
//                ParameterInfo tmpinfo = new ParameterInfo();
//                tmpinfo.setIndex(i);
//                if(an != null && an.length > 0 && an[i] != null && an[i].length > 0 && an[i][0] != null){
//                    RequestParameterCanNullAnnot tmpParaCanNull = (RequestParameterCanNullAnnot)an[i][0];
//                    tmpinfo.setCanNull(tmpParaCanNull.value());
//                }else {
//                    tmpinfo.setCanNull(true);
//                }
//
//                tmpinfo.setParamaterName(attr.variableName(i + pos));
//                tmpinfo.setParameterClass(ParameterList[i]);
//                tmprtn[i] = tmpinfo;
//            }
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }
        return tmprtn;
    }

    /**
     *
     * @param className_para
     * @param methondName_para
     * @return
     */
    private static HashMap<String,ParameterInfo> getParamatersMap(String className_para, String methondName_para) {
        HashMap<String,ParameterInfo> tmprtn = null;
        Class<?> clazz = null;
        int tmpParameterListLength = 0;
        try {
            clazz = Class.forName(className_para);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReflectUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (clazz == null) {
            return tmprtn;
        }
        Method[] methods = clazz.getMethods();
        Method m = null;
        for (Method method : methods) {
            if (methondName_para.equals(method.getName())) {
                m = method;
                break;
            }
        }
        if (m == null) {
            return tmprtn;
        }
        Class[] ParameterList = m.getParameterTypes();
        Annotation[][] an = m.getParameterAnnotations();
        if (ParameterList == null || ParameterList.length == 0) {
            return tmprtn;
        }
        tmprtn = new HashMap<String,ParameterInfo>();
//        try {
//            ClassPool pool = ClassPool.getDefault();
//            CtClass cc = pool.getOrNull(clazz.getName());
//            if (cc == null) {
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
//
//            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
//            for (int i = 0; i < tmpParameterListLength; i++) {
//                ParameterInfo tmpinfo = new ParameterInfo();
//                tmpinfo.setIndex(i);
//                if(an != null && an.length > 0 && an[i] != null && an[i].length > 0 && an[i][0] != null){
//                    RequestParameterCanNullAnnot tmpParaCanNull = (RequestParameterCanNullAnnot)an[i][0];
//                    tmpinfo.setCanNull(tmpParaCanNull.value());
//                }else {
//                    tmpinfo.setCanNull(true);
//                }
//
//                tmpinfo.setParamaterName(attr.variableName(i + pos));
//                tmpinfo.setParameterClass(ParameterList[i]);
//                tmprtn.put(tmpinfo.getParamaterName(),tmpinfo);
//            }
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }
        return tmprtn;
    }

    /**
     * 判断类是否打上标识
     *
     * @param class_para 类
     * @return true: 已打上标识 | false: 未打上标识
     */
    private static boolean classValidator(Class<?> class_para,Class<? extends Annotation> annoClass) {
        return class_para.isAnnotationPresent(annoClass);
    }



    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    private static List<Class<?>> getClasses(String packageName) {

        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()) {
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                if (null != protocol) //如果是以文件的形式保存在服务器上
                {
                    switch (protocol) {
                        case "file":
                            //获取包的物理路径
                            String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                            //以文件的方式扫描整个包下的文件 并添加到集合中
                            findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                            break;
                        case "jar":
                            //如果是jar包文件
                            //定义一个JarFile
                            JarFile jar;
                            try {
                                //获取jar
                                jar = ((JarURLConnection) url.openConnection()).getJarFile();
                                //从此jar包 得到一个枚举类
                                Enumeration<JarEntry> entries = jar.entries();
                                //同样的进行循环迭代
                                while (entries.hasMoreElements()) {
                                    //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                                    JarEntry entry = entries.nextElement();
                                    String name = entry.getName();
                                    //如果是以/开头的
                                    if (name.charAt(0) == '/') {
                                        //获取后面的字符串
                                        name = name.substring(1);
                                    }
                                    //如果前半部分和定义的包名相同
                                    if (name.startsWith(packageDirName)) {
                                        int idx = name.lastIndexOf('/');
                                        //如果以"/"结尾 是一个包
                                        if (idx != -1) {
                                            //获取包名 把"/"替换成"."
                                            packageName = name.substring(0, idx).replace('/', '.');
                                        }
                                        //如果可以迭代下去 并且是一个包
                                        if ((idx != -1) || recursive) {
                                            //如果是一个.class文件 而且不是目录
                                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                                //去掉后面的".class" 获取真正的类名
                                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                                try {
                                                    //添加到classes
                                                    classes.add(Class.forName(packageName + '.' + className));
                                                } catch (ClassNotFoundException e) {
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            } else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * 获取注解值
     * @param anno 注解对象
     * @param fieldName 注解字段名称
     * @return 注解值
     */
    private static String getAnnoFieldValue(Annotation anno,String fieldName){
        return "";
//        return (anno == null || StringUtils.isEmpty(fieldName)) ? null : (String) ReflectUtil.InvokeMethod(anno,fieldName,null);
    }

    /**
     * 从注解对象中获取字段信息对象
     * @param anno 注解对象
     * @return 字段信息对象
     */
    private static ColumnInfo getColInfoFromAnnoField(Annotation anno){
        if(anno == null){
            return null;
        }
        ColumnInfo columnInfo = new ColumnInfo();
        String[] colFields = {"columnName","columnDesc","columnBytes","columnRangeMax","columnRangeMin","canNull"};
        for (String tmpColField: colFields) {
//            ReflectUtil.InvokeMethod(columnInfo,genSetMethodName(tmpColField),ReflectUtil.InvokeMethod(anno,tmpColField,null));
        }
        return  columnInfo;
    }

    /**
     * 从注解对象中获取字段信息对象
     * @param anno 注解对象
     * @return 字段信息对象
     */
    private static EntityInfo getTableInfoFromAnnoField(Annotation anno){
        if(anno == null){
            return null;
        }
        EntityInfo entityInfo = new EntityInfo();
        String[] colFields = {"tableName","tableDesc"};
        for (String tmpColField: colFields) {
//            ReflectUtil.InvokeMethod(entityInfo,genSetMethodName(tmpColField),ReflectUtil.InvokeMethod(anno,tmpColField,null));
        }
        return  entityInfo;
    }

    /**
     * 获取字段的Get函数名称
     * @param fieldName 字段名称
     * @return 字段的Get函数名称
     */
    private static String genGetMethodName(String fieldName){
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
    private static String genSetMethodName(String fieldName){
        if(StringUtils.isEmpty(fieldName)){
            return null;
        }
        String getMethodName = "set";
        return getMethodName + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }




    public static void getPrivateFields(Class<?> class_para){
        Field[] fields = class_para.getDeclaredFields();
        for (Field tmp:fields) {
            TcpPkgAnno tcp = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            System.out.println(tmp.getName()+":pkgIdx="+tcp.pkgIdx()+",pkgName=" + tcp.pkgName());
        }

    }



}
