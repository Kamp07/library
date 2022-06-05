package com.sferum.restapp.library;

import java.util.ArrayList;
import java.util.List;

public class Market {

    private Long id;
    private Book book;
    private int price;
    private Long amount;

    public Market(Long id, Book book, int price, Long amount) {
        this.id = id;
        this.book = book;
        this.price = price;
        this.amount = amount;
    }

    public Market() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
