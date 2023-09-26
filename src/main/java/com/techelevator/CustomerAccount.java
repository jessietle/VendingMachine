package com.techelevator;

import java.math.BigDecimal;

public class CustomerAccount {



    private BigDecimal currentAmount = BigDecimal.ZERO;


    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }
}
