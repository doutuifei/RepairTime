package com.muzi.repairtime.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: lipeng
 * 时间: 2019/3/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class BaseEntity implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.errInfo);
        dest.writeString(this.msg);
    }

    public BaseEntity() {
    }

    protected BaseEntity(Parcel in) {
        this.errInfo = in.readString();
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<BaseEntity> CREATOR = new Parcelable.Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel source) {
            return new BaseEntity(source);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };

}
