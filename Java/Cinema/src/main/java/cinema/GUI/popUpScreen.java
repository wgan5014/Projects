package cinema.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class popUpScreen {
    VBox vbox = new VBox(20);
    Scene scene;
    Stage stage = new Stage();
    Button closeBtn = nodeCreator.createButton("Close");

    public void show(String inputText)
    {
        show(inputText, "");
    }

    public void show(String inputText, String title)
    {
        show(inputText,title, Paint.valueOf("black"));
    }

    public void show(String inputText, String title, Paint colour)
    {
        Text input = nodeCreator.createText(inputText, 0 , 0);
        input.setFill(colour);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20,50,20,50));
        scene = new Scene(vbox);
        createNodes(input);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void createNodes(Text input)
    {
        List<Node> nodes = new ArrayList<Node>();
        input.setTextAlignment(TextAlignment.CENTER);
        nodes.add(input);
        closeBtn.setAlignment(Pos.BOTTOM_CENTER);
        //closeBtn.setLayoutY(input.getLayoutBounds().getHeight() + 50);
        closeBtn.setOnMouseClicked(event -> stage.close());
        nodes.add(closeBtn);
        vbox.getChildren().addAll(nodes);
    }

    public Stage getStage(){return this.stage; }

    public void setOnClose(EventHandler<? super MouseEvent> event)
    {
        closeBtn.setOnMousePressed(event);

    }

    public void setOnCloseScreen(EventHandler<WindowEvent> event) {
        stage.setOnCloseRequest(event);
    }

}
