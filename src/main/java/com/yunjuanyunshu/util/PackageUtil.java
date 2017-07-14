package com.yunjuanyunshu.util;

import com.yunjuanyunshu.annotation.TcpPkgAnno;
import com.yunjuanyunshu.modules.packet.PkgTime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),packageStream[tmpPos]);
                         tmpPos++;
                     }
                     if(tmpType.toLowerCase().equals("short")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readShort(packageStream,tmpPos));
                         tmpPos = tmpPos + 2;
                     }
                     if(tmpType.toLowerCase().equals("int")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readInt32(packageStream,tmpPos));
                         tmpPos = tmpPos + 4;
                     }
                     if(tmpType.toLowerCase().equals("long")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readInt64(packageStream,tmpPos));
                         tmpPos = tmpPos + 8;
                     }
                     if(tmpType.toLowerCase().equals("float")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readFloat(packageStream,tmpPos));
                         tmpPos = tmpPos + 4;
                     }
                     if(tmpType.toLowerCase().equals("double")){
                         ScanUtils.setFieldValue(packageObject,tmp.getName(),ByteUtils.readDouble(packageStream,tmpPos));
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
                    Class<?> tmpPkgClass = getPkgType((int)ScanUtils.getFieldValue(packageObject,"pyType"));
                    if(tmpPkgClass != null){
                        Object tmpobj = tmpPkgClass.newInstance();
                            ScanUtils.setFieldValue(packageObject,tmp.getName(),tmpobj);
                        tmpPos = resolvePackage(packageStream,tmpPos,tmpobj);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }catch (InvocationTargetException e) {
                    e.printStackTrace();
                }catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return tmpPos;
    }
    private static Class<?> getPkgType(int typeid){
        HashMap<Integer,Class<?>> tmpPkgTypeList = new HashMap<Integer,Class<?>>();
        tmpPkgTypeList.put(1, PkgTime.class);
        Class<?> tmpPkgClass = null;
        if(tmpPkgTypeList.containsKey(typeid)){
            tmpPkgClass = tmpPkgTypeList.get(typeid);
        }
        return tmpPkgClass;


    }

}
