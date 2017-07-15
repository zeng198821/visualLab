package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

/**
 * Created by zeng on 2017-7-15.
 */
public class PkgLoginAck {

    @TcpPkgAnno(pkgIdx=1,pkgName = "登录权限",pkgType = "int",pkgLength = 4)
    private int RightLevel ;
}
