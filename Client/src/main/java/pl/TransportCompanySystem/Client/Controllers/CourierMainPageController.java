package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Courier;

public class CourierMainPageController {
	@FXML
	private ImageView image;
	@FXML
	private Label loginAs;

	private LinkedList<Image> listOfImages;
	private Stage loginPage;
	private Courier courier;
	private int imageIndex;

	@FXML
	public void initialize() {
		listOfImages = new LinkedList<Image>();
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/uslugiPaczka2.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/uslugiPaczka.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborLublin.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborLublin3.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborMercedes.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborNysa.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborZuk.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/Uslugi Paczka.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/logo.jpg"));
		image.setImage(listOfImages.getFirst());
		imageIndex = 0;

	}

	@FXML
	public void logOut() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Wylogować?");
		alert.setHeaderText(null);
		alert.setContentText("Wylogować?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			Stage stage = (Stage) image.getScene().getWindow();
			stage.close();
			loginPage.show();
			String text = courier.getLogin() + " logged out";
			connectToServer("Statement", "Courier ", text);
		} else {
			alert.close();
		}
	}

	@FXML
	public void myPackages() throws IOException, ClassNotFoundException {
		Stage courierMainPageStage = (Stage) this.image.getScene().getWindow();
		courierMainPageStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/CourierPackages.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki Kuriera");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		CourierPackagesController courierPackagesController = loader.getController();
		courierPackagesController.setLocationsPage(courierMainPageStage);
		courierPackagesController.setCourier(courier);
		courierPackagesController.searchPackages();
	}

	@FXML
	public void changeStatus() throws IOException, ClassNotFoundException {
		Stage courierMainPageStage = (Stage) this.image.getScene().getWindow();
		courierMainPageStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/CourierPackages.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki Kuriera");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		CourierPackagesController courierPackagesController = loader.getController();
		courierPackagesController.setLocationsPage(courierMainPageStage);
		courierPackagesController.setCourierPackagesController(courierPackagesController);
		courierPackagesController.setEdited(true);
		courierPackagesController.updateInterface();
		courierPackagesController.setCourier(courier);
		courierPackagesController.searchPackages();
	}

	@FXML
	public void prevImage() {
		this.imageIndex--;
		if (imageIndex < 0) {
			imageIndex = listOfImages.size() - 1;
			image.setImage(listOfImages.getLast());
		} else
			image.setImage(listOfImages.get(imageIndex));
	}

	@FXML
	public void nextImage() {
		this.imageIndex++;
		if (imageIndex >= listOfImages.size()) {
			imageIndex = 0;
			image.setImage(listOfImages.getFirst());
		} else
			image.setImage(listOfImages.get(imageIndex));
	}

	public void updateUserName() {
		loginAs.setText("Zalogowano jako " + courier.getLogin());
	}

	public void setLoginPage(Stage loginPage) {
		this.loginPage = loginPage;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

}
