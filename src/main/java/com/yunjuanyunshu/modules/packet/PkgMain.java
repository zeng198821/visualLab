/**
 * @File PkgMain.java
 * @author zeng
 * @Date 2017-07-13
 * @Time 10:59
 */

package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;
import com.yunjuanyunshu.modules.packetEnum.PkgType;

public class PkgMain {

    @TcpPkgAnno(pkgIdx=1,pkgName = "报文包头",pkgType = "int",pkgLength = 4)
    private int header;

    @TcpPkgAnno(pkgIdx=2,pkgName = "报文长度",pkgType = "int",pkgLength = 4)
    private int length;

    @TcpPkgAnno(pkgIdx=3,pkgName = "报文序号",pkgType = "int",pkgLength = 4)
    private int serialsNo;

    @TcpPkgAnno(pkgIdx=4,pkgName = "包类型",pkgType = "int",pkgLength = 4)
    private PkgType pyType;

    @TcpPkgAnno(pkgIdx=5,pkgName = "报文内容",pkgType = "PkgInfoBase",pkgLength = -1)
    private PkgInfoBase info;

    @TcpPkgAnno(pkgIdx=6,pkgName = "求和校验",pkgType = "short",pkgLength = 2)
    private short checkSum;




    /**
     * 构造函数
     */
    public PkgMain() {

    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSerialsNo() {
        return serialsNo;
    }

    public void setSerialsNo(int serialsNo) {
        this.serialsNo = serialsNo;
    }

    public PkgType getPyType() {
        return pyType;
    }

    public void setPyType(PkgType pyType) {
        this.pyType = pyType;
    }

    public PkgInfoBase getInfo() {
        return info;
    }

    public void setInfo(PkgInfoBase info) {
        this.info = info;
    }

    public short getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(short checkSum) {
        this.checkSum = checkSum;
    }
}
