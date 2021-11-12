package cinema.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import cinema.*;

public class CinemaTest {
    @Test
    void testRemoveStaff(){
        assertFalse(Cinema.removeStaff("abc"));
    }
    @Test
    void testCardValid(){
        assertTrue(Cinema.cardValid("qwertyuiopasdfghjkllkjhgfd"));
    }
    @Test
    void testGetMovieOfWeek(){
        assertTrue(Cinema.getMovieOfWeek().size()==0);
    }
    @Test
    void testGetShowingOfWeek(){
        assertTrue(Cinema.getShowingsOfWeek().size()==0);
    }

}
