package cinema.GUI;

import cinema.Cinema;
import cinema.Movie;
import cinema.GUILogic.StaffMovieLogic;
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
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class addShowingPopUpScreen {
    Pane pane = new Pane();
    Scene scene = new Scene(pane, 300,400);
    Stage stage = new Stage();

    String setMovieStr = "";
    Movie setMovie;
    String setScreenSize = "";
    String setLocation = "";
    LocalDateTime time;
    boolean isManager;



    public void show(boolean isManager){
        createNodes();
        stage.setScene(scene);
        stage.show();
        this.isManager = isManager;
        stage.setOnCloseRequest(event -> {
            new ModifyShowingScreen().show(isManager);
        });
    }
    public void createNodes(){
        List<Node> nodes = new ArrayList<>();
        Text title = nodeCreator.createText("Add Showing Screen", 75, 50);
        nodes.add(title);
        //use combobox
//        TextField name = nodeCreator.createTextField("Movie name...", 75, 100);
//        nodes.add(name);
        //TextField time = nodeCreator.createTextField("Movie synopsis...", 75, 150);
        List<String> allMovieNames = new ArrayList<>();
        for(Movie m:Cinema.getMovies()){
            allMovieNames.add(m.getName());
        }
        ComboBox<String> movie = new ComboBox<>(FXCollections.observableArrayList(allMovieNames));
        movie.setLayoutX(10);
        movie.setLayoutY(100);
        movie.setOnAction(e ->{
            movie.getValue();
            setMovieStr = movie.getValue();
            for(Movie m:Cinema.getMovies()){
                if(m.getName().equals(setMovieStr)){
                    setMovie = m;
                    break;
                }
            }
            //System.out.println(setMovie.getName());
        });
        nodes.add(movie);

        DatePicker date = new DatePicker();
        date.setLayoutX(10);
        date.setLayoutY(150);
        date.setMaxWidth(100);
        nodes.add(date);

        TextField hourTextField = nodeCreator.createTextField("h",150,150);
        hourTextField.setMaxWidth(30);
        //String hour = hourTextField.getText();
        hourTextField.setStyle("-fx-border-color: None;");
        nodes.add(hourTextField);
        Text dot = nodeCreator.createText(":",190,165);
        nodes.add(dot);
        TextField minuteTextField = nodeCreator.createTextField("m",200,150);
        minuteTextField.setMaxWidth(30);
        //String minute = minuteTextField.getText();
        nodes.add(minuteTextField);

        Text timeErrorText = nodeCreator.createText("Please enter a valid date time",10,185);
        timeErrorText.setStyle("-fx-font-size: 10");
        timeErrorText.setFill(Paint.valueOf("RED"));
        timeErrorText.setVisible(false);
        nodes.add(timeErrorText);

        //TextField screenSize = nodeCreator.createTextField("screen size...", 75, 200);
        String[] screenSizeOption = {"Bronze","Silver","Gold"};
        ComboBox<String> screenSize = new ComboBox<>(FXCollections.observableArrayList(screenSizeOption));
        screenSize.setLayoutX(100);
        screenSize.setLayoutY(200);
        //screenSize.getSelectionModel().selectFirst();
        screenSize.setOnAction(e -> {
            screenSize.getValue();
            setScreenSize = screenSize.getValue();

        });
        nodes.add(screenSize);

        //TextField location = nodeCreator.createTextField("location...", 75, 250);
        String[] locationOption = {"Redfern","Town Hall","Burwood","Parramatta"};
        ComboBox<String> location = new ComboBox<>(FXCollections.observableArrayList(locationOption));
        location.setLayoutX(100);
        location.setLayoutY(250);
        //location.getSelectionModel().selectFirst();
        location.setOnAction(e -> {
            location.getValue();
            setLocation = location.getValue();

        });
        nodes.add(location);


        Text notCompleteError = nodeCreator.createText("Please complete all the details",50,340);
        notCompleteError.setStyle("-fx-font-size: 15");
        notCompleteError.setFill(Paint.valueOf("RED"));
        notCompleteError.setVisible(false);
        nodes.add(notCompleteError);

//        Button back = nodeCreator.createButton("Go back",80,300);
//        back.setOnMousePressed((e) -> {
//            ModifyShowingScreen.show(this);
//            stage.close();
//        });
//        nodes.add(back);

        Button submit = nodeCreator.createButton("Add Showing",100,300);
        submit.setOnAction(e -> {
            if(date.getValue()==null ||! isValidTime(date.getValue().toString(),hourTextField.getText(),minuteTextField.getText())){
                //error text
                timeErrorText.setVisible(true);
                notCompleteError.setVisible(false);
                date.setStyle("-fx-border-color: red;");
                hourTextField.setStyle("-fx-border-color: red;");
                minuteTextField.setStyle("-fx-border-color: red;");
                //System.out.println("error time");
            }else if(setMovieStr.equals("")||setMovie==null||setScreenSize.equals("")||setLocation.equals("")){
                //error text
                timeErrorText.setVisible(false);
                notCompleteError.setVisible(true);
                date.setStyle("-fx-border-color: None;");
                hourTextField.setStyle("-fx-border-color:None;");
                minuteTextField.setStyle("-fx-border-color:None;");

            }else{
                timeErrorText.setVisible(false);
                notCompleteError.setVisible(false);
                date.setStyle("-fx-border-color: None;");
                hourTextField.setStyle("-fx-border-color:None;");
                minuteTextField.setStyle("-fx-border-color:None;");
                Showing s = new Showing(setMovie,time,setScreenSize,setLocation);
                Cinema.addShowing(s);
                new ModifyShowingScreen().show(isManager);
                stage.close();
            }


        });
        nodes.add(submit);

        pane.getChildren().addAll(nodes);

    }
    public boolean isValidTime(String date,String hour,String minute){
        String DateTimeObj = date + " " + hour+":"+minute;
        LocalDateTime dt;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dt = LocalDateTime.parse(DateTimeObj,formatter);
        }catch(Exception e){
            return false;
        }
        time = dt;
        return true;
    }

}
