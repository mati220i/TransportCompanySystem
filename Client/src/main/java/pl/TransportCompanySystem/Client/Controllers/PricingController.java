package pl.TransportCompanySystem.Client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PricingController {
	@FXML
	private Label costLabel, warning;
	@FXML
	private ComboBox<String> sizeComboBox;
	@FXML
	private TextField weightTextField;

	private Stage userMain;

	@FXML
	public void initialize() {
		ObservableList<String> options = FXCollections.observableArrayList();
		options.add("Mała");
		options.add("Średnia");
		options.add("Duża");
		sizeComboBox.setItems(options);
		warning.setVisible(false);
	}

	@FXML
	public void pricing() {
		String cost = String.valueOf(calculatePrice());
		if (!cost.equals("0.0"))
			warning.setVisible(false);
		costLabel.setText(cost);
	}

	private double calculatePrice() {
		double cost = 0;
		try {
			double weight = Double.parseDouble(weightTextField.getText());

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
			weightTextField.setText("Podana waga musi być cyfrą oraz musi być większa od 0!");
		}
		return cost;
	}

	@FXML
	public void back() {
		this.userMain.show();
		Stage stage = (Stage) costLabel.getScene().getWindow();
		stage.close();
	}

	public void setUserMain(Stage userMain) {
		this.userMain = userMain;
	}

}
