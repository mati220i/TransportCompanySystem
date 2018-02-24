package pl.TransportCompanySystem.Server.dao;

import pl.TransportCompanySystem.Server.oracle.ConnectToDatabase;
import pl.TransportCompanySystem.Tables.Location;
import pl.TransportCompanySystem.Tables.Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class PackageDAO {

	public static Integer countAllPackage() throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT count(*) FROM package";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Integer countPackage(String size) throws SQLException, ClassNotFoundException {
		String selectStmt = "select count(*) from package where packageSize = '" + size + "'";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Integer countPackageComplaint(String withWithout) throws SQLException, ClassNotFoundException {
		String selectStmt;
		if (withWithout.equals("With"))
			selectStmt = "select count(*) from package where WITHCOMPLAINT = 1";
		else
			selectStmt = "select count(*) from package where WITHCOMPLAINT = 0";

		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Integer countPrice(Integer price) throws SQLException, ClassNotFoundException {
		String selectStmt;
		if (price != 0) {
			if (price == 10)
				selectStmt = "select count(*) from package where PRICE < " + price;
			else
				selectStmt = "select count(*) from package where PRICE > " + price;
		} else
			selectStmt = "select count(*) from package where PRICE > 10 and PRICE < 20";

		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Double countAveragePrice() throws SQLException, ClassNotFoundException {
		String selectStmt = "select Round(avg(PRICE),2) from package";
		try {
			ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);
			if (resultLog.next())
				return resultLog.getDouble("Round(avg(PRICE),2)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	public static ArrayList<Package> searchPackages(Integer userID) throws SQLException, ClassNotFoundException {
		String selectStmt = "";
		if (userID > 0)
			selectStmt = "SELECT * FROM package where userID=" + userID;
		else
			selectStmt = "SELECT * FROM package";

		ArrayList<Package> packageList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			packageList = getPackageList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packageList;
	}

	public static ArrayList<Package> getPackageList(ResultSet rs) throws SQLException {
		ArrayList<Package> packageList = new ArrayList<Package>();

		while (rs.next()) {
			Package pack = new Package();
			pack.setPackageID(rs.getInt("packageID"));
			pack.setUserID(rs.getInt("userID"));
			pack.setCourierID(rs.getInt("courierID"));
			pack.setLocationID(rs.getInt("locationID"));

			pack.setPackageSize(rs.getString("packageSize"));
			pack.setPackageWeight(rs.getDouble("packageWeight"));

			int complaint = rs.getInt("withComplaint");
			boolean complaintB = false;
			if (complaint == 1)
				complaintB = true;

			pack.setWithComplaint(complaintB);
			pack.setPrice(rs.getDouble("price"));
			pack.setStatus(rs.getString("status"));
			pack.setPaymentMethod(rs.getString("paymentMethod"));
			pack.setRecipientName(rs.getString("recipientName"));
			pack.setRecipientSurname(rs.getString("recipientSurname"));
			pack.setRecipientAddress(rs.getString("recipientAddress"));

			packageList.add(pack);
		}
		return packageList;
	}

	public static String searchPackageStatus(Integer packageID) throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT status FROM package where packageID=" + packageID;
		String status = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			status = getPackageStatus(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	private static String getPackageStatus(ResultSet rs) throws SQLException {
		String status = null;
		if (rs.next()) {
			status = rs.getString("status");
		}
		return status;
	}

	public static void addPackage(Package pack) throws SQLException, ClassNotFoundException {
		Integer userID, courierID, locationID;
		String packageSize, status, paymentMethod, recipientName, recipientSurname, recipientAddress;
		Double packageWeight, price;
		boolean complaint;

		userID = pack.getUserID();
		courierID = pack.getCourierID();
		locationID = pack.getLocationID();
		packageSize = pack.getPackageSize();
		packageWeight = pack.getPackageWeight();
		complaint = pack.getWithComplaint();
		price = pack.getPrice();
		status = pack.getStatus();
		paymentMethod = pack.getPaymentMethod();
		recipientName = pack.getRecipientName();
		recipientSurname = pack.getRecipientSurname();
		recipientAddress = pack.getRecipientAddress();

		Integer complaintI = 0;
		if (complaint)
			complaintI = 1;

		String stmt = "insert into package values(packageSeq.nextval," + userID + "," + courierID + "," + locationID
				+ ",'" + packageSize + "'," + packageWeight + "," + complaintI + "," + price + ",'" + status + "','"
				+ paymentMethod + "','" + recipientName + "','" + recipientSurname + "','" + recipientAddress + "')";
		try {
			ConnectToDatabase.executeUpdate(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void editPackageData(Package pack) throws SQLException, ClassNotFoundException {
		String packageSize, recipientName, recipientSurname, recipientAddress, status, paymentMethod;
		Integer packageID, courierID, locationID, complaint;
		Double packageWeight, price;

		packageID = pack.getPackageID();
		courierID = pack.getCourierID();
		locationID = pack.getLocationID();
		packageSize = pack.getPackageSize();
		packageWeight = pack.getPackageWeight();

		if (pack.getWithComplaint())
			complaint = 1;
		else
			complaint = 0;

		price = pack.getPrice();
		status = pack.getStatus();
		paymentMethod = pack.getPaymentMethod();
		recipientName = pack.getRecipientName();
		recipientSurname = pack.getRecipientSurname();
		recipientAddress = pack.getRecipientAddress();

		String updateStmt = "update package set courierID=" + courierID + ", locationID=" + locationID
				+ ", packageSize='" + packageSize + "', packageWeight=" + packageWeight + ", withComplaint=" + complaint
				+ ", price=" + price + ", status='" + status + "', paymentMethod='" + paymentMethod
				+ "', recipientName='" + recipientName + "', recipientSurname='" + recipientSurname
				+ "', recipientAddress='" + recipientAddress + "' where packageID =" + packageID;

		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deletePackage(Integer id) throws SQLException, ClassNotFoundException {
		String selectStmt = "delete from package where packageID =" + id;
		try {
			ConnectToDatabase.executeUpdate(selectStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void transferPackage(Integer oldLocationID, Integer newLocationID)
			throws SQLException, ClassNotFoundException {

		Location loc = LocationDAO.getLocation(newLocationID);

		ArrayList<Package> packList = searchPackages(0);

		Iterator<Package> it = packList.iterator();

		while (it.hasNext()) {
			Package p = it.next();
			if (p.getLocationID().equals(oldLocationID)) {
				Date currentDate = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				String dateString = "[" + dateFormat.format(currentDate) + "] ";

				String newStatus = p.getStatus() + "\n" + dateString + " Paczka została nadana do oddziału w "
						+ loc.getLocationName() + "\n";

				String updateStmt = "update package set locationID=" + newLocationID + ", status='" + newStatus
						+ "' where locationID =" + oldLocationID;

				try {
					ConnectToDatabase.executeUpdate(updateStmt);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static ArrayList<Package> searchLocationPackages(Integer locationID)
			throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM package where locationID=" + locationID;

		ArrayList<Package> packageList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			packageList = getPackageList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packageList;
	}

	public static ArrayList<Package> searchCourierPackages(Integer courierID)
			throws SQLException, ClassNotFoundException {
		String selectStmt = "SELECT * FROM package where courierID=" + courierID;

		ArrayList<Package> packageList = null;

		try {
			ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);
			packageList = getPackageList(resultOferty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packageList;
	}

	public static void editPackageCourier(Integer packageID) throws SQLException, ClassNotFoundException {
		String updateStmt = "update package set courierID=" + 2 + " where packageID =" + packageID;

		try {
			ConnectToDatabase.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
