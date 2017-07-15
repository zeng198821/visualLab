package com.yunjuanyunshu.modules.packet;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

/**
 * Created by zeng on 2017-7-15.
 */
public class PkgLogin {
    @TcpPkgAnno(pkgIdx=1,pkgName = "用户名",pkgType = "String",pkgLength = 40)
    private String userName;

    @TcpPkgAnno(pkgIdx=2,pkgName = "密码",pkgType = "String",pkgLength = 20)
    private String password;

}
