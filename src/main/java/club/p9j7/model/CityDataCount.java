package club.p9j7.model;

import java.io.Serializable;

public class CityDataCount implements Serializable {
    private String cityName;
    private Integer count;

    public CityDataCount(String cityName, Integer count) {
        this.cityName = cityName;
        this.count = count;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "CityDataCount{" +
                "cityName='" + cityName + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
