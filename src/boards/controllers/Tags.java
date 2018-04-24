package boards.controllers;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import boards.db.org.MyMongo;
import boards.models.org.Tag;
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

public class Tags extends BaseController {

	private ObservableList<Tag> tags;

	private Tag activeTag;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btAdd;

	@FXML
	private ListView<Tag> lvTags;

	@FXML
	private ComboBox<Tag> cmbTag;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group grpTag;

	@FXML
	private TextField txtTag;

	@FXML
	private Label lblTitle;

	@FXML
	private Button btDelete;

	@FXML
	private Button btUpdateTag;

	@FXML
	private Button btUpdate;

	@FXML
	private Button btCancelTag;

	@FXML
	private Label lblOperation;

	private MyMongo mongo;

	@FXML
	void initialize() {
		tags = FXCollections.observableArrayList();
		lvTags.setCellFactory((lv) -> new ListCell<Tag>() {
			@Override
			protected void updateItem(Tag tag, boolean empty) {
				super.updateItem(tag, empty);
				setText(null);
				setGraphic(null);
				if (!empty && tag != null) {
					setText(tag.getTitle());
					Text icon = GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.5em");
					icon.setFill(Color.WHITE);
					setGraphic(icon);
				}
			}
		});
		Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcon.REMOVE, "1.3em");
		deleteIcon.setFill(Color.RED);
		btDelete.setGraphic(deleteIcon);

		lvTags.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setActiveTag(newValue);
		});

		lvTags.setItems(tags);
		cmbTag.setItems(tags);
		try {
			loadTags();
		} catch (Exception e) {
			showErrorDialog("MongoDB Erreur", "Chargement des Tags", e.getMessage());
		}
		setActiveTag(null);
		edit(false);
	}

	public void btAddClick() {
		lblOperation.setText("Ajout de Tag");
		Tag tag=new Tag();
		tag.generateId();
		setActiveTag(tag);
		edit(true);
	}

	public void btDeleteClick() {
		if (showConfirmDialog("Suppression", "Suppression de tag", "Confirmez-vous la suppression du tag " + activeTag.getTitle() + " ?").equals(ButtonType.OK)) {
			mongo.remove(activeTag);
			tags.remove(activeTag);
		}
	}

	public void cancelTag() {
		edit(false);
	}

	public void loadTags() throws UnknownHostException {
		mongo = new MyMongo();
		mongo.connect("board");
		tags.addAll(mongo.load(Tag.class));
	}

	/**
	 * @param activeTag
	 *            the activeTag to set
	 */
	public void setActiveTag(Tag activeTag) {
		this.activeTag = activeTag;
		if (this.activeTag != null) {
			txtTag.setText(activeTag.getTitle());
			lblTitle.setText(activeTag.getTitle());
		}
		btDelete.setDisable(activeTag == null);
		btUpdate.setDisable(activeTag == null);
		edit(false);
	}

	public void updateTag() {
		activeTag.setTitle(txtTag.getText());
		mongo.save("Tag", activeTag);
		if (!tags.contains(activeTag))
			tags.add(activeTag);
		lvTags.refresh();
		cmbTag.setItems(tags);
		edit(false);
	}

	public void btUpdateClick() {
		lblOperation.setText("Modification de tag");
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
