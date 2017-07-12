/**
 * @File EntityInfo.java
 * @author zeng
 * @Date 2017-03-25
 * @Time 21:45
 */

package com.yunjuanyunshu.annotation;

import java.util.HashMap;

public class EntityInfo {

    /**
     * 表名称
     */
    String tableName;

    /**
     * 表注释名称
     */
    String tableDesc;

    /**
     * 字段列表
     */
    HashMap<String,ColumnInfo> columns;


    /**
     * 获取表名
     * @return 表名称
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名称
     * @param tableName 表名称
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取表注解名称
     * @return
     */
    public String getTableDesc() {
        return tableDesc;
    }

    /**
     * 设置表注解名称
     * @param tableDesc
     */
    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    /**
     * 获取字段属性列表
     * @return 字段属性列表
     */
    public HashMap<String, ColumnInfo> getColumns() {
        return columns;
    }

    /**
     * 设置字段属性列表
     * @param columns 字段属性列表
     */
    public void setColumns(HashMap<String, ColumnInfo> columns) {
        this.columns = columns;
    }
}
