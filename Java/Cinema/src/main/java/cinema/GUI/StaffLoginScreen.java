package cinema.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import cinema.GUILogic.StaffLoginScreenLogic;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class StaffLoginScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 400, 500);
    static Stage stage = new Stage();

    public static void show() {
        createNodes();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((o) ->{
            homeScreen.show();
        });
    }

    public static void createNodes() {
        Text title = nodeCreator.createText("Staff Login", 125, 100);
        title.setStyle("-fx-font-size: 30");
        TextField username = nodeCreator.createTextField("Enter username...", 125, 200);
        PasswordField password = nodeCreator.createPassword("Enter password...", 125, 250);
        Button submit = nodeCreator.createButton("Login", 175, 350);

        Text invalidDetailsText = nodeCreator.createText("Invalid username and/or password", 100, 330, 15);
        invalidDetailsText.setFill(Paint.valueOf("red"));
        invalidDetailsText.setVisible(false);

        submit.setOnMousePressed((e) -> {
            if (!StaffLoginScreenLogic.checkCredentials(username.getText(), password.getText())) {
                invalidDetailsText.setVisible(true);
            }
            else {
            	 StaffHomeScreen.isManager = false; // for testing
                new StaffHomeScreen().show(StaffHomeScreen.isShowingManager());
                stage.close();
            }
        });

        pane.getChildren().addAll(title, username, password, submit, invalidDetailsText);
    }
}
