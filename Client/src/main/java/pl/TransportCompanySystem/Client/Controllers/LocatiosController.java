package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Location;

public class LocatiosController {
	@FXML
	private ListView<String> locationList;

	private Stage userStage;

	@FXML
	public void initialize() {

	}

	public void setLocations() throws IOException {
		connectToServer("Statement", "Location", "List");
		@SuppressWarnings("unchecked")
		ArrayList<Location> location = (ArrayList<Location>) connectToServer("Location", "List", null);
		ObservableList<String> locations = FXCollections.observableArrayList();
		Iterator<Location> iterator = location.iterator();
		while (iterator.hasNext())
			locations.add(iterator.next().getLocationName());

		locationList.setItems(locations);
	}

	@FXML
	public void back() {
		this.userStage.show();
		Stage stage = (Stage) locationList.getScene().getWindow();
		stage.close();
	}

	public void setUserStage(Stage userStage) {
		this.userStage = userStage;
	}

}
