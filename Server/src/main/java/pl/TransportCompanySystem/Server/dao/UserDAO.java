package pl.TransportCompanySystem.Server.dao;

import pl.TransportCompanySystem.Server.oracle.ConnectToDatabase;
import pl.TransportCompanySystem.Tables.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

	public static User checkLoginAndPassword(User user) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT userID, name, surname, login, password, address, accountType FROM users WHERE login= '"
				+ user.getLogin() + "' AND password= '" + user.getPassword() + "'";

		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			user = getIdFromLogin(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	private static User getIdFromLogin(ResultSet rs) throws SQLException {
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setUserID(rs.getInt("userID"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setAddress(rs.getString("address"));
			user.setAccountType(rs.getString("accountType"));
			System.out.println("Type: " + user.getAccountType());
		}
		return user;
	}

	public static void editUserData(User user) throws SQLException, ClassNotFoundException {
		String login, password, name, surname, address;
		Integer userID;

		userID = user.getUserID();
		login = user.getLogin();
		password = user.getPassword();
		name = user.getName();
		surname = user.getSurname();
		address = user.getAddress();

		String updateStmt = "update users set name='" + name + "', surname='" + surname + "', login='" + login
				+ "', password='" + password + "', address='" + address + "' where userid =" + userID;

		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Integer countAllAccount(String accountType) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT count(*) FROM users where accountType = '" + accountType + "'";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static User getUser(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM users WHERE userID=" + id;
		User user = null;
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			user = getUserFromDB(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	private static User getUserFromDB(ResultSet rs) throws SQLException {
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setUserID(rs.getInt("userID"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setAddress(rs.getString("address"));
			user.setAccountType(rs.getString("accountType"));
		}
		return user;
	}

	public static ArrayList<User> searchUsers(String accountType) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM users where accountType='" + accountType + "'";

		ArrayList<User> usersList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			usersList = getUsersList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}

	public static ArrayList<User> getUsersList(ResultSet rs) throws SQLException {
		ArrayList<User> usersList = new ArrayList<User>();

		while (rs.next()) {
			User user = new User();

			user.setUserID(rs.getInt("userID"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setAddress(rs.getString("address"));
			user.setAccountType(rs.getString("accountType"));

			usersList.add(user);
		}
		return usersList;
	}

	public static void deleteUser(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "delete from users where userID =" + id;
		String updateStmt = "update package set userID=" + 2 + " where userID =" + id;
		try {
			ConnectToDatabase.executeUpdate(updateStmt);
			ConnectToDatabase.executeUpdate(selectStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
