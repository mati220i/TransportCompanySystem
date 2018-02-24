package pl.TransportCompanySystem.Client.Controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Tables.Package;
import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

public class EditPackageCourierController {
	@FXML
	private CheckBox complaint;
	@FXML
	private TextArea signature;
	@FXML
	private Label statement;

	private Package pack;
	private Stage courierPackages;
	private CourierPackagesController courierPackagesController;

	@FXML
	public void initialize() {
		statement.setVisible(false);
	}

	@FXML
	public void confirm() throws IOException, ClassNotFoundException {
		if (isCorrect()) {
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			String dateString = "[" + dateFormat.format(currentDate) + "] ";

			String newStatus = pack.getStatus() + "\n" + dateString + "Paczka została odebrana przez: "
					+ signature.getText();
			pack.setStatus(newStatus);
			pack.setWithComplaint(complaint.isSelected());

			connectToServer("Statement", "Edit", "Package");
			connectToServer("Edit", "Package", pack);
			connectToServer("Statement", "Edit", "PackageCourier");
			connectToServer("Edit", "PackageCourier", pack.getPackageID());

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Dostarczono Paczkę");
			alert.setHeaderText(null);
			alert.setContentText("Paczka o numerze " + pack.getPackageID() + " została dostarczona");

			Optional<ButtonType> result = alert.showAndWait();
			if ((result.get() == ButtonType.OK)) {
				Stage stage = (Stage) complaint.getScene().getWindow();
				stage.close();
				courierPackages.show();
				courierPackagesController.searchPackages();
			} else {
				alert.close();
			}
		}
	}

	public boolean isCorrect() {
		if (signature.getText().equals("")) {
			statement.setVisible(true);
			return false;
		} else
			return true;
	}

	@FXML
	public void signatureType() {
		statement.setVisible(false);
	}

	@FXML
	public void back() {
		courierPackages.show();
		Stage stage = (Stage) complaint.getScene().getWindow();
		stage.close();
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public void setCourierPackages(Stage courierPackages) {
		this.courierPackages = courierPackages;
	}

	public void setCourierPackagesController(CourierPackagesController courierPackagesController) {
		this.courierPackagesController = courierPackagesController;
	}

}
