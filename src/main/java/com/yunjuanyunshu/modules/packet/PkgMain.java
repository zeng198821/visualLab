/**
 * @File PkgMain.java
 * @author zeng
 * @Date 2017-07-13
 * @Time 10:59
 */

package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;
import com.yunjuanyunshu.util.BufferUtils;
import com.yunjuanyunshu.util.ScanUtils;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

public class PkgMain extends PkgInfoBase{

    @TcpPkgAnno(pkgIdx=1,pkgName = "报文包头",pkgType = "int",pkgLength = 4)
    private int header;

    @TcpPkgAnno(pkgIdx=2,pkgName = "报文长度",pkgType = "int",pkgLength = 4)
    private int length;

    @TcpPkgAnno(pkgIdx=3,pkgName = "报文序号",pkgType = "int",pkgLength = 4)
    private int serialsNo;

    @TcpPkgAnno(pkgIdx=4,pkgName = "包类型",pkgType = "int",pkgLength = 4)
    private int pyType;

    @TcpPkgAnno(pkgIdx=5,pkgName = "报文内容",pkgType = "PkgInfoBase",pkgLength = -1)
    private PkgInfoBase info;

    @TcpPkgAnno(pkgIdx=6,pkgName = "求和校验",pkgType = "short",pkgLength = 2)
    private short checkSum;

    HashMap<Integer,Class<?>> PkgTypeList = new HashMap<Integer,Class<?>>();

    /**
     * 构造函数
     */
    public PkgMain() {
        PkgTypeList.put(1, PkgTime.class);
    }

    @Override
    int resolveChildPackage(byte[] pkgData, int pos) {
        int tmpPos = pos;
        try {
            Class<?> tmpPkgClass = getPkgType(getPyType());
            if(tmpPkgClass != null){
                PkgInfoBase tmpobj = (PkgInfoBase)tmpPkgClass.newInstance();
                setInfo(tmpobj);
                tmpPos = tmpobj.resolvePackage(pkgData,tmpPos);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tmpPos;
    }

    @Override
    Class<?> getPkgType(int typeid){
        Class<?> tmpPkgClass = null;
        if(PkgTypeList.containsKey(typeid)){
            tmpPkgClass = PkgTypeList.get(typeid);
        }
        return tmpPkgClass;
    }

    public byte[] getThisBytes(){
        int tmpPackageAllLength = this.getPackageLength();
        int tmpCountCheckSum = 0;
        setLength(tmpPackageAllLength - 6);
        byte[] tmpBuffer = new byte[tmpPackageAllLength];
        this.writeObjectToBytes(tmpBuffer,0);
        for(int i =4;i<tmpPackageAllLength -2 ;i++){
            tmpCountCheckSum = tmpCountCheckSum + (0xFF & tmpBuffer[i]);
        }
        BufferUtils.writeInt16(tmpBuffer,tmpPackageAllLength-2,(short)tmpCountCheckSum);
        return tmpBuffer;
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

    public int getPyType() {
        return pyType;
    }

    public void setPyType(int pyType) {
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
