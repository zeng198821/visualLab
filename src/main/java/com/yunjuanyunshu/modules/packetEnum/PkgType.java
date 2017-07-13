package com.yunjuanyunshu.modules.packetEnum;

/**
 * Created by zeng on 17-7-13.
 */
public enum PkgType {
    /**
     * 登录
     */
    login("",101),
    /**
     * 登录响应
     */
    loginAck("",102),

    /**
     * 登录
     */
    loginOut("",103),
    /**
     * 登录
     */
    loginOutAck("",104),

    /**
     * 登出
     */
    setDynAcceihMode("",201),
    /**
     * 登出响应
     */
    setDynAcceihModeAck("",202),

    /**
     * 写数据
     */
    setPoint("",301),
    /**
     * 写数据响应
     */
    setPointAck("",302),

    /**
     * 确认链接
     */
    heartBeat("",401),
    /**
     * 确认链接响应
     */
    heartBeatAck("",402),

    /**
     * 校时
     */
    timeCheck("",501),
    /**
     * 校时响应
     */
    timeCheckAck("",502)
    ;

    private String typeName;
    private int typeCode;

    private PkgType(String name,int code){
        this.typeName = name;
        this.typeCode = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}
