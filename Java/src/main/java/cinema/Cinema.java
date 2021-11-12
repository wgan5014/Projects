
package cinema;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Cinema {
	/* willbranch -- merge conf.
  private static HashMap<String, Customer> customers = new HashMap<String, Customer>();
	static List<Movie> movies = new ArrayList<Movie>();

	public Cinema() {
		//customers = new HashMap<String, Customer>();
	}
  */
  
  /* master branch */
	private static HashMap<String, Customer> customers = new HashMap<>();
	public static HashMap<Integer,Movie> movies = new HashMap<>();
	public static HashMap<Integer,Showing> showings = new HashMap<>();
	static HashMap<Integer, CreditCard> creditCards = new HashMap<>();
	static HashMap<String,GiftCard> giftCards = new HashMap<>();
	static HashMap<Integer,Transaction> transactions = new HashMap<>();

	static HashMap<String, Staff> staff = new HashMap<>();


	public static void addAccount(Customer customer) {
		customers.put(customer.getUsername(), customer);
	}

	public static Customer getCustomer(String username) {
		return customers.getOrDefault(username,null);
	}

	public static Movie getMovie(int movieID){
		return movies.getOrDefault(movieID, null);
	}
	
	public static void removeMovie(int movieID) {
		if (movies.containsKey(movieID)) {
			movies.remove(movieID);
		}
	}
	
	public static void clearMovies() {
		movies.clear();
	}

	public static void addMovie(Movie movie) {
		movies.put(movie.getMovieID(),movie);
	}

	public static void removeMovie(Movie movie) {
		movies.remove(movie.getMovieID());
	}

	public static void addCard(CreditCard c){
		creditCards.put(c.getCardNumber(),c);
	}

	public static CreditCard getCard(int cardNumber){
		return creditCards.getOrDefault(cardNumber,null);
	}

	public static void addShowing(Showing showing){
		showings.put(showing.getShowingID(), showing);
	}

	public static Showing getShowing(int showingID){
		return showings.getOrDefault(showingID, null);
	}

	public static void addGiftCard(GiftCard giftCard){
			giftCards.put(giftCard.getCardNumber(),giftCard);
	}

	public static GiftCard getGiftCard(String cardNumber){
		return giftCards.getOrDefault(cardNumber,null);
	}

	public static boolean addTransaction(Transaction transaction){
		if(transactions.containsKey(transaction.getTransactionID())) return false;
		transactions.put(transaction.getTransactionID(), transaction);
		return true;
	}

	public static Transaction getTransaction(int transactionID){
		return transactions.getOrDefault(transactionID,null);
	}
  
	public static List<Movie> getMovies(){ return new ArrayList<>(movies.values()); }


	public static List<Showing> getShowings(){ return new ArrayList<>(showings.values()); }

	public static void removeShowing(Showing showing) {
		showings.remove(showing.getShowingID());
	}

	//---27/10---
	public static LocalDate getNextMonday(){
		LocalDate day = LocalDate.now();
		//if today is monday, add one day so we can find the next monday.
		if(day.getDayOfWeek().toString().equals("MONDAY")){
			day = day.plusDays(1);
		}
		//find next monday
		while(! day.getDayOfWeek().toString().equals("MONDAY")){
			day = day.plusDays(1);
		}
		return day;
	}
	public static List<Showing> getShowingsOfWeek(){
		List<Showing> outputList = new ArrayList<>();
		List<Showing> allShowings = getShowings();
		for(Showing s: allShowings){
			//check if showing id between now and next monday
			if(s.getTime().compareTo(LocalDateTime.now())>=0  && s.getTime().toLocalDate().compareTo(getNextMonday())<0){
				outputList.add(s);
			}
		}
		return outputList;
	}
	public static List<Movie> getMovieOfWeek(){
		List<Movie> outputList = new ArrayList<>();
		Set<Movie> MovieSet = new HashSet<>();
		List<Showing> showingsThisWeek = getShowingsOfWeek();
		for(Showing s: showingsThisWeek){
			MovieSet.add(s.getMovie());
		}
		for(Movie m:MovieSet){
			outputList.add(m);
		}
		return outputList;
	}


	//----------------

	public static List<Customer> getCustomers(){ return new ArrayList<>(customers.values());}

	public static Boolean cardValid(String giftCardNumber) {
		for(GiftCard g: giftCards.values()) {
			if(Objects.equals(g.getCardNumber(), giftCardNumber)) {
				return false;
			}
		}
		return true;
	}

	public static List<CreditCard> getCreditCards(){return new ArrayList<>(creditCards.values());}

	public static List<GiftCard> getGiftCards(){return new ArrayList<>(giftCards.values());}

	public static void removeGiftCard(String number) {
		giftCards.remove(number);
	}
	public static List<Transaction> getTransactions(){return new ArrayList<>(transactions.values());}

	public static boolean addStaff(Staff staffMember){
		if (!staff.containsKey(staffMember.getUsername())) {
			staff.put(staffMember.getUsername(), staffMember);
			return true;
		}
		return false;
	}

	public static List<Staff> getStaff(){ return new ArrayList<>(staff.values());}

	public static List<Staff> getStaff(boolean includeManagers){
		return staff.values().stream()
				.filter((o) -> !(o.isManager() && !includeManagers))
				.collect(Collectors.toList());
	}
	
	public static boolean removeStaff(String usrname) {
		if (staff.containsKey(usrname)) {
			staff.remove(usrname);
			return true;
		}
		return false;
	}

	public static void removeAllCustomers() {
		customers.clear();
	}

}





