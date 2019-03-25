package com.muzi.repairtime.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class AuditEntity {


    /**
     * listName : pages
     * pages : {"list":[{"id":1,"name":"郭佳殷","pass":null,"phone":"18732850775","check":1,"group":"信息中心","type":"管理员"},{"id":2,"name":"李航天","pass":null,"phone":"15350831515","check":1,"group":"信息中心","type":"维修员"},{"id":3,"name":"李婵婵","pass":null,"phone":"13253221265","check":1,"group":"信息中心","type":"管理员"},{"id":4,"name":"刘明阳","pass":null,"phone":"18631855370","check":1,"group":"信息中心","type":"维修员"},{"id":5,"name":"刘畅","pass":null,"phone":"18732878989","check":1,"group":"信息中心","type":"维修员"},{"id":6,"name":"王佳博","pass":null,"phone":"15503211578","check":1,"group":"信息中心","type":"维修员"},{"id":7,"name":"郭佳殷","pass":null,"phone":"17332850775","check":1,"group":"信息中心","type":"维修员"},{"id":8,"name":"AA","pass":null,"phone":"11111111111","check":1,"group":"心血管内科","type":"普通用户"}],"totalRecord":13,"pageSize":8,"totalPage":2,"currentPage":1,"previousPage":1,"nextPage":2,"pageBar":[1,2],"startIndex":0,"endIndex":8}
     */

    private PagesBean pages;

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public static class PagesBean {
        /**
         * list : [{"id":1,"name":"郭佳殷","pass":null,"phone":"18732850775","check":1,"group":"信息中心","type":"管理员"},{"id":2,"name":"李航天","pass":null,"phone":"15350831515","check":1,"group":"信息中心","type":"维修员"},{"id":3,"name":"李婵婵","pass":null,"phone":"13253221265","check":1,"group":"信息中心","type":"管理员"},{"id":4,"name":"刘明阳","pass":null,"phone":"18631855370","check":1,"group":"信息中心","type":"维修员"},{"id":5,"name":"刘畅","pass":null,"phone":"18732878989","check":1,"group":"信息中心","type":"维修员"},{"id":6,"name":"王佳博","pass":null,"phone":"15503211578","check":1,"group":"信息中心","type":"维修员"},{"id":7,"name":"郭佳殷","pass":null,"phone":"17332850775","check":1,"group":"信息中心","type":"维修员"},{"id":8,"name":"AA","pass":null,"phone":"11111111111","check":1,"group":"心血管内科","type":"普通用户"}]
         * totalRecord : 13
         * pageSize : 8
         * totalPage : 2
         * currentPage : 1
         * previousPage : 1
         * nextPage : 2
         * pageBar : [1,2]
         * startIndex : 0
         * endIndex : 8
         */

        private int totalPage;
        private int currentPage;
        private List<ListBean> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements MultiItemEntity {
            /**
             * id : 1
             * name : 郭佳殷
             * pass : null
             * phone : 18732850775
             * check : 1
             * group : 信息中心
             * type : 管理员
             */

            private int id;
            private String name;
            private Object pass;
            private String phone;
            private int check;
            private String group;
            private String type;

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

            public void setPass(Object pass) {
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

            @Override
            public int getItemType() {
                return check;
            }
        }
    }
}
