package com.muzi.repairtime.entity;

/**
 * 作者: lipeng
 * 时间: 2019/3/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class BaseEntity {

    /**
     * errInfo : 0
     * msg : 登陆成功！
     */

    private String errInfo;
    private String msg;

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "errInfo='" + errInfo + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
