package cinema;

import cinema.GUILogic.PurchaseTicketsLogic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Showing {
    int showingID;
    Movie movie;
    LocalDateTime time;
    String screenSize;
    HashMap<Integer, Booking> bookings;
    int ticketsBooked;
    int theatreID;
    PurchaseTicketsLogic purchaseTicketsLogic;
    String location;


    public Showing(Movie movie, LocalDateTime time, String screenSize,String location)
    {
        this.showingID = (int) Math.round(Math.random() * 1000000);
        this.movie = movie;
        this.time = time;
        this.screenSize = screenSize;
        this.bookings = new HashMap<>();
        this.purchaseTicketsLogic = new PurchaseTicketsLogic();
        this.location = location;
    }

    public Showing(int showingID, Movie movie, LocalDateTime time, String screenSize,String location)
    {
        this.showingID = showingID;
        this.movie = movie;
        this.time = time;
        this.screenSize = screenSize;
        this.bookings = new HashMap<>();
        this.purchaseTicketsLogic = new PurchaseTicketsLogic();
        this.location = location;
    }

    public PurchaseTicketsLogic getPurchaseTicketsLogic() {
        return purchaseTicketsLogic;
    }
    public int getShowingID() {
        return showingID;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<Booking>();
        for(int id : this.bookings.keySet()){
            bookings.add(this.bookings.get(id));
        }
        return bookings;
    }

    public void addBooking(Booking booking){
        if(!bookings.containsKey(booking.getBookingID())){
            bookings.put(booking.getBookingID(), booking);
        }
        this.ticketsBooked += booking.getNumOfTickets(); // added 23/10/2021 - wgan5014
    }

	// meth add 23/10 - w5014
    public int getNumberOfBookings() {
        return this.bookings.size();
    }

	// method added 23/10/2021 - wgan5014
	public int getNumOfTickets() {
		return this.ticketsBooked;
	}


	// meth add 23/10 - w5014
	public int getShowingTheatreID() {
		return theatreID;
	}

    public String getLocation(){ return this.location; }

    public void setLocation(String location){ 
        this.location = location;
    }
}
