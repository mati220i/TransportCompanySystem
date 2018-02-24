package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Location;
import pl.TransportCompanySystem.Tables.Package;
import pl.TransportCompanySystem.Tables.User;

public class NewPackageController {
	@FXML
	private ComboBox<String> locationComboBox, sizeComboBox, paymentMethod;
	@FXML
	private TextField packageWeight, recipientAddress, recipientName, recipientSurname, senderAddress, senderName,
			senderSurname;
	@FXML
	private Label packageCost, warning;

	private Stage userMain;
	private User user;

	@FXML
	public void initialize() {
		warning.setVisible(false);

		ObservableList<String> options = FXCollections.observableArrayList();
		options.add("Mała");
		options.add("Średnia");
		options.add("Duża");
		sizeComboBox.setItems(options);

		ObservableList<String> paymentOptions = FXCollections.observableArrayList();
		paymentOptions.add("Gotówka");
		paymentOptions.add("Karta");
		paymentOptions.add("PayPal");
		paymentOptions.add("DotPay");
		paymentMethod.setItems(paymentOptions);
	}

	public void updateData() {
		senderName.setText(user.getName());
		senderSurname.setText(user.getSurname());
		senderAddress.setText(user.getAddress());
	}

	public void setLocations() throws IOException {
		connectToServer("Statement", "Location", "List");
		@SuppressWarnings("unchecked")
		ArrayList<Location> location = (ArrayList<Location>) connectToServer("Location", "List", null);
		ObservableList<String> locationList = FXCollections.observableArrayList();
		Iterator<Location> iterator = location.iterator();
		while (iterator.hasNext())
			locationList.add(iterator.next().getLocationName());

		locationComboBox.setItems(locationList);
	}

	@FXML
	public void newPackage() throws IOException {
		if (sizeComboBox.getSelectionModel().getSelectedItem() != null
				&& paymentMethod.getSelectionModel().getSelectedItem() != null
				&& locationComboBox.getSelectionModel().getSelectedItem() != null && checkData()) {

			try {
				Integer id = (Integer) connectToServer("Location", "ID",
						locationComboBox.getSelectionModel().getSelectedItem());

				Package pack = new Package();

				pack.setUserID(user.getUserID());
				// Zawsze pacza ląduje do kuriera-admina o nr 1
				pack.setCourierID(1);
				pack.setLocationID(id);
				pack.setPackageSize(sizeComboBox.getSelectionModel().getSelectedItem());
				pack.setPackageWeight(Double.parseDouble(packageWeight.getText()));
				pack.setWithComplaint(false);

				Double price;
				if (packageCost.getText().equals("0") || packageCost.getText().equals("0.0")) {
					price = calculatePrice();
					pack.setPrice(price);
				} else
					pack.setPrice(Double.parseDouble(packageCost.getText()));

				pack.setStatus("Paczka została nadana do oddziału w "
						+ locationComboBox.getSelectionModel().getSelectedItem() + "\n");
				pack.setPaymentMethod(paymentMethod.getSelectionModel().getSelectedItem());
				pack.setRecipientName(recipientName.getText());
				pack.setRecipientSurname(recipientSurname.getText());
				pack.setRecipientAddress(recipientAddress.getText());

				connectToServer("Statement", "Add", "Package");
				connectToServer("Add", "Package", pack);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Nadano Paczkę");
				alert.setHeaderText(null);
				alert.setContentText("Nadano paczkę");

				Optional<ButtonType> result = alert.showAndWait();
				if ((result.get() == ButtonType.OK)) {
					Stage stage = (Stage) recipientName.getScene().getWindow();
					stage.close();
					userMain.show();

				} else {
					alert.close();
				}
			} catch (NumberFormatException e) {
				warning.setVisible(true);
				warning.setText("Waga musi być liczbą!");
			}

		} else {
			warning.setVisible(true);
			warning.setText("Musisz uzupełnić wszystkie dane");
		}
	}

	@FXML
	public void pricing() {
		String cost = String.valueOf(calculatePrice());
		if (!cost.equals("0.0"))
			warning.setVisible(false);
		packageCost.setText(cost);
	}

	private double calculatePrice() {
		double cost = 0;
		try {
			double weight = Double.parseDouble(packageWeight.getText());

			if (weight <= 0)
				throw new NumberFormatException();
			else if (weight > 0 && weight <= 5)
				cost += 5;
			else if (weight > 5 && weight <= 15)
				cost += 10;
			else if (weight > 15 && weight <= 30)
				cost += 15;
			else if (weight > 30)
				cost += 30;

			if (sizeComboBox.getSelectionModel().getSelectedItem() != null) {
				if (sizeComboBox.getSelectionModel().getSelectedItem().equals("Mała"))
					cost *= 1;
				else if (sizeComboBox.getSelectionModel().getSelectedItem().equals("Średnia"))
					cost *= 1.2;
				else if (sizeComboBox.getSelectionModel().getSelectedItem().equals("Duża"))
					cost *= 1.5;
			} else {
				warning.setVisible(true);
				warning.setText("Musisz wybrać rozmiar paczki");
				return 0;
			}
		} catch (NumberFormatException e) {
			packageWeight.setText("Podana waga musi być cyfrą oraz musi być większa od 0!");
			return 0;
		}
		return cost;
	}

	@FXML
	public void back() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Anulować?");
		alert.setHeaderText(null);
		alert.setContentText("Anulować?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.get() == ButtonType.OK)) {
			Stage stage = (Stage) packageCost.getScene().getWindow();
			stage.close();
			userMain.show();
		} else {
			alert.close();
		}
	}

	private boolean checkData() {
		boolean isCorrect = true;

		if (recipientName.getText().trim().equals("")) {
			isCorrect = false;
		}
		if (recipientSurname.getText().trim().equals("")) {
			isCorrect = false;
		}
		if (recipientAddress.getText().trim().equals("")) {
			isCorrect = false;
		}
		if (packageWeight.getText().trim().equals("")) {
			isCorrect = false;
		}

		if (isCorrect)
			return true;
		else
			return false;
	}

	public void setUserMain(Stage userMain) {
		this.userMain = userMain;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
