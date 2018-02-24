package pl.TransportCompanySystem.Tables;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -400608359838122706L;
	private Integer userID;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String address;
	private String accountType;
	private static Integer id;

	public User() {
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static Integer getId() {
		return id;
	}

	public static void setId(Integer id) {
		User.id = id;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", login=" + login + ", password=" + password + ", name=" + name
				+ ", surname=" + surname + ", address=" + address + ", accountType=" + accountType + "]";
	}

}
