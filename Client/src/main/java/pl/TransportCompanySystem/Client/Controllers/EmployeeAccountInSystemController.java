package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Courier;

public class EmployeeAccountInSystemController {
	@FXML
	private TableView<Courier> employeeTable;
	@FXML
	private TableColumn<Courier, String> login, name, surname;
	@FXML
	private TableColumn<Courier, Integer> employeeID;
	@FXML
	private Button detailsButton, returnButton;

	private Stage adminStage, employeeStage;
	private EmployeeAccountInSystemController employeeAccountsController;

	@FXML
	public void initialize() {
		employeeID.setCellValueFactory(new PropertyValueFactory<>("courierID"));
		login.setCellValueFactory(new PropertyValueFactory<>("login"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));

	}

	public void searchEmployees() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Get", "CouriersList");
			@SuppressWarnings("unchecked")
			ObservableList<Courier> couriersList = FXCollections
					.observableArrayList((ArrayList<Courier>) connectToServer("Get", "CouriersList", 0));
			addToCouriersTable(couriersList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToCouriersTable(ObservableList<Courier> couriersList) {
		employeeTable.setItems(couriersList);
	}

	@FXML
	public void details() throws IOException {
		Courier courier = employeeTable.getSelectionModel().getSelectedItem();

		if (courier != null) {

			Stage employeesInSystem = (Stage) this.detailsButton.getScene().getWindow();
			employeesInSystem.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/EmployeeAccountDetails.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Dane Pracownika");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			EmployeeAccountDetailsController employeeAccountDetailsController = loader.getController();
			employeeAccountDetailsController.setCourier(courier);
			employeeAccountDetailsController.setUsersInSystem(employeesInSystem);
			employeeAccountDetailsController.updateData();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden pracownik");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}

		}
	}

	@FXML
	public void edit() throws IOException, ClassNotFoundException {
		Courier courier = employeeTable.getSelectionModel().getSelectedItem();

		if (courier != null) {

			Stage accountsStage = (Stage) this.detailsButton.getScene().getWindow();
			accountsStage.hide();

			FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("/pl/TransportCompanySystem/Client/resources/EmployeeAccountManagementPage.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Zarządzanie Kontem Pracownika");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			EmployeeAccountManagementPageController employeeAccountManagementPageController = loader.getController();
			employeeAccountManagementPageController.setEmployeeMainPage(employeeStage);
			employeeAccountManagementPageController.setCourier(courier);
			employeeAccountManagementPageController.setUserData();
			employeeAccountManagementPageController.setEmployeeAccountsController(employeeAccountsController);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden pracownik");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}
		}
	}

	@FXML
	void returnToMain() {
		this.adminStage.show();
		Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void delete() throws IOException, ClassNotFoundException {
		Courier courier = employeeTable.getSelectionModel().getSelectedItem();

		if (courier != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usunąć Pracownika");
			alert.setHeaderText(null);
			alert.setContentText("Usunąć pracownika o loginie " + courier.getLogin() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				connectToServer("Statement", "EditCar", "Status");
				connectToServer("EditCarStatus", courier.getCarID().toString(), true);

				connectToServer("Statement", "Delete", "Courier");
				connectToServer("Delete", "Courier", courier.getCourierID());
				refresh();
			} else {
				alert.close();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden Pracownik");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void add() throws ClassNotFoundException, IOException {
		Stage accountsStage = (Stage) this.detailsButton.getScene().getWindow();
		accountsStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AddEmployee.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Dodaj Pracownika");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AddEmployeeController addEmployeeController = loader.getController();
		addEmployeeController.setEmployeeMainPage(employeeStage);
		addEmployeeController.searchCars();
		addEmployeeController.setEmployeeAccountsController(employeeAccountsController);
	}

	@FXML
	public void refresh() throws ClassNotFoundException {
		searchEmployees();
	}

	public void setAdminStage(Stage adminStage) {
		this.adminStage = adminStage;
	}

	public void setEmployeeAccountsController(EmployeeAccountInSystemController employeeAccountsController) {
		this.employeeAccountsController = employeeAccountsController;
	}

	public void setEmployeeStage(Stage employeeStage) {
		this.employeeStage = employeeStage;
	}

}
