/**
 * @File PkgTD.java
 * @author zeng
 * @Date 2017-07-13
 * @Time 14:00
 */

package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;
import com.yunjuanyunshu.modules.packetEnum.PkgType;

public class PkgTD extends PkgInfoBase {

    @TcpPkgAnno(pkgIdx=1,pkgName = "信号点类型",pkgType = "int",pkgLength = 4)
    private int nodeType;

    @TcpPkgAnno(pkgIdx=2,pkgName = "信号点ID",pkgType = "int",pkgLength = 4)
    private int nodeId;

    @TcpPkgAnno(pkgIdx=3,pkgName = "Ihid号",pkgType = "int",pkgLength = 4)
    private int Ihid;

    @TcpPkgAnno(pkgIdx=4,pkgName = "信号点值",pkgType = "byte",pkgLength = 1)
    private byte nodeValue;

    @TcpPkgAnno(pkgIdx=5,pkgName = "信号点状态",pkgType = "int",pkgLength = 4)
    private PkgType nodeState;




    /**
     * 构造函数
     */
    public PkgTD() {

    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getIhid() {
        return Ihid;
    }

    public void setIhid(int ihid) {
        Ihid = ihid;
    }

    public byte getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(byte nodeValue) {
        this.nodeValue = nodeValue;
    }

    public PkgType getNodeState() {
        return nodeState;
    }

    public void setNodeState(PkgType nodeState) {
        this.nodeState = nodeState;
    }
}
