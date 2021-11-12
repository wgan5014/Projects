package cinema;

public class GiftCard {

    String cardNumber;
    boolean isRedeemable;
    String state;
    double amount;

    public GiftCard(String cardNumber, boolean isRedeemable, double amount){
        this.cardNumber = cardNumber;
        this.isRedeemable = isRedeemable;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean isRedeemable() {
        return isRedeemable;
    }

    public String getState() {
        this.state = isRedeemable() ? "Not Redeemed" : "Redeemed";
        return state;
    }
    public void setRedeemable(boolean redeemable) {
        isRedeemable = redeemable;
    }

    public double getAmount() { return amount; }


}
