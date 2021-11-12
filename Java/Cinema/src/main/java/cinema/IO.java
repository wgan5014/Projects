package cinema;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class IO {

    public static final String FOLDER_PATH = "./data";
    public static final File dataFolder = new File(FOLDER_PATH);

    //region Loading and Saving methods
    public static void load() {
        JSONObject jObject =  getData();
        if(jObject == null) {
            System.out.println("Data loading failed!");
            return;
        }

        loadMovies(jObject);
        loadCreditCards();
        loadShowings(jObject);
        loadCustomers(jObject);
        loadBookings(jObject);
        loadTransactions(jObject);
        loadGiftCards(jObject);
        loadStaff(jObject);

    }

    public static void save(){
        JSONObject saveJSON = new JSONObject();
        saveJSON.put("movies", moviesToJSON());
        saveJSON.put("customers", customersToJSON());
        saveJSON.put("showings",showingsToJSON());
        saveJSON.put("bookings", bookingsToJSON());
        saveJSON.put("staff", staffToJSON());
        saveJSON.put("transactions", transactionsToJSON());
        saveJSON.put("giftcards", giftCardsToJSON());
        try {
            writeToFile("data.json", saveJSON.toString(3));
        }
        catch (IOException e){
            System.out.println("Failed to write to data.json file");
            e.printStackTrace();
        }
    }
    //endregion

    //region Movie Data
    private static List<JSONObject> moviesToJSON(){
        List<JSONObject> moviesList = new ArrayList<>();
        for(Movie movie : Cinema.getMovies()){
            JSONObject movieJSON = new JSONObject();
            movieJSON.put("cast",movie.getCast());
            movieJSON.put("director", movie.getDirector());
            movieJSON.put("name",movie.getName());
            movieJSON.put("synopsis",movie.getSynopsis());
            movieJSON.put("rating",movie.getClassification());
            movieJSON.put("movieID",movie.getMovieID());
            movieJSON.put("releaseDate",toJSONDateFormat(movie.getReleaseDate()));
            moviesList.add(movieJSON);
        }
        return moviesList;
    }


    private static void loadMovies(JSONObject jObject){
        JSONArray movieJson = (JSONArray) jObject.get("movies");
        for(Object o : movieJson){
            JSONObject movie = (JSONObject) o;
            String name = (String) movie.get("name");
            String director = (String) movie.get("director");
            String synopsis = (String) movie.get("synopsis");
            int id = (int) movie.get("movieID");
            List<String> castList = new ArrayList<>();
            for(Object c : (JSONArray) movie.get("cast")){
                String castMember = (String) c;
                castList.add(castMember);
            }
            String rating = (String) movie.get("rating");
            String releaseString = (String) movie.get("releaseDate");
            LocalDate releaseDate = jsonFormatToLocalDate(releaseString);
            Movie m = new Movie(id,name,director,castList,rating,synopsis,releaseDate);
            Cinema.addMovie(m);
        }
    }
    //endregion

    //region Booking Data
    private static List<JSONObject> bookingsToJSON(){
        List<JSONObject> bookingsList = new ArrayList<>();
        for(Showing s : Cinema.getShowings()){
            for(Booking b : s.getBookings()){
                JSONObject jBooking = new JSONObject();
                jBooking.put("bookingID",b.getBookingID());
                if(b.getUser() != null)
                jBooking.put("user",b.getUser().getUsername());
                jBooking.put("showingID",s.getShowingID());
                JSONObject seats = new JSONObject();
                seats.put("child",b.getSeatTypes().get("child"));
                seats.put("student",b.getSeatTypes().get("student"));
                seats.put("adult",b.getSeatTypes().get("adult"));
                seats.put("senior",b.getSeatTypes().get("senior"));
                jBooking.put("seatTypes",seats);
                jBooking.put("seatPosition",b.getSeatPosition());
                bookingsList.add(jBooking);
            }
        }
        return bookingsList;
    }

    private static void loadBookings(JSONObject jObject){
        JSONArray bookingsArray = (JSONArray) jObject.get("bookings");
        for(Object o : bookingsArray){
            JSONObject booking = (JSONObject) o;
            int id = (int) booking.get("bookingID");
            Customer customer =  Cinema.getCustomer((String) booking.get("user"));
            Showing showing = Cinema.getShowing((int) booking.get("showingID"));
            JSONObject seatTypes = (JSONObject) booking.get("seatTypes");
            String seatPosition = (String) booking.get("seatPosition");
            Booking b = new Booking(id,customer,showing,seatPosition);
            b.addSeats("child", (int) seatTypes.get("child"));
            b.addSeats("student", (int) seatTypes.get("student"));
            b.addSeats("adult", (int) seatTypes.get("adult"));
            b.addSeats("senior", (int) seatTypes.get("senior"));
            if(showing == null){
                System.out.println("Could not find showing: " + (int) booking.get("showingID"));
            }
            else{
                showing.addBooking(b);
            }
        }
    }
    //endregion

    //region Customer Data
    private static List<JSONObject> customersToJSON(){
        List<JSONObject> customerList = new ArrayList<>();
        for(Customer customer : Cinema.getCustomers()){
            JSONObject c = new JSONObject();
            c.put("username",customer.getUsername());
            c.put("password",customer.getPassword());
            if( customer.getCard() != null ){
                c.put("card", customer.getCard().getCardNumber());
            }
            customerList.add(c);
        }
        return customerList;
    }

    private static void loadCustomers(JSONObject jObject){
        JSONArray customersArray = (JSONArray) jObject.get("customers");
        for(Object o : customersArray){
            JSONObject customer = (JSONObject) o;
            String username = (String) customer.get("username");
            String password = (String) customer.get("password");
            CreditCard creditCard = null;
            if(customer.has("card")) {
                if (customer.get("card") instanceof Integer) {
                    creditCard = Cinema.getCard((int) customer.get("card"));
                }
            }
            Customer c = new Customer(username,password, creditCard);
            Cinema.addAccount(c);
        }
    }
    //endregion

    //region Showing Data
    private static List<JSONObject> showingsToJSON(){
        List<JSONObject> showingList = new ArrayList<>();
        for(Showing showing : Cinema.getShowings()){
            if(showing.getMovie() == null) {
                continue;
            }
            JSONObject s = new JSONObject();
            s.put("showingID",showing.getShowingID());
            s.put("movieID",showing.getMovie().getMovieID());
            s.put("date", toJSONDateFormat(showing.getTime()));
            s.put("screen", showing.getScreenSize());
            s.put("location", showing.getLocation());
            showingList.add(s);
        }
        return showingList;
    }

    private static void loadShowings(JSONObject jObject){
        JSONArray showingsJSON = (JSONArray) jObject.get("showings");
        for(Object o : showingsJSON){
            JSONObject showing = (JSONObject) o;
            int id = (int) showing.get("showingID");
            Movie movie = Cinema.getMovie((int) showing.get("movieID"));
            String dateString = (String) showing.get("date");
            LocalDateTime dateObj = jsonFormatToLocalDateTime(dateString);
            String screenSize = (String) showing.get("screen");
            String location = (String) showing.get("location");
            Showing s = new Showing(id,movie,dateObj,screenSize,location);
            Cinema.addShowing(s);
        }
    }
    //endregion

    //region Staff Data
    private static List<JSONObject> staffToJSON(){
        List<JSONObject> staffList = new ArrayList<>();
        for(Staff staff : Cinema.getStaff()){
            JSONObject s = new JSONObject();
            s.put("isManager", staff.isManager());
            s.put("username", staff.getUsername());
            s.put("password", staff.getPassword());
            staffList.add(s);
        }
        return staffList;
    }

    private static void loadStaff(JSONObject jObject){
        JSONArray staffJSON = (JSONArray) jObject.get("staff");
        for(Object o : staffJSON){
            JSONObject member = (JSONObject) o;
            boolean isManager = (boolean) member.get("isManager");
            String username = (String) member.get("username");
            String password = (String) member.get("password");
            Staff staffMember = new Staff(isManager, username, password);
            Cinema.addStaff(staffMember);
        }
    }
    //endregion

    //region Transaction Data
    private static List<JSONObject> transactionsToJSON(){
        List<JSONObject> transList = new ArrayList<>();
        for(Transaction transaction : Cinema.getTransactions()){
            JSONObject s = new JSONObject();
            s.put("id", transaction.getTransactionID());
            s.put("transactionState", transaction.getTransactionState());
            s.put("date", toJSONDateFormat(transaction.getDate()));
            if(transaction.getCustomer() != null){
                s.put("customerUsername", transaction.getCustomer().getUsername());
            }
            transList.add(s);
        }
        return transList;
    }

    private static void loadTransactions(JSONObject jObject){
        JSONArray transactionArray = (JSONArray) jObject.get("transactions");
        for(Object o : transactionArray){
            JSONObject transaction = (JSONObject) o;
            int transactionID = (int) transaction.get("id");
            String transactionState = (String) transaction.get("transactionState");
            String dateString = (String) transaction.get("date");
            LocalDateTime dateObj = jsonFormatToLocalDateTime(dateString);
            Customer customer = null;
            if(transaction.has("customerUsername"))
                customer = Cinema.getCustomer(String.valueOf(transaction.get("customerUsername")));
            Transaction t = new Transaction(transactionID,transactionState, dateObj,customer);
            Cinema.addTransaction(t);
        }
    }
    //endregion

    //region GiftCard Data
    private static List<JSONObject> giftCardsToJSON(){
        List<JSONObject> cardsList = new ArrayList<>();
        for(GiftCard card : Cinema.getGiftCards()){
            JSONObject s = new JSONObject();
            s.put("cardNumber", card.getCardNumber());
            s.put("redeemable", card.isRedeemable());
            s.put("amount", card.getAmount());
            cardsList.add(s);
        }
        return cardsList;
    }

    private static void loadGiftCards(JSONObject jObject){
        JSONArray giftCardArray = (JSONArray) jObject.get("giftcards");
        for(Object o : giftCardArray){
            JSONObject giftCard = (JSONObject) o;
            String cardNumber = (String) giftCard.get("cardNumber");
            boolean isRedeemable = (boolean) giftCard.get("redeemable");
            double amount = ((Number) giftCard.get("amount")).doubleValue();
            GiftCard g = new GiftCard(cardNumber,isRedeemable,amount);
            Cinema.addGiftCard(g);
        }
    }
    //endregion

    //region CreditCard Data
    private static void loadCreditCards(){
        try {
            String jsonString = new String(Objects.requireNonNull(IO.class.getClassLoader().getResourceAsStream("credit_cards.json")).readAllBytes());
            JSONArray cardList = new JSONArray(jsonString);
            for (Object o : cardList){
                JSONObject card = (JSONObject) o;
                String name = (String) card.get("name");
                int number = Integer.parseInt((String) card.get("number"));
                CreditCard c = new CreditCard(name,number);
                Cinema.addCard(c);
            }
        }
        catch(IOException e){
            System.out.println("Could not load credit card details");
        }
    }
    //endregion

    //region Helper Methods
    private static String toJSONDateFormat(LocalDateTime inputDate){
        int year = inputDate.getYear();
        int month = inputDate.getMonthValue();
        int day = inputDate.getDayOfMonth();
        int hour = inputDate.getHour();
        int minute = inputDate.getMinute();
        return day + "/" + month + "/" + year + " " + hour + ":" + minute;
    }

    private static String toJSONDateFormat(LocalDate inputDate){
        int year = inputDate.getYear();
        int month = inputDate.getMonthValue();
        int day = inputDate.getDayOfMonth();
        return day + "/" + month + "/" + year;
    }

    private static LocalDateTime jsonFormatToLocalDateTime(String inputDate){
        String date = inputDate.split(" ")[0];
        String time = inputDate.split(" ")[1];
        int day = Integer.parseInt(date.split("/")[0]);
        int month = Integer.parseInt(date.split("/")[1]);
        int year = Integer.parseInt(date.split("/")[2]);
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        return LocalDateTime.of(year,month,day,hour,minute);
    }

    private static LocalDate jsonFormatToLocalDate(String inputDate){
        String date = inputDate.split(" ")[0];
        int day = Integer.parseInt(date.split("/")[0]);
        int month = Integer.parseInt(date.split("/")[1]);
        int year = Integer.parseInt(date.split("/")[2]);
        return LocalDate.of(year,month,day);
    }



    public static String read(String fileName) throws IOException{
        FileInputStream fis = new FileInputStream(FOLDER_PATH + "/" + fileName);
        String str = new String(fis.readAllBytes());
        fis.close();
        return str;
    }

    public static void writeToFile(String fileName, String textToWrite) throws IOException{
        writeToFile(fileName, textToWrite, false);
    }

    public static void writeToFile(String fileName, String textToWrite, boolean append) throws IOException{
        File f = new File(FOLDER_PATH + "/" + fileName);
        if (!f.exists()) {
            if (!f.createNewFile()) {
                throw new IOException("Could not not create '" + fileName + "' file");
            }
        }
        FileWriter fWriter = new FileWriter(f, append);
        fWriter.write(textToWrite);
        fWriter.close();
    }


    protected static JSONObject getData(){
        File folder = new File(FOLDER_PATH);
        if(!folder.exists()){
            if(!folder.mkdirs()){
                System.out.println("Could not initiate folder in: " + folder.getAbsolutePath());
                return null;
            }
        }

        try {
            String jsonString;
            if(new File(FOLDER_PATH + "/data.json").exists()) {
                jsonString = read("data.json");
            }
            else{
                jsonString = new String(getResourceAsStream("data.json"));
            }
            return new JSONObject(jsonString);
        }
        catch (IOException e){
            System.out.println("Could not initiate/read data.json file in: " + FOLDER_PATH);
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] getResourceAsStream(String fileName){
        try {
            InputStream i  = IO.class.getClassLoader().getResourceAsStream(fileName);
            byte[] b = Objects.requireNonNull(i).readAllBytes();
            i.close();
            return b;
        }
        catch (IOException e){
            return new byte[]{};
        }
    }
    public static void deleteFiles(){
        deleteFolder(dataFolder);
    }

    private static void deleteFolder(File directoryToBeDeleted){
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolder(file);
            }
        }
        directoryToBeDeleted.delete();
    }
    //endregion


}
