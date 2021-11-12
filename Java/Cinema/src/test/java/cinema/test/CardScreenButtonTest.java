
package cinema.test;

import static org.junit.jupiter.api.Assertions.*;


import cinema.GUILogic.CardScreenLogic;

import cinema.IO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CardScreenButtonTest {
    @BeforeAll
    static void load(){
        IO.load();
    }

    @AfterAll
    static void deleteFiles(){
        IO.deleteFiles();
    }

    @Test
    void testCardValidation(){
        CardScreenLogic cardScreenLogic = new CardScreenLogic();
        String[] validDetails1 = {"Donald","23858"};
        String[] validDetails2 = {"Ruth","55134"};
        String[] invalidNameDetails = {"Ryan","23858"};
        String[] invalidNumberDetails1 = {"Christine","35719"};
        String[] invalidNumberDetails2 = {"Christine","3571wad7"};

        assertTrue(cardScreenLogic.isValidDetails(validDetails1[0],validDetails1[1]));
        assertTrue(cardScreenLogic.isValidDetails(validDetails2[0],validDetails2[1]));
        assertFalse(cardScreenLogic.isValidDetails(invalidNameDetails[0],invalidNameDetails[1]));
        assertFalse(cardScreenLogic.isValidDetails(invalidNumberDetails1[0],invalidNumberDetails1[1]));
        assertFalse(cardScreenLogic.isValidDetails(invalidNumberDetails2[0],invalidNumberDetails2[1]));
    }

    @Test
    void testToggleName() {
        CardScreenLogic cardScreenLogic = new CardScreenLogic();
        assertFalse(cardScreenLogic.getName());
        cardScreenLogic.toggleName();
        assertTrue(cardScreenLogic.getName());
        cardScreenLogic.toggleName();
        assertFalse(cardScreenLogic.getName());
    }

    @Test
    void testToggleNum() {
        CardScreenLogic cardScreenLogic = new CardScreenLogic();
        assertFalse(cardScreenLogic.getNum());
        cardScreenLogic.toggleNum();
        assertTrue(cardScreenLogic.getNum());
        cardScreenLogic.toggleNum();
        assertFalse(cardScreenLogic.getNum());
    }

    @Test
    void testToggleGift() {
        CardScreenLogic cardScreenLogic = new CardScreenLogic();
        assertFalse(cardScreenLogic.getGift());
        cardScreenLogic.toggleGift();
        assertTrue(cardScreenLogic.getGift());
        cardScreenLogic.toggleGift();
        assertFalse(cardScreenLogic.getGift());
    }

    @Test
    void testCheckEnableButton() {
        CardScreenLogic cardScreenLogic = new CardScreenLogic();
        assertFalse(cardScreenLogic.checkEnableButton());
        cardScreenLogic.toggleGift();
        assertTrue(cardScreenLogic.checkEnableButton());
        cardScreenLogic.toggleGift();
        assertFalse(cardScreenLogic.checkEnableButton());
        cardScreenLogic.toggleName();
        assertFalse(cardScreenLogic.checkEnableButton());
        cardScreenLogic.toggleNum();
        assertTrue(cardScreenLogic.checkEnableButton());
        cardScreenLogic.toggleName();
        assertFalse(cardScreenLogic.checkEnableButton());
    }
}