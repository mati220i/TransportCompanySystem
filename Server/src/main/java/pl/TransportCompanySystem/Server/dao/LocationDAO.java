package pl.TransportCompanySystem.Server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.TransportCompanySystem.Server.oracle.ConnectToDatabase;
import pl.TransportCompanySystem.Tables.Location;

public class LocationDAO {

	public static ArrayList<Location> searchLocation() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM location";
		ArrayList<Location> locationList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			locationList = getLocationList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locationList;
	}

	public static ArrayList<Location> getLocationList(ResultSet rs) throws SQLException {
		ArrayList<Location> locationList = new ArrayList<Location>();

		while (rs.next()) {
			Location location = new Location();

			location.setLocationID(rs.getInt("locationID"));
			location.setLocationName(rs.getString("locationName"));

			locationList.add(location);
		}
		return locationList;
	}

	public static Integer searchLocationID(String locationName) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT locationID FROM location where locationName='" + locationName + "'";
		Integer locationID = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			locationID = getLocationID(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locationID;
	}

	private static Integer getLocationID(ResultSet rs) throws SQLException {
		Integer locationID = null;
		if (rs.next()) {
			locationID = rs.getInt("locationID");
		}
		return locationID;
	}

	public static Integer countAllLocation() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT count(*) FROM location";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Location getLocation(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM location WHERE locationID=" + id;
		Location location = null;
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			location = getLocationFromDB(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return location;
	}

	public static Location getLocationS(String name) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM location WHERE locationName='" + name + "'";
		Location location = null;
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			location = getLocationFromDB(resultLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return location;
	}

	private static Location getLocationFromDB(ResultSet rs) throws SQLException {
		Location locaion = null;
		if (rs.next()) {
			locaion = new Location();

			locaion.setLocationID(rs.getInt("locationID"));
			locaion.setLocationName(rs.getString("locationName"));
		}
		return locaion;
	}

	public static void editLocation(Location location) throws SQLException, ClassNotFoundException {
		String locationName;
		Integer locationID;

		locationID = location.getLocationID();
		locationName = location.getLocationName();

		String updateStmt = "update location set locationName='" + locationName + "' where locationID =" + locationID;

		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addLocation(Location location) throws SQLException, ClassNotFoundException {
		String name = location.getLocationName();

		String insertStmt = "insert into location values(locationSeq.nextval,'" + name + "')";
		try {
			ConnectToDatabase.executeUpdate(insertStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteLocation(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "delete from location where locationID =" + id;
		try {
			ConnectToDatabase.executeUpdate(selectStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
