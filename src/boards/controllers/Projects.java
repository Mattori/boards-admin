package boards.controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import boards.db.org.MyMongo;
import boards.models.org.Project;
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

public class Projects extends BaseController {

	private ObservableList<Project> pros;

	private Project activePro;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btAdd;

	@FXML
	private ListView<Project> lvProjects;

	@FXML
	private ComboBox<Project> cmbPro;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group grpPro;

	@FXML
	private TextField txtPro;

	@FXML
	private Label lblName;

	@FXML
	private Button btDelete;

	@FXML
	private Button btUpdatePro;

	@FXML
	private Button btUpdate;

	@FXML
	private Button btCancelPro;

	@FXML
	private Label lblOperation;

	private MyMongo mongo;

	@FXML
	void initialize() {
		pros = FXCollections.observableArrayList();
		lvProjects.setCellFactory((lv) -> new ListCell<Project>() {
			@Override
			protected void updateItem(Project pro, boolean empty) {
				super.updateItem(pro, empty);
				setText(null);
				setGraphic(null);
				if (!empty && pro != null) {
					setText(pro.getName());
					Text icon = GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.5em");
					icon.setFill(Color.WHITE);
					setGraphic(icon);
				}
			}
		});
		Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "1.3em");
		deleteIcon.setFill(Color.RED);
		btDelete.setGraphic(deleteIcon);

		lvProjects.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setActivePro(newValue);
		});

		lvProjects.setItems(pros);
		cmbPro.setItems(pros);
		try {
			loadPros();
		} catch (Exception e) {
			showErrorDialog("MongoDB Erreur", "Chargement des Projets", e.getMessage());
		}
		setActivePro(null);
		edit(false);
	}

	public void btAddClick() {
		lblOperation.setText("Ajout de projet");
		Project pro=new Project();
		pro.generateId();
		setActivePro(pro);
		edit(true);
	}

	public void btDeleteClick() {
		if (showConfirmDialog("Suppression", "Suppression de projet", "Confirmez-vous la suppression du projet " + activePro.getName() + " ?").equals(ButtonType.OK)) {
			mongo.remove(activePro);
			pros.remove(activePro);

		}
	}

	public void cancelPro() {
		edit(false);
	}

	public void loadPros() throws UnknownHostException {
		mongo = new MyMongo();
		mongo.connect("board");
		pros.addAll(mongo.load(Project.class));
	}

	/**
	 * @param activePro
	 *            the activePro to set
	 */
	public void setActivePro(Project activePro) {
		this.activePro = activePro;
		if (this.activePro != null) {
			txtPro.setText(activePro.getName());
			lblName.setText(activePro.getName());
		}
		btDelete.setDisable(activePro == null);
		btUpdate.setDisable(activePro == null);
		edit(false);
	}

	public void updatePro() {
		activePro.setName(txtPro.getText());
		mongo.save("Project", activePro);
		if (!pros.contains(activePro))
			pros.add(activePro);
		lvProjects.refresh();
		cmbPro.setItems(pros);
		edit(false);
	}

	public void btUpdateClick() {
		lblOperation.setText("Modification de projet");
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
