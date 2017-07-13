/**
 * @File PkgTime.java
 * @author zeng
 * @Date 2017-07-13
 * @Time 13:57
 */

package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

public class PkgTime extends PkgInfoBase{

    @TcpPkgAnno(pkgIdx=1,pkgName = "时",pkgType = "byte",pkgLength = 1)
    private byte hour;
    @TcpPkgAnno(pkgIdx=2,pkgName = "分",pkgType = "byte",pkgLength = 1)
    private byte minute;
    @TcpPkgAnno(pkgIdx=3,pkgName = "秒",pkgType = "byte",pkgLength = 1)
    private byte second;



    /**
     * 构造函数
     */
    public PkgTime() {

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
}
