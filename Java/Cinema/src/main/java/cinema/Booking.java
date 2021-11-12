package cinema;

import java.util.HashMap;
import java.util.Random;

public class Booking {
    int bookingID;
    Showing showing;
    Customer user;
    String seatPosition;
    int numOfTickets;
    HashMap<String, Integer> seatTypes = new HashMap<String, Integer>();
    int seatsBooked;

    public Booking(int bookingID, Customer user, Showing showing, String seatPosition) {
        this.bookingID = bookingID;
        this.user = user;
        this.seatPosition = seatPosition;
        seatTypes.put("child",0);
        seatTypes.put("student",0);
        seatTypes.put("adult",0);
        seatTypes.put("senior",0);
        this.numOfTickets = this.getNumOfTickets();
    }

    public Booking(Customer user,Showing showing,String seatPosition) {
        this.bookingID = new Random().nextInt(99999);
        this.user = user;
        this.showing = showing;
        this.seatPosition = seatPosition;
        seatTypes.put("child",0);
        seatTypes.put("student",0);
        seatTypes.put("adult",0);
        seatTypes.put("senior",0);

        this.numOfTickets = this.getNumOfTickets();
    }

    public int getBookingID() {
        return bookingID;
    }

    public Customer getUser() {
        return user;
    }

    public String getSeatPosition() {
        return seatPosition;
    }

    public int getNumOfTickets() {
        int sum = 0;
        for(String seatType : this.seatTypes.keySet()){
            sum += seatTypes.get(seatType);
        }
        return sum;
    }

    public HashMap<String, Integer> getSeatTypes() {
        return seatTypes;
    }

    public void addSeats(String seatType, int numberOfSeats){
            this.seatTypes.put(seatType ,this.seatTypes.getOrDefault(seatType,0) + numberOfSeats);
    }

    public Showing getShowing() {
        return showing;
    }
}
