package club.p9j7.model;

public enum City {
    bj("北京"),
    sh("上海"),
    gz("广州"),
    sz("深圳");
    private String value;
    private City(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
