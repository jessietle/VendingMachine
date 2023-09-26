package com.techelevator;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    private Action actionTest;

    @BeforeEach
    void setUp() {
        this.actionTest = new Action();
    }

    @Test
    public void feed_money(){
        String expected ="Current amount: $10";
        String actual = actionTest.feedAmount("10");
        assertEquals(expected,actual);
    }

    @Test
    public void calculateChange_for_100_cents() {
        String actual = actionTest.calculateChange(100);
        String expected ="Your change includes 4 quarters, 0 dimes, and 0 nickels.";
        assertEquals(expected,actual);
    }
    @Test
    public void calculateChange_130cents() {
        String actual = actionTest.calculateChange(130);
        String expected = "Your change includes 5 quarters, 0 dimes, and 1 nickels.";
        assertEquals(expected, actual);
    }
    @Test
    public void calculateChange_140cents() {
        String actual = actionTest.calculateChange(140);
        String expected = "Your change includes 5 quarters, 1 dimes, and 1 nickels.";
        assertEquals(expected, actual);
    }
    @Test
    public void test_purchase(){}

}