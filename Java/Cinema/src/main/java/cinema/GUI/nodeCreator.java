package cinema.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class nodeCreator {

    public static Text createText(String text, double x, double y) {
        return createText(text, x, y, 18);
    }

    public static Text createText(String text, double x, double y, int size) {
         Text t = new Text(x, y, text);
         t.setStyle("-fx-font-size: "+size);
         return t;
    }

    public static Button createButton(String text) {
        return new Button(text);
    }

    public static Button createButton(String text, double x, double y) {
        Button btn = new Button(text);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        return btn;
    }

    public static TextField createTextField(String promptText, double x, double y) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFocusTraversable(true);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        return textField;
    }

    public static PasswordField createPassword(String promptText, double x, double y) {
        PasswordField passwd = new PasswordField();
        passwd.setPromptText(promptText);
        passwd.setFocusTraversable(true);
        passwd.setLayoutX(x);
        passwd.setLayoutY(y);
        return passwd;
    }

    public static DatePicker createDatePicker(String promptText) {
        DatePicker date = new DatePicker();
        date.setPromptText(promptText);
        return date;
    }

    public static DatePicker createDatePicker(String promptText, double x, double y) {
        DatePicker date = new DatePicker();
        date.setPromptText(promptText);
        date.setLayoutX(x);
        date.setLayoutY(y);
        return date;
    }

}
