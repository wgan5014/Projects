package cinema.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import cinema.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ShowingTest {
    List<String> cast2 = new ArrayList<>();
    Movie m2 = new Movie("movie1","a",cast2,"R","hi", LocalDate.now());
    LocalDateTime dt = LocalDateTime.of(2021, Month.DECEMBER, 2, 18, 00, 00);
    Showing s = new Showing(1,m2,dt,"bronze","Town Hall");

    @Test
    void testGetShowingID(){
        assertEquals(1,s.getShowingID());
    }

    @Test
    void testGetMovie(){
        assertEquals(m2,s.getMovie());
    }

    @Test
    void testGetTime(){
        LocalDateTime aDateTime = LocalDateTime.of(2021, Month.DECEMBER, 2, 18, 00, 00);
        assertEquals(aDateTime,s.getTime());
    }

    @Test
    void testGetScreenSize(){
        assertEquals("bronze",s.getScreenSize());
    }

    @Test
    void testGetBookings(){
        List<Booking> bookings = new ArrayList<Booking>();
        assertEquals(bookings,s.getBookings());
    }

    @Test
    void testGetNumberOfBookings(){
        assertEquals(0,s.getNumberOfBookings());
    }

    @Test
    void testAddBooking(){
        CreditCard cc = new CreditCard("a",1927452);
        Customer c = new Customer("a","a",cc);
        Booking b = new Booking(352,c,s,"f");
        s.addBooking(b);
        assertEquals(1,s.getNumberOfBookings());
    }


}
