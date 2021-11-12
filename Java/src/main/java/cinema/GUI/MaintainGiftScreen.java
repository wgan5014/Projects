package cinema.GUI;

import cinema.GiftCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.Pane;
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

public class MaintainGiftScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 800, 700);
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
        Text title = nodeCreator.createText("Gift Card Screen", 350, 70);
        title.setStyle("-fx-font-size: 30");

        TableView<GiftCard> giftCardScreen = new TableView<>();

        //list of table columns
        List<TableColumn<GiftCard,?>> giftCardColumn = new ArrayList<>();

        //movie column
        TableColumn<GiftCard,String> movieNameColumn = new TableColumn<>("Gift card number");
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        movieNameColumn.setMinWidth(200);
        giftCardColumn.add(movieNameColumn);

        //synopsis column
        TableColumn<GiftCard, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceCol.setMinWidth(150);
        giftCardColumn.add(priceCol);


        //director column
        TableColumn<GiftCard, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        stateCol.setMinWidth(100);
        giftCardColumn.add(stateCol);

        //movie formatting
        giftCardScreen.setMaxHeight(350);
        giftCardScreen.setLayoutX(50);
        giftCardScreen.setLayoutY(120);
        giftCardScreen.setFocusTraversable(false);

        ObservableList<GiftCard> giftList = FXCollections.observableArrayList();
        giftList.addAll(Cinema.getGiftCards());
        giftCardScreen.setItems(giftList);
        giftCardScreen.getColumns().addAll(giftCardColumn);


        Button addGiftCard = nodeCreator.createButton("Add Gift Card", 365, 550);
        addGiftCard.setOnMousePressed((e) -> {
            new addGiftCardPopUp().show(isManager);
        });

        Button removeGiftCard = nodeCreator.createButton("Remove Gift Card", 100, 550);
        removeGiftCard.setOnMousePressed((e)-> {

            GiftCard giftCard = giftCardScreen.getSelectionModel().getSelectedItem();
            if(giftCard != null) {
                Cinema.removeGiftCard(giftCard.getCardNumber());
            }
            show(this.isManager);
        });


        Button back = nodeCreator.createButton("Go back", 370, 650);
        back.setOnMousePressed((e) -> {
            StaffHomeScreen.show(false);
            stage.close();
        });
            pane.getChildren().addAll(title, giftCardScreen, back, addGiftCard, removeGiftCard);
//        pane.getChildren().addAll(title, movieTable, editMovie, addMovie, deleteMovie, back);

    }
}
