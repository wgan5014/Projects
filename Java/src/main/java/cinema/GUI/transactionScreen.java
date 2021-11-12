package cinema.GUI;

import cinema.*;
import cinema.GUILogic.CardScreenLogic;
import cinema.GUILogic.PurchaseTicketsLogic;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDateTime;

public class transactionScreen {

    BorderPane pane = new BorderPane();
    Scene scene;
    CardScreenLogic cardScreenLogic = null;
    Stage stage = new Stage();
    Customer customer;
    Timer timer;
    PurchaseTicketsLogic purchaseTicketsLogic;
    Booking userBooking;
    Stage purchaseStage;
    double price;
    boolean giftcard = false;
    GiftCard currentCard = null;
    double newPrice;
    Showing showing;

    public transactionScreen(Customer customer, Timer timer, PurchaseTicketsLogic purchaseTicketsLogic, Booking userBooking, double price, Showing showing) {
        this.customer = customer;
        this.timer = timer;
        timer.addActionListener((e)->{
            Platform.runLater(()->{
                this.stage.close();
            });
        });
        this.purchaseTicketsLogic = purchaseTicketsLogic;
        this.userBooking = userBooking;
        this.price = price;
        this.showing = showing;
    }

    public void show(Stage purchaseStage) {
        pane = new BorderPane();
        pane.setPadding(new Insets(50, 100, 100, 100));
        this.scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        createNodes();
        stage.show();
        this.purchaseStage = purchaseStage;
    }

    public void setGiftcard(boolean val) {
        this.giftcard = val;
    }
    public boolean getGiftCard() {
        return this.giftcard;
    }
    private void createNodes() {
        cardScreenLogic = new CardScreenLogic();
        Button cancelButton = nodeCreator.createButton("Cancel");
        Button confirmButton = nodeCreator.createButton("Confirm");
        confirmButton.setDisable(true);
        Button openGiftCardButton = nodeCreator.createButton("I have a gift card");
        TextField cardHolderField = nodeCreator.createTextField("Card Holder Name", 0, 0);
        cardHolderField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")) {
                if(!cardScreenLogic.getName()) {
                    cardScreenLogic.toggleName();
                }
            }
            else {
                if(cardScreenLogic.getName()) {
                    cardScreenLogic.toggleName();
                }
            }

