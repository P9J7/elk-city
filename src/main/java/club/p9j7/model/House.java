package club.p9j7.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "lianjiaelk", type = "house")
public class House {
    //房屋id

    private Long id;
    //链家id

    private String code;
    //链家网址
    private String url;

    private String title;

    private String subtitle;
    //关注人数
    private Integer favcount = 0 ;

    private Integer cartcount = 0;
    //总价
    private Double price;
    //单价
    private Double unitprice;

    private Double firstPayPrice;

    private Double taxPrice;

    private String roomMainInfo;

    private String roomSubInfo;

    private String roomMainType;

    private String roomSubType;

    private String areaMainInfo;

    private String areaSubInfo;
    //小区名称
    private String communityName;
    //所在区域
    private String areaName;

    private String schoolName;
    //房源标签

    private String tags;
    //装修描述

    private String decoratingDesc;
    //

    private String houseTypeDesc;

    private String investmentDesc;
    //周边配套

    private String villageDesc;

    private String schoolDesc;
    //核心卖点

    private String sellingPointDesc;

    private String reason4saleDesc;

    private String supportingDesc;
    //交通出行

    private String trafficDesc;

    private java.util.Date createtime;

    //基本属性
    private String huxing;

    private String louceng;

    private String jianzhumianji;

    private String huxingjiegou;

    private String taoneimianji;

    private String jianzhuleixing;

    private String fangwuchaoxiang;

    private String jianzhujiegou;

    private String zhuangxiuqingkuang;

    private String tihubili;

    private String peibeidianti;

    private String chanquannianxian;

    //交易属性
    private String guapaishijian;

    private String jiaoyishuxing;

    private String shangcijiaoyi;

    private String fangwuyongtu;

    private String fangwunianxian;

    private String chanquansuoshu;

    private String diyaxinxi;

    private String fangbenbeijian;

    private String transactionContent9;

    private String transactionContent10;

    @JSONField(serialize=false)

    private  String html;

    private  double roomSize;
    /**
     * 0:未处理；1:在售；2:已成交；-1:已经下架；-2：信息异常；-301：找不到
     */

    private Integer status;

    private Double chengjiaoPrice;

    private Date chengjiaoDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public Integer getFavcount() {
        return favcount;
    }

    public void setFavcount(Integer favcount) {
        this.favcount = favcount;
    }
    public Integer getCartcount() {
        return cartcount;
    }

