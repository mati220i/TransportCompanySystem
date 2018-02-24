package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.User;

public class AdminMainPageController {
	@FXML
	private Button accountManagementButton, accountsInSystemButton, availableLocationButton, carsButton,
			employeeAccountsButton, logOutButton, packageInSystemButton;
	@FXML
	private Label allPackage, averageCost, averageNumberOfPackage, bigPrice, largePackage, largePackagePercent,
			maxSalary, mediumPackage, mediumPackagePercent, mediumPrice, minSalary, numberOfAccount, numberOfCars,
			numberOfDelivery, numberOfEmployees, numberOfLocation, numberOfTractors, numberOfTrucks, numberOfVans,
			smallPackage, smallPackagePercent, smallPrice, withComplaints, withComplaintsPercent, withoutComplaints,
			withoutComplaintsPercent, loginAs;
	@FXML
	private Tab packagesTab, accountsTab, locationsTab, employeesTab, carsTab;

	private Stage loginPanelStage;
	private User user;
	private AdminMainPageController adminMainPageController;

	@FXML
	public void initialize() {

	}

	public void updateUserName() {
		loginAs.setText("Zalogowano jako " + user.getLogin());
	}

	@FXML
	public void accountManagement() throws IOException {
		Stage adminMainPageStage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminMainPageStage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AdminAccountManagementPage.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Zarządzanie Kontem Użytkownika");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AdminAccountManagementPageController adminAccountManagementPageController = loader.getController();
		adminAccountManagementPageController.setAdminMainPage(adminMainPageStage);
		adminAccountManagementPageController.setUser(user);
		adminAccountManagementPageController.setUserData();
		adminAccountManagementPageController.setAdminMainPageController(adminMainPageController);
	}

	@FXML
	public void accountsInSystem() throws IOException, ClassNotFoundException {
		Stage adminPage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminPage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AccountInSystem.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki w Systemie");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AccountInSystemController accountsInSystem = loader.getController();
		accountsInSystem.setAdminStage(adminPage);
		accountsInSystem.setAccountInSystemController(accountsInSystem);
		accountsInSystem.searchUsers();
	}

	@FXML
	public void availableLocation() throws IOException, ClassNotFoundException {
		Stage adminPage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminPage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AvailableLocations.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Lokalizacje w Systemie");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AvailableLocationsController locationsInSystem = loader.getController();
		locationsInSystem.setLocationsListStage(stage);
		locationsInSystem.setAdminMainPageStage(adminPage);
		locationsInSystem.searchLocations();
		locationsInSystem.setAvailableLocationsController(locationsInSystem);
	}

	@FXML
	public void admins() throws IOException, ClassNotFoundException {
		Stage adminPage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminPage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/AdminsInSystem.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki w Systemie");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		AdminsInSystemController adminsInSystem = loader.getController();
		adminsInSystem.setAdminStage(adminPage);
		adminsInSystem.searchUsers();
		adminsInSystem.setAdminMainPageController(adminMainPageController);
		adminsInSystem.setAdminsInSystemController(adminsInSystem);
	}

	@FXML
	public void cars() throws IOException, ClassNotFoundException {
		Stage adminPage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminPage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/CarsInSystem.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Pojazdy w Systemie");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		CarsInSystemController carsInSystem = loader.getController();
		carsInSystem.setCarsListStage(stage);
		carsInSystem.setAdminMainPageStage(adminPage);
		carsInSystem.searchCars();
		carsInSystem.setCarsInSystemController(carsInSystem);
	}

