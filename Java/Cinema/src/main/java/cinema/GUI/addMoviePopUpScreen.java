package cinema.GUI;

import cinema.Movie;
import cinema.GUILogic.StaffMovieLogic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class addMoviePopUpScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 300, 400);
    static Stage stage = new Stage();
    boolean isManager;
    public void show(boolean isManager) {
        this.isManager = isManager;
        createNodes();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((o) ->{
            new ModifyMovieScreen().show(isManager);
        });
    }

    public void createNodes() {
        Text title = nodeCreator.createText("Add Movie Screen", 75, 50);
        TextField name = nodeCreator.createTextField("Movie name...", 75, 100);
        TextField synopsis = nodeCreator.createTextField("Movie synopsis...", 75, 150);
        TextField director = nodeCreator.createTextField("Movie director...", 75, 200);
        TextField classification = nodeCreator.createTextField("Movie classification...", 75, 250);
        TextField cast = nodeCreator.createTextField("Movie cast...", 75, 250);
        DatePicker date = nodeCreator.createDatePicker("Movie release date...", 75, 300);

        Button submit = nodeCreator.createButton("Add Movie", 100, 350);
        submit.setOnMousePressed((e) -> {
            StaffMovieLogic.addMovie(name.getText(), director.getText(), cast.getText(), classification.getText(), synopsis.getText(), date.getValue());
            new ModifyMovieScreen().show(isManager);
            stage.close();
        });

        pane.getChildren().addAll(title, name, synopsis, director, classification, cast, date, submit);
    }
}
