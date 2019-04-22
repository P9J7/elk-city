package club.p9j7.model;

import java.io.Serializable;
import java.util.Map;

public class AqiResultContent<T> implements Serializable {
    private String cityName;
    private Integer cityCount;
    private Map<String, T> qualityMap;

    public AqiResultContent(String cityName, Integer cityCount, Map<String, T> qualityMap) {
        this.cityName = cityName;
        this.cityCount = cityCount;
        this.qualityMap = qualityMap;
    }

    public AqiResultContent(String cityName, Integer cityCount) {
        this.cityName = cityName;
        this.cityCount = cityCount;
    }

    public AqiResultContent() {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityCount() {
        return cityCount;
    }

    public void setCityCount(Integer cityCount) {
        this.cityCount = cityCount;
    }

    public Map<String, T> getQualityMap() {
        return qualityMap;
    }

    public void setQualityMap(Map<String, T> qualityMap) {
        this.qualityMap = qualityMap;
    }

    @Override
    public String toString() {
        return "AqiResultContent{" +
                "cityName='" + cityName + '\'' +
                ", cityCount=" + cityCount +
                ", qualityMap=" + qualityMap +
                '}';
    }
}
