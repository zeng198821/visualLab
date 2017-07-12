/**
 * @File TcpServerBase.java
 * @author zeng
 * @Date 2017-07-12
 * @Time 10:50
 */

package com.yunjuanyunshu.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerBase {




    protected ServerSocket ss;

    protected BufferedReader br;

    protected BufferedWriter bw;
    /**
     * 构造函数
     */
    public TcpServerBase() {
        try {
            ss = new ServerSocket(10005);
            Socket s = ss.accept();
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void startRecv() {
        String receiveDataStr=null;
        try{
                char[] a = new char[3];
            //

            while((receiveDataStr=br.readLine()) != null){

                if(receiveDataStr.equals("bye")){
                    break;
                }
                System.out.println("receive data:"+receiveDataStr);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }



}
