/**
 * @File BufferUtils.java
 * @author zeng
 * @Date 2017-07-12
 * @Time 14:10
 */

package com.yunjuanyunshu.util;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtils {

    /**
     * 读取Byte数组转换成shotr
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static short readInt16(byte[] buffer,int offset,boolean...isBigEndian){
        short tmp=0;
        if(buffer != null  && buffer.length >= (offset + 2)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,2);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0] ){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            tmp = byteBuffer.getShort();
        }
        return tmp;
    }

    /**
     * 读取Byte数组转换成int
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static int readInt32(byte[] buffer,int offset,boolean...isBigEndian){
        int tmp=0;
        if(buffer != null  && buffer.length >= (offset + 4)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,4);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            tmp = byteBuffer.getInt();
        }
        return tmp;
    }


    /**
     * 读取Byte数组转换成long
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static long readInt64(byte[] buffer,int offset,boolean...isBigEndian){
        long tmp=0;
        if(buffer != null  && buffer.length >= (offset + 8)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,8);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            tmp = byteBuffer.getLong();
        }
        return tmp;
    }



    /**
     * 读取Byte数组转换成float
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static float readFloat(byte[] buffer,int offset,boolean...isBigEndian){
        float tmp=0;
        if(buffer != null  && buffer.length >= (offset + 4)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,4);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            tmp = byteBuffer.getFloat();
        }
        return tmp;
    }

    /**
     * 读取Byte数组转换成double
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static double readDouble(byte[] buffer, int offset, boolean...isBigEndian){
        double tmp=0;
        if(buffer != null  && buffer.length >= (offset + 8)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,8);
            if(isBigEndian==null || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            tmp = byteBuffer.getDouble();
        }
        return tmp;
    }


    /**
     * 读取Byte数组转换成String
     * @param buffer
     * @param offset 偏移位置
     * @return
     */
    public static String readString(byte[] buffer, int offset,int length){
        String tmp=null;
        char[] tmpBuffer = new char[length];
        if(buffer != null  && buffer.length >= (offset + length)){
            for(int i=0;i<length;i++){
                tmpBuffer[i] = (char)buffer[offset+i];
            }
            tmp = String.valueOf(tmpBuffer);
        }
        return tmp;
    }

    /**
     * 读取Byte数组转换成shotr
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static void writeInt16(byte[] buffer,int offset,short data,boolean...isBigEndian){
        if(buffer != null  && buffer.length >= (offset + 2)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,2);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0] ){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            byteBuffer.putShort(data);
        }
    }

    /**
     * 读取Byte数组转换成int
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static void writeInt32(byte[] buffer,int offset,int data,boolean...isBigEndian){
        if(buffer != null  && buffer.length >= (offset + 4)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,4);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            byteBuffer.putInt(data);
        }
    }


    /**
     * 读取Byte数组转换成long
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static void writeInt64(byte[] buffer,int offset,long data,boolean...isBigEndian){
        long tmp=0;
        if(buffer != null  && buffer.length >= (offset + 8)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,8);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            byteBuffer.putLong(data);
        }
    }



    /**
     * 读取Byte数组转换成float
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static void writeFloat(byte[] buffer,int offset,float data,boolean...isBigEndian){
        if(buffer != null  && buffer.length >= (offset + 4)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,4);
            if(isBigEndian==null || isBigEndian.length == 0 || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            byteBuffer.putFloat(data);
        }
    }

    /**
     * 读取Byte数组转换成double
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static void writeDouble(byte[] buffer, int offset,double data, boolean...isBigEndian){
        if(buffer != null  && buffer.length >= (offset + 8)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,8);
            if(isBigEndian==null || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            byteBuffer.putDouble(data);

        }
    }


    /**
     * 读取Byte数组转换成String
     * @param buffer
     * @param offset 偏移位置
     * @return
     */
    public static void writeString(byte[] buffer, int offset,String data,int length){
        if(data == null || data.length() <length){
            data = "";
            data = StringUtils.leftPad(data,length);
        }
        char[] tmpBuffer = data.toCharArray();
        if(buffer != null  && buffer.length >= (offset + length)){
            for(int i=0;i<length;i++){
                buffer[offset+i] = (byte)tmpBuffer[i];
            }
        }
    }

}
