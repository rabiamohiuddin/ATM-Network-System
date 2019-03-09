/**
* Account class to manage funds and account data such as expiration, balance, and CashCard
* @author Rabia Mohiuddin
* @version 1.0 3/5/19 
* */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Account {
	private String accountNum;
	private String password;
	private double balance = 0;
	private CashCard card = null;

	/**
	 * Creates Account given account number, password, and balance 
	 * @param accountNum - unique account number 
	 * @param password - password to access account 
	 * @param balance - amount of money in account
	 */
	public Account(String accountNum, String password, double balance) {
		this.accountNum = accountNum;
		this.password = password;
		this.balance = balance;
		this.card = new CashCard();
	}

	/**
	 * Retrieves Account number 
	 * @return account number
	 */
	public String getAccountNum() {
		return accountNum;
	}

	/**
	 * Retrieves CashCard object associated with Account
	 * @return CashCard
	 */
	public CashCard getCard() {
		return card;
	}

	/**
	 * Retrieves password associated with Account
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Retrieves balance in Account
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Set's balance to amount given
	 * @param balance - amount of money in account
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Withdraws given amount from Account as long as under balance
	 * @param dollars - amount of money to withdraw
	 */
	public void withdraw(double dollars) {
		// As long as enough balance in account, withdraw funds
		if (dollars <= balance) {
			balance -= dollars;
			System.out.println("Transaction successful! $" + String.format("%.02f", dollars) + " deducted from account.");
			System.out.println("New balance is $" + String.format("%.02f", getBalance()));
		} else {
			// Print error message and throw Exception
			System.out.println("Transaction could not be completed.");
			throw new ArithmeticException("Not enough balance");
		}
	}

	/**
	 * Checks if entered password is correct
	 * @param pass - password entered by user
	 * @return boolean if password is correct or not
	 */
	public boolean verifyPassword(String pass) {
		// If equal, return true
		if (pass.equals(this.password)) {
			return true;
		}
		return false;
	}

	/**
	 * Print Account data (Account number, password, expiration date)
	 */
	public void printAccount() {
		System.out.println("Account #: " + getAccountNum() + ", Password: " + getPassword() + ", Expiration Date: " + getCard().getExpiration().format(DateTimeFormatter.ofPattern("d MMMM YYYY")));
	}

	/**
	* CashCard class to keep track of expiration date
	* @author Rabia Mohiuddin
	* @version 1.0 3/5/19 
	* */
	public class CashCard {
		private LocalDate expiration = null;

		/**
		 * Creates CashCard with expiration 10 years from when created
		 */
		public CashCard() {
			this.expiration = LocalDate.now().plusYears(10);
		}

		/**
		 * Retrieves card number (same as account number)
		 * @return account number
		 */
		public String getCardNum() {
			return accountNum;
		}

		/**
		 * Retrieves expiration date
		 * @return aexpiration date (LocalDate)
		 */
		public LocalDate getExpiration() {
			return expiration;
		}

		/**
		 * Renews card for amount of years given
		 * @param years - number of years to extend card
		 */
		public void renewCard(long years) {
			this.expiration.plusYears(years);
		}

	}

}
