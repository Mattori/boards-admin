package boards.controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import boards.db.org.MyMongo;
import boards.models.org.Storie;
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

public class Stories extends BaseController {

	private ObservableList<Storie> stos;

	private Storie activeSto;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btAdd;

	@FXML
	private ListView<Storie> lvStories;

	@FXML
	private ComboBox<Storie> cmbSto;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group grpSto;

	@FXML
	private TextField txtSto;

	@FXML
	private Label lblCode;

	@FXML
	private Button btDelete;

	@FXML
	private Button btUpdateSto;

	@FXML
	private Button btUpdate;

	@FXML
	private Button btCancelSto;

	@FXML
	private Label lblOperation;

	private MyMongo mongo;

	@FXML
	void initialize() {
		stos = FXCollections.observableArrayList();
		lvStories.setCellFactory((lv) -> new ListCell<Storie>() {
			@Override
			protected void updateItem(Storie sto, boolean empty) {
				super.updateItem(sto, empty);
				setText(null);
				setGraphic(null);
				if (!empty && sto != null) {
					setText(sto.getCode());
					Text icon = GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.5em");
					icon.setFill(Color.WHITE);
					setGraphic(icon);
				}
			}
		});
		Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "1.3em");
		deleteIcon.setFill(Color.RED);
		btDelete.setGraphic(deleteIcon);

		lvStories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setActiveSto(newValue);
		});

		lvStories.setItems(stos);
		cmbSto.setItems(stos);
		try {
			loadStos();
		} catch (Exception e) {
			showErrorDialog("MongoDB Erreur", "Chargement des Développeurs", e.getMessage());
		}
		setActiveSto(null);
		edit(false);
	}

	public void btAddClick() {
		lblOperation.setText("Ajout de Story");
		Storie sto=new Storie();
		sto.generateId();
		setActiveSto(sto);
		edit(true);
	}

	public void btDeleteClick() {
		if (showConfirmDialog("Suppression", "Suppression de Story", "Confirmez-vous la suppression de la Story " + activeSto.getCode() + " ?").equals(ButtonType.OK)) {
			mongo.remove(activeSto);
			stos.remove(activeSto);

		}
	}

	public void cancelSto() {
		edit(false);
	}

	public void loadStos() throws UnknownHostException {
		mongo = new MyMongo();
		mongo.connect("board");
		stos.addAll(mongo.load(Storie.class));
	}

	/**
	 * @param activeSto
	 *            the activeSto to set
	 */
	public void setActiveSto(Storie activeSto) {
		this.activeSto = activeSto;
		if (this.activeSto != null) {
			txtSto.setText(activeSto.getCode());
			lblCode.setText(activeSto.getCode());
		}
		btDelete.setDisable(activeSto == null);
		btUpdate.setDisable(activeSto == null);
		edit(false);
	}

	public void updateSto() {
		activeSto.setCode(txtSto.getText());
		mongo.save("Storie", activeSto);
		if (!stos.contains(activeSto))
			stos.add(activeSto);
		lvStories.refresh();
		cmbSto.setItems(stos);
		edit(false);
	}

	public void btUpdateClick() {
		lblOperation.setText("Modification de la story");
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
