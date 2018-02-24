package pl.TransportCompanySystem.Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.User;
import pl.TransportCompanySystem.Tables.Package;

public class UserPackageDetailsController {
	@FXML
	private CheckBox complaint;
	@FXML
	private TextField packageID, packageSize, paymentMethod, price, recipientAddress, recipientName, recipientSurname,
			senderAddress, senderName, senderSurname, packageWeight;
	@FXML
	private TextArea status;

	private Stage myPackage;
	private Package pack;
	private User user;

	@FXML
	public void initialize() {
		complaint.setDisable(true);
		// Ustawia widoczność checkboxa bez możliwości edycji
		complaint.setStyle("-fx-opacity: 1");
	}

	public void updateDate() {
		complaint.setSelected(pack.getWithComplaint());
		packageID.setText(pack.getPackageID().toString());
		packageSize.setText(pack.getPackageSize());
		paymentMethod.setText(pack.getPaymentMethod());
		price.setText(pack.getPrice().toString());
		recipientAddress.setText(pack.getRecipientAddress());
		recipientName.setText(pack.getRecipientName());
		recipientSurname.setText(pack.getRecipientSurname());
		senderAddress.setText(user.getAddress());
		senderName.setText(user.getName());
		senderSurname.setText(user.getSurname());
		status.setText(pack.getStatus());
		complaint.setDisable(true);
		packageWeight.setText(pack.getPackageWeight().toString());
	}

	@FXML
	public void back() {
		Stage stage = (Stage) senderAddress.getScene().getWindow();
		stage.close();
		myPackage.show();
	}

	public void setMyPackage(Stage myPackage) {
		this.myPackage = myPackage;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
