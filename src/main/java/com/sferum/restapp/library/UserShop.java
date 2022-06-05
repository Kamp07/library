package com.sferum.restapp.library;

import java.util.List;

public class UserShop {

    private List<Market> products;

    public UserShop(List<Market> products) {
        this.products = products;
    }

    public UserShop() {
    }

    public List<Market> getProducts() {
        return products;
    }

    public void setProducts(List<Market> products) {
        this.products = products;
    }
}
