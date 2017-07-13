package com.yunjuanyunshu;

import com.yunjuanyunshu.modules.packet.PkgMain;
import com.yunjuanyunshu.util.ScanUtils;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        ScanUtils.getPrivateFields(PkgMain.class);
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

