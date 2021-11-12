package cinema.GUI;

import cinema.Cinema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// have the ability to:
// add staff
// remove staff

public class EditStaffScreen {
	final static double width = 400;
	final static double height = 500;
	static VBox vbox;
	static Scene scene;
	static Stage stage = new Stage();
	
	public static void show() {
		vbox = new VBox(15);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50,100,100,100));
		scene = new Scene(vbox, width,height);
		stage.setResizable(false);
		stage.setScene(scene);
		createNodes();
		stage.show();
		// show the staff home screen with manager features if it closes
		stage.setOnCloseRequest((e) -> {
			StaffHomeScreen.show(true);
		});
	}

	public static void createNodes() {
		double buttonWidth = 50;
		double fieldWidth = 200;
		
		//nameField.setAlignment(Pos.BASELINE_CENTER);
		//nameField.setPrefWidth(fieldWidth);
      //loginBtn.setAlignment(Pos.BOTTOM_CENTER);
      //loginBtn.setMinWidth(buttonWidth);
      //loginBtn.setLayoutX(width/2 - buttonWidth/2);
		
		Text title = nodeCreator.createText("Edit Staff Screen", 0, 0, 30);
		
		// add staff
		Button addStaff = nodeCreator.createButton("Add a staff member", 0, 0);
		addStaff.setAlignment(Pos.BOTTOM_LEFT);

		// add staff
		addStaff.setOnMousePressed((e) -> {
			EditPopup.show(true);
			
		});
		
		// remove staff
		Button removeStaff = nodeCreator.createButton("Remove a staff member", 0, 0);
		removeStaff.setAlignment(Pos.BOTTOM_RIGHT);
		
		removeStaff.setOnMousePressed((e) -> {
			EditPopup.show(false);
		});
		
		vbox.getChildren().addAll(title, addStaff, removeStaff);
	}
}






