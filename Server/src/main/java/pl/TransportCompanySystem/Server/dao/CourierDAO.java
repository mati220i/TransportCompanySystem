package pl.TransportCompanySystem.Server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.TransportCompanySystem.Server.oracle.ConnectToDatabase;
import pl.TransportCompanySystem.Tables.Courier;

public class CourierDAO {
	public static Integer countAllCouriers() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT count(*) FROM couriers";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Integer getMinSalary() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT min(salary) FROM couriers";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("min(salary)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Integer getMaxSalary() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT max(salary) FROM couriers";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("max(salary)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Courier getCourier(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM couriers WHERE courierID=" + id;
		Courier courier = null;
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			courier = getCourierFromDB(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courier;
	}

	private static Courier getCourierFromDB(ResultSet rs) throws SQLException {
		Courier courier = null;
		if (rs.next()) {
			courier = new Courier();

			courier.setCourierID(rs.getInt("courierID"));
			courier.setCarID(rs.getInt("carID"));
			courier.setLogin(rs.getString("login"));
			courier.setPassword(rs.getString("password"));
			courier.setName(rs.getString("name"));
			courier.setSurname(rs.getString("surname"));
			courier.setSalary(rs.getInt("salary"));
			courier.setAccountType(rs.getString("accountType"));
		}
		return courier;
	}

	public static ArrayList<Courier> searchCouriers() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM couriers";

		ArrayList<Courier> courierList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			courierList = getCouriersList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courierList;
	}

	public static ArrayList<Courier> getCouriersList(ResultSet rs) throws SQLException {
		ArrayList<Courier> couriersList = new ArrayList<Courier>();

		while (rs.next()) {
			Courier courier = new Courier();

			courier.setCourierID(rs.getInt("courierID"));
			courier.setCarID(rs.getInt("carID"));
			courier.setLogin(rs.getString("login"));
			courier.setPassword(rs.getString("password"));
			courier.setName(rs.getString("name"));
			courier.setSurname(rs.getString("surname"));
			courier.setSalary(rs.getInt("salary"));
			courier.setAccountType(rs.getString("accountType"));

			couriersList.add(courier);
		}
		return couriersList;
	}

	public static void editCourier(Courier courier) throws SQLException, ClassNotFoundException {
		String login, password, name, surname;
		Integer courierID, carID, salary;

		courierID = courier.getCourierID();
		login = courier.getLogin();
		password = courier.getPassword();
		name = courier.getName();
		surname = courier.getSurname();
		salary = courier.getSalary();
		carID = courier.getCarID();

		String updateStmt = "update couriers set name='" + name + "', surname='" + surname + "', login='" + login
				+ "', password='" + password + "', salary=" + salary + ", carID=" + carID + " where courierID ="
				+ courierID;

		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteCourier(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "delete from couriers where courierID =" + id;
		String updateStmt = "update package set courierID=" + 1 + " where courierID =" + id;
		try {
			ConnectToDatabase.executeUpdate(updateStmt);
			ConnectToDatabase.executeUpdate(selectStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addCourier(Courier courier) throws SQLException, ClassNotFoundException {
		String login, password, name, surname;
		Integer carID, salary;

		login = courier.getLogin();
		password = courier.getPassword();
		name = courier.getName();
		surname = courier.getSurname();
		carID = courier.getCarID();
		salary = courier.getSalary();

		String insertStmt = "insert into couriers values(courierSeq.nextval, " + carID + ", '" + login + "', '"
				+ password + "', '" + name + "', '" + surname + "', " + salary + ", 'Courier')";
		try {
			ConnectToDatabase.executeUpdate(insertStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Courier checkLoginAndPassword(Courier courier) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT courierID, carID, login, password, name, surname, salary, accountType FROM couriers WHERE login= '"
				+ courier.getLogin() + "' AND password= '" + courier.getPassword() + "'";

		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			courier = getIdFromLogin(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courier;
	}

	private static Courier getIdFromLogin(ResultSet rs) throws SQLException {
		Courier courier = null;
		if (rs.next()) {
			courier = new Courier();

			courier.setCourierID(rs.getInt("courierID"));
			courier.setCarID(rs.getInt("carID"));
			courier.setLogin(rs.getString("login"));
			courier.setPassword(rs.getString("password"));
			courier.setName(rs.getString("name"));
			courier.setSurname(rs.getString("surname"));
			courier.setSalary(rs.getInt("salary"));
			courier.setAccountType(rs.getString("accountType"));
			System.out.println("Type: " + courier.getAccountType());
		}
		return courier;
	}

}
