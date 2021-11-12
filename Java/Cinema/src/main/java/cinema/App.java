package cinema;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import cinema.GUI.*;

import java.time.LocalDateTime;
import javafx.scene.layout.Pane;

public class App extends Application {


    public void init(){
        IO.load();
    }

    @Override
    public void start(Stage stage) {

        homeScreen.show();
    }


    @Override
    public void stop(){
        IO.save();
    }

    public static void main(String[] args) {
		Application.launch(args);
	}


}
