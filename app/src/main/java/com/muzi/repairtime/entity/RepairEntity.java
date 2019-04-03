package com.muzi.repairtime.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class RepairEntity {

    /**
     * {
     * "listName": "pages",
     * "pages": {
     * "list": [
     * {
     * "id": 83,
     * "reportgroup": "心血管内科",
     * "pic": null,
     * "reporter": "AA",
     * "reporterphone": "11111111111",
     * "repair_fir": "硬件",
     * "repair_sec": "计算机主机",
     * "reporttime": 1552449602000,
     * "problem": "测试一波",
     * "acceptordertime": null,
     * "repairer": null,
     * "repairerphone": null,
     * "orderstatus": "未接单",
     * "status_id": 1,
     * "finishtime": null,
     * "consumersatisfaction": null,
     * "cs_id": null
     * }
     * ],
     * "totalRecord": 38,
     * "pageSize": 8,
     * "totalPage": 5,
     * "currentPage": 1,
     * "previousPage": 1,
     * "nextPage": 2,
     * "pageBar": [
     * 1,
     * 2,
     * 3,
     * 4,
     * 5
     * ],
     * "startIndex": 0,
     * "endIndex": 8
     * }
     * }
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
         * list : [{"id":83,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"硬件","repair_sec":"计算机主机","reporttime":1552449602000,"problem":"测试一波","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":82,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"硬件","repair_sec":"计算机主机","reporttime":1552449218000,"problem":"测试数据","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":81,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"软件","repair_sec":"PACS","reporttime":1551936878000,"problem":"111","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":80,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"硬件","repair_sec":"计算机主机","reporttime":1551936397000,"problem":"1230.0","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":72,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"硬件","repair_sec":"计算机显示器","reporttime":1551662550000,"problem":"测试websocket","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":71,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"软件","repair_sec":"HIS","reporttime":1551602016000,"problem":"aaasssddd","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":63,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"硬件","repair_sec":"计算机显示器","reporttime":1551333268000,"problem":"aaa","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null},{"id":62,"reportgroup":"心血管内科","pic":null,"reporter":"AA","reporterphone":"11111111111","repair_fir":"网络","repair_sec":"内网","reporttime":1551328947000,"problem":"s","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":"未接单","status_id":1,"finishtime":null,"consumersatisfaction":null,"cs_id":null}]
         * totalRecord : 38
         * pageSize : 8
         * totalPage : 5
         * currentPage : 1
         * previousPage : 1
         * nextPage : 2
         * pageBar : [1,2,3,4,5]
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

        public static class ListBean implements MultiItemEntity, Parcelable {
            /**
             * id : 83
             * reportgroup : 心血管内科
             * pic : null
             * reporter : AA
             * reporterphone : 11111111111
             * repair_fir : 硬件
             * repair_sec : 计算机主机
             * reporttime : 1552449602000
             * problem : 测试一波
             * acceptordertime : null
             * repairer : null
             * repairerphone : null
             * orderstatus : 未接单
             * status_id : 1
             * finishtime : null
             * consumersatisfaction : null
             * cs_id : null
             */

            private int id;
            private String reportgroup;
            private String pic;
            private String reporter;
            private String reporterphone;
            private String repair_fir;
            private String repair_sec;
            private long reporttime;
            private String problem;
            private long acceptordertime;
            private String repairer;
            private String repairerphone;
            private String orderstatus;
            private int status_id;
            private long finishtime;
            private String consumersatisfaction;
            private int cs_id = -1;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getReportgroup() {
                return reportgroup;
            }

            public void setReportgroup(String reportgroup) {
                this.reportgroup = reportgroup;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getReporter() {
                return reporter;
            }

            public void setReporter(String reporter) {
                this.reporter = reporter;
            }

            public String getReporterphone() {
                return reporterphone;
            }

            public void setReporterphone(String reporterphone) {
                this.reporterphone = reporterphone;
            }

            public String getRepair_fir() {
                return repair_fir;
            }

            public void setRepair_fir(String repair_fir) {
                this.repair_fir = repair_fir;
            }

            public String getRepair_sec() {
                return repair_sec;
            }

            public void setRepair_sec(String repair_sec) {
                this.repair_sec = repair_sec;
            }

            public long getReporttime() {
                return reporttime;
            }

            public void setReporttime(long reporttime) {
                this.reporttime = reporttime;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public long getAcceptordertime() {
                return acceptordertime;
            }

            public void setAcceptordertime(long acceptordertime) {
                this.acceptordertime = acceptordertime;
            }

            public String getRepairer() {
                return repairer;
            }

            public void setRepairer(String repairer) {
                this.repairer = repairer;
            }

            public String getRepairerphone() {
                return repairerphone;
            }

            public void setRepairerphone(String repairerphone) {
                this.repairerphone = repairerphone;
            }

            public String getOrderstatus() {
                return orderstatus;
            }

            public void setOrderstatus(String orderstatus) {
                this.orderstatus = orderstatus;
            }

            public int getStatus_id() {
                return status_id;
            }

            public void setStatus_id(int status_id) {
                this.status_id = status_id;
            }

            public long getFinishtime() {
                return finishtime;
            }

            public void setFinishtime(long finishtime) {
                this.finishtime = finishtime;
            }

            public String getConsumersatisfaction() {
                return consumersatisfaction;
            }

            public void setConsumersatisfaction(String consumersatisfaction) {
                this.consumersatisfaction = consumersatisfaction;
            }

            public int getCs_id() {
                return cs_id;
            }

            public void setCs_id(int cs_id) {
                this.cs_id = cs_id;
            }

            @Override
            public int getItemType() {
                switch (orderstatus) {
                    case "未接单":
                        return 1;
                    case "已完成":
                        return 3;
                    case "未完成":
                        return 4;
                    case "维修中":
                        return 2;
                }
                return 0;
            }

            public ListBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.reportgroup);
                dest.writeString(this.pic);
                dest.writeString(this.reporter);
                dest.writeString(this.reporterphone);
                dest.writeString(this.repair_fir);
                dest.writeString(this.repair_sec);
                dest.writeLong(this.reporttime);
                dest.writeString(this.problem);
                dest.writeLong(this.acceptordertime);
                dest.writeString(this.repairer);
                dest.writeString(this.repairerphone);
                dest.writeString(this.orderstatus);
                dest.writeInt(this.status_id);
                dest.writeLong(this.finishtime);
                dest.writeString(this.consumersatisfaction);
                dest.writeInt(this.cs_id);
            }

            protected ListBean(Parcel in) {
                this.id = in.readInt();
                this.reportgroup = in.readString();
                this.pic = in.readString();
                this.reporter = in.readString();
                this.reporterphone = in.readString();
                this.repair_fir = in.readString();
                this.repair_sec = in.readString();
                this.reporttime = in.readLong();
                this.problem = in.readString();
                this.acceptordertime = in.readLong();
                this.repairer = in.readString();
                this.repairerphone = in.readString();
                this.orderstatus = in.readString();
                this.status_id = in.readInt();
                this.finishtime = in.readLong();
                this.consumersatisfaction = in.readString();
                this.cs_id = in.readInt();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
