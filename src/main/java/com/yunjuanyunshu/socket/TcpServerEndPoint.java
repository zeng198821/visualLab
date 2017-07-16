/**
 * @File TcpServerEndPoint.java
 * @author zeng
 * @Date 2017-07-12
 * @Time 15:31
 */

package com.yunjuanyunshu.socket;

import com.yunjuanyunshu.util.BufferUtils;
import com.yunjuanyunshu.util.PackageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class TcpServerEndPoint extends Thread {

    private boolean runstate;

    private int packageHead;
    /**
     * 收包字节大小
     */
    protected static final int baseSize = 1024;
    /**
     * 缓冲区字节大小
     */
    protected static final int bufferSize = baseSize * 10;
    /**
     * 接收缓冲区头部位置
     */
    private int recvHead;
    /**
     * 接收缓冲区尾部位置
     */
    private int reacTail;

    /**
     * 发送缓冲区头部位置
     */
    private int sendHead;
    /**
     * 发送缓冲区尾部位置
     */
    private int sendTail;



    /**
     * 接收缓冲区字节数组
     */
    private byte[] recvBuffer = new byte[bufferSize];

    /**
     * 发送缓冲区字节数组
     */
    private byte[] sendBuffer = new byte[bufferSize];

    Lock sendLock = new ReentrantLock();

    /**
     * 接收流
     */
    InputStream inputStream;
    /**
     * 发送流
     */
    OutputStream outputStream;

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
                outputStream = this.socket.getOutputStream();
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
        System.out.println("开始接收数据");
        if(bufferSize< reacTail + baseSize){
            moveRecvBufferData();
            return;
        }
        int tmpsize = inputStream.read(recvBuffer, reacTail, baseSize);
        reacTail = reacTail + tmpsize;
        spiltPackage();
        System.out.println("结束接收数据");
    }

    /**
     * 整理缓冲区内容
     */
    private void moveRecvBufferData(){
        sendLock.lock();
        if(reacTail == recvHead){
            reacTail = 0;
            recvHead = 0;
            return;
        }
        if(recvHead > (bufferSize/2)){
            for(int i = 0; i<( (reacTail +1) - recvHead); i++){
                recvBuffer[i] = recvBuffer[recvHead+i];
            }
            reacTail = reacTail - recvHead;
            recvHead = 0;
        }
        sendLock.unlock();
    }

    /**
     * 整理缓冲区内容
     */
    private void moveSendBufferData(){
        sendLock.lock();
        if(sendTail ==sendHead){
            sendTail = 0;
            sendHead = 0;
            return;
        }
        if(sendHead> (bufferSize/2)){
            for(int i = 0; i<( (sendTail +1) - sendHead); i++){
                sendBuffer[i] = sendBuffer[sendHead+i];
            }
            sendTail = sendTail - sendHead;
            sendHead = 0;
        }
        sendLock.unlock();
    }

    /**
     * 将待发送数据写入缓冲池
     * @param sendData 待发送数据
     * @param offset 偏移量
     * @param length 写入长度
     */
    public void sendData(byte[] sendData,int offset,int length){
        sendLock.lock();
        System.arraycopy(sendData,offset,sendBuffer,sendTail,length);
        sendLock.unlock();
    }

    /**
     * 将待发送数据写入缓冲池
     * @param sendData 待发送数据
     */
    public void sendData(byte[] sendData){
        sendData(sendData,0,sendData.length);
    }

    /**
     * 发送数据
     */
    private void sendData(){
        System.out.println("开始接收数据");
        try {
            sendLock.lock();
            outputStream.write(sendBuffer,sendHead,(sendTail - sendHead)+1);
            sendLock.unlock();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bufferSize< sendTail + baseSize){
            moveSendBufferData();
            return;
        }
        System.out.println("开始接收数据");

    }


    /**
     * 拆分业务包
     */
    private void spiltPackage(){
        int tmpCount = 0;
        int tmpHead = recvHead;
        int tmpLength=0;
        while ( tmpHead < reacTail){
            if(BufferUtils.readInt32(recvBuffer,tmpCount) == packageHead){
                tmpLength = BufferUtils.readInt32(recvBuffer,tmpHead+4);
                if(tmpCount+tmpLength +2 > reacTail){
                    //剩余内容还未接收完毕,停止解析包
                    break;
                }else {
                    //剩余内容已接收完毕
                    byte[] tmpPkg = new byte[tmpLength+6];
                    System.arraycopy(recvBuffer,tmpHead,tmpPkg,0,tmpPkg.length);
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
