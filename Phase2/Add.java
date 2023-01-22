import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Add extends InventoryDriver {
	AddHandler addHandler = new AddHandler();
	HBox bottom_action_buttons = new HBox();
	
	// -------------- Labels initialization --------------

	Label brand = new Label("Brand");
	Label type = new Label("Type");
	Label quantity = new Label("Quantity");
	Label price = new Label("Price");
	Label brandName = new Label("Brand Name");
	Label statusMessage = new Label();

	// -------------- GridPanes initialization --------------

	GridPane mainGridPane;

	// -------------- RadioButtons initialization --------------

	RadioButton yesBtn = new RadioButton();
	RadioButton noBtn = new RadioButton();
	ToggleGroup tGroup = new ToggleGroup();

	// -------------- TextFields initialization ---------------
	TextField typeTF = new TextField();
	TextField quantityTF = new TextField();
	TextField priceTF = new TextField();
	TextField brandNameTF = new TextField();

	// -------------- Buttons initialization --------------

	Button saveBtn = new Button("Save");
	Button cancelBtn = new Button("Cancel");
	
	Stage stage;
	Scene addScene;

	public Add() {   // builds the add scene so it can be modified in subclasses with no null pointer exception
		buildAddScene();
	}

	private void buildAddScene() {  // builds the add scene layout according to layout specifications
		mainGridPane = new GridPane();
		yesBtn = new RadioButton("Yes");
		noBtn = new RadioButton("No");
		brandNameTF.clear();
		priceTF.clear();
		quantityTF.clear();
		typeTF.clear();
		statusMessage.setText("");
		tGroup.getToggles().addAll(yesBtn, noBtn);
		tGroup.selectToggle(noBtn);

		typeTF.setPromptText("Type");
		brandNameTF.setPromptText("Brand Name");
		
		quantityTF.textProperty().addListener(new ChangeListener<String>() { // set the quantity text field to numerical values only
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					quantityTF.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		HBox radiohbox = new HBox(yesBtn, noBtn);
		radiohbox.setSpacing(15);

		saveBtn.setOnAction(addHandler);
		cancelBtn.setOnAction(addHandler);

		saveBtn.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		cancelBtn.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");

		yesBtn.setOnAction(addHandler);
		noBtn.setOnAction(addHandler);

		mainGridPane.add(brand, 0, 0);
		mainGridPane.add(type, 0, 1);
		mainGridPane.add(quantity, 0, 2);
		mainGridPane.add(price, 0, 3);

		mainGridPane.add(radiohbox, 1, 0);
		mainGridPane.add(typeTF, 1, 1);
		mainGridPane.add(quantityTF, 1, 2);
		mainGridPane.add(priceTF, 1, 3);

		mainGridPane.setHgap(25);
		mainGridPane.setVgap(15);
		mainGridPane.setPadding(new Insets(15));

		bottom_action_buttons.setSpacing(15);
		bottom_action_buttons.getChildren().clear();
		bottom_action_buttons.getChildren().addAll(saveBtn, cancelBtn);
		bottom_action_buttons.setAlignment(Pos.CENTER);

		statusMessage.setFont(new Font("Courier New", 20));
		statusMessage.setStyle("-fx-text-fill: red");
		VBox mainVbox = new VBox(mainGridPane, statusMessage);
		mainVbox.setSpacing(15);
		mainGridPane.add(bottom_action_buttons, 1, 5);
		mainVbox.setStyle("-fx-background-color: #b8b8b8;");
		addScene = new Scene(mainVbox, 400, 300);
	}

	public static void main(String[] args) { // main method for application debugging and testing
		launch(args);
	}

	@Override
	public void start(Stage addStage) throws Exception {
		this.stage = addStage;
		buildAddScene();
		addStage.setScene(addScene);
		addStage.setTitle("Add item");
		addStage.show();

	}

	// each stage has its own handler so the application is more dynamic and less dependent
	public class AddHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) { // handles each event in add scene according to layout specifications
			if (e.getSource() == saveBtn) {
				handleSaveBtn();
			} else if (e.getSource() == cancelBtn) {
				closeStage();
			} else if (e.getSource() == yesBtn) {
				handleYesBtn();
			} else if (e.getSource() == noBtn) {
				handleNoBtn();
			}
		}

		private void handleNoBtn() {
			mainGridPane.getChildren().remove(brandName);
			mainGridPane.getChildren().remove(brandNameTF);
			statusMessage.setText("");
			priceTF.clear();
			quantityTF.clear();
			typeTF.clear();
		}

		private void handleYesBtn() {
			mainGridPane.add(brandNameTF, 1, 4);
			mainGridPane.add(brandName, 0, 4);
			brandNameTF.clear();
			priceTF.clear();
			quantityTF.clear();
			typeTF.clear();
			statusMessage.setText("");
		}

		private void handleSaveBtn() {
			if (priceTF.getText().trim().length() == 0 || quantityTF.getText().trim().length() == 0)
				return;
			statusMessage.setText("");
			if (tGroup.getSelectedToggle() == yesBtn) {
				if (brandNameTF.getText().trim().length() == 0 || typeTF.getText().trim().length() == 0)
					return;
				String brand = brandNameTF.getText().trim();
				String type = typeTF.getText().trim();
				int quantity = Integer.parseInt(quantityTF.getText().trim());
				double price = Double.parseDouble(priceTF.getText().trim());
				if (!inventory.newItem(brand, type, quantity, price)) {
					statusMessage.setTextFill(Color.RED);
					statusMessage.setText(brand + " " + type + " already exists.");
				} else {
					statusMessage.setTextFill(Color.GREEN);
					statusMessage.setText(brand + " " + type + " - " + "in stock: " + quantity + ", price: $" + price);
				}

			} else if (tGroup.getSelectedToggle() == noBtn) {
				if (typeTF.getText().length() == 0)
					return;
				String type = typeTF.getText().trim();
				int quantity = Integer.parseInt(quantityTF.getText().trim());
				double price = Double.parseDouble(priceTF.getText().trim());
				if (!inventory.newItem(type, quantity, price)) {
					statusMessage.setTextFill(Color.RED);
					statusMessage.setText(type + " already exists.");
				} else {
					statusMessage.setTextFill(Color.GREEN);
					statusMessage.setText(type + " - " + "in stock: " + quantity + ", price: $" + price);
				}
			}
		}

		private void closeStage() {
			stage.close();
		}

	}

}
