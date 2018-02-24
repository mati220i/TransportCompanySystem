package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.User;

public class UserMainPageController {
	@FXML
	private Button nextButton, findLocationButton, myPackageButton, logOutButton, accountManagementButton,
			pricingButton, prevButton, trackingButton, sendPackageButton;
	@FXML
	private ImageView image;
	@FXML
	private Label loginAs;

	private Stage loginPanelStage;
	private User user;
	private LinkedList<Image> listOfImages;
	private int imageIndex;

	private UserMainPageController userMainPageController;

	@FXML
	public void initialize() {
		listOfImages = new LinkedList<Image>();
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/uslugiPaczka.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborLublin.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborLublin3.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborMercedes.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborNysa.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/taborZuk.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/Uslugi Paczka.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/uslugiPaczka2.jpg"));
		listOfImages.add(new Image("/pl/TransportCompanySystem/Client/resources/logo.jpg"));
		image.setImage(listOfImages.getFirst());
		imageIndex = 0;

	}

	public void updateUserName() {
		loginAs.setText("Zalogowano jako " + user.getLogin());
	}

	@FXML
	public void myPackage() throws IOException, ClassNotFoundException {
		Stage userMainPageStage = (Stage) this.accountManagementButton.getScene().getWindow();
		userMainPageStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/MyPackage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki Użytkownika");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		MyPackageController myPackageController = loader.getController();
		myPackageController.setUserMainPage(userMainPageStage);
		myPackageController.setUser(user);
		myPackageController.searchPackages();
	}

	@FXML
	public void accountManagement() throws IOException {
		Stage userMainPageStage = (Stage) this.accountManagementButton.getScene().getWindow();
		userMainPageStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/UserAccountManagementPage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Zarządzanie Kontem Użytkownika");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		UserAccountManagementPageController userAccountManagementPageController = loader.getController();
		userAccountManagementPageController.setUserMainPage(userMainPageStage);
		userAccountManagementPageController.setUser(user);
		userAccountManagementPageController.setUserData();
		userAccountManagementPageController.setAdmin(false);
		userAccountManagementPageController.setUserMainPageController(userMainPageController);
	}

	@FXML
	public void logOut() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Wylogować?");
		alert.setHeaderText(null);
		alert.setContentText("Wylogować?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			Stage stage = (Stage) logOutButton.getScene().getWindow();
			stage.close();
			loginPanelStage.show();
			String text = user.getLogin() + " logged out";
			connectToServer("Statement", "User ", text);
		} else {
			alert.close();
		}
	}

	@FXML
	public void tracking() throws IOException {
		Stage mainStage = (Stage) this.trackingButton.getScene().getWindow();
		mainStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/Tracking.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Śledzenie Przesyłek");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		TrackingController trackingController = loader.getController();
		trackingController.setMainStage(mainStage);
	}

	@FXML
	public void pricing() throws IOException {
		Stage mainStage = (Stage) this.pricingButton.getScene().getWindow();
		mainStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/Pricing.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Wycena Paczki");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		PricingController pricingController = loader.getController();
		pricingController.setUserMain(mainStage);
	}

	@FXML
	public void sendPackage() throws IOException {
		Stage mainStage = (Stage) this.sendPackageButton.getScene().getWindow();
		mainStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/NewPackage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Nadaj Paczkę");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		NewPackageController newPackageController = loader.getController();
		newPackageController.setUserMain(mainStage);
		newPackageController.setUser(user);
		newPackageController.updateData();
		newPackageController.setLocations();
	}

	@FXML
	public void findLocation() throws IOException {
		Stage mainStage = (Stage) this.findLocationButton.getScene().getWindow();
		mainStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/Locations.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Dostępne Lokalizacje");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		LocatiosController locatiosController = loader.getController();
		locatiosController.setUserStage(mainStage);
		locatiosController.setLocations();
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

	public void setLoginPanelStage(Stage loginPanelStage) {
		this.loginPanelStage = loginPanelStage;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserMainPageController(UserMainPageController userMainPageController) {
		this.userMainPageController = userMainPageController;
	}
}
