package com.yunjuanyunshu.socket;

import com.yunjuanyunshu.modules.packet.PkgMain;
import com.yunjuanyunshu.util.JSONUtils;

import java.net.Socket;

/**
 * Created by zeng on 2017-7-16.
 */
public class ClientEndPoint extends TcpServerEndPoint {
    /**
     * 构造函数
     *
     * @param socket
     * @param packageHead
     */
    public ClientEndPoint(Socket socket, int packageHead) {
        super(socket, packageHead);
    }





    @Override
    void handlePackage(byte[] pkgData) {
        PkgMain pkgMain = new PkgMain();
        pkgMain.resolvePackage(pkgData);
        try {
            System.out.println(JSONUtils.obj2json(pkgMain));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
