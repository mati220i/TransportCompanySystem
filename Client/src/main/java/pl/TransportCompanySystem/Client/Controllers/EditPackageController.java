package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Courier;
import pl.TransportCompanySystem.Tables.Location;
import pl.TransportCompanySystem.Tables.Package;

public class EditPackageController {
	@FXML
	private CheckBox complaint;
	@FXML
	private TextField recipientAddress, recipientName, recipientSurname, packageWeight, courierName, courierSurname;
	@FXML
	private TextArea status;
	@FXML
	private ComboBox<String> availableCouriers, paymentMethod, currentLocation, packageSize;
	@FXML
	private Label statement;

	private Stage packagesInSystem;
	private Package pack;
	private PackagesInSystemController packagesController;

	@FXML
	public void initialize() {
		statement.setVisible(false);

		ObservableList<String> paymentOptions = FXCollections.observableArrayList();
		paymentOptions.add("Gotówka");
		paymentOptions.add("Karta");
		paymentOptions.add("PayPal");
		paymentOptions.add("DotPay");
		paymentMethod.setItems(paymentOptions);

		ObservableList<String> packageSizeOptions = FXCollections.observableArrayList();
		packageSizeOptions.add("Mała");
		packageSizeOptions.add("Średnia");
		packageSizeOptions.add("Duża");
		packageSize.setItems(packageSizeOptions);
	}

	public void updateData() throws IOException {
		Courier courierFromDB = getCourierFromDB();
		Location locationFromDB = getLocationFromDB();

		complaint.setSelected(pack.getWithComplaint());
		packageSize.getSelectionModel().select(pack.getPackageSize());
		recipientAddress.setText(pack.getRecipientAddress());
		recipientName.setText(pack.getRecipientName());
		recipientSurname.setText(pack.getRecipientSurname());
		status.setText(pack.getStatus());
		packageWeight.setText(pack.getPackageWeight().toString());
		courierName.setText(courierFromDB.getName());
		courierSurname.setText(courierFromDB.getSurname());

		setCourierList();
		setLocationsList();

		paymentMethod.getSelectionModel().select(pack.getPaymentMethod());
		currentLocation.getSelectionModel().select(locationFromDB.getLocationName());
		availableCouriers.getSelectionModel().select(
				courierFromDB.getCourierID() + " " + courierFromDB.getName() + " " + courierFromDB.getSurname());
	}

	private void setCourierList() throws IOException {
		ArrayList<Courier> couriersList = getCouriersList();
		ObservableList<String> couriers = FXCollections.observableArrayList();

		Iterator<Courier> it = couriersList.iterator();
		while (it.hasNext()) {
			Courier c = it.next();
			String text = c.getCourierID() + " " + c.getName() + " " + c.getSurname();
			couriers.add(text);
		}

		availableCouriers.setItems(couriers);
	}

	private void setLocationsList() throws IOException {
		ArrayList<Location> locationsList = getLocationsList();
		ObservableList<String> availableLocations = FXCollections.observableArrayList();

		Iterator<Location> it = locationsList.iterator();
		while (it.hasNext()) {
			Location l = it.next();
			String text = l.getLocationName();
			availableLocations.add(text);
		}

		currentLocation.setItems(availableLocations);
	}

	private Courier getCourierFromDB() throws IOException {
		connectToServer("Statement", "Get", "Courier");
		return (Courier) connectToServer("Get", "Courier", pack.getCourierID());
	}

