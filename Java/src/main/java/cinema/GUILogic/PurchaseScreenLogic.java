package cinema.GUILogic;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.List;

public class PurchaseScreenLogic {

    public int priceAdd(Text price, int[] prices, int x) {
        int value = Integer.parseInt(price.getText());
        value+= prices[x];
        return value;
    }

    public int priceSub(Text price, int[] prices, int x) {
        int value = Integer.parseInt(price.getText());
        if(value != 0) {
            value -= prices[x];
        }
        return value;
    }


    public String handleAdd(Text amount) {
        int value = Integer.parseInt(amount.getText()) + 1;
        return Integer.toString(value);
    }

    public String handleSub(Text amount) {
        int value = Integer.parseInt(amount.getText()) - 1;
        if(value < 0) {
            value += 1;
        }
        return Integer.toString(value);
    }
}
