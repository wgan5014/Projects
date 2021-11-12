package cinema.GUI;

import cinema.Showing;
import cinema.Staff;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import cinema.Cinema;
import cinema.Movie;
import cinema.GUILogic.StaffMovieLogic;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.ArrayList;

public class ModifyShowingScreen {
    //static VBox vbox = new VBox();
    //static Scene scene = new Scene(vbox,800,700);
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 800, 700);
    static Stage stage = new Stage();
    boolean isManager;
    public TableView<Showing> showingTable = new TableView<>();

    public void show(boolean isManager){
        createNodes();
        stage.setScene(scene);
        stage.show();
        this.isManager = isManager;
    }

    public void createNodes(){
        List<Node> nodes = new ArrayList<>();
        //vbox.setAlignment(Pos.TOP_CENTER);
        stage.setOnCloseRequest((e)-> {
            Platform.runLater(() -> {
                StaffHomeScreen.show(isManager);
            });
        });
        Text title = nodeCreator.createText("Showings", 350, 70);
        title.setStyle("-fx-font-size: 30");
        nodes.add(title);

        //TableView<Showing> showingTable = new TableView<>();
        showingTable = new TableView<>();
        //list of table columns
        List<TableColumn<Showing,?>> showingTableColumns = new ArrayList<>();
        //Showing ID column
        TableColumn<Showing, Integer> showingIDCol = new TableColumn<>("Showing ID");
        showingIDCol.setCellValueFactory(new PropertyValueFactory<>("showingID"));
        showingIDCol.setMinWidth(100);
        showingTableColumns.add(showingIDCol);

        //Movie name column
        TableColumn<Showing, String> movieNameCol = new TableColumn<>("Movie");
        movieNameCol.setCellValueFactory((val)->{
            SimpleStringProperty output = null;

            String movieName = val.getValue().getMovie().getName();
            output = new SimpleStringProperty(movieName);

            return output;
        });
        movieNameCol.setMinWidth(250);
        showingTableColumns.add(movieNameCol);

        //time column
        TableColumn<Showing,String> timeColumn = new TableColumn<>("Times");
        timeColumn.setCellValueFactory((val)->{
            SimpleStringProperty output;
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
        timeColumn.setMinWidth(150);
        showingTableColumns.add(timeColumn);

        //screen size column
        TableColumn<Showing,String> screenSizeCol = new TableColumn<>("Screen size");
        screenSizeCol.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
        screenSizeCol.setMinWidth(100);
        showingTableColumns.add(screenSizeCol);

        //location column
        TableColumn<Showing,String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationCol.setMinWidth(150);
        showingTableColumns.add(locationCol);

        showingTable.setMaxHeight(350);
        showingTable.setLayoutX(20);
        showingTable.setLayoutY(120);
        showingTable.setFocusTraversable(false);

        showingTable.setItems(getAllShowings());
        showingTable.getColumns().addAll(showingTableColumns);
        nodes.add(showingTable);
        //end of table

        //buttons
        Button addShowing = nodeCreator.createButton("Add Showing", 365,500);
        addShowing.setOnAction((e)->{
            new addShowingPopUpScreen().show(isManager);
            stage.close();
        });
        nodes.add(addShowing);

        Button editShowing = nodeCreator.createButton("Edit Showing", 365,550);
        editShowing.setOnAction((e)->{
            Showing showing = showingTable.getSelectionModel().getSelectedItem();
            if(showing != null) {
                new editShowingPopUpScreen().show(showing, isManager);
                stage.close();
            }
        });
        nodes.add(editShowing);

        Button removeShowing = nodeCreator.createButton("Remove Showing", 365,600);
        removeShowing.setOnAction((e)->{
            Showing showing = showingTable.getSelectionModel().getSelectedItem();
            if(showing != null) {
                Cinema.removeShowing(showing);
                show(isManager);
            }
        });
        nodes.add(removeShowing);

        Button back = nodeCreator.createButton("Go back",365,650);
        back.setOnMousePressed((e) -> {
            StaffHomeScreen.show(false);
            stage.close();
        });
        nodes.add(back);

        pane.getChildren().addAll(nodes);



    }
    public ObservableList<Showing> getAllShowings(){
        ObservableList<Showing> outputList = FXCollections.observableArrayList();
        List<Showing> showing = new ArrayList<>();
        for(Showing s: Cinema.getShowings()) {
            if(s.getMovie() != null) {
                showing.add(s);
            }
        }
        outputList.addAll(showing);
        return outputList;
    }



}
