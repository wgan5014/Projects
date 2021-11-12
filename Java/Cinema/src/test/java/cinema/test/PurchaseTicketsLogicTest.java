
package cinema.test;

import static org.junit.jupiter.api.Assertions.*;


import cinema.GUILogic.PurchaseTicketsLogic;

import org.junit.jupiter.api.Test;



public class PurchaseTicketsLogicTest {

    @Test
    void testConstructor() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getFront(), 10);
        assertEquals(purchaseTicketsLogic.getBack(), 20);
        assertEquals(purchaseTicketsLogic.getMiddle(), 15);
    }

    @Test
    void testAddFront() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getFront(), 10);
        assertEquals(purchaseTicketsLogic.addFront(), 10);
        purchaseTicketsLogic.subFront();
        assertEquals(purchaseTicketsLogic.getFront(), 9);
        assertEquals(purchaseTicketsLogic.addFront(), 10);
    }

    @Test
    void testAddMiddle() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getMiddle(), 15);
        assertEquals(purchaseTicketsLogic.addMiddle(), 15);
        purchaseTicketsLogic.subMiddle();
        assertEquals(purchaseTicketsLogic.getMiddle(), 14);
        assertEquals(purchaseTicketsLogic.addMiddle(), 15);
    }

    @Test
    void testAddBack() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getBack(), 20);
        assertEquals(purchaseTicketsLogic.addBack(), 20);
        purchaseTicketsLogic.subBack();
        assertEquals(purchaseTicketsLogic.getBack(), 19);
        assertEquals(purchaseTicketsLogic.addBack(), 20);
    }

    @Test
    void testSubFront() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getFront(), 10);
        assertEquals(purchaseTicketsLogic.subFront(), 9);
        for(int i = 0; i < 9; i++ ) {
            purchaseTicketsLogic.subFront();
        }
        assertEquals(purchaseTicketsLogic.getFront(), 0);
        assertEquals(purchaseTicketsLogic.subFront(), 0);

    }

    @Test
    void testSubMiddle() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getMiddle(), 15);
        assertEquals(purchaseTicketsLogic.subMiddle(), 14);
        for(int i = 0; i < 14; i++ ) {
            purchaseTicketsLogic.subMiddle();
        }
        assertEquals(purchaseTicketsLogic.getMiddle(), 0);
        assertEquals(purchaseTicketsLogic.subMiddle(), 0);
    }

    @Test
    void testSubBack() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertEquals(purchaseTicketsLogic.getBack(), 20);
        assertEquals(purchaseTicketsLogic.subBack(), 19);
        for(int i = 0; i < 19; i++ ) {
            purchaseTicketsLogic.subBack();
        }
        assertEquals(purchaseTicketsLogic.getBack(), 0);
        assertEquals(purchaseTicketsLogic.subBack(), 0);
    }

    @Test
    void testDisableMinus() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertTrue(purchaseTicketsLogic.disableMinus("Front"));
        purchaseTicketsLogic.subFront();
        assertFalse(purchaseTicketsLogic.disableMinus("Front"));

        assertTrue(purchaseTicketsLogic.disableMinus("Middle"));
        purchaseTicketsLogic.subMiddle();
        assertFalse(purchaseTicketsLogic.disableMinus("Middle"));

        assertTrue(purchaseTicketsLogic.disableMinus("Back"));
        purchaseTicketsLogic.subBack();
        assertFalse(purchaseTicketsLogic.disableMinus("Back"));

        assertFalse(purchaseTicketsLogic.disableMinus(""));
    }

    @Test
    void testDisableAdd() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        assertFalse(purchaseTicketsLogic.disableAdd());
        for(int i = 0; i < 10; i++) {
            purchaseTicketsLogic.subFront();
        }
        assertEquals(purchaseTicketsLogic.getFront(), 0);
        assertTrue(purchaseTicketsLogic.disableAdd());
        purchaseTicketsLogic.addFront();
        assertFalse(purchaseTicketsLogic.disableAdd());

        for(int i = 0; i < 15; i++) {
            purchaseTicketsLogic.subMiddle();
        }
        assertEquals(purchaseTicketsLogic.getMiddle(), 0);
        assertTrue(purchaseTicketsLogic.disableAdd());
        purchaseTicketsLogic.addMiddle();
        assertFalse(purchaseTicketsLogic.disableAdd());


        for(int i = 0; i < 20; i++) {
            purchaseTicketsLogic.subBack();
        }
        assertEquals(purchaseTicketsLogic.getBack(), 0);
        assertTrue(purchaseTicketsLogic.disableAdd());
    }

    @Test
    void testReset() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        purchaseTicketsLogic.subFront();
        purchaseTicketsLogic.subBack();
        purchaseTicketsLogic.subMiddle();
        assertEquals(purchaseTicketsLogic.getFront(), 9);
        assertEquals(purchaseTicketsLogic.getMiddle(), 14);
        assertEquals(purchaseTicketsLogic.getBack(), 19);
        assertEquals(purchaseTicketsLogic.getFrontSeating(), 10);
        assertEquals(purchaseTicketsLogic.getMiddleSeating(), 15);
        assertEquals(purchaseTicketsLogic.getBackSeating(), 20);
        purchaseTicketsLogic.reset();
        assertEquals(purchaseTicketsLogic.getFront(), 10);
        assertEquals(purchaseTicketsLogic.getMiddle(), 15);
        assertEquals(purchaseTicketsLogic.getBack(), 20);
    }

    @Test
    void testDone() {
        PurchaseTicketsLogic purchaseTicketsLogic = new PurchaseTicketsLogic();
        purchaseTicketsLogic.subFront();
        purchaseTicketsLogic.subBack();
        purchaseTicketsLogic.subMiddle();
        assertEquals(purchaseTicketsLogic.getFront(), 9);
        assertEquals(purchaseTicketsLogic.getMiddle(), 14);
        assertEquals(purchaseTicketsLogic.getBack(), 19);
        assertEquals(purchaseTicketsLogic.getFrontSeating(), 10);
        assertEquals(purchaseTicketsLogic.getMiddleSeating(), 15);
        assertEquals(purchaseTicketsLogic.getBackSeating(), 20);
        purchaseTicketsLogic.done();
        assertEquals(purchaseTicketsLogic.getFrontSeating(), 9);
        assertEquals(purchaseTicketsLogic.getMiddleSeating(), 14);
        assertEquals(purchaseTicketsLogic.getBackSeating(), 19);
    }


}