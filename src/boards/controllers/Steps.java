package boards.controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import boards.db.org.MyMongo;
import boards.models.org.Step;
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

public class Steps extends BaseController {

	private ObservableList<Step> stes;

	private Step activeSte;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btAdd;

	@FXML
	private ListView<Step> lvSteps;

	@FXML
	private ComboBox<Step> cmbSte;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group grpSte;

	@FXML
	private TextField txtSte;

	@FXML
	private Label lblTitle;

	@FXML
	private Button btDelete;

	@FXML
	private Button btUpdateSte;

	@FXML
	private Button btUpdate;

	@FXML
	private Button btCancelSte;

	@FXML
	private Label lblOperation;

	private MyMongo mongo;

	@FXML
	void initialize() {
		stes = FXCollections.observableArrayList();
		lvSteps.setCellFactory((lv) -> new ListCell<Step>() {
			@Override
			protected void updateItem(Step ste, boolean empty) {
				super.updateItem(ste, empty);
				setText(null);
				setGraphic(null);
				if (!empty && ste != null) {
					setText(ste.getTitle());
					Text icon = GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.5em");
					icon.setFill(Color.WHITE);
					setGraphic(icon);
				}
			}
		});
		Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "1.3em");
		deleteIcon.setFill(Color.RED);
		btDelete.setGraphic(deleteIcon);

		lvSteps.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setActiveSte(newValue);
		});

		lvSteps.setItems(stes);
		cmbSte.setItems(stes);
		try {
			loadStes();
		} catch (Exception e) {
			showErrorDialog("MongoDB Erreur", "Chargement des Développeurs", e.getMessage());
		}
		setActiveSte(null);
		edit(false);
	}

	public void btAddClick() {
		lblOperation.setText("Ajout de développeur");
		Step ste=new Step();
		ste.generateId();
		setActiveSte(ste);
		edit(true);
	}

	public void btDeleteClick() {
		if (showConfirmDialog("Suppression", "Suppression de développeur", "Confirmez-vous la suppression du développeur " + activeSte.getTitle() + " ?").equals(ButtonType.OK)) {
			mongo.remove(activeSte);
			stes.remove(activeSte);

		}
	}

	public void cancelSte() {
		edit(false);
	}

	public void loadStes() throws UnknownHostException {
		mongo = new MyMongo();
		mongo.connect("board");
		stes.addAll(mongo.load(Step.class));
	}

	/**
	 * @param activeSte
	 *            the activeSte to set
	 */
	public void setActiveSte(Step activeSte) {
		this.activeSte = activeSte;
		if (this.activeSte != null) {
			txtSte.setText(activeSte.getTitle());
			lblTitle.setText(activeSte.getTitle());
		}
		btDelete.setDisable(activeSte == null);
		btUpdate.setDisable(activeSte == null);
		edit(false);
	}

	public void updateSte() {
		activeSte.setTitle(txtSte.getText());
		mongo.save("Step", activeSte);
		if (!stes.contains(activeSte))
			stes.add(activeSte);
		lvSteps.refresh();
		cmbSte.setItems(stes);
		edit(false);
	}

	public void btUpdateClick() {
		lblOperation.setText("Modification de step");
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
