/**
 * @File ColumnInfo.java
 * @author zeng
 * @Date 2017-03-25
 * @Time 21:45
 */

package com.yunjuanyunshu.annotation;

public class ColumnInfo {

    /**
     * 字段名称
     */
    String columnName;

    /**
     * 字段注释名称
     */
    String columnDesc;

    /**
     * 字段字节长度
     */
    int columnBytes;

    /**
     * 字段最大值（数字）
     */
    int columnRangeMax;
    /**
     * 字段最小值（数字）
     */
    int columnRangeMin;


    /**
     * 是否允许为空
     */
    Boolean canNull;






    /**
     * 获取字段名称
     * @return 字段名称
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 设置字段名称
     * @param columnName 字段名称
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 获取字段注释名称
     * @return 字段注释名称
     */
    public String getColumnDesc() {
        return columnDesc;
    }

    /**
     * 设置字段注释名称
     * @param columnDesc 字段注释名称
     */
    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
    }

    /**
     * 获取字段字节长度
     * @return 字段字节长度
     */
    public int getColumnBytes() {
        return columnBytes;
    }

    /**
     * 设置字段字节长度
     * @param columnBytes 字段字节长度
     */
    public void setColumnBytes(int columnBytes) {
        this.columnBytes = columnBytes;
    }

    /**
     * 获取字段最大值（数字）
     * @return 字段最大值（数字）
     */
    public int getColumnRangeMax() {
        return columnRangeMax;
    }

    /**
     * 设置字段最大值（数字）
     * @param columnRangeMax 字段最大值（数字）
     */
    public void setColumnRangeMax(int columnRangeMax) {
        this.columnRangeMax = columnRangeMax;
    }

    /**
     * 获取字段最小值（数字）
     * @return 字段最小值（数字）
     */
    public int getColumnRangeMin() {
        return columnRangeMin;
    }

    /**
     * 设置字段最小值（数字）
     * @param columnRangeMin 字段最小值（数字）
     */
    public void setColumnRangeMin(int columnRangeMin) {
        this.columnRangeMin = columnRangeMin;
    }


    /**
     *  获取字段是否允许为空
     * @return true:允许为空 | false:不允许为空
     */
    public boolean getCanNull() {
        return canNull;
    }

    /**
     * 设置字段是否允许为空
     * @param canNull true:允许为空 | false:不允许为空
     */
    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }
}
