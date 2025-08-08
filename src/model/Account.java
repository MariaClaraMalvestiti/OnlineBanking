package model;

import exceptions.IllegalAccountAmountException;
import java.math.BigDecimal;

public class Account {
	long cbu;
	private String type; 
	Double balance;
	// llamar a metodo que genera cbu
	// generar excepciones en los metodos en lugar de carteles
	public Account() {
	}
	
	public Account(long cbu, String type, Double balance) {
		this.cbu = cbu;
		this.type = type;
		this.balance = balance;
	}

	public Double deposit(double value) {		
		this.balance += value;
		return this.balance;
	}
	
	public Double withdraw(double value) {
		if(this.balance - value > 0) { 
			this.balance -= value;
			return this.balance;
		}
		
		return this.balance;
	}
	
	public String getType() { //USD, ARGS
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCbu() {
		return cbu;
	}

	public void setCbu(long cbu) {
		this.cbu = cbu;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) throws IllegalAccountAmountException {
		if(balance < 0) {
			throw new IllegalAccountAmountException();
		}
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [cbu=" + cbu + ", type=" + type + ", balance=" + balance + "]";
	}
	
}
