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
import pl.TransportCompanySystem.Tables.Car;

public class CarsInSystemController {
	@FXML
	private TableView<Car> carsTable;
	@FXML
	private TableColumn<Car, Integer> carID;
	@FXML
	private TableColumn<Car, String> type;
	@FXML
	private TableColumn<Car, String> availibility;

	private Stage carsListStage, adminMainPageStage;
	private CarsInSystemController carsInSystemController;

	@FXML
	public void initialize() throws ClassNotFoundException {
		carID.setCellValueFactory(new PropertyValueFactory<>("carID"));
		type.setCellValueFactory(new PropertyValueFactory<>("carType"));
		availibility.setCellValueFactory(new PropertyValueFactory<>("available"));
	}

	public void searchCars() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Cars", "List");
			@SuppressWarnings("unchecked")
			ObservableList<Car> carsList = FXCollections
					.observableArrayList((ArrayList<Car>) connectToServer("Cars", "List", 0));

			addToCarsTable(carsList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToCarsTable(ObservableList<Car> carnsList) {
		carsTable.setItems(carnsList);
	}

	@FXML
	public void returnToMain() {
		this.adminMainPageStage.show();
		Stage stage = (Stage) carsTable.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void remove() throws IOException, ClassNotFoundException {
		Car car = carsTable.getSelectionModel().getSelectedItem();

		if (car != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usunąć Samochoód");
			alert.setHeaderText(null);
			alert.setContentText("Usunąć samochód o id " + car.getCarID() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				connectToServer("Statement", "Delete", "Car");
				connectToServer("Delete", "Car", car.getCarID());
				searchCars();
			} else {
				alert.close();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden Samochód");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void addCar() throws IOException {
		Stage carsList = (Stage) this.carsTable.getScene().getWindow();
		carsList.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AddCar.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Dodawanie Nowego Samochodu");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AddCarController addCarController = loader.getController();
		addCarController.setCarsList(carsListStage);
		addCarController.setCarsInSystemController(carsInSystemController);
	}

	public void setCarsInSystemController(CarsInSystemController carsInSystemController) {
		this.carsInSystemController = carsInSystemController;
	}

	public void setCarsListStage(Stage carsListStage) {
		this.carsListStage = carsListStage;
	}

	public void setAdminMainPageStage(Stage adminMainPageStage) {
		this.adminMainPageStage = adminMainPageStage;
	}

}
