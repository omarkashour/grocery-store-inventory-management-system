import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Delete extends Modify {
	Stage deleteStage;

	DeleteHandler deleteHandler = new DeleteHandler();

	Button deleteBtn = new Button("Delete");

	Scene deleteScene;

	GridPane deleteGP;

	public static void main(String[] args) { // main method for application debugging and testing
		launch(args);
	}

	@Override
	public void start(Stage deleteStage) throws Exception {
		this.deleteStage = deleteStage;
		buildDeleteScene();
		deleteStage.setScene(deleteScene);
		deleteStage.setTitle("Delete item");
		deleteStage.show();

	}

	private void buildDeleteScene() { // builds the delete scene layout according to layout specifications
		deleteGP = new GridPane();
		brandNameTF.clear();
		priceTF.clear();
		quantityTF.clear();
		typeTF.clear();
		statusMessage.setText("");

		brandOrNotCB.setEditable(true);
		brandOrNotCB.setOnAction(deleteHandler);

		cancelBtn.setOnAction(deleteHandler);
		deleteBtn.setOnAction(deleteHandler);
		deleteBtn.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		deleteGP.add(brand, 0, 0);
		deleteGP.add(type, 0, 1);
		deleteGP.add(quantity, 0, 2);
		deleteGP.add(price, 0, 3);

		deleteGP.add(brandOrNotCB, 1, 0);
		deleteGP.add(typeTF, 1, 1);
		deleteGP.add(quantityTF, 1, 2);
		deleteGP.add(priceTF, 1, 3);

		deleteGP.setHgap(25);
		deleteGP.setVgap(15);
		deleteGP.setPadding(new Insets(15));

		bottom_action_buttons.getChildren().clear();
		bottom_action_buttons.getChildren().addAll(deleteBtn, cancelBtn);

		deleteGP.add(bottom_action_buttons, 1, 5);
		VBox mainVbox = new VBox(deleteGP, statusMessage);
		mainVbox.setSpacing(15);
		mainVbox.setStyle("-fx-background-color: #b8b8b8;");
		deleteScene = new Scene(mainVbox, 500, 300);

	}

	public class DeleteHandler implements EventHandler<ActionEvent> { // handles each event in delete scene according to
																		// specifications

		@Override
		public void handle(ActionEvent e) {
			if (e.getSource() == brandOrNotCB) {
				handleBrandOrNotCB();
			} else if (e.getSource() == deleteBtn) {
				if (brandOrNotCB.getValue().toLowerCase().trim().equals("brand")) {
					if (typeTF.getText().length() > 0 && brandNameTF.getText().length() > 0) {
						String type = typeTF.getText().toString().trim();
						String brand = brandNameTF.getText().trim().toString();
						if (inventory.findItem(type, false, brand) >= 0) {
							inventory.remove(brand, type);
							statusMessage.setTextFill(Color.GREEN);
							statusMessage.setText("successfully deleted " + brand + " " + type);
						} else {
							statusMessage.setTextFill(Color.RED);
							statusMessage.setText("Cannot find " + brand + " " + type);
						}
					}
				}

				else if (brandOrNotCB.getValue().toLowerCase().trim().equals("not brand")) {
					if (typeTF.getText().length() > 0) {
						String type = typeTF.getText().toString().trim();
						if (inventory.findItem(type, false) >= 0) {
							inventory.remove(type);
							statusMessage.setTextFill(Color.GREEN);
							statusMessage.setText("successfully deleted " + type);
						} else {
							statusMessage.setTextFill(Color.RED);
							statusMessage.setText("Cannot find " + type);
						}

					}
				}
			} else if (e.getSource() == cancelBtn) {
				closeStage();
			}

		}

		private void closeStage() {
			deleteStage.close();
		}

		private void handleBrandOrNotCB() {
			if (brandOrNotCB.getValue().trim().toLowerCase().equals("brand")) {
				deleteGP.add(brandNameTF, 1, 4);
				deleteGP.add(brandName, 0, 4);
				brandNameTF.clear();
				priceTF.clear();
				quantityTF.clear();
				typeTF.clear();
				statusMessage.setText("");
			} else if (brandOrNotCB.getValue().trim().toLowerCase().equals("not brand")) {
				deleteGP.getChildren().remove(brandName);
				deleteGP.getChildren().remove(brandNameTF);
				statusMessage.setText("");
				priceTF.clear();
				quantityTF.clear();
				typeTF.clear();
			}
		}

	}

}
