package cinema.GUI;

import cinema.Cinema;
import cinema.Customer;
import cinema.Movie;
import cinema.Showing;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MovieScreen {
    String classification_filter = "";
    String screen_filter = "";
    String location_filter = "all";
    TableView<Movie> MovieTable = new TableView<>();
    Movie selectedMovie = null;
    TableView<Showing> TimeTable = new TableView<>();
    TableView<String> SummaryTable = new TableView<>();


    Showing selectedShowing = null;
    Customer customer = null;

    Button purchaseButton;

    Pane pane = new Pane();
    Scene scene = new Scene(pane, 800,900);
    Stage stage = new Stage();

    public void show(Customer user){
        this.customer = user;
        createNodes();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(o ->
            homeScreen.show()
        );

    }

    public void createNodes(){
        List<Node> nodes = new ArrayList<>();
        List<TableColumn<Movie,?>> MovieTableColumns = new ArrayList<>();
        List<TableColumn<Showing,?>> TimeTableColumns = new ArrayList<>();


        //Filter by screen classification
        Text filter_by_classification_text = nodeCreator.createText("Filter by classification",150,70);
        filter_by_classification_text.setStyle("-fx-font-size: 10");
        nodes.add(filter_by_classification_text);
        String[] filter_by_classification = {"all","G","PG-13","M","MA15+","R18+"};
        ComboBox<String> classification_combo = new ComboBox<>(FXCollections.observableArrayList(filter_by_classification));
        classification_combo.setLayoutX(150);
        classification_combo.setLayoutY(75);
        classification_combo.getSelectionModel().selectFirst();
        classification_combo.setOnAction(e -> {
            classification_filter = classification_combo.getValue();
            MovieTable.setItems(getMovieSet());
        });
        nodes.add(classification_combo);

        Text filter_by_location = nodeCreator.createText("Filter by location",300,70);
        filter_by_location.setStyle("-fx-font-size: 10");
        nodes.add(filter_by_location);

        ComboBox<String> location_combo = new ComboBox<>(FXCollections.observableArrayList("all", "Redfern", "Town Hall", "Burwood", "Parramatta"));
        location_combo.setLayoutX(300);
        location_combo.setLayoutY(75);
        location_combo.getSelectionModel().selectFirst();
        location_combo.setOnAction(e -> {
            location_filter = location_combo.getValue();
            if(!TimeTable.getColumns().isEmpty()){
                TimeTable.setItems(getShowingList());
            }
        });
        nodes.add(location_combo);

        Text infoText = nodeCreator.createText("Movie Information",300,610);
        infoText.setStyle("-fx-font-size: 30");
        infoText.setVisible(false);
        nodes.add(infoText);


        MovieTable = new TableView<>();
        //MovieTable.setMaxWidth(700);
        MovieTable.setMaxHeight(250);
        MovieTable.setLayoutX(150);
        MovieTable.setLayoutY(110);
        MovieTable.setFocusTraversable(false);
        TableColumn<Movie,String> movieNameColumn = new TableColumn<>("Movies of the week");
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        movieNameColumn.setMinWidth(500);
        MovieTableColumns.add(movieNameColumn);
        MovieTable.setOnMouseClicked(event -> {
            pane.getChildren().remove(SummaryTable);
            infoText.setVisible(true);
            Movie oldMovie = selectedMovie;
            selectedMovie = MovieTable.getSelectionModel().getSelectedItem();
            TimeTable.setItems(getShowingList());
            if(oldMovie != selectedMovie) {
                purchaseButton.setDisable(true);
            }
            getSummaryList();
        });
        MovieTable.setItems(getMovieSet());
        MovieTable.getColumns().addAll(MovieTableColumns);

        //Filter by screen size
        Text filter_by_screen_text = nodeCreator.createText("Filter by screen size",150,390);
        filter_by_screen_text.setStyle("-fx-font-size: 10");
        nodes.add(filter_by_screen_text);
        String[] filter_by_screen = {"all","Bronze","Silver","Gold"};
        ComboBox<String> screen_size_combo = new ComboBox<>(FXCollections.observableArrayList(filter_by_screen));
        screen_size_combo.setLayoutX(150);
        screen_size_combo.setLayoutY(395);
        screen_size_combo.getSelectionModel().selectFirst();
        screen_size_combo.setOnAction(e -> {
            screen_size_combo.getValue();
            screen_filter = screen_size_combo.getValue();
            TimeTable.setItems(getShowingList());

        });
        nodes.add(screen_size_combo);

        TimeTable = new TableView<>();
        TimeTable.setMaxHeight(150);
        TimeTable.setLayoutX(150);
        TimeTable.setLayoutY(430);
        TableColumn<Showing,String> timeColumn = new TableColumn<>("Times");
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setCellValueFactory((val)->{
            SimpleStringProperty output;
            //String day = val.getValue().getTime().getDayOfWeek().toString();
            String date = val.getValue().getTime().toLocalDate().toString();
            int hour = val.getValue().getTime().toLocalTime().getHour();
            int min = val.getValue().getTime().toLocalTime().getMinute();
            String ampm = "am";
            if(hour>12){
                hour -= 12;
                ampm = "pm";
            }else if(hour == 12){
                ampm = "pm";
            }


            output = new SimpleStringProperty(date + "  " +hour+":"+ min +ampm );
            return output;
        });
        timeColumn.setMinWidth(200);
        TimeTableColumns.add(timeColumn);
        TableColumn<Showing,String> screenSizeColumn = new TableColumn<>("Screen Size");
        screenSizeColumn.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
        screenSizeColumn.setMinWidth(200);
        TimeTableColumns.add(screenSizeColumn);
        TableColumn<Showing,String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setMinWidth(100);
        TimeTableColumns.add(locationColumn);


        TimeTable.setItems(getShowingList());
        TimeTable.getColumns().addAll(TimeTableColumns);

        Text tips = nodeCreator.createText("Screen Size Costs", 300, 670);
        tips.setStyle("-fx-font-size: 30");
            Text tipsText = nodeCreator.createText(
               "             child   adult   student   senior \n" +
                    "Bronze     13      20      15          15\n" +
                    "Silver        18      25      20          20\n" +
                    "Gold         23      30      25          25", 250, 710);

        nodes.add(tips);
        nodes.add(tipsText);
        Text movieListText = nodeCreator.createText("Movies",350,50);
        movieListText.setStyle("-fx-font-size: 40");
        nodes.add(movieListText);

        Text timingText = nodeCreator.createText("Timings",350,390);
        timingText.setStyle("-fx-font-size: 30");
        nodes.add(timingText);


        TimeTable.setOnMouseClicked(event -> {
            selectedShowing = TimeTable.getSelectionModel().getSelectedItem();
            tips.setVisible(false);
            tipsText.setVisible(false);
            if(selectedShowing != null) {
                purchaseButton.setDisable(false);
            }

        });


        purchaseButton = nodeCreator.createButton("Book", 350, 850);
        purchaseButton.setDisable(true);
        purchaseButton.setOnAction(event -> {
            new PurchaseScreen().show(customer, selectedShowing);
            stage.close();

        });



        pane.getChildren().add(purchaseButton);
        pane.getChildren().addAll(nodes);
        pane.getChildren().add(MovieTable);
        pane.getChildren().add(TimeTable);
    }

    public void getSummaryList() {
        if (selectedMovie==null){
            return;
        }

        for(Movie m:Cinema.getMovies()){
            if(m.getMovieID()==selectedMovie.getMovieID()){
                SummaryTable = new TableView<>();
                SummaryTable.setMinWidth(500);
                SummaryTable.setMaxHeight(200);
                SummaryTable.setLayoutX(150);
                SummaryTable.setLayoutY(630);
                TableColumn<String,String> synopsisCol = new TableColumn<>("Synopsis");
                synopsisCol.setMinWidth(200);
                synopsisCol.setCellFactory(tc -> {
                    TableCell<String,String> cell = new TableCell<>();
                    Text text = new Text();
                    cell.setGraphic(text);
                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(synopsisCol.widthProperty());
                    text.textProperty().bind(cell.itemProperty());
                    return cell ;
                });
                TableColumn<String, String> directorCol = new TableColumn<>("Director");
                directorCol.setCellFactory(tc -> {
                    TableCell<String,String> cell = new TableCell<>();
                    Text text = new Text();
                    cell.setGraphic(text);
                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(directorCol.widthProperty());
                    text.textProperty().bind(cell.itemProperty());
                    return cell ;
                });
                TableColumn<String,String> classificationCol = new TableColumn<>("Classification");
                classificationCol.setCellFactory(tc -> {
                    TableCell<String,String> cell = new TableCell<>();
                    Text text = new Text();
                    cell.setGraphic(text);
                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(classificationCol.widthProperty());
                    text.textProperty().bind(cell.itemProperty());
                    return cell ;
                });
                TableColumn<String,String> castCol = new TableColumn<>("Cast");
                castCol.setCellFactory(tc -> {
                    TableCell<String, String> cell = new TableCell<>();
                    Text text = new Text();
                    cell.setGraphic(text);
                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(castCol.widthProperty());
                    text.textProperty().bind(cell.itemProperty());
                    return cell ;
                });
                this.SummaryTable.getColumns().add(synopsisCol);
                this.SummaryTable.getColumns().add(directorCol);
                this.SummaryTable.getColumns().add(classificationCol);
                this.SummaryTable.getColumns().add(castCol);
                synopsisCol.setCellValueFactory(c -> new SimpleStringProperty(m.getSynopsis()));
                directorCol.setCellValueFactory(c -> new SimpleStringProperty(m.getDirector()));
                classificationCol.setCellValueFactory(c -> new SimpleStringProperty(m.getClassification()));
                castCol.setCellValueFactory(c -> new SimpleStringProperty(m.getCast().toString()));
                pane.getChildren().add(SummaryTable);
                SummaryTable.getItems().addAll("Ds");
            }
        }

    }

    public ObservableList<Showing> getShowingList(){
        ObservableList<Showing> outputList = FXCollections.observableArrayList();
        if (selectedMovie==null){
            return outputList;
        }
        for(Showing s:Cinema.getShowingsOfWeek()){
            if(s.getMovie().getMovieID()==selectedMovie.getMovieID() && (s.getLocation().equalsIgnoreCase(location_filter) || location_filter.equals("all"))){
                outputList.add(s);
            }
        }
        if(screen_filter.equals("") || screen_filter.equals("all")){
            return outputList;
        }
        return new FilteredList<>(outputList, i-> i.getScreenSize().equals(screen_filter));
    }
    //a set(list) of movie
    public ObservableList<Movie> getMovieSet(){
        ObservableList<Movie> outputList = FXCollections.observableArrayList();
        outputList.addAll(Cinema.getMovieOfWeek());
        if(classification_filter.equals("") || classification_filter.equals("all")){
            return outputList;
        }
        return new FilteredList<>(outputList, i->i.getClassification().equals(classification_filter));
    }

}


