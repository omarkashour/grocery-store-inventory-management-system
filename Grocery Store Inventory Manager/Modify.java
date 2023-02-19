import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Modify extends Add {

	Alert cantFind = new Alert(AlertType.INFORMATION);
	ModifyHandler modifyHandler = new ModifyHandler();
	ComboBox<String> brandOrNotCB;

	Button searchBtn = new Button("Search");
	Button updateBtn = new Button("Update");

	Stage modifyStage;
	Scene modifyScene;

	GridPane modifyGP;

	public Modify() {
		buildModifyScene();
	}

	public static void main(String[] args) { // main method for application debugging and testing
		launch(args);
	}

	@Override
	public void start(Stage modifyStage) throws Exception {
		this.modifyStage = modifyStage;
		buildModifyScene();
		modifyStage.setScene(modifyScene);
		modifyStage.setTitle("Update item");
		modifyStage.show();
	}

	private Scene buildModifyScene() { // builds the modify scene layout according to layout specifications
		modifyGP = new GridPane();
		String[] arr = { "Brand", "Not Brand" };
		brandOrNotCB = new ComboBox<String>(FXCollections.observableArrayList(arr));
		brandOrNotCB.setEditable(true);
		statusMessage.setText("");

		brandOrNotCB.setValue("Not Brand");
		brandOrNotCB.setOnAction(modifyHandler);

		searchBtn.setOnAction(modifyHandler);
		updateBtn.setOnAction(modifyHandler);
		cancelBtn.setOnAction(modifyHandler);

		searchBtn.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		cancelBtn.setOnAction(modifyHandler);
		updateBtn.setOnAction(modifyHandler);
		updateBtn.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");

		modifyGP.add(brand, 0, 0);
		modifyGP.add(type, 0, 1);
		modifyGP.add(quantity, 0, 2);
		modifyGP.add(price, 0, 3);
		modifyGP.add(searchBtn, 2, 1);

		modifyGP.add(brandOrNotCB, 1, 0);
		modifyGP.add(typeTF, 1, 1);
		modifyGP.add(quantityTF, 1, 2);
		modifyGP.add(priceTF, 1, 3);

		modifyGP.setHgap(25);
		modifyGP.setVgap(15);
		modifyGP.setPadding(new Insets(15));
		bottom_action_buttons.getChildren().clear();
		bottom_action_buttons.getChildren().addAll(updateBtn, cancelBtn);
		modifyGP.add(bottom_action_buttons, 1, 5);
		VBox mainVbox = new VBox(modifyGP, statusMessage);
		mainVbox.setSpacing(15);
		mainVbox.setStyle("-fx-background-color: #b8b8b8;");
		modifyScene = new Scene(mainVbox, 550, 300);
		return modifyScene;
	}

	public class ModifyHandler implements EventHandler<ActionEvent> { // handles each event in modify scene according to
																		// specifications

		@Override
		public void handle(ActionEvent c) {
			if (c.getSource() == searchBtn) {
				handleSearchBtn();
			} else if (c.getSource() == updateBtn) {
				handleUpdateBtn();
			} else if (c.getSource() == brandOrNotCB) {
				handleBrandOrNotCB();
			} else if (c.getSource() == cancelBtn) {
				closeStage();
			}

		}

		private void handleUpdateBtn() {
			if (priceTF.getText().length() == 0 || quantityTF.getText().length() == 0)
				return;
			if (brandOrNotCB.getValue().toLowerCase().equals("brand")) {
				String brand = brandNameTF.getText().trim();
				String type = typeTF.getText().trim();
				int quantity = Integer.parseInt(quantityTF.getText());
				double price = Double.parseDouble(priceTF.getText().trim());
				if (price < 0 || quantity < 0)
					return;
				if (inventory.findItem(type, false, brand) >= 0) {
					inventory.setPrice(brand, type, price);
					inventory.setQuantity(brand, type, quantity);
					statusMessage.setTextFill(Color.GREEN);
					statusMessage.setText("successfuly updated " + brand + " " + type + ".");
				} else {
					displayWarning(brand, type);
				}

			} else if (brandOrNotCB.getValue().toLowerCase().equals("not brand")) {
				String type = typeTF.getText().trim();
				int quantity = Integer.parseInt(quantityTF.getText());
				double price = Double.parseDouble(priceTF.getText().trim());
				if (price < 0 || quantity < 0)
					return;
				if (inventory.findItem(type, false) >= 0) {
					inventory.setPrice(type, price);
					inventory.setQuantity(type, quantity);
					statusMessage.setTextFill(Color.GREEN);
					statusMessage.setText("successfuly updated " + type + ".");
				} else {
					displayWarning(type);
				}
			}
		}

		private void handleSearchBtn() {
			if (brandOrNotCB.getValue().toString().toLowerCase().equals("brand")) {
				if (typeTF.getText().length() > 0 && brandNameTF.getText().length() > 0) {
					String type = typeTF.getText().toString().trim();
					String brand = brandNameTF.getText().trim().toString();
					if (inventory.findItem(type, false, brand) >= 0) {
						quantityTF.setText(inventory.getQuantity(brand, type) + "");
						priceTF.setText(inventory.getPrice(brand, type) + "");
					} else {
						displayWarning(brand, type);
					}

				}
			} else if (brandOrNotCB.getValue().toString().trim().toLowerCase().equals("not brand")) {
				if (typeTF.getText().trim().length() > 0) {
					String type = typeTF.getText().toString().trim();
					if (inventory.findItem(type, false) >= 0) {
						quantityTF.setText(inventory.getQuantity(type) + "");
						priceTF.setText(inventory.getPrice(type) + "");
					} else {
						displayWarning(type);
					}

				}
			}
		}

		private void handleBrandOrNotCB() {
			if (brandOrNotCB.getValue().toString().trim().toLowerCase().equals("brand")) {
				brandNameTF.clear();
				priceTF.clear();
				quantityTF.clear();
				typeTF.clear();
				statusMessage.setText("");
				modifyGP.getChildren().remove(searchBtn);
				modifyGP.add(searchBtn, 3, 1);
				modifyGP.add(brandNameTF, 2, 1);
			} else if (brandOrNotCB.getValue().toString().toLowerCase().trim().equals("not brand")) {
				brandNameTF.clear();
				priceTF.clear();
				quantityTF.clear();
				typeTF.clear();
				statusMessage.setText("");
				modifyGP.getChildren().remove(brandNameTF);
				modifyGP.getChildren().remove(searchBtn);
				modifyGP.add(searchBtn, 2, 1);
			}
		}

		private void closeStage() {
			modifyStage.close();
		}

		private void displayWarning(String type) {
			cantFind.setHeaderText("Warning");
			cantFind.setContentText("Can't find " + type);
			cantFind.setTitle("Warning");
			cantFind.showAndWait();
		}

		private void displayWarning(String brand, String type) {
			cantFind.setHeaderText("Warning");
			cantFind.setContentText("Can't find " + brand + " " + type);
			cantFind.setTitle("Warning");
			cantFind.showAndWait();
		}
	}

}
