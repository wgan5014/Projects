package cinema.GUI;

import cinema.GUILogic.StaffActionScreenLogic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StaffHomeScreen {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 400, 550);
    static Stage stage = new Stage();
    // for testing purposes
    public static boolean isManager = false;

    public static void show(boolean isManager) {
        createNodes(isManager);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            homeScreen.show();
        });
    }
    // testing purposes
    public static boolean isShowingManager() {
    	return isManager;
    }

    public static void createNodes(boolean isManager) {



        Text title = nodeCreator.createText("Staff Home Screen", 125, 100);
        Button modifyMovies = nodeCreator.createButton("Modify movies", 150, 150);
        modifyMovies.setOnMousePressed((e) -> {
            new ModifyMovieScreen().show(isManager);
            stage.close();
        });

        Button modifyShowings = nodeCreator.createButton("Modify showings", 145, 200);
        modifyShowings.setOnAction((e)->{
            new ModifyShowingScreen().show(isManager);
            stage.close();
        });
        Button maintainCards = nodeCreator.createButton("Maintain gift cards", 145, 250);
        maintainCards.setOnMousePressed((e) -> {
            new MaintainGiftScreen().show(isManager);
            stage.close();
        });
        Button movieSummary = nodeCreator.createButton("Obtain Movie Summary", 135, 300);
        movieSummary.setOnMousePressed((e) -> {
            StaffActionScreenLogic.generateMovieReport();
            new popUpScreen().show("Movie summary csv has been successfully created, found in file movielist.txt");
        });

        Button bookingSummary = nodeCreator.createButton("Obtain Booking Summary", 130, 350);
        bookingSummary.setOnMousePressed((e) -> {
        		StaffActionScreenLogic.generateBookingReport();
            new popUpScreen().show("Booking summary csv has been successfully created, found in file showingslist.csv");
        });

        pane.getChildren().addAll(title, modifyMovies, modifyShowings, maintainCards, movieSummary, bookingSummary);

		// if isManager then add the manager features
		// i.e. being able to see the CSV of transactions
		// and the ability to add/remove Cinema staff
		if (isManager) {
		
			// get transactions list button
			Button getTransactions = nodeCreator.createButton("Obtain Cancelled Transaction Summary", 100, 400);
			getTransactions.setOnMousePressed((e) -> {
				StaffActionScreenLogic.getCancelledTransactions();
				new popUpScreen().show("Cancelled Transaction summary csv has been successfully created, found in file transaction.csv");
			});
			
			// add/remove staff button
			Button editStaff = nodeCreator.createButton("Add/Remove Staff", 145, 450);
			editStaff.setOnMousePressed((e) -> {
				new EditStaffScreen().show();
				stage.close();
			});
			
			// add these button nodes to the pane
			pane.getChildren().addAll(getTransactions, editStaff);
		}
    }
}
