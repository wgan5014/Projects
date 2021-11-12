package cinema;

import java.time.LocalDateTime;

public class Transaction {

    int transactionID;
    String transactionState;
    LocalDateTime date;
    Customer customer = null;

    public Transaction(String transState, LocalDateTime date){
        this.transactionState = transState;
        this.date = date;
        int max = 9999999;
        int min = 1000000;
        int id = (int) ((Math.random() * (max - min)) + min);
        transactionID = id;
        while(Cinema.getTransaction(id) != null){
            id = (int) ((Math.random() * (max - min)) + min);
            transactionID = id;
        }
    }

    public Transaction(int ID,String transState, LocalDateTime date, Customer user){
        this.transactionID = ID;
        this.transactionState = transState;
        this.date = date;
        this.customer = user;
    }

    public Transaction(String transactionState, LocalDateTime now, Customer customer) {
        this(transactionState, now);
        this.customer = customer;
    }

    public int getTransactionID(){ return transactionID; }

    public String getTransactionState() {
        return transactionState;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Customer getCustomer() { return customer; }

    public void setTransactionState(String transactionState){ this.transactionState = transactionState; }

    public void setCustomer(Customer customer){ this.customer = customer; }

}
