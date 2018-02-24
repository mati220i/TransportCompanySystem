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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Location;

public class DeleteLocationController {
	@FXML
	private ComboBox<String> locationsList;
	@FXML
	private Label nameStatement, title;

	private Location location;
	private Stage locationsStage;
	private AvailableLocationsController availableLocationsController;

	@FXML
	public void initialize() {
		nameStatement.setVisible(false);
	}

	public void setData() throws ClassNotFoundException {
		title.setText(title.getText() + " " + location.getLocationName());
		searchLocations();
	}

	public void setLocationList(ObservableList<Location> locations) {
		ObservableList<String> locationsOptions = FXCollections.observableArrayList();

		Iterator<Location> it = locations.iterator();
		while (it.hasNext()) {
			Location l = it.next();
			if (!l.getLocationID().equals(location.getLocationID())) {
				String text = l.getLocationID() + " " + l.getLocationName();
				locationsOptions.add(text);
			}
		}

		locationsList.setItems(locationsOptions);
	}

	public void searchLocations() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Location", "List");
			@SuppressWarnings("unchecked")
			ObservableList<Location> locationsList = FXCollections
					.observableArrayList((ArrayList<Location>) connectToServer("Location", "List", 0));
			setLocationList(locationsList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Integer getLocationIDFromString(String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			if (Character.isDigit(string.charAt(i)))
				sb.append(string.charAt(i));
		}
		Integer id = Integer.parseInt(sb.toString());
		return id;
	}

	public Location selectLocation() throws IOException {
		Integer locationID = getLocationIDFromString(locationsList.getSelectionModel().getSelectedItem());
		Location location = (Location) connectToServer("Get", "Location", locationID);

		return location;
	}

	@FXML
	public void remove() throws IOException, ClassNotFoundException {
		String loc = locationsList.getSelectionModel().getSelectedItem();
		if (loc != null) {
			Location newLocation = selectLocation();

			connectToServer("Statement", "Transfer", "Packages");
			connectToServer("TransferPackages", location.getLocationID().toString(), newLocation.getLocationID());

			connectToServer("Statement", "Delete", "Location");
			connectToServer("Delete", "Location", location.getLocationID());

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usunięto Lokalizację");
			alert.setHeaderText(null);
			alert.setContentText("Usunięto Lokalizację");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				Stage stage = (Stage) locationsList.getScene().getWindow();
				stage.close();
				locationsStage.show();
				availableLocationsController.searchLocations();
			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void cancel() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Anuluj Usuwanie");
		alert.setHeaderText(null);
		alert.setContentText("Wyjść z usuwania Lokalizacji?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			this.locationsStage.show();
			Stage stage = (Stage) locationsList.getScene().getWindow();
			stage.close();
		} else {
			alert.close();
		}
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocationsStage(Stage locationsStage) {
		this.locationsStage = locationsStage;
	}

	public void setAvailableLocationsController(AvailableLocationsController availableLocationsController) {
		this.availableLocationsController = availableLocationsController;
	}

}
