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

public class editMoviePopUpScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 400, 500);
    static Stage stage = new Stage();
    boolean isManager;

    public void show(Movie movie, boolean isManager) {
        createNodes(movie);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((o) ->{
            new ModifyMovieScreen().show(isManager);
        });
    }

    public void createNodes(Movie movie) {
        Text title = nodeCreator.createText("Movie Edit Screen", 75, 50);
       
        TextField name = nodeCreator.createTextField("Edit name...", 75, 100);
        name.setText(movie.getName());

        TextField synopsis = nodeCreator.createTextField("Edit synopsis...", 75, 150);
        synopsis.setText(movie.getSynopsis());

        TextField director = nodeCreator.createTextField("Edit director...", 75, 200);
        director.setText(movie.getDirector());

        TextField classification = nodeCreator.createTextField("Edit classification...", 75, 250);
        classification.setText(movie.getClassification());

        TextField cast = nodeCreator.createTextField("Edit cast...", 75, 300);
        cast.setText(movie.getCast().toString());

        DatePicker date = nodeCreator.createDatePicker("Edit date...", 75, 350);
        date.setValue(movie.getReleaseDate());

        Button submit = nodeCreator.createButton("Submit Changes", 100, 400);
        submit.setOnMousePressed((e) -> {
            StaffMovieLogic.updateMovie(movie, name.getText(), director.getText(), cast.getText(), classification.getText(), synopsis.getText(), date.getValue());
            new ModifyMovieScreen().show(isManager);
            stage.close();
        });

        pane.getChildren().addAll(title, name, synopsis, director, classification, cast, date, submit);
    }
}
