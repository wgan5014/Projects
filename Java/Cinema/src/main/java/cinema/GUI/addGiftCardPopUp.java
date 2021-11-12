package cinema.GUI;

import cinema.Cinema;
import cinema.GiftCard;
import cinema.Movie;
import cinema.GUILogic.StaffMovieLogic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class addGiftCardPopUp {
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, 500, 400);
    static Stage stage = new Stage();
    private boolean isManager;

    public void show(boolean isManager) {
        this.isManager = isManager;
        createNodes();
        stage.setScene(scene);
        stage.show();
    }

    public void createNodes() {
        Text title = nodeCreator.createText("Add Gift Card", 75, 50);
        TextField cardNum = nodeCreator.createTextField("Gift card number...", 75, 100);
        TextField amount = nodeCreator.createTextField("Amount...", 75, 150);

        Text invalidDetailsText = nodeCreator.createText("Invalid gift card number, make sure its not empty, has GC as its suffix and is size 16", 5, 350, 12);
        invalidDetailsText.setFill(Paint.valueOf("red"));
        invalidDetailsText.setVisible(false);

        Text equalCardText = nodeCreator.createText("This card already exists", 5, 350, 15);
        equalCardText.setFill(Paint.valueOf("red"));
        equalCardText.setVisible(false);

        Text invalidDetailsAmount = nodeCreator.createText("Please enter valid amount", 0, 350, 15);
        invalidDetailsAmount.setFill(Paint.valueOf("red"));
        invalidDetailsAmount.setVisible(false);
        Button submit = nodeCreator.createButton("Add card", 100, 300);
        submit.setOnMousePressed((e) -> {
            invalidDetailsText.setVisible(false);
            invalidDetailsAmount.setVisible(false);
            equalCardText.setVisible(false);
            boolean x = true;
            if(cardNum.getText().endsWith("GC")) {
                try {
                    if (!amount.getText().equals("") && Double.parseDouble(amount.getText()) != 0) {
                        String s = cardNum.getText().substring(0, cardNum.getText().length()-2);
                        try {
                            Double.parseDouble(s);
                        }
                        catch (Exception ex) {
                            invalidDetailsText.setVisible(true);
                            x = false;
                        }
                        if(s.length() != 16) {
                            invalidDetailsText.setVisible(true);
                            x = false;
                        }
                        if(!Cinema.cardValid(cardNum.getText())) {
                            equalCardText.setVisible(true);
                            x = false;
                        }
                        if(x) {
                            Cinema.addGiftCard(new GiftCard(cardNum.getText(), true, Double.parseDouble(amount.getText())));
                            new MaintainGiftScreen().show(isManager);

                            stage.close();
                        }
                    } else {
                        invalidDetailsAmount.setVisible(true);
                    }
                }
                catch(Exception ex){
                    invalidDetailsAmount.setVisible(true);
                    x = false;
                }
            }
            else {
                invalidDetailsText.setVisible(true);
            }
        });

        pane.getChildren().addAll(title,cardNum,amount, submit, invalidDetailsAmount, invalidDetailsText, equalCardText);
    }
}