	@FXML
	public void employeeAccounts() throws IOException, ClassNotFoundException {
		Stage adminPage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminPage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/EmployeeAccountInSystem.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Pracownicy w Systemie");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		EmployeeAccountInSystemController employeeAccountsInSystem = loader.getController();
		employeeAccountsInSystem.setAdminStage(adminPage);
		employeeAccountsInSystem.searchEmployees();
		employeeAccountsInSystem.setEmployeeAccountsController(employeeAccountsInSystem);
		employeeAccountsInSystem.setEmployeeStage(stage);
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
			connectToServer("Statement", "Admin ", text);
		} else {
			alert.close();
		}
	}

	@FXML
	public void packageInSystem() throws ClassNotFoundException, IOException {
		Stage adminPage = (Stage) this.accountManagementButton.getScene().getWindow();
		adminPage.hide();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Client/resources/PackagesInSystem.fxml"));
		AnchorPane root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Paczki w Systemie");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setResizable(false);

		PackagesInSystemController packageInSystem = loader.getController();
		packageInSystem.setAdminStage(adminPage);
		packageInSystem.searchPackages();
		packageInSystem.setPackagesController(packageInSystem);
	}

	@FXML
	public void clickOnPackages() throws IOException {
		if (packagesTab.isSelected()) {
			allPackage.setText(countAllPackage() + " szt.");

			smallPackage.setText(countSmallPackage() + " szt.");
			mediumPackage.setText(countMediumPackage() + " szt.");
			largePackage.setText(countLargePackage() + " szt.");

			smallPackagePercent.setText(calculateSmallPackage() + "%");
			mediumPackagePercent.setText(calculateMediumPackage() + "%");
			largePackagePercent.setText(calculateLargePackage() + "%");

			withoutComplaints.setText(countPackageWithoutComplaint() + " szt.");
			withComplaints.setText(countPackageWithComplaint() + " szt.");

			withoutComplaintsPercent.setText(calculatePackageWithoutComplaint() + "%");
			withComplaintsPercent.setText(calculatePackageWithComplaint() + "%");

			smallPrice.setText(countSmallPrice() + " szt.");
			mediumPrice.setText(countMediumPrice() + " szt.");
			bigPrice.setText(countBigPrice() + " szt.");

			averageCost.setText(countAvgPrice() + " zł");
		}
	}

	private Integer countAllPackage() throws IOException {
		connectToServer("Statement", "CountAll", "Package");
		return (Integer) connectToServer("CountAll", "Package", 0);
	}

	private Integer countSmallPackage() throws IOException {
		connectToServer("Statement", "CountSmall", "Package");
		return (Integer) connectToServer("Count", "Package", "Mała");
	}

	private Integer countMediumPackage() throws IOException {
		connectToServer("Statement", "CountMedium", "Package");
		return (Integer) connectToServer("Count", "Package", "Średnia");
	}

	private Integer countLargePackage() throws IOException {
		connectToServer("Statement", "CountLarge", "Package");
		return (Integer) connectToServer("Count", "Package", "Duża");
	}

	private Integer calculateSmallPackage() throws IOException {
		Integer all, small;
		all = countAllPackage();
		small = countSmallPackage();
		Double d = ((double) small / (double) all * 100);
		connectToServer("Statement", "CountLarge", d);
		return d.intValue();
	}

	private Integer calculateMediumPackage() throws IOException {
		Integer all, medium;
		all = countAllPackage();
		medium = countMediumPackage();
		Double d = ((double) medium / (double) all * 100);
		return d.intValue();
	}

	private Integer calculateLargePackage() throws IOException {
		Integer all, large;
		all = countAllPackage();
		large = countLargePackage();
		Double d = ((double) large / (double) all * 100);
		return d.intValue();
	}

	private Integer countPackageWithoutComplaint() throws IOException {
		connectToServer("Statement", "CountPackageWith", "Complaint");
		return (Integer) connectToServer("Count", "PackageComplaint", "Without");
	}

	private Integer countPackageWithComplaint() throws IOException {
		connectToServer("Statement", "CountPackageWithout", "Complaint");
		return (Integer) connectToServer("Count", "PackageComplaint", "With");
	}

	private Integer calculatePackageWithComplaint() throws IOException {
		Integer all, pack;
		all = countAllPackage();
		pack = countPackageWithComplaint();
		Double d = ((double) pack / (double) all * 100);
		return d.intValue();
	}

	private Integer calculatePackageWithoutComplaint() throws IOException {
		Integer all, pack;
		all = countAllPackage();
		pack = countPackageWithoutComplaint();
		Double d = ((double) pack / (double) all * 100);
		return d.intValue();
	}

	private Integer countSmallPrice() throws IOException {
		connectToServer("Statement", "CountSmall", "Price");
		return (Integer) connectToServer("Count", "Price", 10);
	}

	private Integer countMediumPrice() throws IOException {
		connectToServer("Statement", "CountMedium", "Price");
		return (Integer) connectToServer("Count", "Price", 0);
	}

	private Integer countBigPrice() throws IOException {
		connectToServer("Statement", "CountBig", "Price");
		return (Integer) connectToServer("Count", "Price", 20);
	}

	private Double countAvgPrice() throws IOException {
		connectToServer("Statement", "CountAverage", "Price");
		return (Double) connectToServer("CountAverage", "Price", 0);
	}

	@FXML
	public void clickOnAccounts() throws IOException {
		if (accountsTab.isSelected()) {
			numberOfAccount.setText(countAllAccount().toString());
			averageNumberOfPackage.setText(countAverageNumberOfPackage().toString() + " szt.");
		}
	}

	private Integer countAllAccount() throws IOException {
		connectToServer("Statement", "CountAll", "Accounts");
		return (Integer) connectToServer("CountAll", "Accounts", "User");
	}

	private Double countAverageNumberOfPackage() throws IOException {
		connectToServer("Statement", "AverageNumberOf", "Packages");
		double allPackages = (double) countAllPackage();
		double allAccounts = (double) countAllAccount();
		return (Double) (allPackages / allAccounts);
	}

	@FXML
	public void clickOnLocations() throws IOException {
		if (locationsTab.isSelected())
			numberOfLocation.setText(countAllLocations().toString());
	}

	private Integer countAllLocations() throws IOException {
		connectToServer("Statement", "CountAll", "Locations");
		return (Integer) connectToServer("CountAll", "Locations", 0);
	}

	@FXML
	public void clickOnEmployees() throws IOException {
		if (employeesTab.isSelected()) {
			numberOfEmployees.setText(countAllCouriers().toString());
			minSalary.setText(getMinSalary().toString() + " zł");
			maxSalary.setText(getMaxSalary().toString() + " zł");
		}
	}

	private Integer countAllCouriers() throws IOException {
		connectToServer("Statement", "CountAll", "Couriers");
		return (Integer) connectToServer("CountAll", "Couriers", 0);
	}

	private Integer getMinSalary() throws IOException {
		connectToServer("Statement", "GetMin", "Salary");
		return (Integer) connectToServer("GetMin", "Salary", 0);
	}

	private Integer getMaxSalary() throws IOException {
		connectToServer("Statement", "GetMax", "Salary");
		return (Integer) connectToServer("GetMax", "Salary", 0);
	}

	@FXML
	public void clickOnCars() throws IOException {
		if (carsTab.isSelected()) {
			numberOfCars.setText(countAllCars().toString() + " szt.");
			numberOfVans.setText(countVan().toString() + " szt.");
			numberOfDelivery.setText(countDelivery().toString() + " szt.");
			numberOfTrucks.setText(countTrucks().toString() + " szt.");
			numberOfTractors.setText(countTractors().toString() + " szt.");
		}
	}

	private Integer countAllCars() throws IOException {
		connectToServer("Statement", "CountAll", "Cars");
		return (Integer) connectToServer("CountAll", "Cars", 0);
	}

	private Integer countVan() throws IOException {
		connectToServer("Statement", "CountAllType", "Cars");
		return (Integer) connectToServer("CountAllType", "Cars", "Van");
	}

	private Integer countDelivery() throws IOException {
		connectToServer("Statement", "CountAllType", "Cars");
		return (Integer) connectToServer("CountAllType", "Cars", "Dostawczy");
	}

	private Integer countTrucks() throws IOException {
		connectToServer("Statement", "CountAllType", "Cars");
		return (Integer) connectToServer("CountAllType", "Cars", "Ciężarówka");
	}

	private Integer countTractors() throws IOException {
		connectToServer("Statement", "CountAllType", "Cars");
		return (Integer) connectToServer("CountAllType", "Cars", "Ciągnik Siodłowy");
	}

	public void setLoginPanelStage(Stage loginPanelStage) {
		this.loginPanelStage = loginPanelStage;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAdminMainPageController(AdminMainPageController adminMainPageController) {
		this.adminMainPageController = adminMainPageController;
	}

}
