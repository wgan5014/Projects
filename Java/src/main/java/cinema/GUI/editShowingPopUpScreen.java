package cinema.GUI;

import cinema.Cinema;
import cinema.Movie;
import cinema.Showing;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import cinema.GUILogic.ShowingScreenLogic;
import cinema.GUI.ModifyShowingScreen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class editShowingPopUpScreen {
    Pane pane = new Pane();
    Scene scene = new Scene(pane, 300,400);
    Stage stage = new Stage();
    Showing showing;
    boolean isManager;

    public void show(Showing showing, boolean isManager){
        this.showing = showing;
        createNodes();
        stage.setScene(scene);
        stage.show();
        this.isManager = isManager;
        stage.setOnCloseRequest((e) -> {
            Platform.runLater(() -> {
                new ModifyShowingScreen().show(isManager);
            });
        });
    }

    public void createNodes() {
        List<Node> nodes = new ArrayList<>();
        Text title = nodeCreator.createText("Edit Showing", 75, 50);
        nodes.add(title);

        List<String> allMovieNames = new ArrayList<>();
        for(Movie m:Cinema.getMovies()){
            allMovieNames.add(m.getName());
        }

        ComboBox<String> movies = new ComboBox<>(FXCollections.observableArrayList(allMovieNames));
        movies.setLayoutX(10);
        movies.setLayoutY(100);
        movies.setValue(showing.getMovie().getName());
        nodes.add(movies);

        DatePicker date = nodeCreator.createDatePicker("Select date", 10, 150);
        date.setMaxWidth(100);
        date.setValue(showing.getTime().toLocalDate());
        nodes.add(date);

        TextField hourTextField = nodeCreator.createTextField("H",150,150);
        hourTextField.setMaxWidth(30);
        hourTextField.setStyle("-fx-border-color: None;");
        nodes.add(hourTextField);

        Text dot = nodeCreator.createText(":",190,165);
        nodes.add(dot);

        TextField minuteTextField = nodeCreator.createTextField("M",200,150);
        minuteTextField.setMaxWidth(30);
        nodes.add(minuteTextField);

        Text timeErrorText = nodeCreator.createText("Please enter a valid date time",10,185);
        timeErrorText.setStyle("-fx-font-size: 10");
        timeErrorText.setFill(Paint.valueOf("RED"));
        timeErrorText.setVisible(false);
        nodes.add(timeErrorText);

        String[] screenSizeOption = {"Bronze","Silver","Gold"};
        ComboBox<String> screenSize = new ComboBox<>(FXCollections.observableArrayList(screenSizeOption));
        screenSize.setLayoutX(75);
        screenSize.setLayoutY(200);
        screenSize.setValue(showing.getScreenSize());
        nodes.add(screenSize);

        String[] locationOption = {"Redfern","Town Hall","Burwood","Parramatta"};
        ComboBox<String> location = new ComboBox<>(FXCollections.observableArrayList(locationOption));
        location.setLayoutX(75);
        location.setLayoutY(250);
        location.setValue(showing.getLocation());
        nodes.add(location);

        Button submit = nodeCreator.createButton("Edit Showing",100,300);

        submit.setOnAction(e -> {
            if(!ShowingScreenLogic.isValidTime(date.getValue().toString(), hourTextField.getText(), minuteTextField.getText())){
                //error text
                timeErrorText.setVisible(true);
                date.setStyle("-fx-border-color: red;");
                hourTextField.setStyle("-fx-border-color: red;");
                minuteTextField.setStyle("-fx-border-color: red;");
            } else {
                if (movies.getValue() != showing.getMovie().getName()) {
                    Movie newMovie = null;
                    for(Movie m : Cinema.getMovies()) {
                        if (m.getName().equals(movies.getValue())) {
                            newMovie = m;
                        }
                    }
                    showing.setMovie(newMovie);
                }

                String DateTimeObj = date.getValue().toString() + " " + hourTextField.getText() + ":" + minuteTextField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dt = LocalDateTime.parse(DateTimeObj,formatter);

                showing.setTime(dt);
                showing.setScreenSize(screenSize.getValue());
                showing.setLocation(location.getValue());

                new ModifyShowingScreen().show(isManager);
                stage.close();
            }
        });
        nodes.add(submit);

        pane.getChildren().addAll(nodes);
    }
}
