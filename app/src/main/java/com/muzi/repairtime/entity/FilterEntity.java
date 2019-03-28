package com.muzi.repairtime.entity;

/**
 * 作者: lipeng
 * 时间: 2019/3/26
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class FilterEntity {

    private String finishStartTime;

    private String finishEndTime;

    private String reportgroup;

    private String repairer;

    private String orderstatus;

    private String repair_sec;

    private String pic;

    private int currentPage = 1;

    private int totalPage = 1;

    public FilterEntity() {
    }

    public String getFinishStartTime() {
        return finishStartTime;
    }

    public void setFinishStartTime(String finishStartTime) {
        this.finishStartTime = finishStartTime;
    }

    public String getFinishEndTime() {
        return finishEndTime;
    }

    public void setFinishEndTime(String finishEndTime) {
        this.finishEndTime = finishEndTime;
    }

    public String getReportgroup() {
        return reportgroup;
    }

    public void setReportgroup(String reportgroup) {
        this.reportgroup = reportgroup;
    }

    public String getRepairer() {
        return repairer;
    }

    public void setRepairer(String repairer) {
        this.repairer = repairer;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getRepair_sec() {
        return repair_sec;
    }

    public void setRepair_sec(String repair_sec) {
        this.repair_sec = repair_sec;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void nextPage() {
        currentPage++;
    }

}
