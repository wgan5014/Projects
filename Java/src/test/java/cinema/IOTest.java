package cinema;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IOTest {



    @AfterAll
    static void deleteEndFiles(){
        IO.deleteFiles();
    }

    @BeforeAll
    static void deleteStartFiles(){
        IO.deleteFiles();
    }


    @BeforeEach
    void createFiles(){
        IO.load();
        IO.save();
    }


    @Test
    public void createFilesTest(){
        File f = new File(IO.FOLDER_PATH + "/data.json");
        //Checks if data file has been made;
        assertTrue(f.exists());
    }

    @Test
    public void hasDataTest(){
        //Checks if once loading the data, there is actually data in the folder
        JSONObject jObject = IO.getData();

        //Checks jsonObject is not null;
        assertNotNull(jObject);

        //Checks the keys are in the jsonObject
        assertTrue(jObject.has("movies"));
        assertTrue(jObject.has("showings"));
        assertTrue(jObject.has("customers"));
        assertTrue(jObject.has("bookings"));
        assertTrue(jObject.has("transactions"));
    }

    @Test
    public void loadTest(){
        IO.load();
        //Check if movies have been loaded into the Cinema class
        assertNotNull(Cinema.getMovie(11111));
        assertNotNull(Cinema.getMovie(22222));

        //Check if correct Information is shown
        assertEquals("Avengers: Infinity War", Cinema.getMovie(11111).getName());
        assertEquals("Anthony Russo and Joe Russo", Cinema.getMovie(11111).getDirector());
        assertEquals("PG-13", Cinema.getMovie(11111).getClassification());

        //Check if showings have been loaded
        assertNotNull(Cinema.getShowing(4455));
        assertNotNull(Cinema.getShowing(1000));
        assertNotNull(Cinema.getShowing(9023));


        //Check if bookings have been loaded
        assertEquals(1, Cinema.getShowing(4455).getBookings().size(), "Only 1 booking expected for this showing");
        assertEquals(2,Cinema.getShowing(9023).getBookings().size(), "Only 2 booking expected for this showing");

        //Check if credit cards have been loaded (50 cards)
        assertEquals(50,Cinema.getCreditCards().size());

        //Check some valid cards
        assertNotNull(Cinema.getCard(59141));
        assertNotNull(Cinema.getCard(55134));
        assertNotNull(Cinema.getCard(23858));

        CreditCard vincentCard = new CreditCard("Vincent", 59141);
        CreditCard ruthCard = new CreditCard("Ruth", 55134);
        CreditCard donaldCard = new CreditCard("Donald", 23858);
        CreditCard bobCard = new CreditCard("Bob", 21312);

        //Check if initialized cards equal the loaded cards
        assertEquals(vincentCard, Cinema.getCard(59141));
        assertEquals(ruthCard, Cinema.getCard(55134));
        assertEquals(donaldCard, Cinema.getCard(23858));
        assertNotEquals(bobCard, Cinema.getCard(21312));

        //Check if giftcards have been loaded
        assertNotNull(Cinema.getGiftCard("1843271729181022GC"));
        assertNotNull(Cinema.getGiftCard("1234567890123457GC"));
        assertNotNull(Cinema.getGiftCard("1234567890123458GC"));
        assertNotNull(Cinema.getGiftCard("1234567890123459GC"));


        //Check the number of staff loaded
        int totalStaff = Cinema.getStaff(true).size();
        int numManagers = totalStaff - Cinema.getStaff(false).size();
        assertEquals(6, totalStaff);
        assertEquals(1, numManagers);
    }

    @Test
    void saveTest(){
        LocalDate current  = LocalDate.now();
        String m1Title = "m1 Movie";
        String m1Dir = "m1 dir";
        List<String> m1Cast = new ArrayList<String>();
        m1Cast.add("person1");
        m1Cast.add("person2");
        m1Cast.add("person3");
        LocalDate release1 = LocalDate.of(current.getYear(), current.getMonthValue(), current.getDayOfMonth());
        Movie m1 = new Movie(777,m1Title,m1Dir,m1Cast,"R+18","<fun story>", release1);

        String m2Title = "m2 Movie";
        String m2Dir = "m2 dir";
        List<String> m2Cast = new ArrayList<String>();
        m2Cast.add("guy1");
        m2Cast.add("guy2");
        Movie m2 = new Movie(888,m2Title,m2Dir,m2Cast,"G","<fun story>",release1);

        String m3Title = "m3 Movie";
        String m3Dir = "m3 dir";
        List<String> m3Cast = new ArrayList<String>();
        m3Cast.add("dude1");
        Movie m3 = new Movie(999,m3Title,m3Dir,m3Cast,"PG","<fun story>",release1);

        Cinema.addMovie(m1);
        Cinema.addMovie(m2);
        Cinema.addMovie(m3);

        IO.save();

        String read = "testRead";
        try{
            read = IO.read("data.json");
            assertNotEquals("testRead",read);
        }catch (IOException e){
            fail();
        }

        JSONObject json = new JSONObject(read);

        assertTrue(json.has("movies"));
        assertEquals(5, ((JSONArray) json.get("movies")).length());
    }



}
