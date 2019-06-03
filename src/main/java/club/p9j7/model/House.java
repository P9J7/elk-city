package club.p9j7.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "house", type = "house")
public class House implements Serializable {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String dealYear;

    @Field(type = FieldType.Keyword)
    private String dealMonth;


    @Field(type = FieldType.Keyword)
    private String cityName;

    //链家网址
    @Field(index = false, type = FieldType.Keyword)
    private String url;

    @Field(index = false,type = FieldType.Keyword)
    private String title;

    //关注人数
    @Field(type = FieldType.Integer)
    private Integer favcount = 0 ;

    //总价

    @Field(type = FieldType.Double)
    private Double price;
    //单价

    @Field(type = FieldType.Double)
    private Double unitprice;

    //房型
    @Field(type = FieldType.Keyword)
    private String roomMainInfo;

    //平方面积
    @Field(type = FieldType.Double)
    private Double areaMainInfo;

    //建成年代
    @Field(type = FieldType.Keyword)
    private String areaSubInfo;

    //小区名称
    @Field(type = FieldType.Keyword)
    private String communityName;

    //所在区域
    @Field(type = FieldType.Keyword)
    private String areaName;

    /**
     * 1:在售；2:已成交
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    public String getDealYear() {
        return dealYear;
    }

    public void setDealYear(String dealYear) {
        this.dealYear = dealYear;
    }

    public String getDealMonth() {
        return dealMonth;
    }

    public void setDealMonth(String dealMonth) {
        this.dealMonth = dealMonth;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getFavcount() {
        return favcount;
    }

    public void setFavcount(Integer favcount) {
        this.favcount = favcount;
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

    public String getRoomMainInfo() {
        return roomMainInfo;
    }

    public void setRoomMainInfo(String roomMainInfo) {
        this.roomMainInfo = roomMainInfo;
    }

    public Double getAreaMainInfo() {
        return areaMainInfo;
    }

    public void setAreaMainInfo(Double areaMainInfo) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", dealYear='" + dealYear + '\'' +
                ", dealMonth='" + dealMonth + '\'' +
                ", cityName='" + cityName + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", favcount=" + favcount +
                ", price=" + price +
                ", unitprice=" + unitprice +
                ", roomMainInfo='" + roomMainInfo + '\'' +
                ", areaMainInfo=" + areaMainInfo +
                ", areaSubInfo='" + areaSubInfo + '\'' +
                ", communityName='" + communityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", status=" + status +
                '}';
    }
}
