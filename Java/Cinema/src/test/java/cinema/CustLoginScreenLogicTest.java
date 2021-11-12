package cinema;

import cinema.GUILogic.CustLoginScreenLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustLoginScreenLogicTest {

    @BeforeEach
    void loadData(){
        IO.deleteFiles();
        IO.load();
    }

    @Test
    void incorrectUsername(){
        //Inputting incorrect username with random valid passwords
        assertFalse(CustLoginScreenLogic.checkCredentials("randomusername","shrekislife"));
        assertFalse(CustLoginScreenLogic.checkCredentials("randomusername1","prequelssucked"));
    }

    @Test
    void incorrectPassword(){
        //Inputting incorrect password with random valid usernames
        assertFalse(CustLoginScreenLogic.checkCredentials("shrekislove","incorrectpassword1"));
        assertFalse(CustLoginScreenLogic.checkCredentials("hanshotfirst","incorrectpassword2"));
    }

    @Test
    void incorrectDetails(){
        //Inputting both incorrect details
        assertFalse(CustLoginScreenLogic.checkCredentials("randomtester1","randomtesterpassword1"));
        assertFalse(CustLoginScreenLogic.checkCredentials("randomtester2","randomtesterpassword2"));
    }

    @Test
    void correctDetails(){
        //Check correct usernames and passwords
        assertTrue(CustLoginScreenLogic.checkCredentials("shrekislove","shrekislife"));
        assertTrue(CustLoginScreenLogic.checkCredentials("hanshotfirst","prequelssucked"));
        assertTrue(CustLoginScreenLogic.checkCredentials("nocardperson","iforgot"));
    }

}
