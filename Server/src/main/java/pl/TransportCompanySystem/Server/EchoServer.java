package pl.TransportCompanySystem.Server;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.TransportCompanySystem.Server.Controllers.MainController;

// ********************************************************** GUI SERWERA *************************************************

public class EchoServer extends Application {

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/pl/TransportCompanySystem/Server/resources/Main.fxml"));
		AnchorPane pane = loader.load();

		Scene scene = new Scene(pane);

		primaryStage.setTitle("Serwer Firma Transportowa");
		primaryStage.setX(0);
		primaryStage.setX(0);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		MainController mainController = loader.getController();
		mainController.setMainController(mainController);
	}
}
