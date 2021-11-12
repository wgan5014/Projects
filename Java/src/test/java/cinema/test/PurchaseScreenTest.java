
package cinema.test;

import static org.junit.jupiter.api.Assertions.*;

import cinema.GUI.nodeCreator;
import cinema.GUILogic.PurchaseScreenLogic;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import cinema.*;

public class PurchaseScreenTest {
    @Test
    void testPriceAdd() {
        PurchaseScreenLogic purchaseScreenLogic = new PurchaseScreenLogic();
        int[] prices = {10, 20, 30, 40};
        Text price = nodeCreator.createText("0", 0, 0);
        assertEquals(price.getText(), "0");
        assertEquals(purchaseScreenLogic.priceAdd(price, prices, 0), 10);

    }

    @Test
    void testPriceSub() {
        PurchaseScreenLogic purchaseScreenLogic = new PurchaseScreenLogic();
        int[] prices = {10, 20, 30, 40};
        Text price = nodeCreator.createText("10", 0, 0);
        assertEquals(price.getText(), "10");
        assertEquals(purchaseScreenLogic.priceSub(price, prices, 0), 0);
        assertEquals(purchaseScreenLogic.priceSub(price, prices, 0), 0);

    }

    @Test
    void testAdd() {
        PurchaseScreenLogic purchaseScreenLogic = new PurchaseScreenLogic();
        Text amount = nodeCreator.createText("0", 0, 0);
        assertEquals(purchaseScreenLogic.handleAdd(amount), "1");
    }

    @Test
    void testSub() {
        PurchaseScreenLogic purchaseScreenLogic = new PurchaseScreenLogic();
        Text amount = nodeCreator.createText("2", 0, 0);
        assertEquals(purchaseScreenLogic.handleSub(amount), "1");
        amount = nodeCreator.createText("0", 0, 0);
        assertEquals(purchaseScreenLogic.handleSub(amount), "0");
    }
}