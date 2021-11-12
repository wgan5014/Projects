package cinema;

public class CreditCard {
    String cardHolder;
    int cardNumber;

    public CreditCard(String cardHolder, int cardNumber) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof CreditCard))  return false;
        CreditCard c = (CreditCard) other;
        if(!c.getCardHolder().equals(this.cardHolder)) return false;
        if(c.getCardNumber() != this.cardNumber) return false;
        return true;
    }
}
