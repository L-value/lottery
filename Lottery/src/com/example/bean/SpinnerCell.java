package com.example.bean;



/**
 * Created by apple on 16/3/6.
 */
public class SpinnerCell {
    private String name;
    private String value;

    public SpinnerCell() {
        super();
    }

    public SpinnerCell(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

