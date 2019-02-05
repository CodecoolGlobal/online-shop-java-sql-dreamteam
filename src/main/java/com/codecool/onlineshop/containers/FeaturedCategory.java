package com.codecool.onlineshop.containers;

import java.util.Date;

public class FeaturedCategory extends Category {
    private Date expirationDate;


    public FeaturedCategory(String name, Date expirationDate) {
        super(name);
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
