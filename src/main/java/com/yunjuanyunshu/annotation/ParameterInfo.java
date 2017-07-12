/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yunjuanyunshu.annotation;

import java.util.HashMap;

/**
 *
 * @author zeng
 */
public class ParameterInfo {

    /**
     * 形参索引
     */
    private Integer index;

    /**
     * 形参名称
     */
    private String paramaterName;

    /**
     * 形参类型
     */
    private Class<?> parameterClass;

    /**
     * 形参可为空
     */
    private Boolean canNull;


    /**
     * 字段信息列表
     */
    private HashMap<String,ColumnInfo> columnList;



    /**
     *  获取形参是否可为空
     * @return True:可为空 Flase:不为空
     */
    public Boolean getCanNull() {
        return canNull;
    }

    /**
     * 设置形参是否可为空
     * @param canNull True:可为空 Flase:不为空
     */
    public void setCanNull(Boolean canNull) {
        this.canNull = canNull;
    }

    /**
     * 获取形参索引
     * @return 形参索引
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * 设置形参索引
     * @param index 形参索引
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * 获取形参名称
     * @return 形参名称
     */
    public String getParamaterName() {
        return paramaterName;
    }

    /**
     * 设置形参名称
     * @param paramaterName 形参名称
     */
    public void setParamaterName(String paramaterName) {
        this.paramaterName = paramaterName;
    }

    /**
     * 获取形参类型
     * @return 形参类型
     */
    public Class<?> getParameterClass() {
        return parameterClass;
    }

    /**
     * 设置形参类型
     * @param ParameterClass 形参类型
     */
    public void setParameterClass(Class<?> ParameterClass) {
        this.parameterClass = ParameterClass;
    }

    /**
     * 获取字段信息列表 (字段名称,字段信息)
     * @return 字段信息列表 (字段名称,字段信息)
     */
    public HashMap<String, ColumnInfo> getColumnList() {
        return columnList;
    }

    /**
     * 设置字段信息列表 (字段名称,字段信息)
     * @param columnList 字段信息列表 (字段名称,字段信息)
     */
    public void setColumnList(HashMap<String, ColumnInfo> columnList) {
        this.columnList = columnList;
    }
}
