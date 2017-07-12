package com.yunjuanyunshu;

import com.yunjuanyunshu.socket.TcpClientBase;
import com.yunjuanyunshu.socket.TcpServerBase;
import com.yunjuanyunshu.util.ScanUtils;

import java.lang.reflect.Field;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        ScanUtils.getPrivateFields(Ao.class);
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

