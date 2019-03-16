package club.p9j7.model;

import java.text.DecimalFormat;

public class Houseprice {
    final static DecimalFormat df = new DecimalFormat("#.00");

    public Houseprice() {
    }

    public Houseprice(String code, Double price) {
        this.code = code;
        this.price = Double.valueOf(df.format(price));
    }

    /**

     */
    private Long id;
    /**

     */
    private String code;
    /**

     */
    private Double price;
    /**

     */
    private java.util.Date date;

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
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }
}
