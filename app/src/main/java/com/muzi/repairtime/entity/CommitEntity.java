package com.muzi.repairtime.entity;

/**
 * 作者: lipeng
 * 时间: 2019/4/18
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class CommitEntity extends BaseEntity {

    /**
     * obj : {"id":151,"reportgroup":"心血管内科","pic":"刘明阳","reporter":"AA","reporterphone":"11111111111","repair_fir":"软件","repair_sec":"LIS","reporttime":null,"problem":"0.0","acceptordertime":null,"repairer":null,"repairerphone":null,"orderstatus":null,"status_id":0,"finishtime":null,"consumersatisfaction":null,"cs_id":null}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 151
         * reportgroup : 心血管内科
         * pic : 刘明阳
         * reporter : AA
         * reporterphone : 11111111111
         * repair_fir : 软件
         * repair_sec : LIS
         * reporttime : null
         * problem : 0.0
         * acceptordertime : null
         * repairer : null
         * repairerphone : null
         * orderstatus : null
         * status_id : 0
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
        private Object reporttime;
        private String problem;
        private Object acceptordertime;
        private Object repairer;
        private Object repairerphone;
        private Object orderstatus;
        private int status_id;
        private Object finishtime;
        private Object consumersatisfaction;
        private Object cs_id;

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

        public Object getReporttime() {
            return reporttime;
        }

        public void setReporttime(Object reporttime) {
            this.reporttime = reporttime;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public Object getAcceptordertime() {
            return acceptordertime;
        }

        public void setAcceptordertime(Object acceptordertime) {
            this.acceptordertime = acceptordertime;
        }

        public Object getRepairer() {
            return repairer;
        }

        public void setRepairer(Object repairer) {
            this.repairer = repairer;
        }

        public Object getRepairerphone() {
            return repairerphone;
        }

        public void setRepairerphone(Object repairerphone) {
            this.repairerphone = repairerphone;
        }

        public Object getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(Object orderstatus) {
            this.orderstatus = orderstatus;
        }

        public int getStatus_id() {
            return status_id;
        }

        public void setStatus_id(int status_id) {
            this.status_id = status_id;
        }

        public Object getFinishtime() {
            return finishtime;
        }

        public void setFinishtime(Object finishtime) {
            this.finishtime = finishtime;
        }

        public Object getConsumersatisfaction() {
            return consumersatisfaction;
        }

        public void setConsumersatisfaction(Object consumersatisfaction) {
            this.consumersatisfaction = consumersatisfaction;
        }

        public Object getCs_id() {
            return cs_id;
        }

        public void setCs_id(Object cs_id) {
            this.cs_id = cs_id;
        }
    }

}
