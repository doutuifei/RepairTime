package com.muzi.repairtime.entity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/7
 * 邮箱: lipeng@moyi365.com
 * 功能: 科室
 */
public class GroupEntity {

    /**
     * listName : groups
     * pages : [{"id":4,"name":"心血管内科","pic_id":4},{"id":6,"name":"呼吸、消化内科","pic_id":4},{"id":8,"name":"神内、内分泌科","pic_id":4},{"id":10,"name":"神内、肾内科","pic_id":4},{"id":12,"name":"儿科","pic_id":4},{"id":14,"name":"重症医学科","pic_id":5},{"id":18,"name":"产科","pic_id":4},{"id":19,"name":"产房","pic_id":4},{"id":21,"name":"妇科","pic_id":4},{"id":24,"name":"中医、康复医学科","pic_id":6},{"id":26,"name":"骨科、烧伤科","pic_id":5},{"id":28,"name":"普通外科","pic_id":5},{"id":30,"name":"神外、泌尿外科","pic_id":5},{"id":32,"name":"介入、肿瘤科","pic_id":5},{"id":33,"name":"眼、耳鼻咽喉科","pic_id":4},{"id":35,"name":"手术室、麻醉科","pic_id":5},{"id":36,"name":"皮肤科","pic_id":6},{"id":37,"name":"急诊医学科","pic_id":6},{"id":38,"name":"口腔科","pic_id":6},{"id":39,"name":"透析科","pic_id":4},{"id":40,"name":"新生儿洗浴","pic_id":4},{"id":41,"name":"视光中心","pic_id":6},{"id":42,"name":"眩晕门诊","pic_id":6},{"id":43,"name":"慢性病门诊","pic_id":5},{"id":51,"name":"门诊手术室","pic_id":6},{"id":52,"name":"急救中心","pic_id":6},{"id":54,"name":"病理科","pic_id":4},{"id":55,"name":"介入放射科","pic_id":5},{"id":56,"name":"心电图室","pic_id":6},{"id":57,"name":"B超室","pic_id":2},{"id":58,"name":"激光科","pic_id":6},{"id":59,"name":"医学检验科","pic_id":6},{"id":60,"name":"核磁科","pic_id":6},{"id":61,"name":"放射科","pic_id":6},{"id":62,"name":"CT室","pic_id":6},{"id":63,"name":"胃肠镜室","pic_id":6},{"id":64,"name":"碎石科","pic_id":6},{"id":66,"name":"药剂科","pic_id":2},{"id":67,"name":"供应室","pic_id":5},{"id":68,"name":"体检中心","pic_id":5},{"id":69,"name":"器械科","pic_id":5},{"id":70,"name":"中药房","pic_id":5},{"id":71,"name":"收费处","pic_id":6},{"id":72,"name":"住院处","pic_id":6},{"id":73,"name":"住院药房","pic_id":5},{"id":74,"name":"门诊药房","pic_id":5},{"id":75,"name":"药库","pic_id":5},{"id":76,"name":"总务科","pic_id":5},{"id":77,"name":"财务科","pic_id":2},{"id":78,"name":"党办室","pic_id":2},{"id":79,"name":"农合办","pic_id":6},{"id":80,"name":"质控科","pic_id":2},{"id":81,"name":"药剂会计","pic_id":5},{"id":82,"name":"护理部","pic_id":2},{"id":83,"name":"院办室","pic_id":2},{"id":84,"name":"回访中心","pic_id":2},{"id":85,"name":"科教科","pic_id":2},{"id":86,"name":"消防科","pic_id":5},{"id":87,"name":"病案室","pic_id":5},{"id":88,"name":"信息中心","pic_id":1},{"id":90,"name":"医务科","pic_id":2},{"id":91,"name":"感控科","pic_id":2},{"id":92,"name":"孕妇学校","pic_id":6},{"id":93,"name":"预防保健科","pic_id":2},{"id":95,"name":"监察室","pic_id":2}]
     */

    private List<PagesBean> pages;

    public List<PagesBean> getPages() {
        return pages;
    }

    public void setPages(List<PagesBean> pages) {
        this.pages = pages;
    }

    public static class PagesBean {
        /**
         * id : 4
         * name : 心血管内科
         * pic_id : 4
         */

        private int id;
        private String name;
        private int pic_id;

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

        public int getPic_id() {
            return pic_id;
        }

        public void setPic_id(int pic_id) {
            this.pic_id = pic_id;
        }
    }
}
