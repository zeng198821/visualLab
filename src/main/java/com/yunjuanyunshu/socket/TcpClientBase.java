/**
 * @File TcpClientBase.java
 * @author zeng
 * @Date 2017-07-12
 * @Time 10:55
 */

package com.yunjuanyunshu.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpClientBase {

    Socket s;
    BufferedReader br ;
    BufferedWriter bw ;



    /**
     * 构造函数
     */
    public TcpClientBase() {
        try{
            s = new Socket("127.0.0.1",10005);
            br = new BufferedReader(new InputStreamReader(System.in));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

    }
    public void startSend(){
        String sendDataStr=null;
        try{
            while((sendDataStr=br.readLine())!=null){
                if(sendDataStr.equals("bye")){
                    break;
                }
                bw.write(sendDataStr);
                bw.newLine();
                bw.flush();
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

    }
    public void stopSend(){
        if(bw != null)
            try{
                bw.close();
            }catch (Exception ex){
                System.out.println(ex.toString());
            }

    }

}
