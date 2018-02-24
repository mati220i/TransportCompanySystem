package pl.TransportCompanySystem.Client.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Car;
import pl.TransportCompanySystem.Tables.Courier;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

public class EmployeeAccountDetailsController {
	@FXML
	private TextField employeeID, login, password, name, surname, salary, carID, carType;

	private Courier courier;
	private Stage usersInSystem;

	@FXML
	public void initialize() {

	}

	public void updateData() throws IOException {
		Car car = selectCourierCar();

		employeeID.setText(courier.getCourierID().toString());
		login.setText(courier.getLogin());
		password.setText(courier.getPassword());
		name.setText(courier.getName());
		surname.setText(courier.getSurname());
		salary.setText(courier.getSalary().toString());
		carID.setText(car.getCarID().toString());
		carType.setText(car.getCarType());
	}

	public Car selectCourierCar() throws IOException {
		connectToServer("Statement", "Get", "Car");
		return (Car) connectToServer("Get", "Car", courier.getCarID());
	}

	@FXML
	public void courierPackages() throws IOException, ClassNotFoundException {
		Stage userMainPageStage = (Stage) this.employeeID.getScene().getWindow();
		userMainPageStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/CourierPackages.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki Kuriera");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		CourierPackagesController courierPackagesController = loader.getController();
		courierPackagesController.setLocationsPage(userMainPageStage);
		courierPackagesController.setCourier(courier);
		courierPackagesController.searchPackages();
	}

	@FXML
	public void back() {
		Stage stage = (Stage) employeeID.getScene().getWindow();
		stage.close();
		usersInSystem.show();
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public void setUsersInSystem(Stage usersInSystem) {
		this.usersInSystem = usersInSystem;
	}

}
