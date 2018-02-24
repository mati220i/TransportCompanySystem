package pl.TransportCompanySystem.Server.dao;

import pl.TransportCompanySystem.Server.oracle.ConnectToDatabase;
import pl.TransportCompanySystem.Tables.Courier;
import pl.TransportCompanySystem.Tables.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration {
	public static User checkLogin(String login) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT login FROM users WHERE login= '" + login + "'";
		System.out.println("Check Login");
		User user = null;
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			user = getLogin(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static Courier checkCourierLogin(String login) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT login FROM couriers WHERE login= '" + login + "'";
		System.out.println("Check Login");
		Courier courier = null;
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			courier = getCourierLogin(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courier;
	}

	private static User getLogin(ResultSet rs) throws SQLException {
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setLogin(rs.getString("login"));
		}
		return user;
	}

	private static Courier getCourierLogin(ResultSet rs) throws SQLException {
		Courier courier = null;
		if (rs.next()) {
			courier = new Courier();
			courier.setLogin(rs.getString("login"));
		}
		return courier;
	}

	public static void addUser(User user) throws SQLException, ClassNotFoundException {
		String login, password, name, surname, address, accountType;

		login = user.getLogin();
		password = user.getPassword();
		name = user.getName();
		surname = user.getSurname();
		address = user.getAddress();
		accountType = user.getAccountType();

		String updateStmt = "insert into users values(userSeq.nextval,'" + name + "','" + surname + "','" + login
				+ "','" + password + "','" + address + "','" + accountType + "')";
		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
