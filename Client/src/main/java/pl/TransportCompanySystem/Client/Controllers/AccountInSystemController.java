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
import pl.TransportCompanySystem.Tables.User;

public class AccountInSystemController {
	@FXML
	private TableView<User> userTable;
	@FXML
	private TableColumn<User, String> login, name, surname;
	@FXML
	private TableColumn<User, Integer> userID;
	@FXML
	private Button detailsButton, returnButton;

	private Stage adminStage;
	private AccountInSystemController accountInSystemController;
	
	@FXML
	public void initialize() {
		userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
		login.setCellValueFactory(new PropertyValueFactory<>("login"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));

	}

	public void searchUsers() throws ClassNotFoundException {
		try {
			connectToServer("Statement", "Get", "UsersList");
			@SuppressWarnings("unchecked")
			ObservableList<User> usersList = FXCollections
					.observableArrayList((ArrayList<User>) connectToServer("Get", "UsersList", "User"));
			addToUserTable(usersList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToUserTable(ObservableList<User> usersList) {
		userTable.setItems(usersList);
	}

	@FXML
	public void details() throws IOException {
		User user = userTable.getSelectionModel().getSelectedItem();

		if (user != null) {

			Stage usersInSystem = (Stage) this.detailsButton.getScene().getWindow();
			usersInSystem.hide();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/pl/TransportCompanySystem/Client/resources/UserAccountDetails.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Dane Użytkownika");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			UserAccountDetailsController userAccountDetailsController = loader.getController();
			userAccountDetailsController.setUser(user);
			userAccountDetailsController.setUsersInSystem(usersInSystem);
			userAccountDetailsController.updateData();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden użytkownik");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}

		}
	}

	@FXML
	public void editUser() throws IOException {
		User user = userTable.getSelectionModel().getSelectedItem();

		if (user != null) {

			Stage accountsStage = (Stage) this.detailsButton.getScene().getWindow();
			accountsStage.hide();

			FXMLLoader loader = new FXMLLoader(getClass()
					.getResource("/pl/TransportCompanySystem/Client/resources/UserAccountManagementPage.fxml"));
			AnchorPane root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Zarządzanie Kontem Użytkownika");
			stage.setScene(new Scene(root));
			stage.show();
			stage.setResizable(false);

			UserAccountManagementPageController userAccountManagementPageController = loader.getController();
			userAccountManagementPageController.setUserMainPage(accountsStage);
			userAccountManagementPageController.setAccountInSystemController(accountInSystemController);
			userAccountManagementPageController.setUser(user);
			userAccountManagementPageController.setUserData();
			userAccountManagementPageController.setAdmin(true);
			userAccountManagementPageController.setUserEdit(true);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden użytkownik");

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
		User user = userTable.getSelectionModel().getSelectedItem();

		if (user != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usunąć Użytkownika");
			alert.setHeaderText(null);
			alert.setContentText("Usunąć użytkownika o loginie " + user.getLogin() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				connectToServer("Statement", "Delete", "User");
				connectToServer("Delete", "User", user.getUserID());
				refresh();
			} else {
				alert.close();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText("Nie został wybrany żaden użytkownik");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {

			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void refresh() throws ClassNotFoundException {
		searchUsers();
	}

	public void setAdminStage(Stage adminStage) {
		this.adminStage = adminStage;
	}

	public void setAccountInSystemController(AccountInSystemController accountInSystemController) {
		this.accountInSystemController = accountInSystemController;
	}
	
	
}
