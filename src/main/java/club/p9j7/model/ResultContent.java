package club.p9j7.model;

import java.io.Serializable;

public class ResultContent<String, T> implements Serializable {
    private String name;
    private T count;

    public ResultContent(String name, T count) {
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
        return "ResultContent{" +
                "name=" + name +
                ", count=" + count +
                '}';
    }

    public void setCount(T count) {
        this.count = count;
    }
}
