package pl.TransportCompanySystem.Tables;

import java.io.Serializable;

public class Courier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer courierID;
	private Integer carID;
	private String login;
	private String password;
	private String name;
	private String surname;
	private Integer salary;
	private String accountType;

	public Integer getCourierID() {
		return courierID;
	}

	public void setCourierID(Integer courierID) {
		this.courierID = courierID;
	}

	public Integer getCarID() {
		return carID;
	}

	public void setCarID(Integer carID) {
		this.carID = carID;
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

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String userType) {
		this.accountType = userType;
	}

}
