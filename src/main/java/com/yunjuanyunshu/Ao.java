package com.yunjuanyunshu;

import com.yunjuanyunshu.annotation.TcpPkgAnno;

/**
 * Created by zeng on 2017-7-12.
 */
public class Ao {
    @TcpPkgAnno(pkgIdx = 1,pkgName = "id")
    private int id;
    @TcpPkgAnno(pkgIdx = 2,pkgName = "name")
    private String name;
}
