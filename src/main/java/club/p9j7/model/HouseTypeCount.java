package club.p9j7.model;

public class HouseTypeCount {
    private String houseType;
    private Integer count;

    public HouseTypeCount(String houseType, Integer count) {
        this.houseType = houseType;
        this.count = count;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "HouseTypeCount{" +
                "houseType='" + houseType + '\'' +
                ", count=" + count +
                '}';
    }
}
