// As a guest user, I want to register for an account with a unique username so that my account does not get overridden by another account with the same username.
// --wgan5014

// this class ONLY contains the front-end display for the 
// registration process. All back-end logic is handled in CustRegoScreenLogic.java

package cinema.GUI;

import cinema.Cinema;
import cinema.Movie;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.Scene;

import cinema.GUILogic.CustRegoScreenLogic;
import javafx.stage.Stage;

// assume there exists a class nodeCreator.java (can add this in later)

/*

UI Design:

-----------------------
|		  USERNAME		 |
-----------------------
|							 |
-----------------------
|		  PASSWORD		 |
-----------------------
|							 |
-----------------------
|		PASSWD AGAIN	 |
-----------------------
|							 |
-----------------------
|		CREATE ACCT		 |
-----------------------

*/
public class CustRegoScreen {

	private static Pane pane = new Pane();
	private static Scene scene = new Scene(pane, 800, 700);
	private static Stage stage = new Stage();

	public static void show() {
		createNodes();
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest((e)->{
			homeScreen.show();
		});
	}

	public static void createNodes() {
  /* willbranch conf. here
		// creating username
		Text usernameTextPrompt = nodeCreator.createText("Username");
		usernameTextPrompt.setStyle("-fx-font-size: 40");
		usernameTextPrompt.setLayoutX(300);
		usernameTextPrompt.setLayoutY(150);
		
		TextField usernameTextInput = nodeCreator.createTextField("E.g. HughJass24, BeerLover69");
		usernameTextInput.setLayoutX(100);
		usernameTextInput.setLayoutY(200);
		
		// setting password
		Text pwdPrompt = nodeCreator.createText("Password");
		pwdPrompt.setStyle("-fx-font-size: 40");
		pwdPrompt.setLayoutX(300);
		pwdPrompt.setLayoutY(250);
		
		TextField pwdInput = nodeCreator.createTextField("E.g. $WxYz1234!");
		pwdInput.setLayoutX(250);
		pwdInput.setLayoutY(300);
		
		// confirming password
		Text pwdAgainPrompt = nodeCreator.createText("Password again");
		pwdAgainPrompt.setStyle("-fx-font-size: 40");
		pwdAgainPrompt.setLayoutX(300);
		pwdAgainPrompt.setLayoutY(350);
		
		TextField pwdAgainInput = nodeCreator.createTextField("Re-enter password");
		pwdAgainInput.setLayoutX(350);
		pwdAgainInput.setLayoutY(400);
		
		// create account
		Button createAccountButton = nodeCreator.createButton("Create Account");
		createAccountButton.setPrefSize(100, 30);
		createAccountButton.setLayoutX(300);
		createAccountButton.setLayoutY(500);
    */
		Text title = nodeCreator.createText("User Registration", 300, 100);
		title.setStyle("-fx-font-size: 32");

		// creating username
		Text usernameTextPrompt = nodeCreator.createText("Username", 350, 250);
		usernameTextPrompt.setStyle("-fx-font-size: 30");
		
		TextField usernameTextInput = nodeCreator.createTextField("E.g. HughJass24, BeerLover69", 350, 275);
		
		// setting password
		Text pwdPrompt = nodeCreator.createText("Password", 350, 350);
		pwdPrompt.setStyle("-fx-font-size: 30");
		
		PasswordField pwdInput = nodeCreator.createPassword("E.g. $WxYz1234!", 350, 375);
		
		// confirming password
		Text pwdAgainPrompt = nodeCreator.createText("Password again", 325, 450);
		pwdAgainPrompt.setStyle("-fx-font-size: 30");

		TextField pwdAgainInput = nodeCreator.createPassword("Re-enter password", 350, 475);
		
		// create account
		Button createAccountButton = nodeCreator.createButton("Create Account", 375, 600);
		createAccountButton.setPrefSize(100, 30);
		
		/*
		* When the user clicks on 'Create Account'
		--------------------------------------------
		|			CustRegoScreenLogic Starts here	 |
		--------------------------------------------
		* Check if the username is valid
		* If username valid, check if passwords match
		* If password match
		* The home-page display screen will be shown
		* I.e. create account --> go to home page
		* Otherwise, display the relevant error messages
		* On the same screen
		--------------------------------------------
		|			CustRegoScreenLogic Ends here 	 |
		--------------------------------------------
		*/
		createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// parsing inputs
				String username, pwd, pwdagain;
				username = usernameTextInput.getText();
				pwd = pwdInput.getText();
				pwdagain = pwdAgainInput.getText();
				
				// a return status of the inputs given to create a new account from the guest account
				int stat = CustRegoScreenLogic.createAccountChecks(username, pwd, pwdagain);
				
				// if status isn't 0 then there's an error
				// retain everything and create the appropriate error text
				if (stat != 0) {
					pane.getChildren().retainAll(
						title,
						usernameTextPrompt,
						usernameTextInput,
						pwdPrompt,
						pwdInput,
						pwdAgainPrompt,
						pwdAgainInput,
						createAccountButton
					);
					pane.getChildren().add(createErrorText(username, stat));
				}
				
				// if no error --> stat == 0 --> (i.e. nothing null, username is valid && pwds match)
				else {
					// write the account into the db
					CustRegoScreenLogic.createAccount(username, pwd);
					System.out.println("Successful account creation!");
					// load the home page
					//createAccountButton.getScene().setRoot(new homeScreen().getHomeScreen());
					new MovieScreen().show(Cinema.getCustomer(username));
					stage.close();
				}
			}
		});
		
		// add all the elements
		pane.getChildren().addAll(
      // -- didn't add title b4 ==> willbranch
			title,
			usernameTextPrompt,
			usernameTextInput,
			pwdPrompt,
			pwdInput,
			pwdAgainPrompt,
			pwdAgainInput,
			createAccountButton
		);
	}

	
	/*
	* A method to create the error text based
	* on the error case while registering
	*/
	public static Text createErrorText(String username, int stat) {
		String[] messages = {
			null,
			"No user entered",
			"No password entered",
			"No password re-entered",
			"Passwords do not match! Please re-enter your password.",
			null
		};
		
		if (stat == 5) {
			String suggestedUser = CustRegoScreenLogic.generateNewUser(username);
			String ins = String.format("Username is already taken! Please try another username, like %s", suggestedUser);
			messages[5] = ins;
		}
		
		String text = messages[stat];
		
		/*
		if (!incorrectPwdMatch) {
			String suggestedUser = CustRegoScreenLogic.generateNewUser(username);
			text = String.format("Username is already taken! Please try another username, like %s", suggestedUser);
		}
		else {
			text = "Passwords entered do not match!";
		}
		*/
		/*
    -- merge conflict err. ===> willbranch
		Text errorText = nodeCreator.createText(text);
		errorText.setStyle("-fx-font-size: 12");
		errorText.setFill(Paint.valueOf("RED"));
		errorText.setLayoutX(250);
		errorText.setLayoutY(700);
    */

		Text errorText = nodeCreator.createText(text, 250, 650);
		errorText.setStyle("-fx-font-size: 12");
		errorText.setFill(Paint.valueOf("RED"));
    
		return errorText;
	}
}



