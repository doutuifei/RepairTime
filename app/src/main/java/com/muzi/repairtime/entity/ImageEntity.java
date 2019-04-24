package com.muzi.repairtime.entity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/4/22
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ImageEntity {

    /**
     * listName : imagePathes
     * pages : [{"id":70,"o_id":183,"path":"E:/repairtime/images/orders/20190422/心血管内科/183/d321db963d1541b5a4d7177bd2b929e3_1555921992354.form-data","virtualPath":"/files/20190422/心血管内科/183/d321db963d1541b5a4d7177bd2b929e3_1555921992354.form-data"},{"id":71,"o_id":183,"path":"E:/repairtime/images/orders/20190422/心血管内科/183/34d27153ef5446cd88d209a402eed879_1555921992382.form-data","virtualPath":"/files/20190422/心血管内科/183/34d27153ef5446cd88d209a402eed879_1555921992382.form-data"},{"id":72,"o_id":183,"path":"E:/repairtime/images/orders/20190422/心血管内科/183/c6a402b8996a49b3ae18b4ac59f57bc3_1555921992415.form-data","virtualPath":"/files/20190422/心血管内科/183/c6a402b8996a49b3ae18b4ac59f57bc3_1555921992415.form-data"}]
     */

    private String listName;
    private List<PagesBean> pages;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<PagesBean> getPages() {
        return pages;
    }

    public void setPages(List<PagesBean> pages) {
        this.pages = pages;
    }

    public static class PagesBean {
        /**
         * id : 70
         * o_id : 183
         * path : E:/repairtime/images/orders/20190422/心血管内科/183/d321db963d1541b5a4d7177bd2b929e3_1555921992354.form-data
         * virtualPath : /files/20190422/心血管内科/183/d321db963d1541b5a4d7177bd2b929e3_1555921992354.form-data
         */

        private int id;
        private int o_id;
        private String virtualPath;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getO_id() {
            return o_id;
        }

        public void setO_id(int o_id) {
            this.o_id = o_id;
        }

        public String getVirtualPath() {
            return virtualPath;
        }

        public void setVirtualPath(String virtualPath) {
            this.virtualPath = virtualPath;
        }
    }
}
