package com.yunjuanyunshu;

import com.yunjuanyunshu.modules.packet.PkgMain;
import com.yunjuanyunshu.modules.packet.PkgTD;
import com.yunjuanyunshu.modules.packet.PkgTime;
import com.yunjuanyunshu.util.PackageUtil;
import com.yunjuanyunshu.util.ScanUtils;

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
                0x10,0x00,0x00,0x00,
                0x06,0x00,0x00,0x00,
                0x01,0x00,0x00,0x00,
                0x07,0x08,0x09,
                0x11,0x22
        };
        PkgMain pkgMain = new PkgMain();
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

