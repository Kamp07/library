package com.sferum.restapp.library;

import java.util.List;

public class User {

    private List<Account> books;
    private int balance = 30000;

    public User(List<Account> books) {
        this.books = books;

    }

    public User() {
    }

    public List<Account> getBooks() {
        return books;
    }

    public void setBooks(List<Account> books) {
        this.books = books;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
