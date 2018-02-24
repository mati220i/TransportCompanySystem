package pl.TransportCompanySystem.Server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.TransportCompanySystem.Server.Controllers.MainController;
import pl.TransportCompanySystem.Tables.Car;
import pl.TransportCompanySystem.Tables.Courier;
import pl.TransportCompanySystem.Tables.Location;
import pl.TransportCompanySystem.Tables.Package;
import pl.TransportCompanySystem.Tables.User;
import pl.TransportCompanySystem.Server.dao.CarDAO;
import pl.TransportCompanySystem.Server.dao.CourierDAO;
import pl.TransportCompanySystem.Server.dao.LocationDAO;
import pl.TransportCompanySystem.Server.dao.PackageDAO;
import pl.TransportCompanySystem.Server.dao.Registration;
import pl.TransportCompanySystem.Server.dao.UserDAO;

public class RequestHandler implements Runnable {
	private final Socket client;

	private MainController mainController;

	public RequestHandler(Socket client) {
		this.client = client;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

			String userInput, userInput2;
			userInput = (String) in.readObject();
			userInput2 = (String) in.readObject();
			Object userInput3 = in.readObject();

			if (userInput.equals("Statement")) {
				mainController.setTextArea("Statement from Client: " + userInput2 + " " + userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Free") && userInput2.equals("Login")) {
				User user = Registration.checkLogin((String) userInput3);
				out.writeObject(user);
				out.flush();
				System.out.println(user);
			} else if (userInput.equals("Free") && userInput2.equals("CourierLogin")) {
				Courier courier = Registration.checkCourierLogin((String) userInput3);
				out.writeObject(courier);
				out.flush();
				System.out.println(courier);
			} else if (userInput.equals("Add") && userInput2.equals("User")) {
				Registration.addUser((User) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Check") && userInput2.equals("Login")) {
				User user = UserDAO.checkLoginAndPassword((User) userInput3);
				out.writeObject(user);
				out.flush();
				System.out.println(user);
			} else if (userInput.equals("Check") && userInput2.equals("CourierLogin")) {
				Courier courier = CourierDAO.checkLoginAndPassword((Courier) userInput3);
				out.writeObject(courier);
				out.flush();
				System.out.println(courier);
			} else if (userInput.equals("CountAll") && userInput2.equals("Package")) {
				Integer qty = PackageDAO.countAllPackage();
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("Count") && userInput2.equals("Package")) {
				Integer qty = PackageDAO.countPackage((String) userInput3);
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("Count") && userInput2.equals("PackageComplaint")) {
				Integer qty = PackageDAO.countPackageComplaint((String) userInput3);
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("Count") && userInput2.equals("Price")) {
				Integer qty = PackageDAO.countPrice((Integer) userInput3);
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("CountAverage") && userInput2.equals("Price")) {
				Double avg = PackageDAO.countAveragePrice();
				out.writeObject(avg);
				out.flush();
				System.out.println(avg);
			} else if (userInput.equals("Edit") && userInput2.equals("User")) {
				UserDAO.editUserData((User) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("User") && userInput2.equals("Package")) {
				ArrayList<Package> packageList = PackageDAO.searchPackages((Integer) userInput3);
				out.writeObject(packageList);
				out.flush();
				System.out.println(packageList);
			} else if (userInput.equals("Package") && userInput2.equals("Status")) {
				String status = PackageDAO.searchPackageStatus((Integer) userInput3);
				out.writeObject(status);
				out.flush();
				System.out.println(status);
			} else if (userInput.equals("Location") && userInput2.equals("List")) {
				ArrayList<Location> locationList = LocationDAO.searchLocation();
				out.writeObject(locationList);
				out.flush();
				System.out.println(locationList);
			} else if (userInput.equals("Location") && userInput2.equals("ID")) {
				Integer locationID = LocationDAO.searchLocationID((String) userInput3);
				out.writeObject(locationID);
				out.flush();
				System.out.println(locationID);
			} else if (userInput.equals("Add") && userInput2.equals("Package")) {
				PackageDAO.addPackage((Package) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("CountAll") && userInput2.equals("Accounts")) {
				Integer accQty = UserDAO.countAllAccount((String) userInput3);
				out.writeObject(accQty);
				out.flush();
				System.out.println(accQty);
			} else if (userInput.equals("CountAll") && userInput2.equals("Locations")) {
				Integer qty = LocationDAO.countAllLocation();
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("CountAll") && userInput2.equals("Couriers")) {
				Integer qty = CourierDAO.countAllCouriers();
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("GetMin") && userInput2.equals("Salary")) {
				Integer salary = CourierDAO.getMinSalary();
				out.writeObject(salary);
				out.flush();
				System.out.println(salary);
			} else if (userInput.equals("GetMax") && userInput2.equals("Salary")) {
				Integer salary = CourierDAO.getMaxSalary();
				out.writeObject(salary);
				out.flush();
				System.out.println(salary);
			} else if (userInput.equals("CountAll") && userInput2.equals("Cars")) {
				Integer qty = CarDAO.countAllCars();
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("CountAllType") && userInput2.equals("Cars")) {
				Integer qty = CarDAO.countAllTypeCars((String) userInput3);
				out.writeObject(qty);
				out.flush();
				System.out.println(qty);
			} else if (userInput.equals("All") && userInput2.equals("Packages")) {
				ArrayList<Package> packageList = PackageDAO.searchPackages(0);
				out.writeObject(packageList);
				out.flush();
				System.out.println(packageList);
			} else if (userInput.equals("Get") && userInput2.equals("User")) {
				User user = UserDAO.getUser((Integer) userInput3);
				out.writeObject(user);
				out.flush();
				System.out.println(user);
			} else if (userInput.equals("Get") && userInput2.equals("Courier")) {
				Courier courier = CourierDAO.getCourier((Integer) userInput3);
				out.writeObject(courier);
				out.flush();
				System.out.println(courier);
			} else if (userInput.equals("Get") && userInput2.equals("Location")) {
				Location location = LocationDAO.getLocation((Integer) userInput3);
				out.writeObject(location);
				out.flush();
				System.out.println(location);
			} else if (userInput.equals("Get") && userInput2.equals("CouriersList")) {
				ArrayList<Courier> couriersList = CourierDAO.searchCouriers();
				out.writeObject(couriersList);
				out.flush();
				System.out.println(couriersList);
			} else if (userInput.equals("Get") && userInput2.equals("LocationString")) {
				Location location = LocationDAO.getLocationS((String) userInput3);
				out.writeObject(location);
				out.flush();
				System.out.println(location);
			} else if (userInput.equals("Edit") && userInput2.equals("Package")) {
				PackageDAO.editPackageData((Package) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Delete") && userInput2.equals("Package")) {
				PackageDAO.deletePackage((Integer) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Get") && userInput2.equals("UsersList")) {
				ArrayList<User> usersList = UserDAO.searchUsers((String) userInput3);
				out.writeObject(usersList);
				out.flush();
				System.out.println(usersList);
			} else if (userInput.equals("Delete") && userInput2.equals("User")) {
				UserDAO.deleteUser((Integer) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Edit") && userInput2.equals("Location")) {
				LocationDAO.editLocation((Location) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Add") && userInput2.equals("Location")) {
				LocationDAO.addLocation((Location) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("TransferPackages")) {
				PackageDAO.transferPackage(Integer.parseInt(userInput2), (Integer) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Delete") && userInput2.equals("Location")) {
				LocationDAO.deleteLocation((Integer) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Location") && userInput2.equals("Packages")) {
				ArrayList<Package> packageList = PackageDAO.searchLocationPackages((Integer) userInput3);
				out.writeObject(packageList);
				out.flush();
				System.out.println(packageList);
			} else if (userInput.equals("Courier") && userInput2.equals("Packages")) {
				ArrayList<Package> packageList = PackageDAO.searchCourierPackages((Integer) userInput3);
				out.writeObject(packageList);
				out.flush();
				System.out.println(packageList);
			} else if (userInput.equals("Get") && userInput2.equals("Car")) {
				Car car = CarDAO.getCar((Integer) userInput3);
				out.writeObject(car);
				out.flush();
				System.out.println(car);
			} else if (userInput.equals("Cars") && userInput2.equals("List")) {
				ArrayList<Car> carsList = CarDAO.searchCars();
				out.writeObject(carsList);
				out.flush();
				System.out.println(carsList);
			} else if (userInput.equals("Edit") && userInput2.equals("Courier")) {
				CourierDAO.editCourier((Courier) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("EditCarStatus")) {
				CarDAO.editCarStatus(Integer.parseInt(userInput2), (boolean) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Delete") && userInput2.equals("Courier")) {
				CourierDAO.deleteCourier((Integer) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Add") && userInput2.equals("Courier")) {
				CourierDAO.addCourier((Courier) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Delete") && userInput2.equals("Car")) {
				CarDAO.deleteCar((Integer) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Add") && userInput2.equals("Car")) {
				CarDAO.addCar((Car) userInput3);
				out.writeObject(null);
				out.flush();
			} else if (userInput.equals("Edit") && userInput2.equals("PackageCourier")) {
				PackageDAO.editPackageCourier((Integer) userInput3);
				out.writeObject(null);
				out.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
