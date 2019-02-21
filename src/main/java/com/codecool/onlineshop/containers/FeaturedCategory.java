package com.codecool.onlineshop.containers;

import java.util.Date;

public class FeaturedCategory extends Category {
    private Date expirationDate;
    private int percent;

    public FeaturedCategory(String name, Date expirationDate, boolean isAvailable, int percent, int id) {
        super(id, name, isAvailable);
        this.expirationDate = expirationDate;
        this.percent = percent;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
    public int getPercent() { return percent; }
}
