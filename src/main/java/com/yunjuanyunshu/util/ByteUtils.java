/**
 * @File ByteUtils.java
 * @author zeng
 * @Date 2017-07-12
 * @Time 14:24
 */

package com.yunjuanyunshu.util;

import java.nio.*;
import java.nio.charset.Charset;

public class ByteUtils {

    /**
     * 读取Byte数组转换成Int
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static short readShort(byte[] buffer,int offset,boolean...isBigEndian){
        short tmp=0;
        if(buffer != null  && buffer.length >= (offset + 2)){
            byte[] tmpbuffer = new byte[2];
            for(int i =0;i<2;i++){
                tmpbuffer[i] = buffer[offset+i];
            }
            tmp = byteArrayToShort(tmpbuffer,isBigEndian);
        }
        return tmp;
    }

    /**
     * 读取Byte数组转换成Int
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static int readInt32(byte[] buffer,int offset,boolean...isBigEndian){
        int tmp=0;
        if(buffer != null  && buffer.length >= (offset + 4)){
            byte[] tmpbuffer = new byte[4];
            for(int i =0;i<4;i++){
                tmpbuffer[i] = buffer[offset+i];
            }
            tmp = byteArrayToInt(tmpbuffer,isBigEndian);
        }
        return tmp;
    }

    /**
     * 读取Byte数组转换成Long
     * @param buffer 偏移位置
     * @param offset
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static long readInt64(byte[] buffer,int offset,boolean...isBigEndian){
        long tmp=0;
        if(buffer != null  && buffer.length >= (offset + 8)){
            byte[] tmpbuffer = new byte[8];
            for(int i =0;i<8;i++){
                tmpbuffer[i] = buffer[offset+i];
            }
            tmp = byteArrayToLong(tmpbuffer,isBigEndian);
        }
        return tmp;
    }
    /**
     * 读取Byte数组转换成Int
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static float readFloat(byte[] buffer,int offset,boolean...isBigEndian){
        float tmp=0;
        if(buffer != null  && buffer.length >= (offset + 4)){
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,4);
            if(isBigEndian==null || !isBigEndian[0]){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            tmp = byteBuffer.getFloat();
        }
        return tmp;
    }

    /**
     * 读取Byte数组转换成Int
     * @param buffer
     * @param offset 偏移位置
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static double readDouble(byte[] buffer, int offset, boolean...isBigEndian){
        double tmp=0;
        if(buffer != null  && buffer.length >= (offset + 8)){
            if(buffer != null  && buffer.length >= (offset + 8)){
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer,offset,8);
                if(isBigEndian==null || !isBigEndian[0]){
                    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                }
                tmp = byteBuffer.getDouble();
            }
        }
        return tmp;
    }



    /**
     * Int 转换成 byte
     * @param x
     * @return
     */
    public static byte intToByte(int x) {
        return (byte) x;
    }

