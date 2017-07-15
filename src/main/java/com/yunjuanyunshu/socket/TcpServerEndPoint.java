/**
 * @File TcpServerEndPoint.java
 * @author zeng
 * @Date 2017-07-12
 * @Time 15:31
 */

package com.yunjuanyunshu.socket;

import com.yunjuanyunshu.util.BufferUtils;
import com.yunjuanyunshu.util.ByteUtils;
import com.yunjuanyunshu.util.PackageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

abstract class TcpServerEndPoint extends Thread {

    private boolean runstate;

    private int packageHead;
    /**
     * 收包字节大小
     */
    protected static final int recvSize = 1024;
    /**
     * 缓冲区字节大小
     */
    protected static final int bufferSize = recvSize * 10;
    /**
     * 缓冲区头部位置
     */
    private int head;
    /**
     * 缓冲区尾部位置
     */
    private int tail;

    /**
     * 缓冲区字节数组
     */
    private byte[] buffer = new byte[bufferSize];

    InputStream inputStream;

    /**
     * 通讯对象
     */
    Socket socket;


    /**
     * 构造函数
     */
    public TcpServerEndPoint(Socket socket, int packageHead) {
        this.socket = socket;
        this.packageHead = packageHead;
    }

    /**
     * 启动线程
     */
    public  void start() {
        try{
            if(this.socket != null){
                inputStream = this.socket.getInputStream();
            }
            runstate = true;
            super.start();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    /**
     * 线程执行体
     */
    @Override
    public void run() {
        super.run();
        while (runstate){
            try{
                recvData();
                sendData();
                Thread.sleep(100);
            }catch (Exception ex){
                System.out.println(ex.toString());
            }

        }
    }


    /**
     * 接收数据
     */
    private void recvData() throws IOException {
        if(bufferSize<tail+recvSize){
            moveBufferData();
            return;
        }
        int tmpsize = inputStream.read(buffer,tail,recvSize);
        tail = tail + tmpsize;
        spiltPackage();
    }

    /**
     * 整理缓冲区内容
     */
    private void moveBufferData(){
        if(head > (bufferSize/2)){
            for(int i =0;i<( (tail+1) - head );i++){
                buffer[i] = buffer[head];
            }
        }
    }

    /**
     * 发送数据
     */
    private void sendData(){

    }

    /**
     * 拆分业务包
     */
    private void spiltPackage(){
        int tmpCount = 0;
        int tmpHead = head;
        int tmpLength=0;
        while ( tmpHead < tail ){
            if(BufferUtils.readInt32(buffer,tmpCount) == packageHead){
                tmpLength = BufferUtils.readInt32(buffer,tmpHead+4);
                if(tmpCount+tmpLength +2 > tail){
                    //剩余内容还未接收完毕,停止解析包
                    break;
                }else {
                    //剩余内容已接收完毕
                    byte[] tmpPkg = new byte[tmpLength+6];
                    System.arraycopy(buffer,tmpHead,tmpPkg,0,tmpPkg.length);
                    if(PackageUtil.checkPackageIsReady(tmpPkg)){
                        //包检查通过，进入处理包业务逻辑部分
                        handlePackage(tmpPkg);
                    }
                }
            }
            tmpHead++;
        }
    }

    /**
     * 处理数据包
     * @param pkgData
     */
    abstract void handlePackage(byte[] pkgData);
}
