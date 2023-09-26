package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

/*
 * This class is provided to you as a *suggested* class to start
 * your project. Feel free to refactor this code as you see fit.
 */
public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };

	static List<Item> inventoryList = new ArrayList<>();
	static Map<String, SalesReport> salesReportMap = new HashMap<>(); //for sale report
	static File inputFile = new File("problem.csv");

	public static void main(String[] args) {
		VendingMachineCLI cli = new VendingMachineCLI();
		inventoryList = cli.populateInventoryList(inputFile);
		Action action = new Action();
		action.initialize(inventoryList,salesReportMap);
	}
	//method to load data and run application first time(should only be run once)
	public List<Item> populateInventoryList(File inputFile) {
		// load data from exist file
		if(inputFile.exists()) {
			try (Scanner inputScanner = new Scanner(inputFile)) {
				// add lineCount to count what line we are on when reading the input file
				int lineCount = 1;
				while (inputScanner.hasNextLine()) {
					String lineInput = inputScanner.nextLine();
					String[] words = lineInput.split(",");

					// a String combined of all possible word for types of item
					String type = "DrinkMunchyGumCandy";

					/*
					We only import the data if it passes all the checks:

					Otherwise, we will print out the warning messing with the line number
					 */
					if (words.length >= 4 && words[0].length() == 2 && words[2].matches("[0-9.]+") && type.contains(words[3])) {
						BigDecimal itemPrice = new BigDecimal(words[2]);
						inventoryList.add(new Item(words[0], words[1], itemPrice, words[3], 5));
						salesReportMap.put(words[0] ,new SalesReport(words[1],itemPrice));
					} else {
						System.out.println("ERROR IN THE INPUT DATA AT LINE #" + lineCount);
					}

					lineCount++;
				}
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return inventoryList;

	}
}