    /**
     * byte 转换成 Int
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    /**
     * byte数组转换成Long
     * @param b
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static int byteArrayToLong(byte[] b,boolean...isBigEndian ) {
        return !(isBigEndian==null ||  isBigEndian.length ==0 || !isBigEndian[0])
                ?
                 b[7] & 0xFFFF        |
                (b[6] & 0xFFFF) << 8  |
                (b[5] & 0xFFFF) << 16 |
                (b[4] & 0xFFFF) << 24 |
                (b[3] & 0xFFFF) << 32 |
                (b[2] & 0xFFFF) << 40 |
                (b[1] & 0xFFFF) << 48 |
                (b[0] & 0xFFFF) << 56
                :
                 b[0] & 0xFFFF        |
                (b[1] & 0xFFFF) << 8  |
                (b[2] & 0xFFFF) << 16 |
                (b[3] & 0xFFFF) << 24 |
                (b[0] & 0xFFFF) << 32 |
                (b[1] & 0xFFFF) << 40 |
                (b[2] & 0xFFFF) << 48 |
                (b[3] & 0xFFFF) << 56 ;
    }

    /**
     * Long转换成byte数组
     * @param a
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static byte[] longToByteArray(long a ,boolean...isBigEndian ) {
        return !(isBigEndian==null ||  isBigEndian.length ==0 || !isBigEndian[0])
                ?
                new byte[] {
                        (byte) (a & 0xFFFF),
                        (byte) ((a >> 8) & 0xFFFF),
                        (byte) ((a >> 16) & 0xFFFF),
                        (byte) ((a >> 24) & 0xFFFF),
                        (byte) ((a >> 32) & 0xFFFF),
                        (byte) ((a >> 40) & 0xFFFF),
                        (byte) ((a >> 48) & 0xFFFF),
                        (byte) ((a >> 56) & 0xFFFF)
                }
                :
                new byte[] {
                        (byte) ((a >> 56) & 0xFFFF),
                        (byte) ((a >> 48) & 0xFFFF),
                        (byte) ((a >> 40) & 0xFFFF),
                        (byte) ((a >> 32) & 0xFFFF),
                        (byte) ((a >> 24) & 0xFFFF),
                        (byte) ((a >> 16) & 0xFFFF),
                        (byte) ((a >> 8) & 0xFFFF),
                        (byte) (a & 0xFFFF)
                };
    }

    /**
     * byte数组转换成int
     * @param b
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static int byteArrayToInt(byte[] b,boolean...isBigEndian ) {
        return !(isBigEndian==null || isBigEndian.length ==0 || !isBigEndian[0])
                ?
                (b[3] & 0xFF |
                        (b[2] & 0xFF) << 8 |
                        (b[1] & 0xFF) << 16 |
                        (b[0] & 0xFF) << 24)
                :
                (       b[0] & 0xFF |
                        (b[1] & 0xFF) << 8 |
                        (b[2] & 0xFF) << 16 |
                        (b[3] & 0xFF) << 24);
    }

    /**
     * int转换成byte数组
     * @param a
     * @param isBigEndian
     * @return
     */
    public static byte[] intToByteArray(int a ,boolean...isBigEndian ) {
        return !(isBigEndian==null ||  isBigEndian.length ==0 || !isBigEndian[0])
                ?
                new byte[] {
                        (byte) (a & 0xFF),
                        (byte) ((a >> 8) & 0xFF),
                        (byte) ((a >> 16) & 0xFF),
                        (byte) ((a >> 24) & 0xFF)
                }
                :
                new byte[] {
                        (byte) ((a >> 24) & 0xFF),
                        (byte) ((a >> 16) & 0xFF),
                        (byte) ((a >> 8) & 0xFF),
                        (byte) (a & 0xFF)
                };
    }



    /**
     * byte数组转换成int
     * @param b
     * @param isBigEndian 是否按大端法进行解析 true：按大端法解析 | false或者为空：按小端法进行解析
     * @return
     */
    public static short byteArrayToShort(byte[] b,boolean...isBigEndian ) {
        return !(isBigEndian==null ||  isBigEndian.length ==0 || !isBigEndian[0]) ?
                (short)(b[1] & 0xFF | (b[0] & 0xFF) << 8 ):(short)( b[0] & 0xFF | (b[1] & 0xFF) << 8);
    }

    /**
     * int转换成byte数组
     * @param a
     * @param isBigEndian
     * @return
     */
    public static byte[] shortToByteArray(short a ,boolean...isBigEndian ) {
        return !(isBigEndian==null ||  isBigEndian.length ==0 || !isBigEndian[0])
                ?
                new byte[] {
                        (byte) (a & 0xFF),
                        (byte) ((a >> 8) & 0xFF)
                }
                :
                new byte[] {
                        (byte) ((a >> 8) & 0xFF),
                        (byte) (a & 0xFF)
                };
    }


    public static byte[] getBytes(short data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(char data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }

    public static byte[] getBytes(int data)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] getBytes(long data)
    {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    public static byte[] getBytes(float data)
    {
        int intBits = Float.floatToIntBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(double data)
    {
        long intBits = Double.doubleToLongBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(String data, String charsetName)
    {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static byte[] getBytes(String data)
    {
        return getBytes(data, "GBK");
    }


    public static short getShort(byte[] bytes)
    {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static char getChar(byte[] bytes)
    {
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static int getInt(byte[] bytes)
    {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }

    public static long getLong(byte[] bytes)
    {
        return(0xffL & (long)bytes[0]) | (0xff00L & ((long)bytes[1] << 8)) | (0xff0000L & ((long)bytes[2] << 16)) | (0xff000000L & ((long)bytes[3] << 24))
                | (0xff00000000L & ((long)bytes[4] << 32)) | (0xff0000000000L & ((long)bytes[5] << 40)) | (0xff000000000000L & ((long)bytes[6] << 48)) | (0xff00000000000000L & ((long)bytes[7] << 56));
    }

    public static float getFloat(byte[] bytes)
    {
        return Float.intBitsToFloat(getInt(bytes));
    }

    public static double getDouble(byte[] bytes)
    {
        long l = getLong(bytes);
        System.out.println(l);
        return Double.longBitsToDouble(l);
    }

    public static String getString(byte[] bytes, String charsetName)
    {
        return new String(bytes, Charset.forName(charsetName));
    }

    public static String getString(byte[] bytes)
    {
        return getString(bytes, "GBK");
    }




}
