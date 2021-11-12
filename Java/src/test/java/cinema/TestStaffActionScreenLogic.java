package cinema;

import cinema.GUILogic.StaffActionScreenLogic;
import cinema.Cinema;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TestStaffActionScreenLogic {

	Movie m;
	Scanner sc = null;

	@BeforeEach
	public void setMovie() {
		// create the movie variable
		List<String> cast = new ArrayList<>();
		cast.add("Tyler");
		Movie m1 = new Movie(
			1,
			"a",
			"a",
			cast,
			"G",
			"a",
			LocalDate.of(2022,10,10)
		);
		// add the movie
		Cinema.addMovie(m1);
		// set the movie
		m = m1;
	}

	@AfterEach
	public void clear() {
		Cinema.clearMovies();
	}

	// test if the file is written correctly
	@Test
	public void testGenerateMovieReport() {
		// call the method
		String s2 = StaffActionScreenLogic.generateMovieReport();
		assertTrue(s2.equals("movieslist.txt"));
		try {
			sc = new Scanner(new File(s2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		// header
		assertTrue(sc.nextLine().strip().equals("cast,director,name,synopsis,rating,movieID"));
		// the movie data
		assertTrue(sc.nextLine().strip().equals("Tyler,a,a,a,G,1"));
		sc.close();
	}
	
	@Test
	public void testGenerateShowingCSV() {
				// create showing for a movie
		// showing id, date, screen type
		Showing s = new Showing(1, m, LocalDateTime.of(2022, 10, 11,12,12), "Gold","Redfern");
		
		// add a booking for the showing
		// booking id, customer, showing, seat-type
		Booking b = new Booking(1, new Customer("bob", "bob", new CreditCard("bob",123)), s, "child");
		
		// add booking to the showing
		s.addBooking(b);


		// add showing into the cinema
		Cinema.addShowing(s);
		
		// generate the booking report
		StaffActionScreenLogic.generateBookingReport();
		//assertTrue(s2 != null);
		
		//assertTrue(s2.equals("showingslist.csv"));
		try {
			sc = new Scanner(new File("showingslist.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		// header
		assertTrue(sc.nextLine().strip().equals("show_id,num_bookings,num_seats_booked,num_seats_available\n"));
		// showing data
		assertTrue(sc.nextLine().strip().equals("1,1,1,44")); // num_seats_availible = 44 b/c only 1 ticket made & 45 seats per theatre
		sc.close();
	}
}

