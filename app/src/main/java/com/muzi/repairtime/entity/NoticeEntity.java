package com.muzi.repairtime.entity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class NoticeEntity extends BaseEntity {

    /**
     * listName : pages
     * pages : {"list":[{"id":321,"title":"测试公告发布","content":"<p>测试websocket<\/p>","publishtime":1551666995000,"username":"郭佳殷"},{"id":320,"title":"测试公告发布","content":"<p>ab/lab/pay<\/p>","publishtime":1551255925000,"username":"郭佳殷"},{"id":319,"title":"测试公告发布","content":"<p>测试公告发布<\/p><p><b><\/b><i><\/i><u><\/u><sub><\/sub><sup><\/sup><strike><\/strike><br><\/p>","publishtime":1551255031000,"username":"李婵婵"},{"id":318,"title":"RepairTime","content":"<p><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><\/p>","publishtime":1551254830000,"username":"郭佳殷"}],"totalRecord":4,"pageSize":8,"totalPage":1,"currentPage":1,"previousPage":1,"nextPage":1,"pageBar":[1],"startIndex":0,"endIndex":4}
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
         * list : [{"id":321,"title":"测试公告发布","content":"<p>测试websocket<\/p>","publishtime":1551666995000,"username":"郭佳殷"},{"id":320,"title":"测试公告发布","content":"<p>ab/lab/pay<\/p>","publishtime":1551255925000,"username":"郭佳殷"},{"id":319,"title":"测试公告发布","content":"<p>测试公告发布<\/p><p><b><\/b><i><\/i><u><\/u><sub><\/sub><sup><\/sup><strike><\/strike><br><\/p>","publishtime":1551255031000,"username":"李婵婵"},{"id":318,"title":"RepairTime","content":"<p><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><a href=\"http://169.254.85.180:8080/RepairTime/user/index\" style=\"color: rgb(255, 255, 255); margin-right: 10px; display: inline-block; background-repeat: no-repeat; background-position: left center; background-size: auto 100%; font-size: 16px; height: 44px; line-height: 44px; background-color: rgb(34, 34, 34);\">RepairTime<\/a><\/p>","publishtime":1551254830000,"username":"郭佳殷"}]
         * totalRecord : 4
         * pageSize : 8
         * totalPage : 1
         * currentPage : 1
         * previousPage : 1
         * nextPage : 1
         * pageBar : [1]
         * startIndex : 0
         * endIndex : 4
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

        public static class ListBean {
            /**
             * id : 321
             * title : 测试公告发布
             * content : <p>测试websocket</p>
             * publishtime : 1551666995000
             * username : 郭佳殷
             */

            private int id;
            private String title;
            private String content;
            private long publishtime;
            private String username;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getPublishtime() {
                return publishtime;
            }

            public void setPublishtime(long publishtime) {
                this.publishtime = publishtime;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }

    @Override
    public String toString() {
        return "NoticeEntity{" +
                "pages=" + pages +
                '}'+super.toString();
    }
}
