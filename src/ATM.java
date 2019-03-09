/**
* ATM class to provide interaction between User and Bank
* @author Rabia Mohiuddin
* @version 1.0 3/5/19 
* */

import java.time.LocalDate;

public class ATM {
	private String atmID;
	private String bankID;
	private double transactionLimit;
	private Bank bank = null;

	/**
	 * Creates ATM given unique bank ID, unique ATM ID, and transaction limit
	 * @param bankID - unique bank ID
	 * @param atmID - unique atm ID 
	 * @param transactionLimit - max amount of money able to withdraw from ATM
	 */
	public ATM(String bankID, String atmID, double transactionLimit) {
		this.bankID = bankID;
		this.atmID = atmID;
		this.transactionLimit = transactionLimit;
	}

	/**
	 * Retrieves ATM ID 
	 * @return ATM ID
	 */
	public String getAtmID() {
		return atmID;
	}

	/**
	 * Retrieves Bank ID 
	 * @return Bank ID
	 */
	public String getBankID() {
		return bankID;
	}

	/**
	 * Retrieves Bank from ATM Network System using bank ID
	 * @return Bank from ATM Network System
	 */
	public Bank getBank() {
		// If bank hasn't been retrieved yet
		if (bank == null) {
			// Call ATM System static method
			bank = ATMSystem.findBank(bankID);
		}
		return bank;
	}

	/**
	 * Retrieves transaction limit specified by ATM
	 * @return transaction limit
	 */
	public double getTransactionLimit() {
		return transactionLimit;
	}

	/**
	 * Sets transaction limit of ATM
	 * @param transactionLimit - amount able to withdraw at one time from this ATM
	 */
	public void setTransactionLimit(double transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	/**
	 * Prints ATM Data (Transaction limit)
	 */
	public void printATMData() {
		System.out.println(getAtmID());
		System.out.println("      This ATM can withdraw a maximum of " + "$" + String.format("%.02f", getTransactionLimit()) + "\n");
	}

	/**
	 * Checks with associated bank if an account number exists there
	 * @param cardNum - account number 
	 * @return account with given account number
	 */
	public Account checkIfAccountExists(String cardNum) {
		return getBank().getAccount(cardNum);
	}

	/**
	 * Checks if given Account's CashCard is expired
	 * @param a - Account of user
	 * @return boolean whether Account is expired or not
	 */
	public boolean checkExpiration(Account a) {
		// Check if the expiration date passed
		if (a.getCard().getExpiration().isBefore(LocalDate.now())) {
			return false;
		}
		return true;
	}

	/**
	 * Validate account by checking if it exists in bank and its expiration date
	 * @param cardNum - account number 
	 * @return account with given account number
	 */
	public Account validateAccount(String cardNum) {
		Account a = checkIfAccountExists(cardNum);
		if (a != null) {
			if (checkExpiration(a)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Checks password with given account
	 * @param acc - Account trying to verify
	 * @param pass - user entered password
	 * @return boolean returned from Account verifying password
	 */
	public boolean verifyPassword(Account acc, String pass) {
		return acc.verifyPassword(pass);
	}

	/**
	 * Withdraws money from Account as long as within the transaction limit of ATM
	 * @param acc - Account trying to withdraw from
	 * @param amount - amount trying to withdraw	  
	 */
	public void withdraw(Account acc, double amount) {
		// Check if under transaction limit
		if (amount <= getTransactionLimit()) {
			acc.withdraw(amount);
		} else {
			// Error, throw exception
			System.out.println("Transaction could not be completed.");
			throw new ArithmeticException("Over Transaction Limit");
		}
	}

}
