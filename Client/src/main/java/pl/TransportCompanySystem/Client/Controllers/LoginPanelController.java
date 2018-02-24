package pl.TransportCompanySystem.Client.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Courier;
import pl.TransportCompanySystem.Tables.User;
import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

public class LoginPanelController {
	@FXML
	private TextField loginTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button logInButton, cancelButton;
	@FXML
	private Label statement;
	@FXML
	private CheckBox isCourier;

	private Stage mainStage;

	@FXML
	public void initialize() {
		statement.setVisible(false);
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@FXML
	public void logIn() throws IOException {
		if (isCourier.isSelected()) {
			Courier courier = new Courier();
			courier.setLogin(loginTextField.getText());
			courier.setPassword(passwordField.getText());

			connectToServer("Statemnet", "Check", "CourierLogin");
			Courier courierFromDB = (Courier) connectToServer("Check", "CourierLogin", courier);

			if (courierFromDB == null) {
				statement.setVisible(true);
			} else {
				String text = courierFromDB.getLogin() + " logged into system";
				connectToServer("Statement", "Courier ", text);
				loadCourierMainPage(courierFromDB);
			}
		} else {
			User user = new User();
			user.setLogin(loginTextField.getText());
			user.setPassword(passwordField.getText());

			connectToServer("Statement", "Check", "Login");
			User userFromDB = (User) connectToServer("Check", "Login", user);

			if (userFromDB == null) {
				statement.setVisible(true);
			} else {
				if (userFromDB.getAccountType().equals("Admin")) {
					String text = userFromDB.getLogin() + " logged into system";
					connectToServer("Statement", "Admin ", text);
					loadAdminMainPage(userFromDB);
				} else {
					String text = userFromDB.getLogin() + " logged into system";
					connectToServer("Statement", "User ", text);
					loadUserMainPage(userFromDB);
				}
			}
		}

	}

	private void loadUserMainPage(User user) throws IOException {
		Stage loginPanelStage = (Stage) this.logInButton.getScene().getWindow();
		loginPanelStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/UserMainPage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Panel Użytkownika");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		UserMainPageController userMainPageController = loader.getController();
		userMainPageController.setLoginPanelStage(loginPanelStage);
		userMainPageController.setUser(user);
		userMainPageController.updateUserName();
		userMainPageController.setUserMainPageController(userMainPageController);
	}

	private void loadCourierMainPage(Courier courier) throws IOException {
		Stage loginPanelStage = (Stage) this.logInButton.getScene().getWindow();
		loginPanelStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/CourierMainPage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Panel Kuriera");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		CourierMainPageController courierMainPageController = loader.getController();
		courierMainPageController.setLoginPage(loginPanelStage);
		courierMainPageController.setCourier(courier);
		courierMainPageController.updateUserName();
	}

	private void loadAdminMainPage(User user) throws IOException {
		Stage loginPanelStage = (Stage) this.logInButton.getScene().getWindow();
		loginPanelStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AdminMainPage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Panel Administratora");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AdminMainPageController adminMainPageController = loader.getController();
		adminMainPageController.setLoginPanelStage(loginPanelStage);
		adminMainPageController.setUser(user);
		adminMainPageController.updateUserName();
		adminMainPageController.setAdminMainPageController(adminMainPageController);
	}

	@FXML
	public void typeLogin() {
		statement.setVisible(false);
	}

	@FXML
	public void typePassword() {
		statement.setVisible(false);
	}

	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
		mainStage.show();
	}
}
