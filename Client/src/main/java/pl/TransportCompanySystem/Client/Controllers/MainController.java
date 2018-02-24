package pl.TransportCompanySystem.Client.Controllers;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Button logInButton, registerButton, quitButton;

	@FXML
	public void initialize() {

	}

	@FXML
	public void logIn() throws IOException {
		Stage mainStage = (Stage) this.registerButton.getScene().getWindow();
		mainStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/LoginPanel.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Panel Logowania do Firmy Transportowej");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		LoginPanelController loginPanelController = loader.getController();
		loginPanelController.setMainStage(mainStage);
	}

	@FXML
	public void register() throws IOException {
		Stage mainStage = (Stage) this.registerButton.getScene().getWindow();
		mainStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/RegistrationPanel.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Panel Rejestracji nowego użytkownika do Firmy Transportowej");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		RegistrationPanelController registrationPanelController = loader.getController();
		registrationPanelController.setMainStage(mainStage);
	}

	@FXML
	public void quit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Wyłącz program");
		alert.setHeaderText(null);
		alert.setContentText("Wyłączyć program?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			System.exit(0);
		} else {
			alert.close();
		}
	}
}
