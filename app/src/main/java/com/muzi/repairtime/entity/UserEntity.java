package com.muzi.repairtime.entity;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class UserEntity extends BaseEntity{

    /**
     * listName : baseinfo
     * pages : {"name":"李航天","group":"信息中心","phone":"15350831515","ipAddress":"169.254.88.81","type":"维修员","pic":"郭佳殷"}
     */

    private String listName;
    private PagesBean pages;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public static class PagesBean {
        /**
         * name : 李航天
         * group : 信息中心
         * phone : 15350831515
         * ipAddress : 169.254.88.81
         * type : 维修员
         * pic : 郭佳殷
         */

        private String name;
        private String group;
        private String phone;
        private String ipAddress;
        private String type;
        private String pic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

}
