import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StockReport extends InventoryDriver {

	Stage stockReportStage;
	Scene stockScene;

	VBox vb1;

	TextField fileNameTF;

	TextArea stockReportTxtArea;

	CheckBox exportCB = new CheckBox("Export a copy to a file");
	CheckBox txtAreaCB = new CheckBox("Text Area");

	Button exportBtn = new Button("Export");

	StockReportHandler stockReportHandler = new StockReportHandler();

	public static void main(String[] args) { // main method for application debugging and testing
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stockReportStage = stage;
		buildStockReportScene();
		stockReportStage.setScene(stockScene);
		stockReportStage.setTitle("Stock Report");
		stockReportStage.show();
	}

	private void buildStockReportScene() { // builds the StockReport scene layout according to layout specifications
		Label info = new Label("The following options can be used to print a report");
		info.setFont(new Font("Arial", 22));

		ToggleGroup checkBoxesTG = new ToggleGroup();
		exportCB = new CheckBox("Export a copy to a file");
		txtAreaCB = new CheckBox("Text Area");
		exportCB.setSelected(false);
		txtAreaCB.setSelected(false);

		fileNameTF = new TextField();
		fileNameTF.setPrefWidth(120);
		fileNameTF.setMaxWidth(120);
		fileNameTF.setPromptText("File Name");
		exportBtn = new Button("Export");
		exportBtn.setOnAction(stockReportHandler);
		exportBtn.setStyle("-fx-background-radius: 7em; -fx-min-width: 65px;");

		stockReportTxtArea = new TextArea();
		VBox mainvb = new VBox(info);
		mainvb.setSpacing(20);
		mainvb.setPadding(new Insets(25));

		vb1 = new VBox(fileNameTF, exportBtn);
		vb1.setSpacing(5);
		optionsGP = new GridPane();
		optionsGP.add(exportCB, 0, 0);
		optionsGP.add(txtAreaCB, 1, 0);
		optionsGP.setHgap(10);
		optionsGP.setVgap(15);

		exportCB.setOnAction(stockReportHandler);
		txtAreaCB.setOnAction(stockReportHandler);
		mainvb.getChildren().add(optionsGP);
		mainvb.setStyle("-fx-background-color: #b8b8b8;");
		stockScene = new Scene(mainvb, 700, 400);

	}

	public class StockReportHandler implements EventHandler<ActionEvent> { // handles each event in StockReport scene
																			// according to specifications

		@Override
		public void handle(ActionEvent e) {
			if (e.getSource() == exportCB) {
				handleExportCB();
			} else if (e.getSource() == txtAreaCB) {
				handleTxtAreaCB();
			} else if (e.getSource() == exportBtn) {
				handleExportBtn();
			}

		}

		private void handleExportBtn() {
			if (fileNameTF.getText().length() == 0)
				return;
			try {
				PrintWriter writer = new PrintWriter(fileNameTF.getText().trim());
				writer.write(inventory.stockReport());
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		private void handleTxtAreaCB() {
			if (txtAreaCB.isSelected()) {
				stockReportTxtArea.setText(inventory.stockReport());
				optionsGP.add(stockReportTxtArea, 1, 1);
			} else {
				optionsGP.getChildren().remove(stockReportTxtArea);
			}

		}

		private void handleExportCB() {
			if (exportCB.isSelected()) {
				optionsGP.add(vb1, 0, 1);
			} else {
				optionsGP.getChildren().remove(vb1);
			}

		}

	}
}
