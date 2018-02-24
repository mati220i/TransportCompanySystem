package pl.TransportCompanySystem.Client.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.User;

public class UserAccountDetailsController {
	@FXML
	private TextField userID, login, password, name, surname, address;
	@FXML
	private Button userPackagesButton;

	private User user;
	private Stage usersInSystem;
	private boolean isAdmin = false;

	@FXML
	public void initialize() {

	}

	public void updateData() {
		if (isAdmin)
			userPackagesButton.setVisible(false);

		userID.setText(user.getUserID().toString());
		login.setText(user.getLogin());
		password.setText(user.getPassword());
		name.setText(user.getName());
		surname.setText(user.getSurname());
		address.setText(user.getAddress());
	}

	@FXML
	public void userPackages() throws IOException, ClassNotFoundException {
		Stage userMainPageStage = (Stage) this.address.getScene().getWindow();
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
	public void back() {
		Stage stage = (Stage) userID.getScene().getWindow();
		stage.close();
		usersInSystem.show();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUsersInSystem(Stage usersInSystem) {
		this.usersInSystem = usersInSystem;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
