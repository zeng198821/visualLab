package com.yunjuanyunshu.util;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Created by zeng on 2017-7-13.
 */
public class PackageUtil {

    public  static  int resolvePackage(byte[] packageStream, int pos, Object packageObject){
        int tmpPos = pos;
        Field[] tmplist = ScanUtils.getPrivateFields(packageObject.getClass());
        for (Field tmp : tmplist) {
            TcpPkgAnno tmpAnno = tmp.getDeclaredAnnotation(TcpPkgAnno.class);
            if(tmpAnno==null){
                continue;
            }
            int tmpLength = tmpAnno.pkgLength();
            String tmpType = tmpAnno.pkgType();
            if(tmpLength>0){
                try {
                     if(tmpType.toLowerCase().equals("byte")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),packageStream[pos]);
                         tmpPos++;
                     }
                     if(tmpType.toLowerCase().equals("short")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readShort(packageStream,pos));
                         tmpPos = tmpPos + 2;
                     }
                     if(tmpType.toLowerCase().equals("int")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readInt32(packageStream,pos));
                         tmpPos = tmpPos + 4;
                     }
                     if(tmpType.toLowerCase().equals("long")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readInt64(packageStream,pos));
                         tmpPos = tmpPos + 8;
                     }
                     if(tmpType.toLowerCase().equals("float")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readFloat(packageStream,pos));
                         tmpPos = tmpPos + 4;
                     }
                     if(tmpType.toLowerCase().equals("double")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readDouble(packageStream,pos));
                         tmpPos = tmpPos + 8;
                     }
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    tmpPos = resolvePackage(packageStream,tmpPos,tmp.getType().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return tmpPos;
    }

}
