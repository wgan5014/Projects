package cinema.GUILogic;

import cinema.Cinema;
import cinema.CreditCard;

public class CardScreenLogic {
    private boolean checkName = false;
    private boolean checkNum = false;
    private boolean checkGift = false;

    public boolean isValidDetails(String holderName, String numberText){
        try{
            int cardNumber = Integer.parseInt(numberText);
            CreditCard card = Cinema.getCard(cardNumber);

            if(card == null) {
                System.out.println("no card error");
                return false;
            }
            if(!card.getCardHolder().equalsIgnoreCase(holderName)){
                System.out.println("holder found no error");
                return false;
            }
        }catch(NumberFormatException nfe){
            System.out.println("exception error");
            return false;
        }
        return true;
    }

    public boolean checkEnableButton() {
        return (checkName && checkNum) || checkGift;
    }

    public void toggleName() {
        checkName = !checkName;
    }

    public void toggleNum() {
        checkNum = !checkNum;
    }

    public void toggleGift() {
        checkGift = !checkGift;
    }

    public boolean getName() {
        return checkName;
    }

    public boolean getNum() {
        return checkNum;
    }

    public boolean getGift() {
        return checkGift;
    }
}
