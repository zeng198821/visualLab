/**
 * @File PkgInfoBase.java
 * @author zeng
 * @Date 2017-07-13
 * @Time 11:08
 */

package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;
import com.yunjuanyunshu.util.BufferUtils;
import com.yunjuanyunshu.util.PackageUtil;
import com.yunjuanyunshu.util.ScanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class PkgInfoBase {

    abstract int resolveChildPackage(byte[] pkgData,int pos);

    public  int resolvePackage(byte[] packageStream){
        return resolvePackage(packageStream,0);
    }

    public  int resolvePackage(byte[] packageStream, int pos){
        int tmpPos = pos;
        Field[] tmplist = ScanUtils.getPrivateFields(this.getClass());
        for (Field tmp : tmplist) {
            TcpPkgAnno tmpAnno = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            if(tmpAnno==null){
                continue;
            }
            int tmpLength = tmpAnno.pkgLength();
            if(tmpLength>0){
                tmpPos =  readDataFromBytes(packageStream,tmpPos,this,tmp,tmpAnno);
            }else {
                tmpPos = resolveChildPackage(packageStream,tmpPos);
            }
        }
        return tmpPos;
    }

    private  int readDataFromBytes(byte[] packageStream, int pos,Object packageObject,Field tmp,TcpPkgAnno tmpAnno){
        int tmpPos = pos;
        String tmpType = tmpAnno.pkgType();
        int tmpLength = tmpAnno.pkgLength();
        try {
            if(tmpType.toLowerCase().equals("byte")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),packageStream[tmpPos]);
                tmpPos++;
            }
            if(tmpType.toLowerCase().equals("short")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(), BufferUtils.readInt16(packageStream,tmpPos));
                tmpPos = tmpPos + 2;
            }
            if(tmpType.toLowerCase().equals("int")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),BufferUtils.readInt32(packageStream,tmpPos));
                tmpPos = tmpPos + 4;
            }
            if(tmpType.toLowerCase().equals("long")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),BufferUtils.readInt64(packageStream,tmpPos));
                tmpPos = tmpPos + 8;
            }
            if(tmpType.toLowerCase().equals("float")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),BufferUtils.readFloat(packageStream,tmpPos));
                tmpPos = tmpPos + 4;
            }
            if(tmpType.toLowerCase().equals("double")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),BufferUtils.readDouble(packageStream,tmpPos));
                tmpPos = tmpPos + 8;
            }
            if(tmpType.toLowerCase().equals("String")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),BufferUtils.readString(packageStream,tmpPos,tmpLength));
                tmpPos = tmpPos + tmpLength;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return tmpPos;
    }
    private  int writeDataToBytes(byte[] packageStream, int pos,Object packageObject,Field tmp,TcpPkgAnno tmpAnno){
        int tmpPos = pos;
        String tmpType = tmpAnno.pkgType();
        int tmpLength = tmpAnno.pkgLength();
        try {
            if(tmpType.toLowerCase().equals("byte")){
                packageStream[tmpPos] = (byte)ScanUtils.getFieldValue(packageObject,tmp.getName());
                tmpPos++;
            }
            if(tmpType.toLowerCase().equals("short")){
                BufferUtils.writeInt16(packageStream,tmpPos,(short)ScanUtils.getFieldValue(packageObject,tmp.getName()));
                tmpPos = tmpPos + 2;
            }
            if(tmpType.toLowerCase().equals("int")){
                BufferUtils.writeInt32(packageStream,tmpPos,(int)ScanUtils.getFieldValue(packageObject,tmp.getName()));
                tmpPos = tmpPos + 4;
            }
            if(tmpType.toLowerCase().equals("long")){
                BufferUtils.writeInt64(packageStream,tmpPos,(long)ScanUtils.getFieldValue(packageObject,tmp.getName()));
                tmpPos = tmpPos + 8;
            }
            if(tmpType.toLowerCase().equals("float")){
                BufferUtils.writeFloat(packageStream,tmpPos,(float)ScanUtils.getFieldValue(packageObject,tmp.getName()));
                tmpPos = tmpPos + 4;
            }
            if(tmpType.toLowerCase().equals("double")){
                BufferUtils.writeDouble(packageStream,tmpPos,(double)ScanUtils.getFieldValue(packageObject,tmp.getName()));
                tmpPos = tmpPos + 8;
            }
            if(tmpType.toLowerCase().equals("String")){
                BufferUtils.writeString(packageStream,tmpPos,(String)ScanUtils.getFieldValue(packageObject,tmp.getName()),tmpLength);
                tmpPos = tmpPos + tmpLength;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tmpPos;
    }

    public   void writeObjectToBytes(byte[] sendBuffer,int pos){
        int tmpPos = pos;
        Field[] tmplist = ScanUtils.getPrivateFields(this.getClass());
        for (Field tmp : tmplist) {
            TcpPkgAnno tmpAnno = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            if(tmpAnno==null){
                continue;
            }
            if(tmpAnno.pkgLength()>0){
                writeDataToBytes(sendBuffer,tmpPos,this,tmp,tmpAnno);
                tmpPos = tmpPos + tmpAnno.pkgLength();
            }else {
                try {
                    PkgInfoBase tmpChild = (PkgInfoBase)ScanUtils.getFieldValue(this,tmp.getName());
                    if(tmpChild!=null){
                        tmpChild.writeObjectToBytes(sendBuffer,tmpPos);
                        tmpPos = tmpPos + tmpChild.getPackageLength();
                    }
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    abstract  Class<?> getPkgType(int typeid);

    public int getPackageLength(){
        int tmpLenth=0;
        Field[] fields = this.getClass().getDeclaredFields();
        Class tmpListClass=null;
        try {
            tmpListClass = Class.forName("java.util.List");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Field tmp : fields) {
            TcpPkgAnno tcpAnno = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            if(tmp.getType() == tmpListClass){
                String tmpChildClassStr = tmp.getGenericType().getTypeName();
                tmpChildClassStr = tmpChildClassStr.substring(tmpChildClassStr.indexOf("<")+1,tmpChildClassStr.indexOf(">"));
                try {
                    tmpListClass = null;
                    tmpListClass = Class.forName(tmpChildClassStr);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(tmpListClass != null){
                    try {
                        PkgInfoBase tmpChildObj  = (PkgInfoBase)tmpListClass.newInstance();
                        List<PkgInfoBase> tmpList = (List<PkgInfoBase>)ScanUtils.getFieldValue(this,tmp.getName());
                        tmpLenth = tmpLenth + (tmpChildObj.getPackageLength() * tmpList.size());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(tcpAnno == null){
                continue;
            }

            int tmpsize= tcpAnno.pkgLength();
            if(tmpsize <= 0 ){
                if(!tcpAnno.pkgType().equals("list")){
                    try {
                        PkgInfoBase tmpChild = (PkgInfoBase)ScanUtils.getFieldValue(this,tmp.getName());
                        tmpsize = tmpChild.getPackageLength();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    tmpsize = 0;
                }

            }
            tmpLenth = tmpLenth + tmpsize;
        }
        return tmpLenth;
    }

    /**
     * 判断包是否接收完毕
     * @param packageStream 包体缓冲
     * @param pos 当前偏移值
     * @return true:包体检验通过 | false:包体检验失败
     */
    public  boolean checkPackageIsReady(byte[] packageStream,int pos){
        boolean tmpPackageIsReady = false;
        int tmpPkgLength = BufferUtils.readInt32(packageStream,pos+4);
        int tmpCountCheckSum =0;
        short tmpPkgCheckSum = BufferUtils.readInt16(packageStream,pos+4+tmpPkgLength);
        for(int i =4;i<(pos+4+tmpPkgLength);i++){
            tmpCountCheckSum = tmpCountCheckSum + (0xFF & packageStream[i]);
        }
        if(tmpCountCheckSum == (0xFFFF & tmpPkgCheckSum)){
            tmpPackageIsReady = true;
        }
        return tmpPackageIsReady;
    }
    /**
     * 判断包是否接收完毕
     * @param packageStream 包体缓冲
     * @return true:包体检验通过 | false:包体检验失败
     */
    public  boolean checkPackageIsReady(byte[] packageStream){
        return checkPackageIsReady(packageStream,0);
    }


}
