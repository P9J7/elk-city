package club.p9j7.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Document(indexName = "aqi", type = "aqi")
public class Aqi {
    @Id
    private Integer id;
    @Field(type = FieldType.Integer)
    private Integer aqi;
    @Field(type = FieldType.Keyword)
    private Quality quality;
    @Field(type = FieldType.Integer)
    private Integer pm2_5;
    @Field(type = FieldType.Integer)
    private Integer pm10;
    @Field(type = FieldType.Integer)
    private Integer so2;
    @Field(type = FieldType.Double)
    private BigDecimal co;
    @Field(type = FieldType.Integer)
    private Integer no2;
    @Field(type = FieldType.Integer)
    private Integer o3;
    @Field(type = FieldType.Date, format = DateFormat.date)
    private String time_point;
    @Field(type = FieldType.Keyword)
    private String city;

    public Aqi(Integer id, Integer aqi, Quality quality, Integer pm2_5, Integer pm10, Integer so2, BigDecimal co, Integer no2, Integer o3, String time_point, String city) {
        this.id = id;
        this.aqi = aqi;
        this.quality = quality;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.so2 = so2;
        this.co = co;
        this.no2 = no2;
        this.o3 = o3;
        this.time_point = time_point;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime_point() {
        return time_point;
    }

    public void setTime_point(String time_point) {
        this.time_point = time_point;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aqi() {

    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Integer getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(Integer pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public Integer getPm10() {
        return pm10;
    }

    public void setPm10(Integer pm10) {
        this.pm10 = pm10;
    }

    public Integer getSo2() {
        return so2;
    }

    public void setSo2(Integer so2) {
        this.so2 = so2;
    }

    public BigDecimal getCo() {
        return co;
    }

    public void setCo(BigDecimal co) {
        this.co = co;
    }

    public Integer getNo2() {
        return no2;
    }

    public void setNo2(Integer no2) {
        this.no2 = no2;
    }

    public Integer getO3() {
        return o3;
    }

    public void setO3(Integer o3) {
        this.o3 = o3;
    }

    @Override
    public String toString() {
        return "Aqi{" +
                "id=" + id +
                ", aqi=" + aqi +
                ", quality=" + quality +
                ", pm2_5=" + pm2_5 +
                ", pm10=" + pm10 +
                ", so2=" + so2 +
                ", co=" + co +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", time_point=" + time_point +
                ", city='" + city + '\'' +
                '}';
    }
}
