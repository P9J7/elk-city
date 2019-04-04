package club.p9j7.model;

import java.time.LocalDate;

public class Aqi {
    private Integer id;
    private Integer aqi;
    private Quality quality;
    private Integer pm2_5;
    private Integer pm10;
    private Integer so2;
    private Integer co;
    private Integer no2;
    private Integer o3;
    private LocalDate timePoint;

    public Aqi(Integer id, Integer aqi, Quality quality, Integer pm2_5, Integer pm10, Integer so2, Integer co, Integer no2, Integer o3, LocalDate timePoint) {
        this.id = id;
        this.aqi = aqi;
        this.quality = quality;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.so2 = so2;
        this.co = co;
        this.no2 = no2;
        this.o3 = o3;
        this.timePoint = timePoint;
    }

    public LocalDate getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(LocalDate timePoint) {
        this.timePoint = timePoint;
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

    public Integer getCo() {
        return co;
    }

    public void setCo(Integer co) {
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
                ", timePoint=" + timePoint +
                '}';
    }
}
