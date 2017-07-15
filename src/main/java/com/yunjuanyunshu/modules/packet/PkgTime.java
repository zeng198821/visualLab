/**
 * @File PkgTime.java
 * @author zeng
 * @Date 2017-07-13
 * @Time 13:57
 */

package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

public class PkgTime extends PkgInfoBase{

    @TcpPkgAnno(pkgIdx=1,pkgName = "年",pkgType = "short",pkgLength = 2)
    private short year;
    @TcpPkgAnno(pkgIdx=2,pkgName = "月",pkgType = "byte",pkgLength = 1)
    private byte month;
    @TcpPkgAnno(pkgIdx=3,pkgName = "日",pkgType = "byte",pkgLength = 1)
    private byte day;

    @TcpPkgAnno(pkgIdx=4,pkgName = "时",pkgType = "byte",pkgLength = 1)
    private byte hour;
    @TcpPkgAnno(pkgIdx=5,pkgName = "分",pkgType = "byte",pkgLength = 1)
    private byte minute;
    @TcpPkgAnno(pkgIdx=6,pkgName = "秒",pkgType = "byte",pkgLength = 1)
    private byte second;



    /**
     * 构造函数
     */
    public PkgTime() {

    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public byte getMonth() {
        return month;
    }

    public void setMonth(byte month) {
        this.month = month;
    }

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public byte getHour() {
        return hour;
    }

    public void setHour(byte hour) {
        this.hour = hour;
    }

    public byte getMinute() {
        return minute;
    }

    public void setMinute(byte minute) {
        this.minute = minute;
    }

    public byte getSecond() {
        return second;
    }

    public void setSecond(byte second) {
        this.second = second;
    }

    @Override
    int resolveChildPackage(byte[] pkgData, int pos) {
        return pos;
    }

    @Override
    Class<?> getPkgType(int typeid) {
        return null;
    }
}
