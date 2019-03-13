package com.muzi.repairtime.entity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ProjectItemEntity {

    /**
     * listName : pages
     * pages : [{"id":4,"repair_sec":"HIS","repair_fir_id":2},{"id":5,"repair_sec":"LIS","repair_fir_id":2},{"id":6,"repair_sec":"PACS","repair_fir_id":2}]
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
         * id : 4
         * repair_sec : HIS
         * repair_fir_id : 2
         */

        private int id;
        private String repair_sec;
        private int repair_fir_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRepair_sec() {
            return repair_sec;
        }

        public void setRepair_sec(String repair_sec) {
            this.repair_sec = repair_sec;
        }

        public int getRepair_fir_id() {
            return repair_fir_id;
        }

        public void setRepair_fir_id(int repair_fir_id) {
            this.repair_fir_id = repair_fir_id;
        }
    }

}