            confirmButton.setDisable(!cardScreenLogic.checkEnableButton());

        });
        TextField cardNumberField = nodeCreator.createTextField("Card Number", 0, 0);
        cardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")) {
                if(!cardScreenLogic.getNum()) {
                    cardScreenLogic.toggleNum();
                }
            }
            else {
                if(cardScreenLogic.getNum()) {
                    cardScreenLogic.toggleNum();
                }
            }

            confirmButton.setDisable(!cardScreenLogic.checkEnableButton());

        });
        if(customer.getCard() != null) {
            cardHolderField.setText(customer.getCard().getCardHolder());
            cardNumberField.setText(Integer.toString(customer.getCard().getCardNumber()));
        }

        Text incorrectCardText = nodeCreator.createText("Incorrect credit card details", 0, 0,15);
        incorrectCardText.setFill(Paint.valueOf("red"));
        incorrectCardText.setVisible(false);
        Text incorrectGiftCardText = nodeCreator.createText("Incorrect gift card details", 0, 0,15);
        incorrectGiftCardText.setFill(Paint.valueOf("red"));
        incorrectGiftCardText.setVisible(false);
        Text giftCardSuccess = nodeCreator.createText("Gift card successfully added, remaining must be paid through card ", 0, 0, 15);
        giftCardSuccess.setFill(Paint.valueOf("green"));
        giftCardSuccess.setVisible(false);
        Text redeemedGiftCardText = nodeCreator.createText("Gift card has already been redeemed", 0, 0,15);
        redeemedGiftCardText.setFill(Paint.valueOf("red"));
        redeemedGiftCardText.setVisible(false);



        TextField giftCardField = nodeCreator.createTextField("Gift Card Number", 0, 0);
        giftCardField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")) {
                if(!cardScreenLogic.getGift()) {
                    cardScreenLogic.toggleGift();
                }
            }
            else {
                if(cardScreenLogic.getGift()) {
                    cardScreenLogic.toggleGift();
                }
            }

            confirmButton.setDisable(!cardScreenLogic.checkEnableButton());

        });
        Text titleText = nodeCreator.createText("Enter your card details", 0, 0);

        CheckBox checkBox = new CheckBox();
        Text saveCardText = nodeCreator.createText("Save your card details", 0, 0);
        HBox saveCardHbox = new HBox(15);
        saveCardHbox.getChildren().addAll(checkBox, saveCardText);
        saveCardHbox.setAlignment(Pos.BASELINE_CENTER);

        VBox fieldsVBox = new VBox(15);
        fieldsVBox.getChildren().addAll(
                titleText,
                incorrectCardText,
                cardHolderField,
                cardNumberField,
                saveCardHbox,
                openGiftCardButton);

        fieldsVBox.setAlignment(Pos.CENTER);


        pane.setCenter(fieldsVBox);
        Text pricing = nodeCreator.createText("Price: ", 0, 0);
        Text priceAmt = nodeCreator.createText(Double.toString(price), 0, 0);
        HBox buttonPane = new HBox(15);
        buttonPane.setPadding(new Insets(20, 0, 5, 0));
        buttonPane.setAlignment(Pos.BASELINE_CENTER);
        buttonPane.getChildren().addAll(confirmButton, cancelButton, pricing, priceAmt);
        pane.setBottom(buttonPane);


        pane.setRight(null);
        VBox giftCardPanel = new VBox(5);
        giftCardPanel.getChildren().addAll(incorrectGiftCardText,giftCardField);
        VBox giftErrorPanel = new VBox(5);
        giftErrorPanel.getChildren().addAll(giftCardSuccess, redeemedGiftCardText);
        giftErrorPanel.setAlignment(Pos.BASELINE_CENTER);
        giftCardPanel.setAlignment(Pos.BASELINE_CENTER);

        Button noGiftCardButton = nodeCreator.createButton("I do not have a gift card");
        openGiftCardButton.setOnMousePressed((e) ->{
            setGiftcard(true);
            fieldsVBox.getChildren().remove(openGiftCardButton);
            cardHolderField.setDisable(true);
            cardNumberField.setDisable(true);
            checkBox.setDisable(true);
            checkBox.setSelected(false);
            fieldsVBox.getChildren().add(noGiftCardButton);
            fieldsVBox.getChildren().add(giftCardPanel);
            fieldsVBox.getChildren().add(giftErrorPanel);
            Platform.runLater(() ->{
               this.scene.setRoot(new Pane());
               this.scene = new Scene(pane,600,500);
                stage.setScene(this.scene);
               stage.sizeToScene();
            });
        });

        noGiftCardButton.setOnMousePressed((e) ->{
            setGiftcard(false);
            fieldsVBox.getChildren().remove(noGiftCardButton);
            fieldsVBox.getChildren().add(openGiftCardButton);
            fieldsVBox.getChildren().remove(giftCardPanel);
            fieldsVBox.getChildren().remove(giftErrorPanel);
            cardHolderField.setDisable(false);
            cardNumberField.setDisable(false);
            checkBox.setDisable(false);
            Platform.runLater(() ->{
                this.scene.setRoot(new Pane());
                this.scene = new Scene(pane,600,400);
                stage.setScene(this.scene);
                stage.sizeToScene();
            });
        });

        confirmButton.setOnAction((e) ->{
            timer.restart();
            giftCardSuccess.setVisible(false);
            redeemedGiftCardText.setVisible(false);
            incorrectGiftCardText.setVisible(false);
            //gift card valid
            if(getGiftCard()) {
                GiftCard giftCard = Cinema.getGiftCard(giftCardField.getText());

                if(giftCard != null) {
                    if(giftCard.isRedeemable()) {
                        if(currentCard == null) {
                            currentCard = giftCard;
                        }
                        else {
                            currentCard.setRedeemable(true);
                            currentCard = giftCard;
                        }

                        if(giftCard.getAmount() <= price) {
                            newPrice = price;
                            newPrice -= giftCard.getAmount();
                            priceAmt.setText(Double.toString(newPrice));
                            giftCardSuccess.setVisible(true);
                            fieldsVBox.getChildren().remove(noGiftCardButton);
                            cardHolderField.setDisable(false);
                            cardNumberField.setDisable(false);
                            checkBox.setDisable(false);
                            //disable gift card, enable card, all the shbang
                            setGiftcard(false);
                            giftCardField.setDisable(true);
                        }
                        else {
                            giftCard.setRedeemable(false);
                            showing.addBooking(userBooking);
                            int cost = purchaseTicketsLogic.done();
                            popUpScreen receiptScreen = new popUpScreen();
                                    receiptScreen.show("receipt\n" +
                                    "Movie: " + userBooking.getShowing().getMovie().getName() + "\n" +
                                    "Time: " + userBooking.getShowing().getTime() + "\n" +
                                    "Screen size: " + userBooking.getShowing().getScreenSize() + "\n" +
                                    "Booking ID: " + userBooking.getBookingID() + "\n" +
                                    "Seat position: " + userBooking.getSeatPosition() + "\n" +
                                    "Tickets booked: " + cost + "\n" +
                                    "Price: " + price + "\n"

                            );
                            receiptScreen.setOnClose((r) ->{
                                Platform.runLater(()->{
                                    this.stage.close();
                                    this.purchaseStage.close();
                                    new MovieScreen().show(customer);
                                });
                            });

                            receiptScreen.setOnCloseScreen((r) ->{
                                Platform.runLater(()->{
                                    this.stage.close();
                                    this.purchaseStage.close();
                                    new MovieScreen().show(customer);
                                });
                            });
                        }
                    }
                    else {
                        redeemedGiftCardText.setVisible(true);
                    }
                    //implement gift card already redeemed

                }
                //implement invalid gift card
                else {
                    incorrectGiftCardText.setVisible(true);
                }
            }

            //Code to be implemented when successful and gift card not chosen
            else {
                String holderName = cardHolderField.getText().trim();
                String cardNumber = cardNumberField.getText().trim();
                if (!cardScreenLogic.isValidDetails(holderName, cardNumber)) {
                    new popUpScreen().show("Invalid/Incorrect card details given", "Transaction Error", Paint.valueOf("RED"));
                    return;
                }
                //Code to be implemented card is successful
                else {
                    int cost = purchaseTicketsLogic.done(); //TO BE RUN WHEN SUCCESSFUL PURCHASE ONLY. This confirms the tickets booked and uses the updated value for future bookings.
                    showing.addBooking(userBooking);
                    if (checkBox.isSelected()) {
                        System.out.println(cardHolderField.getText());
                        customer.setCard(cardHolderField.getText(), Integer.parseInt(cardNumberField.getText()));
                    }
                    if(currentCard != null) {
                        currentCard.setRedeemable(false);
                    }

                    //story point 24: receipt
                    //story point 24: receipt
                    popUpScreen receiptScreen = new popUpScreen();
                    receiptScreen.show(
                            "Receipt\n"+
                                    "Movie: " + userBooking.getShowing().getMovie().getName()+"\n"+
                                    "Time: " + userBooking.getShowing().getTime()+"\n" +
                                    "Screen size: " + userBooking.getShowing().getScreenSize()+"\n"+
                                    "Booking ID: " + userBooking.getBookingID()+"\n"+
                                    "Seat position: " + userBooking.getSeatPosition()+"\n"+
                                    "Tickets booked: " + cost +"\n" +
                                    "Price: " + price + "\n"

                    );
                    receiptScreen.setOnClose((r) ->{
                        Platform.runLater(()->{
                            this.stage.close();
                            this.purchaseStage.close();
                            new MovieScreen().show(customer);
                        });
                    });
                    receiptScreen.setOnCloseScreen((r) ->{
                        Platform.runLater(()->{
                            this.stage.close();
                            this.purchaseStage.close();
                            new MovieScreen().show(customer);
                        });
                    });
                }
            }
        });





        cancelButton.setOnMousePressed((e)->{
            Transaction cancelledTransaction = new Transaction("Transaction cancelled", LocalDateTime.now(),this.customer);
            Cinema.addTransaction(cancelledTransaction);
            if(currentCard != null) {
                currentCard.setRedeemable(true);
            }
            Platform.runLater(() ->{
               stage.close();
            });
        });

        pane.setOnMouseMoved((e)->{
            timer.restart();
        });

    }

}
