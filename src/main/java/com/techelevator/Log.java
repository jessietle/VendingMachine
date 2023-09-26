package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();

    private String action;
    private BigDecimal actionAmount;
    private BigDecimal balanceAfterAction;

    private Item item;

    public Log(String action, BigDecimal actionAmount, BigDecimal balanceAfterAction){
        this.action = action;
        this.actionAmount = actionAmount;
        this.balanceAfterAction = balanceAfterAction;
    }

    public Log(Item item, BigDecimal balanceAfterAction){
        this.item = item;
        this.balanceAfterAction = balanceAfterAction;
    }

    public void displayLog() {
        DateTimeFormatter dateFormatter;
        DateTimeFormatter timeFormatter;
        String displayLine;

        dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        if(this.action != null){
            displayLine = this.date.format(dateFormatter) + " " + this.time.format(timeFormatter) +
                    " " + this.action + ": $" + this.actionAmount + " $" + this.balanceAfterAction;
        }else{
            displayLine = this.date.format(dateFormatter) + " " + this.time.format(timeFormatter) +
                    " " + this.item.getItemName() + " " + this.item.getItemCode() + ": $" +
                    this.item.getItemPrice() + " $" + this.balanceAfterAction;
        }


        File logFile = new File("log.txt");

        if(! logFile.exists()){
            try{
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(logFile, true))){
            writer.println(displayLine);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
