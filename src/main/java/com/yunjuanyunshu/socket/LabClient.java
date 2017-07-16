package com.yunjuanyunshu.socket;

import com.yunjuanyunshu.modules.packet.PkgMain;
import com.yunjuanyunshu.modules.packet.PkgTime;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by zeng on 2017-7-16.
 */
public class LabClient {
    ClientEndPoint clientEndPoint ;
    public LabClient(){
        try {
            clientEndPoint = new ClientEndPoint(new Socket("127.0.0.1",61234),0x7E6D5C4B);
            clientEndPoint.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLogin(){

    }

    public void sendTest(){
        PkgMain tmpTestMain = new PkgMain();
        tmpTestMain.setPyType(1);
        tmpTestMain.setHeader(0x7E6D5C4B);
        for(int i =0;i<1000;i++){
            tmpTestMain.setSerialsNo(i);
            tmpTestMain.setInfo(new PkgTime(new Date()));
            clientEndPoint.sendData(tmpTestMain.getThisBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
