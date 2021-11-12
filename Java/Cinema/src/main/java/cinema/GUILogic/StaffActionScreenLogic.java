
package cinema.GUILogic;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

import cinema.Transaction;
import cinema.IO;
import cinema.Movie;
import cinema.Showing;
import cinema.Cinema;

/*
* This role will also able to obtain two 
* reports (either csv or text file) upon logged in
* A list of the upcoming movies & shows that include the item details.
* A summary that includes number of bookings for each movie session as 
* well as how many seats have been booked and are available for each session.
*/
public class StaffActionScreenLogic {

	public static String generateMovieReport() {
		/*
		* for each movie in Cinema.getMovies()
		* write each attribute with commas separating each attr
		* return filename
		*/
		String filename = "movieslist.txt";
		
		// write the header
		try {
			IO.writeToFile(filename, "cast,director,name,synopsis,rating,movieID\n");
		} catch (IOException e) {
			System.out.println("Failed to write header to movie report file:");
			e.printStackTrace();
		}
		
		// write the data on each movie as comma-separated
		for (Movie movie : Cinema.getMovies()) {
			String line = String.format(
				"%s,%s,%s,%s,%s,%d\n",
				String.join(" ", movie.getCast()),
				movie.getDirector(),
				movie.getName(),
				movie.getSynopsis().replace(",", ""),
				movie.getClassification(),
				movie.getMovieID()
			);
			try {
				IO.writeToFile(filename, line, true);
			} catch (IOException e) {
				System.out.println("Failed to write line " + line + "to " + filename);
				e.printStackTrace();
			}
		}
		return filename;
	}
	
	public static String generateBookingReport() {
		/*
		* For each showing in Cinema.getShowings() do
		* write #bookings,#of setas booked,# of seats available in a session
		*/
		String filename = "showingslist.csv";
		
		// write the header
		try {
			IO.writeToFile(filename, "show_id,num_bookings,num_seats_booked,num_seats_available\n");
		} catch (IOException e) {
			System.out.println("Failed to write header to showings report file:");
			e.printStackTrace();
		}
		
		// write the data on each movie as comma-separated
		for (Showing s : Cinema.getShowings()) {
			// the number of seats booked
			
			String line = String.format(
				"%d,%d,%d,%d\n",
				s.getShowingID(),
				s.getBookings().size(),
				s.getNumOfTickets(),
				45 - s.getNumOfTickets()
			);
			try {
				IO.writeToFile(filename, line, true);
			} catch (IOException e) {
				System.out.println("Failed to write line " + line + "to " + filename);
				e.printStackTrace();
			}
		}
		return filename;
	}
	
	public static String getCancelledTransactions() {
		String filename = "transactions.csv";
		try {
			IO.writeToFile(filename, "date,time,reason\n");
		} catch (IOException e) {
			System.out.println("Failed to write header to transactions report file:");
			e.printStackTrace();
		}
		String l = null;
		
		// foreach transaction
		for (Transaction t : Cinema.getTransactions()) {
			// if transaction wasn't successful
			if (!t.getTransactionState().equals("Successful")) {
				// write to file
				// date | time | reason
				l = String.format(
					"%s,%s,%s\n",
					t.getDate().toLocalDate().toString(), // date string
					t.getDate().toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString(), // time str
					t.getTransactionState() // reason of cancellation
				);
				try {
					IO.writeToFile(filename, l, true);
				} catch (IOException e) {
					System.out.println("Failed to log incomplete transaction ID: " + t.getTransactionID());
					System.out.println("Could not write line " + l + " to " + filename);
					e.printStackTrace();
				}
			}
		}
		return filename;
	}
}






