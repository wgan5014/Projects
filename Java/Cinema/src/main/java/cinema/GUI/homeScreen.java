package cinema.GUI;


import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class homeScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 400,500);
    static Stage stage = new Stage();

    public static void show(){
        createNodes();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    static void createNodes(){
        List<Node> nodes = new ArrayList<>();
        Button loginGuestbtn = nodeCreator.createButton("View as guest");
        Button loginCustomerbtn = nodeCreator.createButton("Login as Customer");
        Button loginStaffbtn = nodeCreator.createButton("Login as Staff");
        Button loginManagerbtn = nodeCreator.createButton("Login as Manager");
        Button registerbtn = nodeCreator.createButton("Register account");

        double minWidth = 150;
        double startY = 150;

        loginGuestbtn.setMinWidth(minWidth);
        loginGuestbtn.setLayoutX(scene.getWidth()/2 - loginGuestbtn.getMinWidth()/2);
        loginGuestbtn.setLayoutY(startY);
        nodes.add(loginGuestbtn);
        loginGuestbtn.setOnMousePressed((o) -> {
            new MovieScreen().show(null);
            stage.close();
        });


        loginCustomerbtn.setMinWidth(minWidth);
        loginCustomerbtn.setLayoutX(scene.getWidth()/2 - loginCustomerbtn.getMinWidth()/2);
        loginCustomerbtn.setLayoutY(startY + 50);
        nodes.add(loginCustomerbtn);
        loginCustomerbtn.setOnMousePressed((o)->{
            CustLoginScreen.show();
            stage.close();
        });

        loginStaffbtn.setMinWidth(minWidth);
        loginStaffbtn.setLayoutX(scene.getWidth()/2 - loginStaffbtn.getMinWidth()/2);
        loginStaffbtn.setLayoutY(startY + 50*2);
        nodes.add(loginStaffbtn);
        loginStaffbtn.setOnMousePressed((o) ->{
            new StaffLoginScreen().show();
            stage.close();
        });

        loginManagerbtn.setMinWidth(minWidth);
        loginManagerbtn.setLayoutX(scene.getWidth()/2 - loginManagerbtn.getMinWidth()/2);
        loginManagerbtn.setLayoutY(startY + 50*3);
        nodes.add(loginManagerbtn);
        loginManagerbtn.setOnMousePressed((o) -> {
        		ManagerLoginScreen.show();
	        	stage.close();
        });
        

        registerbtn.setMinWidth(minWidth);
        registerbtn.setLayoutX(scene.getWidth()/2 - registerbtn.getMinWidth()/2);
        registerbtn.setLayoutY(startY + 50*4);
        nodes.add(registerbtn);
        registerbtn.setOnMousePressed((o) ->{
            CustRegoScreen.show();
            stage.close();
        });

        Text welcomeTxt = nodeCreator.createText("Welcome to the Cinema Booking System", 50, 50);
        welcomeTxt.setStyle("-fx-font-size: 18");
        nodes.add(welcomeTxt);

        pane.getChildren().addAll(nodes);


    }

}
