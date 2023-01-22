import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InventoryDriver extends Application {

	// -------------- initialize inventory --------------
	static Inventory inventory = new Inventory("groceries");

	Button addBtn_scene = new Button("Add");
	Button modifyBtn_scene = new Button("Modify");
	Button deleteBtn_scene = new Button("Delete");
	Button stockReportBtn_scene = new Button("Stock Report");
	GridPane optionsGP;
	DriverHandler driverHandler = new DriverHandler();
	Stage primaryStage = new Stage();
	Scene menuScene;

	public static void main(String[] args) {
		inventory.newItem("bread", 15, 9.99);
		inventory.newItem("al-jebrini", "milk", 2, 2.00);
		inventory.newItem("eggs", 3, 1.50);
		inventory.newItem("bread", 2, 1.25); // warning: in stock
		inventory.stockReport();
		inventory.update("al-jebrini", "milk", .25); // raise price 25%
		inventory.update("eggs", -1); // lower quantity by 1
		inventory.update("juice", 3); // warning: not stocked
		inventory.newItem("juneidi", "milk", 4, 1.95);
		inventory.stockReport();
		inventory.setPrice("cola", 10); // warning: not stocked
		inventory.setQuantity("al-jebrini", "milk", 3);
		inventory.setPrice("eggs", 2.00);
		System.out.println("milk quantity: " + inventory.getQuantity("alsafi", "milk")); // not stocked
		System.out.println("milk quantity: " + inventory.getQuantity("milk")); // ambiguity
		System.out.println("eggs price: " + inventory.getPrice("eggs"));
		System.out.println("milk price: " + inventory.getPrice("milk")); // ambiguity
		System.out.println((new Item("cola").setPrice(5)).compareTo(new Item("milk").setPrice(8)));

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		buildMenuScene();
		primaryStage.setScene(menuScene);
		primaryStage.show();
		primaryStage.setTitle("1210082 - Omar Kashour | Project Phase 2");
	}

	private void buildMenuScene() { // builds the menu scene layout
		Label header = new Label("Inventory Management System : Comp 2311 Project, Phase 2");
		HBox headerHbox = new HBox(header);
		headerHbox.setAlignment(Pos.CENTER);
		header.setFont(new Font("Arial", 21));
		header.setStyle("-fx-font-style: italic;");

		Image menuImage = new Image(
				"https://www.kizeo-forms.com/wp-content/uploads/2020/06/Custom-inventory-management-app-with-kizeo-forms-01.jpg");
		ImageView imageView = new ImageView(menuImage);
		imageView.setFitHeight(250);
		imageView.setFitWidth(250);
		imageView.setRotate(30);

		HBox buttonsHbox = new HBox();
		buttonsHbox.setSpacing(15);
		addBtn_scene.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		modifyBtn_scene.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		deleteBtn_scene.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		stockReportBtn_scene.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");
		buttonsHbox.getChildren().addAll(addBtn_scene, modifyBtn_scene, deleteBtn_scene, stockReportBtn_scene);
		buttonsHbox.setAlignment(Pos.CENTER);

		addBtn_scene.setOnAction(driverHandler);
		modifyBtn_scene.setOnAction(driverHandler);
		deleteBtn_scene.setOnAction(driverHandler);
		stockReportBtn_scene.setOnAction(driverHandler);

		BorderPane bp = new BorderPane();
		bp.setTop(headerHbox);
		bp.setBottom(buttonsHbox);
		bp.setCenter(imageView);

		bp.setStyle("-fx-background-color: #b8b8b8;");

		menuScene = new Scene(bp, 700, 400);
	}

	public class DriverHandler implements EventHandler<ActionEvent> {  // handles each event in menu scene according to specifications

		@Override
		public void handle(ActionEvent e) {          // shows the selected scene
			if (e.getSource() == addBtn_scene) {
				showAddStage();
			} else if (e.getSource() == modifyBtn_scene) {
				showModifyStage();
			} else if (e.getSource() == deleteBtn_scene) {
				showDeleteStage();
			} else if (e.getSource() == stockReportBtn_scene) {
				showStockReportStage();
			}

		}

		private void showStockReportStage() {
			Stage stockReportStage = new Stage();
			StockReport delete = new StockReport();
			try {
				delete.start(stockReportStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private void showDeleteStage() {
			Stage deleteStage = new Stage();
			Delete delete = new Delete();
			try {
				delete.start(deleteStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private void showModifyStage() {
			Stage modifyStage = new Stage();
			Modify modify = new Modify();
			try {
				modify.start(modifyStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private void showAddStage() {
			Stage addStage = new Stage();
			Add add = new Add();
			try {
				add.start(addStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
