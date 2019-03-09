/**
* ATMSystem class to provide ATM and Bank functionality such as login and withdraw
* @author Rabia Mohiuddin
* @version 1.0 3/5/19 
* */

import java.util.ArrayList;
import java.util.Scanner;

public class ATMSystem {
	static ArrayList<Bank> Banks = new ArrayList<Bank>();
	static ArrayList<ATM> ATMs = new ArrayList<ATM>();

	/**
	 * Prints all ATMs and their transaction limit currently in the system
	 */
	public static void printATMData() {
		// Iterate through ATMs and print data
		for (ATM atm : ATMs) {
			atm.printATMData();
			System.out.println();
		}
	}

	/**
	 * Prints all customers in each Bank currently in the system
	 */
	public static void printAllBanksCustomers() {
		// Iterate through Banks and print customer accounts associated
		for (Bank bank : Banks) {
			bank.printAllCustomerAccounts();
			System.out.println();
		}
	}

	/**
	 * Finds ATM in the system using the atmID 
	 * @param atmID - atm ID string
	 * @return ATM - ATM object associated with atmID
	 */
	public static ATM findATM(String atmID) {
		// Iterate through ATMs and find one associated with given ATM ID
		for (ATM atm : ATMs) {
			if (atm.getAtmID().equals(atmID)) {
				return atm;
			}
		}
		return null;
	}

	/**
	 * Finds Bank in the system using the bankID 
	 * @param bankID - bank ID string
	 * @return Bank - Bank object associated with bankID
	 */
	public static Bank findBank(String bankID) {
		// Iterate through Banks and find one associated with given bank ID
		for (Bank bank : Banks) {
			if (bank.getBankID().equals(bankID)) {
				return bank;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// Initialize Bank and ATM IDs
		final String bank1ID = "111";
		final String bank2ID = "222";
		final String bank1ATM1ID = "ATM" + bank1ID + "-1";
		final String bank1ATM2ID = "ATM" + bank1ID + "-2";
		final String bank2ATM1ID = "ATM" + bank2ID + "-1";
		final String bank2ATM2ID = "ATM" + bank2ID + "-2";

		// Create two Banks
		Bank bank1 = new Bank(bank1ID);
		Bank bank2 = new Bank(bank2ID);

		// Add Banks to ArrayList of Banks in ATM Network System
		Banks.add(bank1);
		Banks.add(bank2);

		// Add ATMs to ArrayList of ATMs in ATM Network System
		ATMs.add(new ATM(bank1ID, bank1ATM1ID, 2500.00));
		ATMs.add(new ATM(bank1ID, bank1ATM2ID, 5000.00));
		ATMs.add(new ATM(bank2ID, bank2ATM1ID, 1500.00));
		ATMs.add(new ATM(bank2ID, bank2ATM2ID, 10000.00));

		// Add customer Accounts to Banks
		bank1.addAccount(new Account(bank1ID + "-000001", "1cust1", 100000.00));
		bank1.addAccount(new Account(bank1ID + "-000002", "1cust2", 56700.00));
		bank1.addAccount(new Account(bank1ID + "-000003", "1cust3", 3400.00));
		bank1.addAccount(new Account(bank1ID + "-000004", "1cust4", 1000.00));
		bank1.addAccount(new Account(bank1ID + "-000005", "1cust5", 1000000.00));

		bank2.addAccount(new Account(bank2ID + "-000001", "2cust1", 20000.00));
		bank2.addAccount(new Account(bank2ID + "-000002", "2cust2", 56900.00));
		bank2.addAccount(new Account(bank2ID + "-000003", "2cust3", 3900.00));
		bank2.addAccount(new Account(bank2ID + "-000004", "2cust4", 5000.00));
		bank2.addAccount(new Account(bank2ID + "-000005", "2cust5", 5000000.00));

		System.out.println("Welcome to the ATM Network System!");
		System.out.println("By Rabia Mohiuddin [CS 151 - Spring 2019]");

		printAllBanksCustomers();

		System.out.println("\nATMs Available");
		printATMData();

		Scanner sc = new Scanner(System.in);
		boolean ATMOpen = true;

		while (ATMOpen) {
			try {
				System.out.println("Chose an ATM: ");
				String atmID = sc.nextLine(); // Read user input

				if (atmID.equals("quit")) {
					ATMOpen = false;
					break;
				}

				// Find requested ATM
				ATM atm = findATM(atmID);
				if (atm == null) {
					throw new IllegalArgumentException("ATM not found");
				}

				// Find Account
				System.out.println("Enter your card number");
				String cardNum = sc.nextLine(); // Read user input
				Account userAccount = atm.validateAccount(cardNum);

				// If no Account returned, account does not exist or card is expired
				if (userAccount == null) {
					throw new IllegalArgumentException("Card not found or is expired");
				}

				// Validate password - gets one try then goes back to picking an ATM
				System.out.println("Authorizing card");
				System.out.println("Enter your password");
				String password = sc.nextLine(); // Read user input
				try {
					if (!atm.verifyPassword(userAccount, password)) {
						throw new IllegalArgumentException("Invalid Password. Try Again");
					}
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage() + "\n");
					password = sc.nextLine(); // Read user input
					if (!atm.verifyPassword(userAccount, password)) {
						throw new IllegalArgumentException("Invalid Password.");
					}
				}

				System.out.println("Authorizing accepted");

				// Loop to keep withdrawing until user enters quit
				boolean withdraw = true;
				while (withdraw) {
					System.out.println();
					System.out.println("Current Balance is $" + String.format("%.02f", userAccount.getBalance()));
					System.out.println("How much would you like to withdraw?");
					String amount = sc.nextLine(); // Read user input
					if (amount.equals("quit")) {
						withdraw = false;
						ATMOpen = false;
						break;
					}

					try {
						// Convert amount to number
						double dAmount = Double.valueOf(amount);
						// Withdraw from Account
						atm.withdraw(userAccount, dAmount);

						// Amount must be a number
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage() + "\n");
						System.out.println("Invalid amount");
					} catch (ArithmeticException e) {
						// Over Transaction limit or account balance
						System.out.println(e.getMessage() + "\n");
						System.out.println("Please enter a new amount");
					}
				}

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage() + "\n");
			}

		}

		sc.close();
		// Goodbye
		System.out.println("Thank you for visiting the 151 ATM Network System!");
		System.out.println("We hope to see you again soon!");
	}
}
