package com.sferum.restapp.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    void getBalanceTest() {
        int actualBalance = controller.getAccount().getBalance();

        int expectedBalance = 30000;

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void authorInId0_BrusEkkel() {
        Book actualBook = controller.userShop.getProducts().get(0).getBook();
        String actualAuthor = actualBook.getAuthor();

        Book expectedBook = new Book("Философия Java","Брюс Эккель");
        String expectedAuthor = expectedBook.getAuthor();

        assertEquals(expectedAuthor, actualAuthor);
    }
}