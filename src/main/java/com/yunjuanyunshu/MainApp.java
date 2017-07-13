package com.yunjuanyunshu;

import com.yunjuanyunshu.modules.packet.PkgMain;
import com.yunjuanyunshu.modules.packet.PkgTD;
import com.yunjuanyunshu.modules.packet.PkgTime;
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
        PkgMain pkgMain = new PkgMain();
        PkgTime pkgTime = new PkgTime();
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

