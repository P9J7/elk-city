package club.p9j7.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "lianjiaelk", type = "house")
public class House {
    //房屋id
    private String cityName;

    @Id
    private Long id;
    //链家id

    private String code;
    //链家网址
    private String url;

    private String title;

    private String subtitle;
    //关注人数
    private Integer favcount = 0 ;

    //总价
    private Double price;
    //单价
    private Double unitprice;

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
    /**
     * 0:未处理；1:在售；2:已成交；-1:已经下架；-2：信息异常；-301：找不到
     */

    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "House{" +
                "cityName='" + cityName + '\'' +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", favcount=" + favcount +
                ", price=" + price +
                ", unitprice=" + unitprice +
                ", roomMainInfo='" + roomMainInfo + '\'' +
                ", roomSubInfo='" + roomSubInfo + '\'' +
                ", roomMainType='" + roomMainType + '\'' +
                ", roomSubType='" + roomSubType + '\'' +
                ", areaMainInfo='" + areaMainInfo + '\'' +
                ", areaSubInfo='" + areaSubInfo + '\'' +
                ", communityName='" + communityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", status=" + status +
                '}';
    }
}
