package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;



public class Action {
    List<Item> inventoryList = new ArrayList<>();
    Map<String,SalesReport> salesReportMap = new HashMap<>();
    CustomerAccount account = new CustomerAccount();
    Scanner input = new Scanner(System.in);
    Menu menu = new Menu();

    int transactionCounter = 0;

    Log log;
    public void initialize(List<Item> items, Map<String,SalesReport> sales) {
        inventoryList = items;
        salesReportMap = sales;
        // run menu first time

        String option = menu.displayMainMenu();
        firstDisplayMenu(option);
    }

    public void firstDisplayMenu(String option ){
        if (option.equals("1")) {
            // display vending machine items
            displayInventoryList(inventoryList);

            // print the menu again to get new option
            String newOption = menu.displayMainMenu();

            // call itself again with a new option in the main menu.
            firstDisplayMenu(newOption);
        } else if (option.equals("2")) {
            String secondMenuChoice = menu.displaySecondMenu(account);
            // call second helper to run the second menu
            secondDisplayMenu(secondMenuChoice);
        }else  if( option.equals("3")) {
            System.out.println("THANK YOU AND HAVE A GREAT DAY");

        }else if(option.equals("4")) {
            printSalesReport();

        }else{
            System.out.println("INVALID INPUT.PLEASE CHOOSE IT AGAIN");
            String newOption = menu.displayMainMenu();
            firstDisplayMenu(newOption);
        }
    }

    public void secondDisplayMenu(String secondMenuChoice) {
        if(secondMenuChoice.equals("1")){
            feedMoney();
        } else if(secondMenuChoice.equals("2")) {
            purchase();
        } else if(secondMenuChoice.equals("3")){
            giveChange();
        }else{
            System.out.println("INVALID INPUT.PLEASE CHOOSE IT AGAIN");
            String newOption = menu.displaySecondMenu(account);
            secondDisplayMenu(newOption);
        }
    }
    public void displayInventoryList(List<Item> list){
        for(Item eachItem: list){
            System.out.println(eachItem.getItemCode() + " " + eachItem.getItemName() + " " +
                    eachItem.getItemPrice() + " " + eachItem.getItemType() + " " + eachItem.getItemQuantity());
        }
    }

    public void feedMoney(){
        System.out.println("How much do you want to feed? ");

        String feedAmount = input.nextLine();
        feedAmount(feedAmount);
        String newOption = menu.displaySecondMenu(account);
        secondDisplayMenu(newOption);
    }
    public String feedAmount(String feedAmount){
        account.setCurrentAmount(new BigDecimal(feedAmount).add(account.getCurrentAmount()));
        String currentBalance ="Current amount: $" + account.getCurrentAmount();
        System.out.println(currentBalance);

        log = new Log("FEED MONEY", new BigDecimal(feedAmount).setScale(2),
                account.getCurrentAmount().setScale(2));
        log.displayLog();
        //get new option for second menu and re-run

       return currentBalance;
    }

    public void purchase(){
        displayInventoryList(inventoryList);
        System.out.println("Please enter a code: ");
        String codeName = input.nextLine();

        // break code here
        boolean doesExist = purchaseHelper(codeName);

        // for invalid condition
        if (!doesExist) {
            System.out.println("INVALID CODE");
        }
        String newOption = menu.displaySecondMenu(account);
        secondDisplayMenu(newOption);
    }

