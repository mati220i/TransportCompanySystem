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
import pl.TransportCompanySystem.Tables.Package;

public class CourierPackagesController {
	@FXML
	private Button detailsButton, returnButton;
	@FXML
	private TableView<Package> packageTable;
	@FXML
	private TableColumn<Package, Integer> idTableColumn;
	@FXML
	private TableColumn<Package, String> sizeTableColumn, paymentMethodTableColumn;
	@FXML
	private TableColumn<Package, Double> priceTableColumn;

	private Stage couriersStage;
	private Courier courier;
	private CourierPackagesController courierPackagesController;
	private boolean isEdited;

	@FXML
	public void initialize() throws ClassNotFoundException {
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("packageID"));
		sizeTableColumn.setCellValueFactory(new PropertyValueFactory<>("packageSize"));
		priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		paymentMethodTableColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
	}

	public void updateInterface() {
		if (isEdited)
			detailsButton.setText("Dostarcz");
	}

	public void searchPackages() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Courier Packages: ", courier.getCourierID());
			@SuppressWarnings("unchecked")
			ObservableList<Package> packageList = FXCollections.observableArrayList(
					(ArrayList<Package>) connectToServer("Courier", "Packages", courier.getCourierID()));
			addToPackageTable(packageList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToPackageTable(ObservableList<Package> packageList) {
		packageTable.setItems(packageList);
	}

	@FXML
	public void returnToMain() {
		this.couriersStage.show();
		Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
	}

	@FXML // If isEdited is true, this function changes package status
	public void details() throws IOException {
		Package pack = packageTable.getSelectionModel().getSelectedItem();

		if (pack != null) {

			if (isEdited) {
				Stage courierPackages = (Stage) this.detailsButton.getScene().getWindow();
				courierPackages.hide();

				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/pl/TransportCompanySystem/Client/resources/EditPackageCourier.fxml"));
				AnchorPane root = loader.load();
				Stage stage = new Stage();
				stage.setTitle("Edycja Paczki");
				stage.setScene(new Scene(root));
				stage.show();
				stage.setResizable(false);

				EditPackageCourierController editPackageController = loader.getController();
				editPackageController.setCourierPackages(courierPackages);
				editPackageController.setPackage(pack);
				editPackageController.setCourierPackagesController(courierPackagesController);
			} else {
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

	public void setLocationsPage(Stage locationsStage) {
		this.couriersStage = locationsStage;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	public void setCourierPackagesController(CourierPackagesController courierPackagesController) {
		this.courierPackagesController = courierPackagesController;
	}

}
