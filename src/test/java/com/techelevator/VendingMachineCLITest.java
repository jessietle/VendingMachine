package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineCLITest {
    private VendingMachineCLI vendingMachineCLI;
    List<Item> actual = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.vendingMachineCLI = new VendingMachineCLI();
    }

    @Test
    void populateInventoryList_Test_Code() {
        actual = vendingMachineCLI.populateInventoryList(new File("main.csv"));
        String actualCode = actual.get(0).getItemCode();
        String expectedCode ="A1";
        assertEquals(expectedCode,actualCode);
    }
    @Test
    void populateInventoryList_Test_Name() {
        actual = vendingMachineCLI.populateInventoryList(new File("main.csv"));
        String actualCode = actual.get(1).getItemName();
        String expectedCode = "Ginger Ayle";
        assertEquals(expectedCode, actualCode);
    }
    @Test
    void populateInventoryList_Test_Price() {
        actual = vendingMachineCLI.populateInventoryList(new File("main.csv"));
        BigDecimal actualCode = actual.get(1).getItemPrice();
        BigDecimal expectedCode = BigDecimal.valueOf(1.85);
        assertEquals(expectedCode, actualCode);
    }
    @Test
    void populateInventoryList_Test_Type() {
        actual = vendingMachineCLI.populateInventoryList(new File("main.csv"));
        String actualCode = actual.get(1).getItemType();
        String expectedCode = "Drink" ;
        assertEquals(expectedCode, actualCode);
    }
}