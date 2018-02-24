package pl.TransportCompanySystem.Client.Controllers;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Courier;
import pl.TransportCompanySystem.Tables.Location;
import pl.TransportCompanySystem.Tables.Package;
import pl.TransportCompanySystem.Tables.User;

public class PackageDetailsController {
	@FXML
	private CheckBox complaint;
	@FXML
	private TextField packageID, packageSize, paymentMethod, price, recipientAddress, recipientName, recipientSurname,
			senderAddress, senderName, senderSurname, packageWeight, courierName, courierSurname, currentLocation;
	@FXML
	private TextArea status;

	private Stage packagesInSystem;
	private Package pack;

	@FXML
	public void initialize() {
		complaint.setDisable(true);
		// Ustawia widoczność checkboxa bez możliwości edycji
		complaint.setStyle("-fx-opacity: 1");
	}

	public void updateData() throws IOException {
		User userFromDB = getUserFromDB();
		Courier courierFromDB = getCourierFromDB();
		Location locationFromDB = getLocationFromDB();

		complaint.setSelected(pack.getWithComplaint());
		packageID.setText(pack.getPackageID().toString());
		packageSize.setText(pack.getPackageSize());
		paymentMethod.setText(pack.getPaymentMethod());
		price.setText(pack.getPrice().toString());
		recipientAddress.setText(pack.getRecipientAddress());
		recipientName.setText(pack.getRecipientName());
		recipientSurname.setText(pack.getRecipientSurname());
		senderAddress.setText(userFromDB.getAddress());
		senderName.setText(userFromDB.getName());
		senderSurname.setText(userFromDB.getSurname());
		status.setText(pack.getStatus());
		complaint.setDisable(true);
		packageWeight.setText(pack.getPackageWeight().toString());
		currentLocation.setText(locationFromDB.getLocationName());
		courierName.setText(courierFromDB.getName());
		courierSurname.setText(courierFromDB.getSurname());
	}

	private User getUserFromDB() throws IOException {
		connectToServer("Statement", "Get", "User");
		return (User) connectToServer("Get", "User", pack.getUserID());
	}

	private Courier getCourierFromDB() throws IOException {
		connectToServer("Statement", "Get", "Courier");
		return (Courier) connectToServer("Get", "Courier", pack.getCourierID());
	}

	private Location getLocationFromDB() throws IOException {
		connectToServer("Statement", "Get", "Location");
		return (Location) connectToServer("Get", "Location", pack.getLocationID());
	}

	@FXML
	public void back() {
		Stage stage = (Stage) senderAddress.getScene().getWindow();
		stage.close();
		packagesInSystem.show();
	}

	public void setPackagesInSystem(Stage packageInSystem) {
		this.packagesInSystem = packageInSystem;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

}
