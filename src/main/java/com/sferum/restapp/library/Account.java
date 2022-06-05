package com.sferum.restapp.library;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;


public class Account {

    private Book book;
    private Long amount;



    public Account(Book book, Long amount) {
        this.book = book;
        this.amount = amount;
    }

    public Account() {
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


}
