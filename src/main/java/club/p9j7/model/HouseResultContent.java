package club.p9j7.model;

import java.io.Serializable;

public class HouseResultContent<String, T> implements Serializable {
    private String name;
    private T count;

    public HouseResultContent(String name, T count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getCount() {
        return count;
    }

    @Override
    public java.lang.String toString() {
        return "HouseResultContent{" +
                "name=" + name +
                ", count=" + count +
                '}';
    }

    public void setCount(T count) {
        this.count = count;
    }
}
