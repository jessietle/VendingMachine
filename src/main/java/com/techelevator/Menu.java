package com.techelevator;

import java.util.Scanner;

public class Menu {
    public String displayMainMenu(){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the vending Machine");
        System.out.println("Please choose from the menu");
        System.out.println(1+ ") Display Vending Machine Items\n" +
                2 +") Purchase\n" +
                3 +  ") Exit");
        String option = userInput.nextLine();
        return option;
    }

    public String displaySecondMenu(CustomerAccount account){

        Scanner userInput = new Scanner(System.in);
        System.out.println("Current money provided: $" + account.getCurrentAmount());
        System.out.println();
        System.out.println("(1) Feed Money\n" +
                "(2) Select Product\n" +
                "(3) Finish Transaction");
        String input = userInput.nextLine();
        return input;
    }

}
