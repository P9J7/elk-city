package club.p9j7.model;

import java.io.Serializable;

public class AreaDataCount implements Serializable {
    private String areaName;
    private Long count;

    public AreaDataCount(String areaName, Long count) {
        this.areaName = areaName;
        this.count = count;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AreaDataCount{" +
                "areaName='" + areaName + '\'' +
                ", count=" + count +
                '}';
    }
}
