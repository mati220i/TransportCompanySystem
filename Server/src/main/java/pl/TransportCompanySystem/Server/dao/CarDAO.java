package pl.TransportCompanySystem.Server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.TransportCompanySystem.Server.oracle.ConnectToDatabase;
import pl.TransportCompanySystem.Tables.Car;

public class CarDAO {
	public static Integer countAllCars() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT count(*) FROM cars";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Integer countAllTypeCars(String type) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT count(*) FROM cars where carType='" + type + "'";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Car getCar(Integer carID) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM cars where carID=" + carID;
		Car car = null;

		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			car = getCarFromDB(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}

	private static Car getCarFromDB(ResultSet rs) throws SQLException {
		Car car = null;
		if (rs.next()) {
			car = new Car();

			car.setCarID(rs.getInt("carID"));
			car.setCarType(rs.getString("carType"));

			Integer available = rs.getInt("isAvailable");
			boolean isAvailable = false;

			if (available == 0)
				isAvailable = false;
			else
				isAvailable = true;

			car.setAvailable(isAvailable);
		}

		return car;
	}

	public static ArrayList<Car> searchCars() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM cars";

		ArrayList<Car> carsList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			carsList = getCarsList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carsList;
	}

	public static ArrayList<Car> getCarsList(ResultSet rs) throws SQLException {
		ArrayList<Car> carsList = new ArrayList<Car>();

		while (rs.next()) {
			Car car = new Car();

			car.setCarID(rs.getInt("carID"));
			car.setCarType(rs.getString("carType"));

			Integer available = rs.getInt("isAvailable");
			Boolean isAvailable = false;

			if (available == 0)
				isAvailable = false;
			else
				isAvailable = true;

			car.setAvailable(isAvailable);

			carsList.add(car);
		}
		return carsList;
	}

	public static void editCarStatus(Integer carID, boolean availability) throws SQLException, ClassNotFoundException {
		Integer isAvailable;

		if (availability)
			isAvailable = 1;
		else
			isAvailable = 0;

		String updateStmt = "update cars set isAvailable=" + isAvailable + " where carID =" + carID;

		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteCar(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "delete from cars where carID =" + id;
		String updateStmt = "update couriers set carID=" + 1 + " where carID =" + id;
		try {
			ConnectToDatabase.executeUpdate(updateStmt);
			ConnectToDatabase.executeUpdate(selectStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addCar(Car car) throws SQLException, ClassNotFoundException {
		String carType;

		carType = car.getCarType();

		String insertStmt = "insert into cars values(carSeq.nextval,'" + carType + "', 1)";
		try {
			ConnectToDatabase.executeUpdate(insertStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