    public boolean purchaseHelper(String codeName) {
        BigDecimal remainingBalance = account.getCurrentAmount();
        boolean result = false;

        // using for-loop int i to update quantity each time customer purchase
        for (int i = 0; i < inventoryList.size(); i++) {
            //get the target item from inventory list
            Item target = inventoryList.get(i);

            if (target.getItemCode().equalsIgnoreCase(codeName) && remainingBalance.compareTo(BigDecimal.ZERO) > 0) {     //using equalsIgnoreCase to ignore case sensitive
                //  set it to true if the code is valid
                result = true;

                if (target.getItemQuantity() == 0) {
                    System.out.println(" ITEM SOLD OUT");
                } else {
                    transactionCounter++;
                    boolean isDiscount = false;
                    if (transactionCounter % 2 == 0 && LocalDate.now().getMonthValue() == 8) {
                        remainingBalance = remainingBalance.add(BigDecimal.ONE).subtract(target.getItemPrice());
                        isDiscount = true;
                    } else {
                        //item name, cost, money, quantity remaining
                        remainingBalance = remainingBalance.subtract(target.getItemPrice());
                    }

                    if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
                        remainingBalance = (remainingBalance.add((target.getItemPrice())));
                        System.out.println("OUT OF MONEY, CANNOT MAKE PURCHASE. PLEASE ADD MORE MONEY");
                    } else {
                        // update HashMap for sale report
                        SalesReport sale = salesReportMap.get(target.getItemCode());
                        if(isDiscount){
                            sale.setSellAtDiscountQuantity(sale.getSellAtDiscountQuantity()+1);
                        }else{
                            sale.setSellAtFullPriceQuantity(sale.getSellAtFullPriceQuantity()+1);
                        }

                        // update quantity after finish purchasing and set the target with its index
                        target.setItemQuantity(target.getItemQuantity() - 1);
                        inventoryList.set(i, target);


                        // update account balance after finish purchasing
                        account.setCurrentAmount(remainingBalance);
                        System.out.println(target.getItemName() + " $" + target.getItemPrice() +
                                " $" + remainingBalance);

                        log = new Log(target, account.getCurrentAmount());
                        log.displayLog();

                        //message
                        if (target.getItemType().equals("Drink")) {
                            System.out.println("Glug Glug, Yum!");
                        } else if (target.getItemType().equals("Munchy")) {
                            System.out.println("Crunch Crunch, Yum!");
                        } else if (target.getItemType().equals("Gum")) {
                            System.out.println("Chew Chew, Yum!");
                        } else if (target.getItemType().equals("Candy")) {
                            System.out.println("Yummy Yummy, So Sweet!");
                        }
                    }
                }

                // INSIDE if clause (will stop the for loop if it's able to find the corrected code
                break;
            }
        }
        return result;
    }
    public String giveChange(){
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        String returnChange ="";
        String centsString = account.getCurrentAmount().multiply(BigDecimal.valueOf(100)).toString();
        centsString = centsString.replace(".00", "");
        int cents = Integer.parseInt(centsString);
        System.out.println("THANK YOU FOR YOUR PURCHASE.");
        if(cents > 0){
           calculateChange(cents);
        }
        account.setCurrentAmount(BigDecimal.ZERO);

        //go back to main menu
        String option = menu.displayMainMenu();
        firstDisplayMenu(option);
        return returnChange;
    }
    public String calculateChange(int cents){
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        String returnChange ="";
        quarters = cents / 25; //370 cents (14 quarters)
        cents = cents - (quarters * 25); //20 cents
        dimes = cents / 10; //2
        cents = cents - (dimes * 10); //0
        nickels = cents / 5;
        returnChange = "Your change includes " + quarters + " quarters, " + dimes + " dimes, and "
                + nickels + " nickels.";
        System.out.println(returnChange);
        System.out.println();
        log = new Log("GIVE CHANGE", account.getCurrentAmount(), BigDecimal.valueOf(0, 2));
        log.displayLog();
        return returnChange;
    }
        //method to print sale report
    public void printSalesReport()  {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh_mm_ss");

        File salesReport = new File("saleReport"+ " on " +formatter.format((date)) + ".txt");

        double totalSales = 0;

        try(PrintWriter dataOutput = new PrintWriter(salesReport)){
            for(Map.Entry<String,SalesReport> report : salesReportMap.entrySet()){
                double itemPrice = report.getValue().getItemPrice().doubleValue();
                int fullPriceQty = report.getValue().getSellAtFullPriceQuantity();
                int discountPriceQty = report.getValue().getSellAtDiscountQuantity();
                totalSales =totalSales+ (itemPrice*fullPriceQty) +((itemPrice-1)*discountPriceQty);
                dataOutput.println(report.getValue().toString());

            }dataOutput.println("** TOTAL SALES**" + totalSales);

        }catch( FileNotFoundException e){
            e.printStackTrace();
        }
    }


}
