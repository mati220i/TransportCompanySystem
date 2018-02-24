package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Car;

public class AddCarController {
	@FXML
	private TextField typeTextField;
	@FXML
	private Label typeStatement;

	private Stage carsList;
	private CarsInSystemController carsInSystemController;

	@FXML
	public void initialize() {
		typeStatement.setVisible(false);
	}

	@FXML
	public void typeEntered() {
		typeStatement.setVisible(false);
	}

	@FXML
	public void add() throws IOException, ClassNotFoundException {
		if (checkData()) {
			Car car = new Car();
			car.setCarType(typeTextField.getText().trim());

			connectToServer("Statement", "Add", "Car");

			connectToServer("Add", "Car", car);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Dodano Nowy Samochód");
			alert.setHeaderText(null);
			alert.setContentText("Dodano Nowy Samochód");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				Stage stage = (Stage) typeTextField.getScene().getWindow();
				stage.close();
				carsList.show();
				carsInSystemController.searchCars();
			} else {
				alert.close();
			}
		}
	}

	private boolean checkData() {
		boolean isCorrect = true;

		if (typeTextField.getText().trim().equals("")) {
			typeStatement.setText("Podaj Typ");
			typeStatement.setVisible(true);
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
		alert.setTitle("Anuluj Dodawanie");
		alert.setHeaderText(null);
		alert.setContentText("Wyjść z dodawania Samochodu bez zapisywania?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			this.carsList.show();
			Stage stage = (Stage) typeTextField.getScene().getWindow();
			stage.close();
		} else {
			alert.close();
		}
	}

	public void setCarsList(Stage carsList) {
		this.carsList = carsList;
	}

	public void setCarsInSystemController(CarsInSystemController carsInSystemController) {
		this.carsInSystemController = carsInSystemController;
	}

}
