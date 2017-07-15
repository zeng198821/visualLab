package com.yunjuanyunshu;

import com.yunjuanyunshu.modules.packet.PkgMain;
import com.yunjuanyunshu.modules.packet.PkgTD;
import com.yunjuanyunshu.modules.packet.PkgTime;
import com.yunjuanyunshu.util.PackageUtil;
import com.yunjuanyunshu.util.ScanUtils;

import java.nio.*;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        //ScanUtils.getPrivateFields(PkgMain.class);
        byte[] testPkg = new byte[]{
                0x1c,0x2d,0x3e,0x4f,
                0x13,0x00,0x00,0x00,
                0x06,0x00,0x00,0x00,
                0x01,0x00,0x00,0x00,
                (byte) 0xE1,0x07,0x7,0x0f,
                0x16,0x35,0x0b,
                0x6e,0x01
        };

        PkgTime tmpTestTime = new PkgTime();
        tmpTestTime.setYear((short) 2017);
        tmpTestTime.setMonth((byte)7);
        tmpTestTime.setDay((byte)16);
        tmpTestTime.setHour((byte)0);
        tmpTestTime.setMinute((byte)33);
        tmpTestTime.setSecond((byte)11);

        PkgMain tmpTestMain = new PkgMain();
        tmpTestMain.setInfo(tmpTestTime);
        tmpTestMain.setSerialsNo(6);
        tmpTestMain.setPyType(1);
        tmpTestMain.setHeader(0x4f3e2d1c);
        byte[] tmpbuffer = tmpTestMain.getThisBytes();

        PkgMain pkgMain = new PkgMain();
        pkgMain.checkPackageIsReady(testPkg);
        pkgMain.resolvePackage(testPkg,0);
        byte[] testPk =  new byte[100];
        ByteBuffer byteBuffer = ByteBuffer.wrap(testPkg,0,testPkg.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.getInt(12);
        byteBuffer = ByteBuffer.wrap(testPk);
        byteBuffer.putInt(10);
        byteBuffer.putInt(16);

        PackageUtil.resolvePackage(testPkg,0,pkgMain);
        ScanUtils.setFieldValue(pkgMain,"header",0x6c7d8e9f);
        PkgTime pkgTime = (PkgTime)ScanUtils.makeFieldInst(PkgTime.class);
        PkgTD pkgTD =new PkgTD();
        //pkgTD.setTest(pkgTime);
        pkgMain.setInfo(pkgTD);
        System.out.println(ScanUtils.getObjectSize(pkgMain));
        return;
//        if(args == null || args.length==0){
//            System.out.println("arg = s or arg = c");
//        }else{
//            if(args[0].equals("s")){
//                TcpServerBase tsb = new TcpServerBase();
//                tsb.startRecv();
//            }
//            if(args[0].equals("c")){
//                TcpClientBase tcb = new TcpClientBase();
//                tcb.startSend();
//                tcb.stopSend();
//            }
//        }
    }

}

