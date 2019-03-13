package com.muzi.repairtime.entity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ProjectListEntity {

    /**
     * listName : firsts
     * pages : [{"id":1,"repair_fir":"硬件"},{"id":2,"repair_fir":"软件"},{"id":3,"repair_fir":"网络"}]
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
         * id : 1
         * repair_fir : 硬件
         */

        private int id;
        private String repair_fir;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRepair_fir() {
            return repair_fir;
        }

        public void setRepair_fir(String repair_fir) {
            this.repair_fir = repair_fir;
        }
    }
}
