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
import pl.TransportCompanySystem.Tables.Package;

public class PackagesInSystemController {
	@FXML
	private TableView<Package> packageTable;
	@FXML
	private TableColumn<Package, String> complaint, paymentMethod, size;
	@FXML
	private TableColumn<Package, Double> price, weight;
	@FXML
	private TableColumn<Package, Integer> packageID, courier, locations, user;
	@FXML
	private Button detailsButton, returnButton;

	private Stage adminStage;
	private PackagesInSystemController packagesController;

	@FXML
	public void initialize() {
		packageID.setCellValueFactory(new PropertyValueFactory<>("packageID"));
		complaint.setCellValueFactory(new PropertyValueFactory<>("withComplaint"));
		paymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
		size.setCellValueFactory(new PropertyValueFactory<>("packageSize"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		weight.setCellValueFactory(new PropertyValueFactory<>("packageWeight"));
		courier.setCellValueFactory(new PropertyValueFactory<>("courierID"));
		locations.setCellValueFactory(new PropertyValueFactory<>("locationID"));
		user.setCellValueFactory(new PropertyValueFactory<>("userID"));
	}

	public void searchPackages() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "All", "Packages");
			@SuppressWarnings("unchecked")
			ObservableList<Package> packageList = FXCollections
					.observableArrayList((ArrayList<Package>) connectToServer("All", "Packages", 0));
			addToPackageTable(packageList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToPackageTable(ObservableList<Package> packageList) {
		packageTable.setItems(packageList);
	}

	@FXML
	public void details() throws IOException {
		Package pack = packageTable.getSelectionModel().getSelectedItem();

		if (pack != null) {

			Stage packagesInSystem = (Stage) this.detailsButton.getScene().getWindow();
			packagesInSystem.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/PackageDetails.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Szczegóły Paczki");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			PackageDetailsController packageDetailsController = loader.getController();
			packageDetailsController.setPackagesInSystem(packagesInSystem);
			packageDetailsController.setPackage(pack);
			packageDetailsController.updateData();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie została wybrana żadna paczka");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}

		}
	}

	@FXML
	public void editPackage() throws IOException {
		Package pack = packageTable.getSelectionModel().getSelectedItem();

		if (pack != null) {

			Stage packagesInSystem = (Stage) this.detailsButton.getScene().getWindow();
			packagesInSystem.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/EditPackage.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Edycja Paczki");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			EditPackageController editPackageController = loader.getController();
			editPackageController.setPackagesInSystem(packagesInSystem);
			editPackageController.setPackage(pack);
			editPackageController.updateData();
			editPackageController.setPackagesController(packagesController);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie została wybrana żadna paczka");

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
		Package pack = packageTable.getSelectionModel().getSelectedItem();

		if (pack != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usunąć Paczkę");
			alert.setHeaderText(null);
			alert.setContentText("Usunąć paczkę o numerze " + pack.getPackageID());

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				connectToServer("Statement", "Delete", "Package");
				connectToServer("Delete", "Package", pack.getPackageID());
				refresh();
			} else {
				alert.close();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie została wybrana żadna paczka");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void refresh() throws ClassNotFoundException {
		searchPackages();
	}

	public void setAdminStage(Stage adminStage) {
		this.adminStage = adminStage;
	}

	public void setPackagesController(PackagesInSystemController packagesController) {
		this.packagesController = packagesController;
	}

}
