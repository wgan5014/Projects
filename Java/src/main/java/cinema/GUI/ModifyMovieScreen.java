package cinema.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
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

public class ModifyMovieScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 950, 700);
    static Stage stage = new Stage();
    private boolean isManager;
    public void show(boolean isManager) {
        this.isManager = isManager;
        createNodes();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((o) ->{
            StaffHomeScreen.show(isManager);
        });
    }

    public void createNodes() {
        Text title = nodeCreator.createText("Movies", 350, 70);
        title.setStyle("-fx-font-size: 30");

        TableView<Movie> movieTable = new TableView<>();

        //list of table columns
        List<TableColumn<Movie,?>> movieTableColumns = new ArrayList<>();

        //movie column
        TableColumn<Movie,String> movieNameColumn = new TableColumn<>("Name");
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        movieNameColumn.setMinWidth(200);
        movieTableColumns.add(movieNameColumn);

        //synopsis column
        TableColumn<Movie, String> synopsisCol = new TableColumn<>("Synopsis");
        synopsisCol.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
        synopsisCol.setMinWidth(150);
        movieTableColumns.add(synopsisCol);

        
        //director column
        TableColumn<Movie, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));
        directorCol.setMinWidth(100);
        movieTableColumns.add(directorCol);

        //classification column
        TableColumn<Movie, String> classificationCol = new TableColumn<>("Classification");
        classificationCol.setCellValueFactory(new PropertyValueFactory<>("classification"));
        classificationCol.setMinWidth(100);
        movieTableColumns.add(classificationCol);

        //cast column
        TableColumn<Movie, String> castCol = new TableColumn<>("Cast");
        castCol.setCellValueFactory(new PropertyValueFactory<>("cast"));
        castCol.setMinWidth(150);
        movieTableColumns.add(castCol);

        //release date column
        TableColumn<Movie, String> dateCol = new TableColumn<>("Release Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        dateCol.setMinWidth(150);
        movieTableColumns.add(dateCol);

        //movie formatting
        movieTable.setMaxHeight(350);
        movieTable.setLayoutX(50);
        movieTable.setLayoutY(120);
        movieTable.setFocusTraversable(false);

        movieTable.setItems(FXCollections.observableList(Cinema.getMovies()));
        movieTable.getColumns().addAll(movieTableColumns);

        //add buttons
        Button editMovie = nodeCreator.createButton("Edit Movie", 365, 500);
        editMovie.setOnMousePressed((e) -> {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();
            if(movie != null) {
                new editMoviePopUpScreen().show(movie, isManager);
                stage.close();
            }
        });

        Button addMovie = nodeCreator.createButton("Add Movie", 365, 550);
        addMovie.setOnMousePressed((e) -> {
            new addMoviePopUpScreen().show(isManager);
            stage.close();
        });

        Button deleteMovie = nodeCreator.createButton("Remove Movie", 355, 600);
        deleteMovie.setOnMousePressed((e) -> {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();
            if(movie != null) {
                StaffMovieLogic.removeMovie(movie);
                this.show(isManager);
            }
        });

        Button back = nodeCreator.createButton("Go back", 370, 650);
        back.setOnMousePressed((e) -> {
// willbranch
        		// show() had no args b4
            StaffHomeScreen.show(StaffHomeScreen.isShowingManager());
            //StaffHomeScreen.show(false); // <-- from master
//master
            stage.close();
        });

        pane.getChildren().addAll(title, movieTable, editMovie, addMovie, deleteMovie, back);
    }
}
