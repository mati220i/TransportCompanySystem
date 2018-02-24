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
import pl.TransportCompanySystem.Tables.Location;

public class AddLocationController {
	@FXML
	private TextField nameTextField;
	@FXML
	private Label nameStatement;

	private Stage locationList;
	private AvailableLocationsController availableLocationsController;

	@FXML
	public void initialize() {
		nameStatement.setVisible(false);
	}

	@FXML
	public void nameEntered() {
		nameStatement.setVisible(false);
	}

	@FXML
	public void add() throws IOException, ClassNotFoundException {
		if (checkData()) {
			Location location = new Location();
			location.setLocationName(nameTextField.getText().trim());

			connectToServer("Statement", "Add", "Location");

			connectToServer("Add", "Location", location);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Dodano Nową Lokalizację");
			alert.setHeaderText(null);
			alert.setContentText("Dodano Nową Lokalizację");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				Stage stage = (Stage) nameTextField.getScene().getWindow();
				stage.close();
				locationList.show();
				availableLocationsController.searchLocations();
			} else {
				alert.close();
			}
		}
	}

	private boolean checkData() {
		boolean isCorrect = true;

		if (nameTextField.getText().trim().equals("")) {
			nameStatement.setText("Podaj Nazwę");
			nameStatement.setVisible(true);
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
		alert.setContentText("Wyjść z dodawania Lokalizacji bez zapisywania?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			this.locationList.show();
			Stage stage = (Stage) nameTextField.getScene().getWindow();
			stage.close();
		} else {
			alert.close();
		}
	}

	public void setLocationList(Stage locationList) {
		this.locationList = locationList;
	}

	public void setAvailableLocationsController(AvailableLocationsController availableLocationsController) {
		this.availableLocationsController = availableLocationsController;
	}

}
