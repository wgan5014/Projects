package cinema.GUI;

import cinema.Transaction;
import cinema.Cinema;
import cinema.Staff;

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
import cinema.Staff;

import java.util.ArrayList;
import java.util.List;

public class EditPopup {

	final static double width = 400;
	final static double height = 500;
	static VBox vbox;
	static Scene scene;
	static Stage stage = new Stage();
	static boolean isManager = false;
	
	public static void show(boolean isAddStaff) {
		vbox = new VBox(15);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50,100,100,100));
		scene = new Scene(vbox, width,height);
		stage.setResizable(false);
		stage.setScene(scene);
		createNodes(isAddStaff);
		stage.show();
		// show the staff home screen with manager features if it closes
		stage.setOnCloseRequest((e) -> {
			EditStaffScreen.show();
		});
		isManager = false;
	}
	
	public static void createNodes(boolean isAddStaff) {
		
		double buttonWidth = 50;
		double fieldWidth = 200;
		
		TextField usr = nodeCreator.createTextField("Staff username", 0, 0);
		usr.setAlignment(Pos.BASELINE_CENTER);
		usr.setPrefWidth(fieldWidth);
		
		Button submit = nodeCreator.createButton("Submit", 0, 0);
		submit.setAlignment(Pos.BOTTOM_CENTER);
		submit.setMinWidth(buttonWidth);
		// submit.setLayoutX(width/2 - buttonWidth/2);
		
		Text usrnameInvalidText = nodeCreator.createText("Invalid/empty username entered", 0, 0, 15);
		usrnameInvalidText.setVisible(false);
		
		if (isAddStaff) {

			Text usrExists = nodeCreator.createText("Username already exists in system", 0, 0, 15);
			usrExists.setVisible(false);
			
			// prompt for password
			Text pwdPrompt = nodeCreator.createText("Enter password", 0, 0, 15);

			// input field for pwd
// willbranch
			PasswordField pwdField = nodeCreator.createPassword("Password", 0,0);
//=======
			//TextField pwdField = nodeCreator.createTextField("Password",0,0);
// master
			pwdField.setPrefWidth(fieldWidth);
			pwdField.setAlignment(Pos.BASELINE_CENTER);
			
			// invalid pwd text
			Text invalidpwd = nodeCreator.createText("Password is empty", 0, 0, 15);
			invalidpwd.setVisible(false);

			// check if creating manager or not
			//List<Integer> tmp = new ArrayList<>();
			//tmp.add(1);

			var wrapper = new Object(){ String value = ""; };

			//list.forEach(s->{
				//wrapper.value += "a";
			//});
			
			// create manager prompt
			Text createManager = nodeCreator.createText("Creating a manager?", 0, 0, 15);
//  willbranch
			createManager.setX(width/2 - createManager.getLayoutBounds().getWidth());
			// createManager.setX(width/2 - usrExists.getLayoutBounds().getWidth());
// master
			
			// yes button for creating a manager or not
			Button yesManager = nodeCreator.createButton("Yes", 0, 0);
			yesManager.setMinWidth(buttonWidth);
			yesManager.setAlignment(Pos.BASELINE_CENTER);

			// no button for creating manager
			Button noManager = nodeCreator.createButton("No", 0, 0);
			noManager.setMinWidth(buttonWidth);
// willbranch
			noManager.setAlignment(Pos.BASELINE_CENTER);
			// wrapper.value = "a"; System.out.println(wrapper.value); 
			// wrapper.value = "b"; System.out.println(wrapper.value);
			yesManager.setOnMousePressed((e) -> { isManager = true; });
			noManager.setOnMousePressed((e) -> { isManager = false; });
			// noManager.setAlignment(Pos.BASELINE_RIGHT);
			
			//yesManager.setOnMousePressed((e) -> { isManager = true; });
			//noManager.setOnMousePressed((e) -> { isManager = false; });
//master
			
			submit.setOnMousePressed((e) -> {
				String usrname = usr.getText().trim();
				String pwd = pwdField.getText();
				// valid usr? (i.e. not empty)
				if (usrname != null && usrname.length() != 0) {
					// non empty pwd?
// willbranch
					if (pwd != null && pwd.length() != 0) {
						// is the usr non existent in system?
						if (isManager) { System.out.println("is manager"); }
						else { System.out.println("not manager"); }
						if (Cinema.addStaff(new Staff(isManager, usrname, pwd))) {
              
					  //if (pwd1 == null || pwd1.length() != 0) {
						// is the usr non existent in system?
						//if (Cinema.addStaff(new Staff(isManager, usrname, pwd1))) {
// master
							// #added
							stage.close(); // close popup after adding
						} else {
							usrExists.setVisible(true);
							usrnameInvalidText.setVisible(false);
							invalidpwd.setVisible(false);
						} // usr already in system
					} else { 
						invalidpwd.setVisible(true);
						usrnameInvalidText.setVisible(false);
						usrExists.setVisible(false);
					}
				} else { 
					usrnameInvalidText.setVisible(true);
					invalidpwd.setVisible(false);
					usrExists.setVisible(false);
				}
				/*
				if (usrname == null || usrname.length() == 0) {
					usrnameInvalidText.setVisible(true);
				} else {
					String pwd = pwdField.getText();
					if (pwd.length() == 0) {
						invalidpwd.setVisible(true);
					} else {
						boolean stat = Cinema.addStaff(new Staff());
					}
				}
				*/
			});
			vbox.getChildren().addAll(usr, submit, usrnameInvalidText, usrExists, pwdPrompt, pwdField, invalidpwd, createManager, yesManager, noManager);
		}
		
		// removing staff member
		else {
			Text couldNotRemoveText = nodeCreator.createText("Could not remove - staff username not found", 0, 0, 15);
			couldNotRemoveText.setVisible(false);
			submit.setOnMousePressed((e) -> {
				String usrname = usr.getText().trim();
				// check usrname not empty
				if (usrname != null && usrname.length() != 0) {
					// can successfully remove?
					if (Cinema.removeStaff(usrname)) {
						stage.close(); // closepopup once removed
					} else {
						couldNotRemoveText.setVisible(true);
						usrnameInvalidText.setVisible(false);
					}
				} else {
					usrnameInvalidText.setVisible(true);
					couldNotRemoveText.setVisible(false);
				}
			});
			vbox.getChildren().addAll(usr, submit, usrnameInvalidText, couldNotRemoveText);
		}
	}
}


