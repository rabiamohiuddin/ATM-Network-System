/**
* Bank class to store and keep track of user Accounts
* @author Rabia Mohiuddin
* @version 1.0 3/5/19 
* */

import java.util.ArrayList;

public class Bank {
	private String bankID;
	private ArrayList<Account> customerAccounts = new ArrayList<Account>();

	/**
	 * Creates bank using unique bank ID
	 * @param bankID - bank identifier
	 */
	public Bank(String bankID) {
		this.bankID = bankID;
	}

	/**
	 * Adds Account to Bank customer accounts
	 * @param acc - Account want to add to Bank
	 */
	public void addAccount(Account acc) {
		customerAccounts.add(acc);
	}

	/**
	 * Retrieves Bank ID
	 * @return bankID
	 */
	public String getBankID() {
		return bankID;
	}

	/**
	 * Retrieves Account given account number
	 * @param accountNum
	 * @return Account associated with account number
	 */
	public Account getAccount(String accountNum) {
		// Iterate through customer accounts to find one with given account number
		for (Account acc : customerAccounts) {
			if (acc.getCard().getCardNum().equals(accountNum)) {
				return acc;
			}
		}
		return null;
	}

	/**
	 * Print all customer accounts held by Bank currently
	 */
	public void printAllCustomerAccounts() {
		System.out.println("Bank " + getBankID() + " Accounts");
		for (Account acc : customerAccounts) {
			acc.printAccount();
		}
	}

}
