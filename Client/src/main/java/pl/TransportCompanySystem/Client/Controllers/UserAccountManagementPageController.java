package pl.TransportCompanySystem.Client.Controllers;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.User;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

public class UserAccountManagementPageController {
	@FXML
	private TextField addressTextField, loginTextField, nameTextField, passwordTextField, surnameTextField;
	@FXML
	private Label loginStatement, passwordStatement, nameStatement, surnameStatement, addressStatement;
	@FXML
	private CheckBox editLoginCheckBox;

	private Stage userMainPage;
	private User user;
	private UserMainPageController userMainPageController;
	private AdminsInSystemController adminsInSystemController;
	private AccountInSystemController accountInSystemController;
	private AdminMainPageController adminMainPageController;
	private boolean isAdmin, isUserEdit;

	@FXML
	public void initialize() {
		loginStatement.setVisible(false);
		passwordStatement.setVisible(false);
		nameStatement.setVisible(false);
		surnameStatement.setVisible(false);
		addressStatement.setVisible(false);
	}

	public void setUserData() {
		addressTextField.setText(user.getAddress());
		loginTextField.setText(user.getLogin());
		nameTextField.setText(user.getName());
		passwordTextField.setText(user.getPassword());
		surnameTextField.setText(user.getSurname());
	}

	@FXML
	public void editLogin() {
		if (editLoginCheckBox.isSelected())
			loginTextField.setDisable(false);
		else
			loginTextField.setDisable(true);
	}

	@FXML
	public void editData() throws IOException, ClassNotFoundException {
		if (this.checkData()) {
			if (this.checkLoginInDB()) {
				user.setAddress(addressTextField.getText().trim());
				user.setLogin(loginTextField.getText().trim());
				user.setName(nameTextField.getText().trim());
				user.setPassword(passwordTextField.getText().trim());
				user.setSurname(surnameTextField.getText().trim());

				connectToServer("Statemnet", "Edit", "User");
				connectToServer("Edit", "User", user);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Edytowano Użytkownika");
				alert.setHeaderText(null);
				alert.setContentText("Dane użytkownika " + user.getLogin() + " zostały zaktualizowane");

				Optional<ButtonType> result = alert.showAndWait();
				if ((result.get() == ButtonType.OK)) {
					Stage stage = (Stage) addressTextField.getScene().getWindow();
					stage.close();
					userMainPage.show();

					if (isAdmin) {
						if (isUserEdit)
							accountInSystemController.searchUsers();
						else {
							adminsInSystemController.refresh();
							adminMainPageController.setUser(user);
							adminMainPageController.updateUserName();
						}
					} else
						userMainPageController.updateUserName();
				} else {
					alert.close();
				}
			} else {
				loginStatement.setText("Login zajęty");
				loginStatement.setVisible(true);
			}
		}
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

	private boolean checkLoginInDB() throws IOException {
		if (editLoginCheckBox.isSelected()) {
			connectToServer("Statement", "Free", "Login");
			User user = (User) connectToServer("Free", "Login", loginTextField.getText().trim());
			if (user == null) {
				return true;
			} else {
				return false;
			}
		} else
			return true;
	}

	private boolean checkData() {
		boolean isCorrect = true;

		if (loginTextField.getText().trim().equals("")) {
			loginStatement.setText("Podaj Login");
			loginStatement.setVisible(true);
			isCorrect = false;
		}
		if (passwordTextField.getText().trim().equals("")) {
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
	public void cancel() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Anuluj edycję");
		alert.setHeaderText(null);
		alert.setContentText("Wyjść z edycji danych bez zapisywania?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			this.userMainPage.show();
			Stage stage = (Stage) addressTextField.getScene().getWindow();
			stage.close();
		} else {
			alert.close();
		}
	}

	public void setUserMainPage(Stage userMainPage) {
		this.userMainPage = userMainPage;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserMainPageController(UserMainPageController userMainPageController) {
		this.userMainPageController = userMainPageController;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setAdminMainPageController(AdminMainPageController adminMainPageController) {
		this.adminMainPageController = adminMainPageController;
	}

	public void setAdminsInSystemController(AdminsInSystemController adminsInSystemController) {
		this.adminsInSystemController = adminsInSystemController;
	}

	public void setUserEdit(boolean isUserEdit) {
		this.isUserEdit = isUserEdit;
	}

	public void setAccountInSystemController(AccountInSystemController accountInSystemController) {
		this.accountInSystemController = accountInSystemController;
	}

}
