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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Location;

public class AvailableLocationsController {
	@FXML
	private TableView<Location> locationTable;
	@FXML
	private TableColumn<Location, Integer> locationID;
	@FXML
	private TableColumn<Location, String> name;

	private Stage locationsListStage, adminMainPageStage;
	private AvailableLocationsController availableLocationsController;

	@FXML
	public void initialize() throws ClassNotFoundException {
		locationID.setCellValueFactory(new PropertyValueFactory<>("locationID"));
		name.setCellValueFactory(new PropertyValueFactory<>("locationName"));
	}

	public void searchLocations() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Location", "List");
			@SuppressWarnings("unchecked")
			ObservableList<Location> locationsList = FXCollections
					.observableArrayList((ArrayList<Location>) connectToServer("Location", "List", 0));
			addToLocationsTable(locationsList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToLocationsTable(ObservableList<Location> locationsList) {
		locationTable.setItems(locationsList);
	}

	@FXML
	public void returnToMain() {
		this.adminMainPageStage.show();
		Stage stage = (Stage) locationTable.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void edit() throws IOException {
		Location location = locationTable.getSelectionModel().getSelectedItem();

		if (location != null) {
			Stage accountsStage = (Stage) this.locationTable.getScene().getWindow();
			accountsStage.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/EditLocation.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Edycja Lokalizacji");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			EditLocationController editLocationController = loader.getController();
			editLocationController.setLocationList(locationsListStage);
			editLocationController.setLocation(location);
			editLocationController.setAvailableLocationsController(availableLocationsController);
			editLocationController.setData();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie została wybrana żadna Lokalizacja");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}

		}
	}

	@FXML
	public void remove() throws IOException, ClassNotFoundException {
		Location location = locationTable.getSelectionModel().getSelectedItem();

		if (location != null) {
			Stage accountsStage = (Stage) this.locationTable.getScene().getWindow();
			accountsStage.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/DeleteLocation.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Usuwanie Lokalizacji");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			DeleteLocationController deleteLocationController = loader.getController();
			deleteLocationController.setLocationsStage(locationsListStage);
			deleteLocationController.setLocation(location);
			deleteLocationController.setAvailableLocationsController(availableLocationsController);
			deleteLocationController.setData();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie została wybrana żadna Lokalizacja");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void addLocation() throws IOException {
		Stage locationsList = (Stage) this.locationTable.getScene().getWindow();
		locationsList.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AddLocation.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Dodawanie Nowej Lokalizacji");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AddLocationController addLocationController = loader.getController();
		addLocationController.setLocationList(locationsList);
		addLocationController.setAvailableLocationsController(availableLocationsController);
	}

	@FXML
	public void packagesInLocation() throws IOException, ClassNotFoundException {
		Location location = locationTable.getSelectionModel().getSelectedItem();

		if (location != null) {
			Stage locationsStage = (Stage) this.locationTable.getScene().getWindow();
			locationsStage.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/PackageInLocation.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Paczki w Lokalizacji");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			PackageInLocationController packageInLocationController = loader.getController();
			packageInLocationController.setLocationsPage(locationsStage);
			packageInLocationController.setLocation(location);
			packageInLocationController.searchPackages();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie została wybrana żadna Lokalizacja");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}

		}
	}

	public void setAvailableLocationsController(AvailableLocationsController availableLocationsController) {
		this.availableLocationsController = availableLocationsController;
	}

	public void setLocationsListStage(Stage locationsListStage) {
		this.locationsListStage = locationsListStage;
	}

	public void setAdminMainPageStage(Stage adminMainPageStage) {
		this.adminMainPageStage = adminMainPageStage;
	}

}
