package com.techelevator;

import java.math.BigDecimal;
import java.util.List;

public class SalesReport {
    private String itemName;
    private int sellAtFullPriceQuantity;
    private int sellAtDiscountQuantity;
    private BigDecimal itemPrice;
    List<Integer> exerciseScore;
    public SalesReport(String itemName, BigDecimal itemPrice) {
        this.itemName = itemName;
        this.sellAtDiscountQuantity =0;
        this.sellAtFullPriceQuantity =0;
        this.itemPrice = itemPrice;
        }


    public int getSellAtFullPriceQuantity() {
        return sellAtFullPriceQuantity;
    }

    public void setSellAtFullPriceQuantity(int sellAtFullPriceQuantity) {
        this.sellAtFullPriceQuantity = sellAtFullPriceQuantity;
    }

    public int getSellAtDiscountQuantity() {
        return sellAtDiscountQuantity;
    }

    public void setSellAtDiscountQuantity(int sellAtDiscountQuantity) {
        this.sellAtDiscountQuantity = sellAtDiscountQuantity;
    }
    public BigDecimal getItemPrice(){
        return itemPrice;
    }

    public String toString(){
        return this.itemName +" | " + this.sellAtFullPriceQuantity +" | " + this.sellAtDiscountQuantity;
    }

}
