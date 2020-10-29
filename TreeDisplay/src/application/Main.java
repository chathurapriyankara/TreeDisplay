package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sun.jdi.connect.spi.Connection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Main extends Application {
	private PersonBST bst = new PersonBST();
	private Group group = new Group();

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane borderPane = new BorderPane();
			ScrollPane scrollPane = new ScrollPane();
			Scene scene = new Scene(borderPane, 2200, 800);
			Button selectFile = new Button("Select Data File");
			selectFile.setMaxWidth(180);
			selectFile.setOnAction(a -> {
				selectFile(primaryStage);
			});
			VBox vBox = new VBox(selectFile);
			vBox.setSpacing(5);
			vBox.setAlignment(Pos.TOP_CENTER);
			vBox.setPadding(new Insets(10, 10, 10, 10));
			vBox.setStyle("-fx-background-color : gray");
			borderPane.setLeft(vBox);
			primaryStage.setScene(scene);
			primaryStage.show();
			scrollPane.setContent(group);
			borderPane.setCenter(scrollPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectFile(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		loadData(group, selectedFile);
	}

	public void loadData(Group group, File file) {
		try {
			Scanner scan = new Scanner(file);
			if (bst.root != null) {
				bst.root = null;
			}
			while (scan.hasNext()) {
				String fName = scan.next();
				String lName = scan.next();
				Person p = new Person(fName, lName);
				bst.addPerson(p);
				scan.nextLine();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		group.getChildren().clear();

		//1500 is hard coded value if you need to print larger tree the value 1500 needs to be changed.
		drawTree(group, 0, 1500, 20, 60, bst.root);
	}

	private void drawTree(Group group, double startX, double endX, double y, double edgeLength, Person node) {
		int labelHeight = 15;
		//Print only the first letter of the name
		Label label = new Label(node.getFirstName().charAt(0)+"");
		label.setLayoutX((startX + endX) * 0.5 - labelHeight * 0.5);
		label.setLayoutY((y - labelHeight) + edgeLength * 0.5);
		label.setTextFill(Color.WHITE);
		label.setStyle("-fx-background-color: blue");
		label.setPadding(new Insets(3, 3, 3, 3));

		String tip = "";
		tip += "First Name: " + node.getFirstName() + "\n";
		if (node.getBefore() != null) {
			tip += "Before: " + node.getBefore().getFirstName() + "\n";
		} else {
			tip += "Before: None\n";
		}
		if (node.getAfter() != null) {
			tip += "After: " + node.getAfter().getFirstName();
		} else {
			tip += "After: None";
		}
		Tooltip tooltip = new Tooltip(tip);
		label.setTooltip(tooltip);
		group.getChildren().add(label);
		double nodeGap = Math.min((endX - startX) / 8, 10);

		if (node.getBefore() != null) {
			double x1 = (startX + endX) * 0.5 - nodeGap;
			double y1 = y + edgeLength * 0.5 + 5;
			double x2 = (startX + (startX + endX) * 0.5) * 0.5;
			double y2 = y + edgeLength + edgeLength * 0.5 - labelHeight;
			Line line = new Line(x1, y1, x2, y2);
			line.setStrokeWidth(2);
			line.setStyle("-fx-stroke:red");
			group.getChildren().add(line);
			drawTree(group, startX, (startX + endX) * 0.5, y + edgeLength, edgeLength, node.getBefore());
		}
		if (node.getAfter() != null) {
			double x1 = (startX + endX) * 0.5 + nodeGap;
			double y1 = y + edgeLength * 0.5 + 5;
			double x2 = (endX + (startX + endX) * 0.5) * 0.5;
			double y2 = y + edgeLength + edgeLength * 0.5 - labelHeight;
			Line line = new Line(x1, y1, x2, y2);
			line.setStrokeWidth(2);
			line.setStyle("-fx-stroke:green");
			group.getChildren().add(line);
			drawTree(group, (startX + endX) * 0.5, endX, y + edgeLength, edgeLength, node.getAfter());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
