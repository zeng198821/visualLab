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
            if(tmpLength>0){
                readDataFromBytes(packageStream,tmpPos,packageObject,tmp,tmpAnno);
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

    private static void readDataFromBytes(byte[] packageStream, int tmpPos,Object packageObject,Field tmp,TcpPkgAnno tmpAnno){
        String tmpType = tmpAnno.pkgType();
        try {
            if(tmpType.toLowerCase().equals("byte")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),packageStream[tmpPos]);
                tmpPos++;
            }
            if(tmpType.toLowerCase().equals("short")){
                ScanUtils.setFieldValue(packageObject,tmp.getName(),BufferUtils.readInt16(packageStream,tmpPos));
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
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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

    /**
     * 判断包是否接收完毕
     * @param packageStream 包体缓冲
     * @param pos 当前偏移值
     * @return true:包体检验通过 | false:包体检验失败
     */
    public static boolean checkPackageIsReady(byte[] packageStream,int pos){
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
    public static boolean checkPackageIsReady(byte[] packageStream){
        return checkPackageIsReady(packageStream,0);
    }




}
