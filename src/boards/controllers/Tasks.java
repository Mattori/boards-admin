package boards.controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import boards.db.org.MyMongo;
import boards.models.org.Task;
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

public class Tasks extends BaseController {

	private ObservableList<Task> tass;

	private Task activeTas;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btAdd;

	@FXML
	private ListView<Task> lvTasks;

	@FXML
	private ComboBox<Task> cmbTas;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group grpTas;

	@FXML
	private TextField txtTas;

	@FXML
	private Label lblContent;

	@FXML
	private Button btDelete;

	@FXML
	private Button btUpdateTas;

	@FXML
	private Button btUpdate;

	@FXML
	private Button btCancelTas;

	@FXML
	private Label lblOperation;

	private MyMongo mongo;

	@FXML
	void initialize() {
		tass = FXCollections.observableArrayList();
		lvTasks.setCellFactory((lv) -> new ListCell<Task>() {
			@Override
			protected void updateItem(Task tas, boolean empty) {
				super.updateItem(tas, empty);
				setText(null);
				setGraphic(null);
				if (!empty && tas != null) {
					setText(tas.getContent());
					Text icon = GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.5em");
					icon.setFill(Color.WHITE);
					setGraphic(icon);
				}
			}
		});
		Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "1.3em");
		deleteIcon.setFill(Color.RED);
		btDelete.setGraphic(deleteIcon);

		lvTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setActiveTas(newValue);
		});

		lvTasks.setItems(tass);
		cmbTas.setItems(tass);
		try {
			loadTass();
		} catch (Exception e) {
			showErrorDialog("MongoDB Erreur", "Chargement des Tâches", e.getMessage());
		}
		setActiveTas(null);
		edit(false);
	}

	public void btAddClick() {
		lblOperation.setText("Ajout de développeur");
		Task tas=new Task();
		tas.generateId();
		setActiveTas(tas);
		edit(true);
	}

	public void btDeleteClick() {
		if (showConfirmDialog("Suppression", "Suppression de développeur", "Confirmez-vous la suppression du développeur " + activeTas.getContent() + " ?").equals(ButtonType.OK)) {
			mongo.remove(activeTas);
			tass.remove(activeTas);

		}
	}

	public void cancelTas() {
		edit(false);
	}

	public void loadTass() throws UnknownHostException {
		mongo = new MyMongo();
		mongo.connect("board");
		tass.addAll(mongo.load(Task.class));
	}

	/**
	 * @param activeTas
	 *            the activeTas to set
	 */
	public void setActiveTas(Task activeTas) {
		this.activeTas = activeTas;
		if (this.activeTas != null) {
			txtTas.setText(activeTas.getContent());
			lblContent.setText(activeTas.getContent());
		}
		btDelete.setDisable(activeTas == null);
		btUpdate.setDisable(activeTas == null);
		edit(false);
	}

	public void updateTas() {
		activeTas.setContent(txtTas.getText());
		mongo.save("Task", activeTas);
		if (!tass.contains(activeTas))
			tass.add(activeTas);
		lvTasks.refresh();
		cmbTas.setItems(tass);
		edit(false);
	}

	public void btUpdateClick() {
		lblOperation.setText("Modification de la tâche");
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
