package pl.TransportCompanySystem.Client.Controllers;

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
import pl.TransportCompanySystem.Tables.User;
import pl.TransportCompanySystem.Tables.Package;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MyPackageController {
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

	private Stage userMainPageStage;
	private User user;

	@FXML
	public void initialize() throws ClassNotFoundException {
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("packageID"));
		sizeTableColumn.setCellValueFactory(new PropertyValueFactory<>("packageSize"));
		priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		paymentMethodTableColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
	}

	public void searchPackages() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Packages User: ", user.getLogin());
			@SuppressWarnings("unchecked")
			ObservableList<Package> packageList = FXCollections
					.observableArrayList((ArrayList<Package>) connectToServer("User", "Package", user.getUserID()));
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
		this.userMainPageStage.show();
		Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void details() throws IOException {
		Package pack = packageTable.getSelectionModel().getSelectedItem();

		if (pack != null) {

			Stage myPackage = (Stage) this.detailsButton.getScene().getWindow();
			myPackage.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/UserPackageDetails.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Szczegóły Paczki");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			UserPackageDetailsController userPackageDetailsController = loader.getController();
			userPackageDetailsController.setMyPackage(myPackage);
			userPackageDetailsController.setUser(user);
			userPackageDetailsController.setPackage(pack);
			userPackageDetailsController.updateDate();
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

	public void setUserMainPage(Stage userMainPageStage) {
		this.userMainPageStage = userMainPageStage;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
