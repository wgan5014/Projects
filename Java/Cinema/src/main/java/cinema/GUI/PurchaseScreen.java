package cinema.GUI;

import cinema.*;
import cinema.GUILogic.PurchaseScreenLogic;
import cinema.GUILogic.PurchaseTicketsLogic;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PurchaseScreen{
  
    Pane pane = new Pane();
    Scene scene = new Scene(pane,800,800);
    static Stage stage = new Stage();
    Customer customer = null;
    PurchaseScreenLogic purchaseScreenLogic = new PurchaseScreenLogic();
    Timer timer = null;
    PurchaseTicketsLogic purchaseTicketsLogic;
    String screensize;
    Showing showing;
    public void show(Customer customer, Showing showing){
        this.customer = customer;
        this.purchaseTicketsLogic = showing.getPurchaseTicketsLogic();
        this.screensize = showing.getScreenSize();
        this.showing = showing;

        createNodes();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((e)->{
            Transaction userCancelledTransaction = new Transaction("User Cancelled", LocalDateTime.now(),this.customer);
            Cinema.addTransaction(userCancelledTransaction);
            timer.stop();
            new MovieScreen().show(this.customer);
        });

        this.timer= new Timer(1000*60*2, (e) ->{
            Transaction timedOutTransaction = new Transaction("Timed out", LocalDateTime.now(),this.customer);
            Cinema.addTransaction(timedOutTransaction);
            Platform.runLater(()->{
                createNodes();
                purchaseTicketsLogic.reset();
                stage.close();
                new MovieScreen().show(this.customer);
                new popUpScreen().show("You have been idle for too long!\n " +
                        "Your booking has been cancelled.");
            });
            timer.stop();
        });
        timer.setRepeats(false);
        timer.start();

//        this.purchaseTicketsLogic = purchaseTicketsLogic;
    }

    public void show(){
        show(null, null);
    }

    /**

     Purchase Screen

     Choose Screen Size
     Choose Seating
     Choose Kids/Adults/Elders/Student..
     Show pricing
     Cancel Purchase
     Confirm Purchase

     */
    void createNodes(){

        int price = 0;

        List<Node> nodes = new ArrayList<>();

        Rectangle screenRect = addRect(70, 130);
        nodes.add(screenRect);
        Text pricingAmt = nodeCreator.createText(Integer.toString(price),150,450);

        Button cancel = nodeCreator.createButton("Cancel Purchase");
        cancel.setLayoutX(20);
        cancel.setLayoutY(500);
        cancel.setOnMousePressed((o) -> {
            new MovieScreen().show(customer);
            Transaction cancelledTransaction = new Transaction("User cancelled", LocalDateTime.now(),this.customer);
            Cinema.addTransaction(cancelledTransaction);
            timer.stop();
            stage.close();
        });

        List<Text> amounts = new ArrayList<>();

        Text chosen = nodeCreator.createText("None", 270, 180);
        Button confirm = nodeCreator.createButton("Purchase");
        confirm.setLayoutX(150);
        confirm.setLayoutY(500);
        confirm.setDisable(true);
        confirm.setOnAction(e -> {
            Booking userBooking = new Booking(customer,showing,chosen.getText());

            userBooking.addSeats("Child", Integer.parseInt(amounts.get(0).getText()));
            userBooking.addSeats("Adult", Integer.parseInt(amounts.get(1).getText()));
            userBooking.addSeats("Student", Integer.parseInt(amounts.get(2).getText()));
            userBooking.addSeats("Senior", Integer.parseInt(amounts.get(3).getText()));
            new transactionScreen(customer,timer, purchaseTicketsLogic,userBooking, Integer.parseInt(pricingAmt.getText()), showing).show(stage);

        });

        Button createAccountButton = nodeCreator.createButton("Create an account");
        createAccountButton.setLayoutX(150);
        createAccountButton.setLayoutY(500);
        createAccountButton.setOnMousePressed((o) -> {
            Platform.runLater(() ->{
               stage.close();
               timer.stop();
            });
            CustRegoScreen.show();
        });


        pane.setOnMouseClicked((e) ->{
            timer.restart();
        });
        pane.setOnMouseMoved((e) ->{
            timer.restart();
        });

//        cancel.setDisable(true);
        nodes.add(cancel);


        Text seatingPosition = nodeCreator.createText("Choose Seating Position",0,50);
        seatingPosition.setStyle("-fx-font-size: 40");
        nodes.add(seatingPosition);

        Text chosenText = nodeCreator.createText("Chosen Seating Preference: ", 50, 180);

        nodes.add(chosenText);
        nodes.add(chosen);



        List<Button> buttons = new ArrayList<>();
        purchaseTicketsLogic.reset();
        Text frontText = nodeCreator.createText(Integer.toString(purchaseTicketsLogic.getFront()),500, 90);
        Text middleText = nodeCreator.createText(Integer.toString(purchaseTicketsLogic.getMiddle()),500, 120);
        Text backText = nodeCreator.createText(Integer.toString(purchaseTicketsLogic.getBack()),500, 150);
        nodes.add(backText);
        nodes.add(middleText);
        nodes.add(frontText);
        reset(amounts, buttons, frontText, middleText, backText, pricingAmt);

        Button front = nodeCreator.createButton("Front");
        front.setMinWidth(200);
        front.setLayoutX(300);
        front.setLayoutY(70);
        front.setOnAction(e -> {
            timer.restart();
            chosen.setText(front.getText());

            if(Integer.parseInt(pricingAmt.getText()) != 0) {
                confirm.setDisable(false);
            }

            reset(amounts, buttons, frontText, middleText, backText, pricingAmt);
        });
        nodes.add(front);



        Button middle = nodeCreator.createButton("Middle");
        middle.setMinWidth(200);
        middle.setLayoutX(300);
        middle.setLayoutY(100);
        middle.setOnAction(e -> {
            timer.restart();
            chosen.setText(middle.getText());

            if(Integer.parseInt(pricingAmt.getText()) != 0) {
                confirm.setDisable(false);
            }
            reset(amounts, buttons, frontText, middleText, backText, pricingAmt);
        });
        nodes.add(middle);


        Button back = nodeCreator.createButton("Back");
        back.setMinWidth(200);
        back.setLayoutX(300);
        back.setLayoutY(130);

        back.setOnAction(e -> {
            timer.restart();
            chosen.setText(back.getText());
            if(Integer.parseInt(pricingAmt.getText()) != 0) {
                confirm.setDisable(false);
            }
            for(Button button: buttons) {
                purchaseTicketsLogic.reset();
                frontText.setText(Integer.toString(purchaseTicketsLogic.getFront()));
                middleText.setText(Integer.toString(purchaseTicketsLogic.getMiddle()));
                backText.setText(Integer.toString(purchaseTicketsLogic.getBack()));
                button.setDisable(false);
            }
            reset(amounts, buttons, frontText, middleText, backText, pricingAmt);
        });
        nodes.add(back);


        Text ticketHolder = nodeCreator.createText("Ticket holder information",0,250);
        ticketHolder.setStyle("-fx-font-size: 40");
        nodes.add(ticketHolder);
        Rectangle ticketRect = addRect(270, 130);
        nodes.add(ticketRect);
    // logic for purchase tickets
        Text child = nodeCreator.createText("Child (under 12)",30,290);
        Text adult = nodeCreator.createText("Adult",30,320);
        Text student = nodeCreator.createText("Student",30,350);
        Text senior = nodeCreator.createText("Senior/Pensioner",30,380);
        nodes.add(child);
        nodes.add(adult);
        nodes.add(student);
        nodes.add(senior);
        int[] prices = {0,0,0,0};
        if(Objects.equals(screensize, "Gold")) {
            prices[0] = 23;
            prices[1] = 30;
            prices[2] = 25;
            prices[3] = 25;
        } else if(Objects.equals(screensize, "Silver")) {
            prices[0] = 18;
            prices[1] = 25;
            prices[2] = 20;
            prices[3] = 20;
        } else {
            prices[0] = 13;
            prices[1] = 20;
            prices[2] = 15;
            prices[3] = 15;
        }

        for(int i = 0; i < 4; i++) {
            int yPos = 290+i*30 - 15;
            Button addBtn = nodeCreator.createButton("+");

            addBtn.setLayoutX(800 - 130);
            addBtn.setLayoutY(yPos);
            Button subBtn = nodeCreator.createButton("-");
            Text amount = nodeCreator.createText("0",800 - 165 ,yPos + 15);
            Text indPrice = nodeCreator.createText("$" + Integer.toString(prices[i]), 500, yPos+15);
            nodes.add(indPrice);
            subBtn.setLayoutX(800 - 200);
            amounts.add(amount);
            subBtn.setLayoutY(yPos);
            subBtn.setDisable(true);
            addBtn.setDisable(true);
            buttons.add(addBtn);
            buttons.add(subBtn);
            nodes.add(addBtn);
            nodes.add(subBtn);
            nodes.add(amount);
        }




        Text pricing = nodeCreator.createText("Pricing: ",0,450);
        pricing.setStyle("-fx-font-size: 40");

        pricingAmt.setStyle("-fx-font-size: 40");
        Text[] texts = {frontText, middleText, backText};
        handlePrice(buttons, prices, amounts, pricingAmt, chosen, confirm, texts);
        nodes.add(pricing);
        nodes.add(pricingAmt);
        if(customer != null)
            nodes.add(confirm);
        else{
            nodes.add(createAccountButton);
        }




        pane.getChildren().addAll(nodes);
    }

    public void reset(List<Text> amounts, List<Button> buttons, Text frontText, Text middleText, Text backText, Text price) {
        for(Button button: buttons) {
            purchaseTicketsLogic.reset();
            frontText.setText(Integer.toString(purchaseTicketsLogic.getFront()));
            middleText.setText(Integer.toString(purchaseTicketsLogic.getMiddle()));
            backText.setText(Integer.toString(purchaseTicketsLogic.getBack()));
            button.setDisable(false);
        }
        for(Text amount: amounts) {
            amount.setText("0");
        }
        price.setText("0");
    }

    public void disableAdd(List<Button> buttons) {
        buttons.get(0).setDisable(true);
        buttons.get(2).setDisable(true);
        buttons.get(4).setDisable(true);
        buttons.get(6).setDisable(true);
    }

    public void enableAdd(List<Button> buttons) {
        buttons.get(0).setDisable(false);
        buttons.get(2).setDisable(false);
        buttons.get(4).setDisable(false);
        buttons.get(6).setDisable(false);
    }

    public void disableSub(List<Button> buttons) {
        buttons.get(1).setDisable(true);
        buttons.get(3).setDisable(true);
        buttons.get(5).setDisable(true);
        buttons.get(7).setDisable(true);
    }

    public void enableSub(List<Button> buttons) {
        buttons.get(1).setDisable(false);
        buttons.get(3).setDisable(false);
        buttons.get(5).setDisable(false);
        buttons.get(7).setDisable(false);
    }

    public void handleAdd(int i, List<Text> amounts, List<Button> buttons, Text price, int[] prices, Button confirm, Text chosen, Text[] texts) {
        timer.restart();
        Text amount = amounts.get(i);
        amount.setText(purchaseScreenLogic.handleAdd(amount));
        price.setText(Integer.toString(purchaseScreenLogic.priceAdd(price, prices, i)));
        if(Integer.parseInt(price.getText()) != 0) {
            if(!chosen.getText().equals("None")) {
                confirm.setDisable(false);
            }
            if(chosen.getText().equals("Front")) {
                texts[0].setText(Integer.toString(purchaseTicketsLogic.subFront()));
            }
            else if(chosen.getText().equals("Middle")) {
                texts[1].setText(Integer.toString(purchaseTicketsLogic.subMiddle()));
            }
            else if(chosen.getText().equals("Back")){
                texts[2].setText(Integer.toString(purchaseTicketsLogic.subBack()));
            }
            if(purchaseTicketsLogic.disableAdd()) {
                disableAdd(buttons);
            }
            else {
                enableAdd(buttons);
                if(!purchaseTicketsLogic.disableMinus(chosen.getText())) {
                    enableSub(buttons);
                }
            }
        }

    }

    public void handleSub(int i, List<Text> amounts, List<Button> buttons, Text price, int[] prices, Button confirm, Text chosen, Text[] texts) {
        timer.restart();
        Text amount = amounts.get(i);

        if(Integer.parseInt(amount.getText()) != 0) {
            amount.setText(purchaseScreenLogic.handleSub(amount));
            price.setText(Integer.toString(purchaseScreenLogic.priceSub(price, prices, i)));
            if(Integer.parseInt(price.getText()) != 0) {
                if(!chosen.getText().equals("None")) {
                    confirm.setDisable(false);
                }

            }
            else {
                confirm.setDisable(true);
            }
            if(chosen.getText().equals("Front")) {
                texts[0].setText(Integer.toString(purchaseTicketsLogic.addFront()));
            }
            else if(chosen.getText().equals("Middle")) {
                texts[1].setText(Integer.toString(purchaseTicketsLogic.addMiddle()));
            }
            else if(chosen.getText().equals("Back")){
                texts[2].setText(Integer.toString(purchaseTicketsLogic.addBack()));
            }

            if(purchaseTicketsLogic.disableMinus(chosen.getText())) {
                disableSub(buttons);
            }
            else {
                enableSub(buttons);
                if(!purchaseTicketsLogic.disableAdd()) {
                    enableAdd(buttons);
                }
            }

        }
    }
    public void handlePrice(List<Button> buttons, int[] prices, List<Text> amounts, Text price, Text chosen, Button confirm, Text[] texts) {
        buttons.get(0).setOnAction(e -> {
            handleAdd(0, amounts, buttons, price, prices, confirm, chosen, texts);

        });

        buttons.get(1).setOnAction(e -> {
            handleSub(0, amounts, buttons, price, prices, confirm, chosen, texts);
        });

        buttons.get(2).setOnAction(e -> {
            handleAdd(1, amounts, buttons, price, prices, confirm, chosen, texts);
        });

        buttons.get(3).setOnAction(e -> {
            handleSub(1, amounts, buttons, price, prices, confirm, chosen, texts);
        });
        buttons.get(4).setOnAction(e -> {
            handleAdd(2, amounts, buttons, price, prices, confirm, chosen, texts);
        });

        buttons.get(5).setOnAction(e -> {
            handleSub(2, amounts, buttons, price, prices, confirm, chosen, texts);
        });
        buttons.get(6).setOnAction(e -> {
            handleAdd(3, amounts, buttons, price, prices, confirm, chosen, texts);
        });

        buttons.get(7).setOnAction(e -> {
            handleSub(3, amounts, buttons, price, prices, confirm, chosen, texts);
        });
    }


    public Rectangle addRect(int y, int height) {
        Rectangle r = new Rectangle();
        r.setX(10);
        r.setY(y);
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.BLACK);
        r.setWidth(780);
        r.setHeight(height);
        r.setArcWidth(20);
        r.setArcHeight(20);
        return r;
    }
}
