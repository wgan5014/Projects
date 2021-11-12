package cinema.GUI;

import cinema.Cinema;
import cinema.GUILogic.StaffLoginScreenLogic;
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
import javafx.stage.Stage;
import cinema.GUILogic.StaffLoginScreenLogic;

import java.util.ArrayList;
import java.util.List;

public class ManagerLoginScreen {
	final static double width = 400;
	final static double height = 500;
	static VBox vbox; 
	static Scene scene;
	static Stage stage = new Stage();
	public static void show() {
		vbox = new VBox(15);
		vbox = new VBox(15);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50,100,100,100));
		scene = new Scene(vbox, width,height);
		stage.setResizable(false);
		stage.setScene(scene);
		createNodes();
		stage.show();
		stage.setOnCloseRequest((e)->{
			homeScreen.show();
		});
	}
	
	public static void createNodes() {
		double buttonWidth = 50;
		double fieldWidth = 200;

		Text loginText = nodeCreator.createText("Manager Account Login", 0, 0, 30);
		
		loginText.setX(width/2 - loginText.getLayoutBounds().getWidth());
		
		Text invalidDetailsText = nodeCreator.createText("Invalid username and/or password", 0, 0, 15);
		invalidDetailsText.setFill(Paint.valueOf("red"));
		invalidDetailsText.setVisible(false);
		
		TextField nameField = nodeCreator.createTextField("Username", 0 , 0);
		nameField.setAlignment(Pos.BASELINE_CENTER);
		nameField.setPrefWidth(fieldWidth);


		PasswordField passwordField = nodeCreator.createPassword("Password", 0,0);
		passwordField.setPrefWidth(fieldWidth);
		passwordField.setAlignment(Pos.BASELINE_CENTER);

		Button loginBtn = nodeCreator.createButton("Login", 0 , 0);
		loginBtn.setAlignment(Pos.BOTTOM_CENTER);
		loginBtn.setMinWidth(buttonWidth);
		loginBtn.setLayoutX(width/2 - buttonWidth/2);
		
		loginBtn.setOnMousePressed((e) -> {
			String usr = nameField.getText().trim();
			String pwd = passwordField.getText().trim();
			
			if (!StaffLoginScreenLogic.checkManagerCredentials(usr, pwd)) {
				invalidDetailsText.setVisible(true);
				System.out.println("User: " + usr + " Password: " + pwd);
			} else {
				// takes a boolean of whether to display manager features or not
				StaffHomeScreen.isManager = true;
				StaffHomeScreen.show(StaffHomeScreen.isShowingManager());
				stage.close();
			}
		});
		
		vbox.getChildren().addAll(
			loginText,
			invalidDetailsText,
			nameField,
			passwordField,
			loginBtn
		);
	}
}

/*
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;*/
