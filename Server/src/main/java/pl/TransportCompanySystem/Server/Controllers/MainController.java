package pl.TransportCompanySystem.Server.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.TransportCompanySystem.Server.RequestHandler;

public class MainController {
	@FXML
	private Button onButton, offButton;
	@FXML
	private TextArea textArea;
	@FXML
	private TextField textField;
	@FXML
	private CheckBox checkBox;

	private int port = 50000;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ExecutorService executor1 = null, executor2 = null;

	private MainController mainController;

	private String startDate;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public Button getOnButton() {
		return onButton;
	}

	@FXML
	public void initialize() {
		this.offButton.setDisable(true);
	}

	class WaitForClient implements Runnable {
		public void run() {
			setTextArea("Serwer włączony na porcie " + port);
			textArea.setWrapText(true);

			try {
				serverSocket = new ServerSocket(port);
				executor1 = Executors.newFixedThreadPool(10);
				setTextArea("Oczekiwanie na klientow");

				while (true) {
					clientSocket = serverSocket.accept();
					RequestHandler requestHandler = new RequestHandler(clientSocket);

					requestHandler.setMainController((mainController));
					Runnable wokrer = requestHandler;
					executor1.execute(wokrer);

				}
			} catch (IOException e) {
				e.printStackTrace();
				setTextArea("Blad podczas odczytu");
			} finally {
				if (executor1 != null) {
					executor1.shutdown();
				}
			}
		}

	}

	@FXML
	public void turnOnServer() {
		this.offButton.setDisable(false);
		this.onButton.setDisable(true);
		this.checkBox.setSelected(false);
		this.port = Integer.parseInt(textField.getText());
		this.textField.setDisable(true);

		this.executor2 = Executors.newSingleThreadExecutor();
		executor2.execute(new WaitForClient());

		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
		this.startDate = "Logs[" + dateFormat.format(currentDate) + "]";

	}

	@FXML
	public void turnOffServer() throws IOException {
		this.offButton.setDisable(true);
		this.onButton.setDisable(false);
		this.checkBox.setSelected(false);

		executor1.shutdown();
		executor2.shutdown();
		try {
			if (!serverSocket.isClosed())
				serverSocket.close();
			if (!clientSocket.isClosed())
				clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTextArea("Wyłączono Serwer na porcie " + this.port);
		setTextArea("Is shutdwon: " + executor1.isShutdown());
		setTextArea("Is terminated: " + executor1.isTerminated());
		saveLogsInFile();
	}

	@FXML
	public void clicked() {
		if (this.textField.isDisable())
			this.textField.setDisable(false);
		else
			this.textField.setDisable(true);
	}

	private void saveLogsInFile() throws IOException {
		setTextArea("Zapisano plik z logami serwera");

		PrintWriter serverLogsFile = new PrintWriter(this.startDate + ".txt");
		serverLogsFile.println(textArea.getText());
		serverLogsFile.close();
	}

	public void setTextArea(String text) {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String dateString = "[" + dateFormat.format(currentDate) + "]";
		this.textArea.setText(this.textArea.getText() + "\n" + dateString + ":  " + text);
	}
}
