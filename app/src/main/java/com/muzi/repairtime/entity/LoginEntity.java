package com.muzi.repairtime.entity;

import android.os.Parcel;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class LoginEntity extends BaseEntity {

    /**
     * user : {"id":8,"name":"AA","pass":null,"phone":"11111111111","check":1,"group":"心血管内科","type":"普通用户"}
     */

    private UserBean obj;

    public UserBean getUser() {
        return obj;
    }

    public void setUser(UserBean obj) {
        this.obj = obj;
    }

    public static class UserBean implements android.os.Parcelable {

        /**
         * id : 8
         * name : AA
         * pass : null
         * phone : 11111111111
         * check : 1
         * group : 心血管内科
         * type : 普通用户
         * phoneId : 734f197756c1457fbc779efcd61c93c5
         */

        private int id;
        private String name;
        private String pass;
        private String phone;
        private int check;
        private String group;
        private String type;
        private String phoneId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPhoneId() {
            return phoneId;
        }

        public void setPhoneId(String phoneId) {
            this.phoneId = phoneId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.pass);
            dest.writeString(this.phone);
            dest.writeInt(this.check);
            dest.writeString(this.group);
            dest.writeString(this.type);
            dest.writeString(this.phoneId);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.pass = in.readString();
            this.phone = in.readString();
            this.check = in.readInt();
            this.group = in.readString();
            this.type = in.readString();
            this.phoneId = in.readString();
        }

        public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
            @Override
            public UserBean createFromParcel(Parcel source) {
                return new UserBean(source);
            }

            @Override
            public UserBean[] newArray(int size) {
                return new UserBean[size];
            }
        };
    }
}
