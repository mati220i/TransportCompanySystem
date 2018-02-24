package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Car;
import pl.TransportCompanySystem.Tables.Courier;
import pl.TransportCompanySystem.Tables.User;

public class EmployeeAccountManagementPageController {
	@FXML
	private TextField salaryTextField, loginTextField, nameTextField, passwordTextField, surnameTextField;
	@FXML
	private Label loginStatement, passwordStatement, nameStatement, surnameStatement, salaryStatement, carStatement;
	@FXML
	private CheckBox editLoginCheckBox;
	@FXML
	private ComboBox<String> car;

	private Stage employeesPage;
	private Courier courier;
	private EmployeeAccountInSystemController employeeAccountsController;

	@FXML
	public void initialize() {
		loginStatement.setVisible(false);
		passwordStatement.setVisible(false);
		nameStatement.setVisible(false);
		surnameStatement.setVisible(false);
		salaryStatement.setVisible(false);
		carStatement.setVisible(false);
	}

	public void setUserData() throws ClassNotFoundException, IOException {
		salaryTextField.setText(courier.getSalary().toString());
		loginTextField.setText(courier.getLogin());
		nameTextField.setText(courier.getName());
		passwordTextField.setText(courier.getPassword());
		surnameTextField.setText(courier.getSurname());

		Car carFromDB = (Car) connectToServer("Get", "Car", courier.getCarID());
		String text = carFromDB.getCarID() + " " + carFromDB.getCarType();

		car.getSelectionModel().select(text);
		searchCars();
	}

	@FXML
	public void editLogin() {
		if (editLoginCheckBox.isSelected())
			loginTextField.setDisable(false);
		else
			loginTextField.setDisable(true);
	}

	@FXML
	public void editData() throws IOException, ClassNotFoundException {
		if (this.checkData()) {
			if (this.checkLoginInDB()) {

				if (!courier.getCarID().equals(getCarIDFromString(car.getSelectionModel().getSelectedItem()))) {
					Integer newCar, oldCar;
					newCar = getCarIDFromString(car.getSelectionModel().getSelectedItem());
					oldCar = courier.getCarID();

					boolean available = true, notAvailable = false;

					changeCarStatus(newCar, notAvailable);
					changeCarStatus(oldCar, available);
				}

				courier.setSalary(Integer.parseInt(salaryTextField.getText()));
				courier.setLogin(loginTextField.getText().trim());
				courier.setName(nameTextField.getText().trim());
				courier.setPassword(passwordTextField.getText().trim());
				courier.setSurname(surnameTextField.getText().trim());

				Car car = selectCar();

				courier.setCarID(car.getCarID());

				connectToServer("Statemnet", "Edit", "Courier");
				connectToServer("Edit", "Courier", courier);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Edytowano Pracownika");
				alert.setHeaderText(null);
				alert.setContentText("Dane Pracownika " + courier.getLogin() + " zostały zaktualizowane");

				Optional<ButtonType> result = alert.showAndWait();
				if ((result.get() == ButtonType.OK)) {
					Stage stage = (Stage) salaryStatement.getScene().getWindow();
					stage.close();
					employeesPage.show();
					employeeAccountsController.refresh();
				} else {
					alert.close();
				}
			} else {
				loginStatement.setText("Login zajęty");
				loginStatement.setVisible(true);
			}
		}
	}

	public void changeCarStatus(Integer carID, boolean availability) throws IOException {
		connectToServer("Statement", "EditCar", "Status");
		connectToServer("EditCarStatus", carID.toString(), availability);
	}

	public void setCarsList(ObservableList<Car> cars) {
		ObservableList<String> carsOptions = FXCollections.observableArrayList();

		Iterator<Car> it = cars.iterator();
		while (it.hasNext()) {
			Car c = it.next();
			if (c.getAvailable()) {
				String text = c.getCarID() + " " + c.getCarType();
				carsOptions.add(text);
			}
		}

		car.setItems(carsOptions);
	}

	public void searchCars() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Cars", "List");
			@SuppressWarnings("unchecked")
			ObservableList<Car> carsList = FXCollections
					.observableArrayList((ArrayList<Car>) connectToServer("Cars", "List", 0));
			setCarsList(carsList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Integer getCarIDFromString(String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			if (Character.isDigit(string.charAt(i)))
				sb.append(string.charAt(i));
		}
		Integer id = Integer.parseInt(sb.toString());
		return id;
	}

	public Car selectCar() throws IOException {
		Integer carID = getCarIDFromString(car.getSelectionModel().getSelectedItem());
		Car carFromDB = (Car) connectToServer("Get", "Car", carID);

		return carFromDB;
	}

	@FXML
	public void loginEntered() {
		loginStatement.setVisible(false);
	}

	@FXML
	public void passwordEntered() {
		passwordStatement.setVisible(false);
	}

	@FXML
	public void nameEntered() {
		nameStatement.setVisible(false);
	}

	@FXML
	public void surnameEntered() {
		surnameStatement.setVisible(false);
	}

	@FXML
	public void salaryEntered() {
		salaryStatement.setVisible(false);
	}

	private boolean checkLoginInDB() throws IOException {
		if (editLoginCheckBox.isSelected()) {
			connectToServer("Statement", "Free", "Login");
			User user = (User) connectToServer("Free", "Login", loginTextField.getText().trim());
			if (user == null) {
				return true;
			} else {
				return false;
			}
		} else
			return true;
	}

	private boolean checkData() {
		boolean isCorrect = true;

		if (loginTextField.getText().trim().equals("")) {
			loginStatement.setText("Podaj Login");
			loginStatement.setVisible(true);
			isCorrect = false;
		}
		if (passwordTextField.getText().trim().equals("")) {
			passwordStatement.setVisible(true);
			isCorrect = false;
		}
		if (nameTextField.getText().trim().equals("")) {
			nameStatement.setVisible(true);
			isCorrect = false;
		}
		if (surnameTextField.getText().trim().equals("")) {
			surnameStatement.setVisible(true);
			isCorrect = false;
		}
		if (salaryTextField.getText().trim().equals("")) {
			salaryStatement.setVisible(true);
			isCorrect = false;
		}

		if (isCorrect)
			return true;
		else
			return false;
	}

	@FXML
	public void cancel() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Anuluj edycję");
		alert.setHeaderText(null);
		alert.setContentText("Wyjść z edycji danych bez zapisywania?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			this.employeesPage.show();
			Stage stage = (Stage) salaryStatement.getScene().getWindow();
			stage.close();
		} else {
			alert.close();
		}
	}

	public void setEmployeeMainPage(Stage employeeMainPage) {
		this.employeesPage = employeeMainPage;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public void setEmployeeAccountsController(EmployeeAccountInSystemController employeeAccountsController) {
		this.employeeAccountsController = employeeAccountsController;
	}

}