	private Location getLocationFromDB() throws IOException {
		connectToServer("Statement", "Get", "Location");
		return (Location) connectToServer("Get", "Location", pack.getLocationID());
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Courier> getCouriersList() throws IOException {
		connectToServer("Statement", "Get", "CouriersList");
		return (ArrayList<Courier>) connectToServer("Get", "CouriersList", 0);
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Location> getLocationsList() throws IOException {
		connectToServer("Statement", "Location", "List");
		return (ArrayList<Location>) connectToServer("Location", "List", 0);
	}

	@FXML
	public void selectCourier() throws IOException {
		Integer courierID = getCourierIDFromString(availableCouriers.getSelectionModel().getSelectedItem());
		Courier courier = (Courier) connectToServer("Get", "Courier", courierID);
		courierName.setText(courier.getName());
		courierSurname.setText(courier.getSurname());
	}

	private Integer getCourierIDFromString(String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			if (Character.isDigit(string.charAt(i)))
				sb.append(string.charAt(i));
		}
		Integer id = Integer.parseInt(sb.toString());
		return id;
	}

	@FXML
	public void confirm() throws IOException, ClassNotFoundException {
		if (checkData()) {
			try {
				Package editedPackage = new Package();
	
				editedPackage.setPackageID(pack.getPackageID());
				editedPackage.setUserID(pack.getUserID());
	
				Integer courierID = getCourierIDFromString(availableCouriers.getSelectionModel().getSelectedItem().trim());
				editedPackage.setCourierID(courierID);
	
				connectToServer("Statement", "Get", "LocationString");
				Location location = (Location) connectToServer("Get", "LocationString",
						currentLocation.getSelectionModel().getSelectedItem().trim());
				editedPackage.setLocationID(location.getLocationID());
				editedPackage.setPackageSize(packageSize.getSelectionModel().getSelectedItem().trim());
				editedPackage.setPackageWeight(Double.parseDouble(packageWeight.getText()));
	
				boolean isComplaint = complaint.isSelected();
				editedPackage.setWithComplaint(isComplaint);
				editedPackage.setPrice(pack.getPrice());
				editedPackage.setPaymentMethod(paymentMethod.getSelectionModel().getSelectedItem());
				editedPackage.setRecipientName(recipientName.getText());
				editedPackage.setRecipientSurname(recipientSurname.getText());
				editedPackage.setRecipientAddress(recipientAddress.getText());
				String newStatus = changeStatus(editedPackage);
				editedPackage.setStatus(newStatus);
	
				connectToServer("Statement", "Edit", "Package");
				connectToServer("Edit", "Package", editedPackage);
	
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Edytowano Paczkę");
				alert.setHeaderText(null);
				alert.setContentText("Paczka o numerze " + editedPackage.getPackageID() + " została zaktualizowana");
	
				Optional<ButtonType> result = alert.showAndWait();
				if ((result.get() == ButtonType.OK)) {
					Stage stage = (Stage) recipientAddress.getScene().getWindow();
					stage.close();
					packagesInSystem.show();
					packagesController.refresh();
				} else {
					alert.close();
				}
			}catch (NumberFormatException e) {
				packageWeight.setText("Waga Paczki musi być liczbą!");
			}
		} else {
			statement.setVisible(true);
		}
	}

	private boolean checkData() {
		boolean correct = true;

		if (recipientName.getText().equals(""))
			correct = false;
		else if (recipientSurname.getText().equals(""))
			correct = false;
		else if (recipientAddress.getText().equals(""))
			correct = false;
		else if (packageWeight.getText().equals(""))
			correct = false;

		return correct;
	}

	private String changeStatus(Package editedPackage) throws IOException {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String dateString = "[" + dateFormat.format(currentDate) + "] ";

		String newStatus = status.getText() + "\n";
		if (!pack.getCourierID().equals(editedPackage.getCourierID()))
			;
		newStatus += dateString + "Paczka została nadana kurierowi\n";
		if (!pack.getLocationID().equals(editedPackage.getLocationID()))
			newStatus += dateString + "Paczka została nadana do oddziału w "
					+ currentLocation.getSelectionModel().getSelectedItem() + "\n";

		return newStatus;
	}

	@FXML
	public void back() throws ClassNotFoundException {
		Stage stage = (Stage) recipientAddress.getScene().getWindow();
		stage.close();
		packagesInSystem.show();
		packagesController.refresh();
	}

	public void setPackagesInSystem(Stage packageInSystem) {
		this.packagesInSystem = packageInSystem;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public void setPackagesController(PackagesInSystemController packagesController) {
		this.packagesController = packagesController;
	}

}
