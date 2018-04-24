package boards.controllers;

import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public abstract class BaseController {

	protected Alert initDialog(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource(Main.styleSheet).toExternalForm());
		return alert;
	}

	public void showErrorDialog(String title, String header, String content) {
		initDialog(AlertType.ERROR, title, header, content).showAndWait();
	}

	public ButtonType showConfirmDialog(String title, String header, String content) {
		return initDialog(AlertType.CONFIRMATION, title, header, content).showAndWait().get();
	}
}
