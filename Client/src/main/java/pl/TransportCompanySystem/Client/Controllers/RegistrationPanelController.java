package pl.TransportCompanySystem.Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.User;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.Optional;

public class RegistrationPanelController {
	@FXML
	private TextField loginTextField, nameTextField, surnameTextField, addressTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button cancelButton;
	@FXML
	private Label loginStatement, passwordStatement, nameStatement, surnameStatement, addressStatement;

	private Stage mainStage;
	private boolean isAdmin;
	private AdminsInSystemController adminsInSystemController;

	@FXML
	public void initialize() {
		loginStatement.setVisible(false);
		passwordStatement.setVisible(false);
		nameStatement.setVisible(false);
		surnameStatement.setVisible(false);
		addressStatement.setVisible(false);
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@FXML
	public void createAccount() throws IOException, ClassNotFoundException {
		if (this.checkData()) {
			if (this.checkLoginInDB()) {
				User user = new User();
				user.setLogin(loginTextField.getText());
				user.setPassword(passwordField.getText().trim());
				user.setName(nameTextField.getText().trim());
				user.setSurname(surnameTextField.getText().trim());
				user.setAddress(addressTextField.getText().trim());

				if (isAdmin)
					user.setAccountType("Admin");
				else
					user.setAccountType("User");

				connectToServer("Statement", "Add", "User");
				connectToServer("Add", "User", user);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Dodano Użytkownika");
				alert.setHeaderText(null);
				alert.setContentText("Dodano użytkownika " + user.getLogin());

				Optional<ButtonType> result = alert.showAndWait();
				if ((result.get() == ButtonType.OK)) {
					Stage stage = (Stage) cancelButton.getScene().getWindow();
					stage.close();

					if (isAdmin)
						adminsInSystemController.searchUsers();

					mainStage.show();

				} else {
					alert.close();
				}
			} else {
				loginStatement.setText("Login zajęty");
				loginStatement.setVisible(true);
			}
		}
	}

	private boolean checkLoginInDB() throws IOException {
		connectToServer("Statement", "Free", "Login");
		User user = (User) connectToServer("Free", "Login", loginTextField.getText().trim());
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkData() {
		boolean isCorrect = true;

		if (loginTextField.getText().trim().equals("")) {
			loginStatement.setText("Podaj Login");
			loginStatement.setVisible(true);
			isCorrect = false;
		}
		if (passwordField.getText().trim().equals("")) {
			passwordStatement.setVisible(true);
			isCorrect = false;
		}
		if (nameTextField.getText().trim().equals("")) {
			nameStatement.setVisible(true);
			isCorrect = false;
		}
		if (surnameTextField.getText().trim().equals("")) {
			surnameStatement.setVisible(true);
			isCorrect = false;
		}
		if (addressTextField.getText().trim().equals("")) {
			addressStatement.setVisible(true);
			isCorrect = false;
		}

		if (isCorrect)
			return true;
		else
			return false;
	}

	@FXML
	public void loginEntered() {
		loginStatement.setVisible(false);
	}

	@FXML
	public void passwordEntered() {
		passwordStatement.setVisible(false);
	}

	@FXML
	public void nameEntered() {
		nameStatement.setVisible(false);
	}

	@FXML
	public void surnameEntered() {
		surnameStatement.setVisible(false);
	}

	@FXML
	public void addressEntered() {
		addressStatement.setVisible(false);
	}

	@FXML
	public void cancel() {
		this.mainStage.show();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setAdminsInSystemController(AdminsInSystemController adminsInSystemController) {
		this.adminsInSystemController = adminsInSystemController;
	}

}
