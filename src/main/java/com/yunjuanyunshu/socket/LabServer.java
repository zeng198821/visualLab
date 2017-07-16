package com.yunjuanyunshu.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by zeng on 2017-7-16.
 */
public class LabServer {

    HashMap<Integer,ServerEndpoint> clientList;

    ServerSocket serverSocket;

    public LabServer(){
        try {
            serverSocket = new ServerSocket(61234);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientList = new HashMap<Integer,ServerEndpoint>();
    }
    public void startTest(){
        for (int i=0;i<100;i++){
            try {
                Socket s = serverSocket.accept();
                ServerEndpoint serverEndpoint = new ServerEndpoint(s,0x7E6D5C4B);
                clientList.put(i,serverEndpoint);
                serverEndpoint.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