    public void setCartcount(Integer cartcount) {
        this.cartcount = cartcount;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getFirstPayPrice() {
        return firstPayPrice;
    }

    public void setFirstPayPrice(Double firstPayPrice) {
        this.firstPayPrice = firstPayPrice;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getRoomMainInfo() {
        return roomMainInfo;
    }

    public void setRoomMainInfo(String roomMainInfo) {
        this.roomMainInfo = roomMainInfo;
    }
    public String getRoomSubInfo() {
        return roomSubInfo;
    }

    public void setRoomSubInfo(String roomSubInfo) {
        this.roomSubInfo = roomSubInfo;
    }
    public String getRoomMainType() {
        return roomMainType;
    }

    public void setRoomMainType(String roomMainType) {
        this.roomMainType = roomMainType;
    }
    public String getRoomSubType() {
        return roomSubType;
    }

    public void setRoomSubType(String roomSubType) {
        this.roomSubType = roomSubType;
    }
    public String getAreaMainInfo() {
        return areaMainInfo;
    }

    public void setAreaMainInfo(String areaMainInfo) {
        this.areaMainInfo = areaMainInfo;
    }
    public String getAreaSubInfo() {
        return areaSubInfo;
    }

    public void setAreaSubInfo(String areaSubInfo) {
        this.areaSubInfo = areaSubInfo;
    }
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getDecoratingDesc() {
        return decoratingDesc;
    }

    public void setDecoratingDesc(String decoratingDesc) {
        this.decoratingDesc = decoratingDesc;
    }
    public String getHouseTypeDesc() {
        return houseTypeDesc;
    }

    public void setHouseTypeDesc(String houseTypeDesc) {
        this.houseTypeDesc = houseTypeDesc;
    }
    public String getInvestmentDesc() {
        return investmentDesc;
    }

    public void setInvestmentDesc(String investmentDesc) {
        this.investmentDesc = investmentDesc;
    }
    public String getVillageDesc() {
        return villageDesc;
    }

    public void setVillageDesc(String villageDesc) {
        this.villageDesc = villageDesc;
    }
    public String getSchoolDesc() {
        return schoolDesc;
    }

    public void setSchoolDesc(String schoolDesc) {
        this.schoolDesc = schoolDesc;
    }
    public String getSellingPointDesc() {
        return sellingPointDesc;
    }

    public void setSellingPointDesc(String sellingPointDesc) {
        this.sellingPointDesc = sellingPointDesc;
    }
    public String getReason4saleDesc() {
        return reason4saleDesc;
    }

    public void setReason4saleDesc(String reason4saleDesc) {
        this.reason4saleDesc = reason4saleDesc;
    }
    public String getSupportingDesc() {
        return supportingDesc;
    }

    public void setSupportingDesc(String supportingDesc) {
        this.supportingDesc = supportingDesc;
    }
    public String getTrafficDesc() {
        return trafficDesc;
    }

    public void setTrafficDesc(String trafficDesc) {
        this.trafficDesc = trafficDesc;
    }
    public java.util.Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(java.util.Date createtime) {
        this.createtime = createtime;
    }

    public String getHuxing() {
        return huxing;
    }

    public void setHuxing(String huxing) {
        this.huxing = huxing;
    }

    public String getLouceng() {
        return louceng;
    }

    public void setLouceng(String louceng) {
        this.louceng = louceng;
    }

    public String getJianzhumianji() {
        return jianzhumianji;
    }

    public void setJianzhumianji(String jianzhumianji) {
        this.jianzhumianji = jianzhumianji;
    }

    public String getHuxingjiegou() {
        return huxingjiegou;
    }

    public void setHuxingjiegou(String huxingjiegou) {
        this.huxingjiegou = huxingjiegou;
    }

    public String getTaoneimianji() {
        return taoneimianji;
    }

    public void setTaoneimianji(String taoneimianji) {
        this.taoneimianji = taoneimianji;
    }

    public String getJianzhuleixing() {
        return jianzhuleixing;
    }

    public void setJianzhuleixing(String jianzhuleixing) {
        this.jianzhuleixing = jianzhuleixing;
    }

    public String getFangwuchaoxiang() {
        return fangwuchaoxiang;
    }

    public void setFangwuchaoxiang(String fangwuchaoxiang) {
        this.fangwuchaoxiang = fangwuchaoxiang;
    }

    public String getJianzhujiegou() {
        return jianzhujiegou;
    }

    public void setJianzhujiegou(String jianzhujiegou) {
        this.jianzhujiegou = jianzhujiegou;
    }

    public String getZhuangxiuqingkuang() {
        return zhuangxiuqingkuang;
    }

    public void setZhuangxiuqingkuang(String zhuangxiuqingkuang) {
        this.zhuangxiuqingkuang = zhuangxiuqingkuang;
    }

    public String getTihubili() {
        return tihubili;
    }

    public void setTihubili(String tihubili) {
        this.tihubili = tihubili;
    }

    public String getPeibeidianti() {
        return peibeidianti;
    }

    public void setPeibeidianti(String peibeidianti) {
        this.peibeidianti = peibeidianti;
    }

    public String getChanquannianxian() {
        return chanquannianxian;
    }

    public void setChanquannianxian(String chanquannianxian) {
        this.chanquannianxian = chanquannianxian;
    }

    public String getGuapaishijian() {
        return guapaishijian;
    }

    public void setGuapaishijian(String guapaishijian) {
        this.guapaishijian = guapaishijian;
    }

    public String getJiaoyishuxing() {
        return jiaoyishuxing;
    }

    public void setJiaoyishuxing(String jiaoyishuxing) {
        this.jiaoyishuxing = jiaoyishuxing;
    }

    public String getShangcijiaoyi() {
        return shangcijiaoyi;
    }

    public void setShangcijiaoyi(String shangcijiaoyi) {
        this.shangcijiaoyi = shangcijiaoyi;
    }

    public String getFangwuyongtu() {
        return fangwuyongtu;
    }

    public void setFangwuyongtu(String fangwuyongtu) {
        this.fangwuyongtu = fangwuyongtu;
    }

    public String getFangwunianxian() {
        return fangwunianxian;
    }

    public void setFangwunianxian(String fangwunianxian) {
        this.fangwunianxian = fangwunianxian;
    }

    public String getChanquansuoshu() {
        return chanquansuoshu;
    }

    public void setChanquansuoshu(String chanquansuoshu) {
        this.chanquansuoshu = chanquansuoshu;
    }

    public String getDiyaxinxi() {
        return diyaxinxi;
    }

    public void setDiyaxinxi(String diyaxinxi) {
        this.diyaxinxi = diyaxinxi;
    }

    public String getFangbenbeijian() {
        return fangbenbeijian;
    }

    public void setFangbenbeijian(String fangbenbeijian) {
        this.fangbenbeijian = fangbenbeijian;
    }

    public String getTransactionContent9() {
        return transactionContent9;
    }

    public void setTransactionContent9(String transactionContent9) {
        this.transactionContent9 = transactionContent9;
    }
    public String getTransactionContent10() {
        return transactionContent10;
    }

    public void setTransactionContent10(String transactionContent10) {
        this.transactionContent10 = transactionContent10;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public double getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(double roomSize) {
        this.roomSize = roomSize;
    }

    public Double getChengjiaoPrice() {
        return chengjiaoPrice;
    }

    public void setChengjiaoPrice(Double chengjiaoPrice) {
        this.chengjiaoPrice = chengjiaoPrice;
    }

    public Date getChengjiaoDate() {
        return chengjiaoDate;
    }

    public void setChengjiaoDate(Date chengjiaoDate) {
        this.chengjiaoDate = chengjiaoDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return code + url + title + subtitle;
    }
}
