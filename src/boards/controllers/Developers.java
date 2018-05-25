package boards.controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import boards.db.org.MyMongo;
import boards.models.org.Developer;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Developers extends BaseController {

	private ObservableList<Developer> devs;

	private Developer activeDev;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btAdd;

	@FXML
	private ListView<Developer> lvDevelopers;

	@FXML
	private ComboBox<Developer> cmbDev;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group grpDev;

	@FXML
	private TextField txtDev;

	@FXML
	private Label lblIdentity;

	@FXML
	private Button btDelete;

	@FXML
	private Button btUpdateDev;

	@FXML
	private Button btUpdate;

	@FXML
	private Button btCancelDev;

	@FXML
	private Label lblOperation;

	private MyMongo mongo;

	@FXML
	void initialize() {
		devs = FXCollections.observableArrayList();
		lvDevelopers.setCellFactory((lv) -> new ListCell<Developer>() {
			@Override
			protected void updateItem(Developer dev, boolean empty) {
				super.updateItem(dev, empty);
				setText(null);
				setGraphic(null);
				if (!empty && dev != null) {
					setText(dev.getIdentity());
					Text icon = GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.5em");
					icon.setFill(Color.WHITE);
					setGraphic(icon);
				}
			}
		});
		Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "1.3em");
		deleteIcon.setFill(Color.RED);
		btDelete.setGraphic(deleteIcon);

		lvDevelopers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setActiveDev(newValue);
		});

		lvDevelopers.setItems(devs);
		cmbDev.setItems(devs);
		try {
			loadDevs();
		} catch (Exception e) {
			showErrorDialog("MongoDB Erreur", "Chargement des Développeurs", e.getMessage());
		}
		setActiveDev(null);
		edit(false);
	}

	public void btAddClick() {
		lblOperation.setText("Ajout de développeur");
		Developer dev=new Developer();
		dev.generateId();
		setActiveDev(dev);
		edit(true);
	}

	public void btDeleteClick() {
		if (showConfirmDialog("Suppression", "Suppression de développeur", "Confirmez-vous la suppression du développeur " + activeDev.getIdentity() + " ?").equals(ButtonType.OK)) {
			mongo.remove(activeDev);
			devs.remove(activeDev);

		}
	}

	public void cancelDev() {
		edit(false);
	}

	public void loadDevs() throws UnknownHostException {
		mongo = new MyMongo();
		mongo.connect("board");
		devs.addAll(mongo.load(Developer.class));
	}

	/**
	 * @param activeDev
	 *            the activeDev to set
	 */
	public void setActiveDev(Developer activeDev) {
		this.activeDev = activeDev;
		if (this.activeDev != null) {
			txtDev.setText(activeDev.getIdentity());
			lblIdentity.setText(activeDev.getIdentity());
		}
		btDelete.setDisable(activeDev == null);
		btUpdate.setDisable(activeDev == null);
		edit(false);
	}

	public void updateDev() {
		activeDev.setIdentity(txtDev.getText());
		mongo.save("Developer", activeDev);
		if (!devs.contains(activeDev))
			devs.add(activeDev);
		lvDevelopers.refresh();
		cmbDev.setItems(devs);
		edit(false);
	}

	public void btUpdateClick() {
		lblOperation.setText("Modification de développeur");
		edit(true);
	}

	public void edit(boolean edition) {
		ObservableList<Node> childs = stackPane.getChildren();
		int toTop = 0, toLast = 1;
		if (!edition) {
			toTop = 1;
			toLast = 0;
		}
		childs.get(toTop).toFront();
		childs.get(toLast).toBack();
		childs.get(toTop).setVisible(true);
		childs.get(toLast).setVisible(false);
	}
}
