package model;

import java.util.Objects;

public class Client {
	private final Integer id;
	private String pass;
	private String email;
	private String name;
	private String lastname; 
	private final String dni;
	private Account account;

	public Client(Integer id, String dni,String pass, String email) {
		this.id = id;
		this.dni = dni;
		this.pass = pass;
		this.email = email;
	//	account = new Account();
	}

	public Integer getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDni() {
		return dni;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


		
}
