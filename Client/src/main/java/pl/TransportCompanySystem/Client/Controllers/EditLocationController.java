package pl.TransportCompanySystem.Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Location;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.Optional;

public class EditLocationController {
	@FXML
	private TextField nameTextField;
	@FXML
	private Label nameStatement;

	private Stage locationList;
	private Location location;
	private AvailableLocationsController availableLocationsController;

	@FXML
	public void initialize() {
		nameStatement.setVisible(false);
	}

	public void setData() {
		nameTextField.setText(location.getLocationName());
	}

	@FXML
	public void nameEntered() {
		nameStatement.setVisible(false);
	}

	@FXML
	public void editData() throws IOException, ClassNotFoundException {
		if (checkData()) {
			location.setLocationName(nameTextField.getText().trim());

			connectToServer("Statement", "Edit", "Location");

			connectToServer("Edit", "Location", location);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edytowano Lokalizację");
			alert.setHeaderText(null);
			alert.setContentText("Edytowano Lokalizację");

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
		alert.setTitle("Anuluj edycję");
		alert.setHeaderText(null);
		alert.setContentText("Wyjść z edycji Lokalizacji bez zapisywania?");

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

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setAvailableLocationsController(AvailableLocationsController availableLocationsController) {
		this.availableLocationsController = availableLocationsController;
	}

}
