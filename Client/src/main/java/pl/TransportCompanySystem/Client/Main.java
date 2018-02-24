package pl.TransportCompanySystem.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/pl/TransportCompanySystem/Client/resources/Main.fxml"));
		primaryStage.setTitle("Panel Logowania Firmy Transportowej");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
