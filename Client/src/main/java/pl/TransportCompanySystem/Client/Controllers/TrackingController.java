package pl.TransportCompanySystem.Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static pl.TransportCompanySystem.Client.ClientSocket.connectToServer;

import java.io.IOException;

public class TrackingController {
	@FXML
	private TextField packageID;
	@FXML
	private TextArea status;

	private Stage mainStage;

	@FXML
	public void initialize() {

	}

	@FXML
	public void search() throws IOException {
		if (!packageID.getText().equals("")) {
			try {
				Integer packID = Integer.parseInt(packageID.getText());
				connectToServer("Statement", "Package", "Status");
				String stat = (String) connectToServer("Package", "Status", packID);
	
				if (stat != null)
					status.setText(stat);
				else {
					status.setText("Nie ma takiej paczki w systemie");
				}
			} catch (NumberFormatException e) {
				packageID.setText("Numer Paczki musi być liczbą!");
			}
		} else {
			status.setText("Musisz podać nr paczki");
		}
	}

	public void back() {
		this.mainStage.show();
		Stage stage = (Stage) packageID.getScene().getWindow();
		stage.close();
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
}
